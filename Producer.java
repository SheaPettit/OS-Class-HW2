import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {
	private CircularBuffer b;
	private File file;
	private int maxCopy;

	public Producer(CircularBuffer b, String file, int maxCopy) {
		this.b = b;
		this.file = new File(file);
		this.maxCopy = maxCopy;
		new Thread(this, "Producer").start();
	}

	public void run() {
		try (FileInputStream inStream = new FileInputStream(file)) {
			ThreadLocalRandom rand = ThreadLocalRandom.current();
			int item = inStream.read();
			while (item > -1) {
				int randomNum = rand.nextInt(maxCopy) + 1;
				System.out.println("Random Number In Producer: " + randomNum);
				for (int i = 0; i < randomNum; i++) {
					if (item == -1)
						break;
					if (!b.put(Byte.valueOf((byte) item)))
						break;
					item = inStream.read();
				}
			}
			b.donePutting();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
