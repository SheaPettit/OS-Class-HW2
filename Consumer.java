import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Consumer implements Runnable {
	private CircularBuffer b;
	private File file;
	private int maxCopy;

	public Consumer(CircularBuffer b, String file, int maxCopy) {
		this.b = b;
		this.file = new File(file);
		this.maxCopy = maxCopy;
		System.out.println("Max Copy: " + maxCopy);
		new Thread(this, "Producer").start();
	}

	public void run() {
		file.delete();
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try (FileOutputStream outStream = new FileOutputStream(file)) {
			ThreadLocalRandom rand = ThreadLocalRandom.current();
			Byte item;
			while (!b.getDonePutting()) {
				int randomNum = rand.nextInt(maxCopy) + 1;
				System.out.println("Random Number In Consumer: " + randomNum);
				for (int i = 0; i < randomNum; i++) {

					item = b.get();
					if (item == null)
						break;
					outStream.write(item);
				}
			}
			item = b.get();
			while(item != null) {
				outStream.write(item);
				item = b.get();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
