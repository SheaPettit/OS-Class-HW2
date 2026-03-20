import java.util.concurrent.Semaphore;

public class CircularBuffer {
	private byte[] buffer;
	private int size;
	private Semaphore putSem;
	private Semaphore getSem;
	private int nextPut;
	private int nextGet;
	private boolean donePutting;

	public CircularBuffer(int bufferSize) {
		buffer = new byte[bufferSize];
		putSem = new Semaphore(bufferSize);
		getSem = new Semaphore(0);
		nextPut = 0;
		nextGet = 0;
		size = bufferSize;
		donePutting = false;
	}

	public boolean canPut(int num) {
		if(num <= putSem.availablePermits()) {
			return true;
		}
		return false;
	}

	public void put(byte[] items, int num) {
		try {
			putSem.acquire(num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < num; i++) {
			buffer[nextPut] = items[i];
			if (++nextPut >= size)
				nextPut = 0;
		}
		getSem.release(num);
	}

	public boolean canGet(int num) {
		if(num <= getSem.availablePermits())
			return true;
		return false;
	}

	public byte[] get(int num) {
		if(num == 0)
			return null;
		try {
			getSem.acquire(num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		byte[] items = new byte[num];
		for(int i = 0; i < num; i++) {
			items[i] = buffer[nextGet++];
			if (nextGet >= size)
				nextGet = 0;
		}
		putSem.release(num);
		return items;
	}

	public int getSize() {
		return size;
	}
	
	public byte[] getRemaining() {
		return get(getSem.availablePermits());
	}

	public boolean getDonePutting() {
		return donePutting;
	}

	public void donePutting() {
		donePutting = true;
	}
}
