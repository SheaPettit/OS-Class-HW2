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
			int randomNum;
			while (!b.getDonePutting()) {
				randomNum = rand.nextInt(maxCopy) + 1;
				if(b.canGet(randomNum)) {
					outStream.write(b.get(randomNum));
				}
			}
			byte[] items = b.getRemaining();
			if(items != null)
				outStream.write(items);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
