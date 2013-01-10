package javadoccleaner;

/**
 * The line below does not start a new comment,
 *
 /**
 *
 * and therefore should not confuse the cleaner.
 */
public class HandlingOfIrregularComments_B {

  /**
   ****************************
   * some ASCII art should stay
   ****************************
   */
  int x;

  /**
   /**
   * ok
   */
  int y;

  /**
   * ok
   /**
   */
  int z;

  /**
   /**
   *
   /**
   */
  int w;

  /*
   *
   */
  int noJavadocCommentAbove;

}
