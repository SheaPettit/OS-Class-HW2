import java.util.concurrent.Semaphore;

public class CircularBuffer {
	private Byte[] buffer;
	private int size;
	private Semaphore putSem;
	private Semaphore getSem;
	private int nextPut;
	private int nextGet;
	private boolean donePutting;

	public CircularBuffer(int bufferSize) {
		buffer = new Byte[bufferSize];
		putSem = new Semaphore(bufferSize);
		getSem = new Semaphore(0);
		nextPut = 0;
		nextGet = 0;
		size = bufferSize;
		System.out.println("Buffer Size: " + size);
	}

	public boolean put(Byte item) {
		try {
			putSem.acquire();
		} catch (InterruptedException e) {
			return false;
		}
		buffer[nextPut] = item;
		if (++nextPut >= size)
			nextPut = 0;
		getSem.release();
		System.out.println("Read byte from old file. NextPut: " + nextPut + " Get: " + getSem.availablePermits() + " Put: " + putSem.availablePermits());
		return true;
	}

	public Byte get() {
		try {
			getSem.acquire();
		} catch (InterruptedException e) {
			return null;
		}
		Byte returnItem = buffer[nextGet++];
		if (nextGet >= size)
			nextGet = 0;
		putSem.release();
		System.out.println("Wrote byte to new file. NextGet: " + nextGet + " Get: " + getSem.availablePermits() + " Put: " + putSem.availablePermits());
		return returnItem;
	}

	public int getSize() {
		return size;
	}

	public boolean getDonePutting() {
		System.out.println("Done Putting");
		return donePutting;
	}

	public void donePutting() {
		donePutting = true;
	}
}
