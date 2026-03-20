import java.io.File;
import java.io.FileNotFoundException;
public class Consumer implements Runnable {
  private CircularBuffer b;
  private File file;
  public Consumer(CircularBuffer b, String file){
    this.b = b;
    this.file = new File(file);
    new Thread(this, "Producer").start();
  }
  public void run() {
//whatever goes here
  }
}
