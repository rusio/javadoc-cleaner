package javadoccleaner

class JavadocCleaner {

  public static void main(args) {
    if (args.size() == 0) {
      println "usage: JavadocCleaner <dir-or-file ...>"
    }

    def cleaner = new JavadocCleaner()
    args.each { cleaner.cleanup(new File(it)) }
  }

  public void cleanup(File source) {
    if (source.isDirectory()) {
      for (File child : source.listFiles()) {
        cleanup(child)
      }
    }
    else {
      if (source.getName().endsWith(".java")) {
        try {
          println "cleaning $source"
          source.setText(cleanup(source.getText("UTF-8")), "UTF-8")
        }
        catch (IOException e) {
          println "ERROR: ${e.message}"
        }
      }
    }
  }

  public String cleanup(String javaSourceCode) {
    BufferedReader reader = new BufferedReader(new StringReader(javaSourceCode))
    StringWriter target = new StringWriter()
    BufferedWriter writer = new BufferedWriter(target)
    cleanup(reader, writer)
    reader.close()
    writer.close()
    return target.toString()
  }

  // TODO: simplify this
  public void cleanup(BufferedReader source, BufferedWriter target) {
    JavadocComment detectedComment = null
    String currentLine;
    while ((currentLine = source.readLine()) != null) {
      //currentLine = trimTrailingWhitespace(currentLine)
      switch (currentLine) {
        case JavadocComment.JAVADOC_BEGIN:
          if (detectedComment == null) {
            detectedComment = new JavadocComment()
            detectedComment.setFirstLine(currentLine)
          }
          else {
            detectedComment.addBodyLine(currentLine)
          }
          break
        case JavadocComment.JAVADOC_END:
          if (detectedComment != null) {
            detectedComment.setLastLine(currentLine)
            detectedComment.removeNoise()
            if (!detectedComment.isEmpty()) {
              target.write(detectedComment.toString() + "\n")
            }
            detectedComment = null
          }
          else {
            target.write(currentLine + "\n")
          }
          break
        default:
          if (detectedComment != null) {
            detectedComment.addBodyLine(currentLine)
          }
          else {
            target.write(currentLine + "\n")
          }
          break
      }
    }
  }

  String trimTrailingWhitespace(String line) {
    return line.replaceAll(/\s+$/, "")
  }


}
