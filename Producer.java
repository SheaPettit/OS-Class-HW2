import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
public class Producer implements Runnable {
  private CircularBuffer b;
  private File file;
  public Producer(CircularBuffer b, String file){
    this.b = b;
    this.file = new File(file);
    new Thread(this, "Producer").start();
  }
  public void run() {
    try (Scanner scan = new Scanner(file)){
    	int bufferSize = b.getSize();
    	ThreadLocalRandom rand = ThreadLocalRandom.current();
    	while(scan.hasNextByte()) {
    		for(int i = 0; i < rand.nextInt(bufferSize) + 1; i++){
    			
    		}
    	}
    } catch (FileNotFoundException e) {
		e.printStackTrace();
	}
  }
}
