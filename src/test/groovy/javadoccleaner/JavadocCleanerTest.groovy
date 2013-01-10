package javadoccleaner

import org.junit.rules.TestName
import org.junit.*
import static org.junit.Assert.*

public class JavadocCleanerTest {

  JavadocCleaner cleaner
  String input
  String expectedOutput

  @Rule
  public static TestName testName = new TestName()

  @Before
  void setUp() {
    String baseName = testName.getMethodName().substring("test".length())
    def inputResource = getClass().getResource(baseName + "_A.java")
    if (inputResource == null) {
      fail("cannot find input resource for test: " + testName.getMethodName())
    }
    input = inputResource.getText().replace("_A", "")
    expectedOutput = getClass().getResource(baseName + "_B.java").getText().replace("_B", "")
    cleaner = new JavadocCleaner()
  }

  @After
  void tearDown() {
    assertEquals(expectedOutput, cleaner.cleanup(input))
  }


  @Test
  void testRemovalOfEmptyComments() {
  }

  @Ignore
  @Test
  void testCleanupOfSingleLineJavadocComments() {
    // TODO
//    input = "/***/"
//    expectedOutput = ""
//    assertEquals(expectedOutput, cleaner.cleanup(input))
//
//    input = "/**  */"
//    expectedOutput = ""
//    assertEquals(expectedOutput, cleaner.cleanup(input))    
  }

  @Test
  void testRemovalOfEmptyTags() {
  }

  @Test
  void testRemovalOfJavaFileName() {
  }
  
  @Test
  void testRemovalOfCompleteComment() {
  }

  @Test
  void testRemovalOfEmptyLeadingLines() {
  }

  @Test
  void testRemovalOfEmptyTrailingLines() {
  }

  @Test
  void testRemovalOfLeadingAndTrailingHtmlBrLines() {
  }

  @Test
  void testHandlingOfIrregularComments() {
  }

  // WEITER
  @Test
  void testRemovalOfRepeatingComments() {
  }
}
