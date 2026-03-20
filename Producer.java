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
	}

	public void run() {
		try (FileInputStream inStream = new FileInputStream(file)) {
			ThreadLocalRandom rand = ThreadLocalRandom.current();
			byte[] items = null;
			int randomNum;
			int numItems;
			while (true) {
				randomNum = rand.nextInt(maxCopy) + 1;
				if(b.canPut(randomNum)) {
					items = new byte[randomNum];
					numItems = inStream.read(items);
					if(numItems == -1) {
						break;
					}
					if(numItems < randomNum) {
						byte[] cutItems = new byte[numItems];
						for(int i = 0; i < numItems; i++)
							cutItems[i] = items[i];
						b.put(cutItems, numItems);
						break;
					}
					b.put(items, randomNum);
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
