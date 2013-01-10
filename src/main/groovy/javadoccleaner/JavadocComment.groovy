package javadoccleaner

import java.util.regex.Pattern

class JavadocComment {

  public static Pattern JAVADOC_BEGIN = ~/^(\s*)(\/\*\*)(\s*)$/
  public static Pattern JAVADOC_END = ~/^(\s*)(\*\/)(\s*)$/

  public static Pattern EMPTY_STAR_LINE = ~/(\s*)(\*)(\s*)/

  public static Pattern EMPTY_RETURN_LINE = ~/(\s*)(\*)(\s*)(@return)(\s*)/
  public static Pattern EMPTY_PARAM_LINE = ~/(\s*)(\*)(\s*)(@param)(\s+)(\w[\w\d]*)(\s*)/
  public static Pattern EMPTY_THROWS_LINE = ~/(\s*)(\*)(\s*)(@throws)(\s+)(\w[\w\d]*)(\s*)/

  public static List<Pattern> EMPTY_TAGS = [
      EMPTY_RETURN_LINE,
      EMPTY_PARAM_LINE,
      EMPTY_THROWS_LINE,
  ]

  public static Pattern PARAM_LINE = ~/^\s*\*\s*@param\s*([^\s]+)\s*(.*?)\s*$/

  public static Pattern JAVA_FILE_NAME_LINE = ~/^(\s*)(\*)(\s*)(\w+\.java)(\s*)(<br\/?>)?(\s*)/

  String firstLine = null
  LinkedList<String> bodyLines = []
  String lastLine = null

  String joinToLowercase(String s) { s.replaceAll(/\s/, "").toLowerCase() }

  public void setFirstLine(String line) {
    firstLine = line
  }

  public void addBodyLine(String line) {
    bodyLines.add(line)
  }

  public void setLastLine(String line) {
    lastLine = line
  }

  public void removeNoise() {
    removeJavaFileNameLines()

    removeRepeatingParams()
    removeLeadingEmptyLines()
    removeTrailingEmptyLines()


    Iterator<String> iter = bodyLines.iterator()
    while (iter.hasNext()) {
      String bodyLine = iter.next()
      if (EMPTY_TAGS.any { it.matcher(bodyLine).matches() }) {
        iter.remove()
      }
    }
    if (isEmpty()) {
      firstLine = ""
      bodyLines = []
      lastLine = ""
    }
  }

  void removeRepeatingParams() {

    bodyLines.removeAll {
      def matcher = it =~ PARAM_LINE
      if (matcher.matches()) {
        def paramName = matcher.group(1)
        def description = matcher.group(2)
        if (joinToLowercase(description) == "the${joinToLowercase(paramName)}") {
          return true
        }
      }
      return false
    }
  }

  void removeLeadingEmptyLines() {
    while (bodyLines.size() > 0 && bodyLines.first() ==~ EMPTY_STAR_LINE) {
      bodyLines.removeFirst()
    }
  }

  void removeTrailingEmptyLines() {
    while (bodyLines.size() > 0 && bodyLines.last() ==~ EMPTY_STAR_LINE) {
      bodyLines.removeLast()
    }
  }

  void removeJavaFileNameLines() {
    bodyLines.removeAll { it ==~ JAVA_FILE_NAME_LINE }    
  }

  public boolean isEmpty() {
    return bodyLines.every { line -> line.matches(EMPTY_STAR_LINE) }
  }

  public String toString() {
    return firstLine + "\n" + bodyLines.join("\n") + "\n" + lastLine
  }

}
