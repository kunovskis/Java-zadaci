import java.util.concurrent.Semaphore;

public class TwoThreads {
	public static class Thread1 extends Thread {
		public void run() {
			System.out.println("A");
			System.out.println("B");
		}
	}

	public static class Thread2 extends Thread {
		public void run() {
			System.out.println("1");
			System.out.println("2");
		}
	}
	
	public static void main(String[] args) {
		//new Thread1().start();
		//new Thread2().start();
		Runnable A1 = new ThreadAB("A","B");
		Runnable A2 = new ThreadAB("1","2");
		Thread t1 = new Thread(A1);
		Thread t2 = new Thread(A2);
		t1.start();
		t2.start();
		
	}

}

/* prvo ke ja pomini azbukata, a posle broevite */


class ThreadAB implements Runnable
{
	String a;
	String b;
	public static Semaphore s = new Semaphore(0);
	
	public ThreadAB(String s1, String s2){
		a=s1;
		b=s2;
	}
	
	public void run(){
		if(a=="A"){
		System.out.println(a);
		System.out.println(b);
		s.release();
		}
		else{
			try {
				s.acquire();
			} catch (InterruptedException e) {
				System.err.println("Nekoj interrupted exception!?");
			}
			System.out.println(a);
			System.out.println(b);
		}
		
	}
}