public class Driver {
	public static void main(String[] args) {
		CircularBuffer buf = new CircularBuffer(Integer.parseInt(args[3]));
		int maxCopy = Integer.parseInt(args[2]);
		new Producer(buf, args[0], maxCopy);
		new Consumer(buf, args[1], maxCopy);
	}
}	