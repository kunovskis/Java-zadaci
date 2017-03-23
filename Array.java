import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Array<E> {
	
	private E data[];
	private int size;
	
	public Array(int size) {
		data = (E[]) new Object[size];
		this.size = size;
	}
	
	public void set(int position, E o) {
		if (position >= 0 && position < size)
			data[position] = o;
		else
			System.out.println("Ne moze da se vmetne element na dadenata pozicija");
	}
	public int getLength(){
		return size;
	}
	public E getEl(int i){
		return data[i];
	}
	public static int brojDoProsek(Array<Integer> niza){
		int N = niza.getLength();
		float prosek = 0;
		for(int i=0;i<N;i++){
			prosek += niza.getEl(i);
		}
		prosek = (float)prosek/N;
		float razlika = Math.abs(niza.getEl(0)-prosek);
		int poz = 0;
		for(int i=1;i<N;i++){
			if(Math.abs(niza.getEl(i)-prosek)<razlika){
				razlika = Math.abs(niza.getEl(i)-prosek);
				poz = i;
			}
			else if(Math.abs(niza.getEl(i)-prosek)==razlika){
				if(niza.getEl(i)<niza.getEl(poz)){
					razlika = Math.abs(niza.getEl(i)-prosek);
					poz = i;
				}
			}
		}
		return niza.getEl(poz).intValue();
	}
	public static void main(String[] args) throws IOException{
		BufferedReader stdin = new BufferedReader( new InputStreamReader(System.in)); 
		String s = stdin.readLine();
		int N = Integer.parseInt(s);
        
		Array<Integer> niza= new Array <Integer>(N);
		for(int i=0;i<N;i++){
			s = stdin.readLine();
			int num = Integer.parseInt(s);
			Integer numInt = new Integer (num);
			niza.set(i, numInt);
		}
		System.out.println(brojDoProsek(niza));		
	}
}
