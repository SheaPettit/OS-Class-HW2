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
  }
  public synchronized boolean put(Byte item){
    try {
      putSem.acquire();
    }
    catch (InterruptedException e) {
      return false;
    }
    buffer[nextPut] = item;
    if(++nextPut >= size)
      nextPut = 0;
    getSem.release();
    return true;
  }
  public synchronized Byte get(){
    try {
      getSem.acquire();
    }
    catch (InterruptedException e) {
      return null;
    }
    Byte returnItem = buffer[nextGet++];
    if(nextGet >= size)
      nextGet = 0;
    putSem.release();
    return returnItem;
  }
  public int getSize() {
	  return size;
  }
  public boolean getDonePutting() {
	  return donePutting;
  }
  public void donePutting() {
	  donePutting = true;
  }
}
