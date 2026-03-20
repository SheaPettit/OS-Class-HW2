public class Driver {
  public static void main(String[] args){
    CircularBuffer buf = new CircularBuffer(12);
    buf.put(Byte.valueOf((byte) 50));
    Byte item = buf.get();
    System.out.println(item);
  }
}