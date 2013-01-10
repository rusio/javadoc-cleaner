package javadoccleaner;

public class RemovalOfEmptyComments_B {

  int x;
  int y;

  int z;

  public int method() {
    new Runnable() {
      @Override
      public void run() {
      }
    };
    return 42;
  }
}
