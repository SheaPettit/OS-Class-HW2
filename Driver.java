public class Driver {
	public static void main(String[] args) {
		CircularBuffer buf = new CircularBuffer(Integer.parseInt(args[3]));
		int maxCopy = Integer.parseInt(args[2]);
		Producer p = new Producer(buf, args[0], maxCopy);
		Thread prodThread = new Thread(p);
		Consumer c = new Consumer(buf, args[1], maxCopy);
		Thread consThread = new Thread(c);
		prodThread.start();
		consThread.start();
		try {
			prodThread.join();
			consThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}	