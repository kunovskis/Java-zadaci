package RoundRobin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

public class RoundRobin {
	
	public static void main(String [] args) throws NumberFormatException, IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		Queue<Process> queue = new ArrayQueue<Process>(N);
		Stack<Process> stack = new ArrayStack<Process>(N);
		Stack<Process> stackReverse = new ArrayStack<Process>(N);
		for(int i = 0; i < N; i++){
			String line = br.readLine();
			String [] parts = line.split(" ");
			Process ins = new Process(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
			
			
			while(!queue.isEmpty()){
				stack.push(queue.dequeue());
			}
			while(!stack.isEmpty()){
				stackReverse.push(stack.pop());
			}
			boolean inserted = false;
			
			
			while(!stackReverse.isEmpty()){
				
				Process fromStack = stackReverse.pop();
				if(inserted == false && ins.timeIn < fromStack.timeIn){
					queue.enqueue(ins);
					inserted = true;
				}else if ( inserted == false && ( ins.timeIn == fromStack.timeIn && ins.timeLeft > fromStack.timeLeft)){
					queue.enqueue(ins);
					inserted = true;
				}
				queue.enqueue(fromStack);
				
			}
			if(inserted == false){
				queue.enqueue(ins);
			}
			
		}
		int RR = Integer.parseInt(br.readLine());
		while(!queue.isEmpty()){
			
			Process pr = queue.dequeue();
			System.out.print(pr.toString());
			pr.timeLeft -= RR;
			
			if( pr.timeLeft > 0 ) queue.enqueue(pr);
			
		}
		
		
		
	}

}

interface Queue<E> {
    public boolean isEmpty ();
    public int size ();
    public E peek ();
    public void clear ();
    public void enqueue (E x);	
    public E dequeue ();
    	
}

class ArrayQueue<E> implements Queue<E> {
	
    E[] elems;
    int length, front, rear;

    @SuppressWarnings("unchecked")
    public ArrayQueue (int maxlength) {
        elems = (E[]) new Object[maxlength];
        clear();
    }

    public boolean isEmpty () {
        return (length == 0);
    }

    public int size () {
        return length;
    }

    public E peek () {
        if (length > 0)
            return elems[front];
        else
            throw new NoSuchElementException();
    }

    public void clear () {
        length = 0;
        front = rear = 0;  // arbitrary
    }

    public void enqueue (E x) {
        elems[rear++] = x;
        if (rear == elems.length)  rear = 0;
        length++;
    }

    public E dequeue () {
        if (length > 0) {
            E frontmost = elems[front];
            elems[front++] = null;
            if (front == elems.length)  front = 0;
            length--;
            return frontmost;
        } else
            throw new NoSuchElementException();
    }
}
interface Stack<E> {
    public boolean isEmpty ();
    public E peek ();
    public void clear ();
    public void push (E x);
    public E pop ();
}
class ArrayStack<E> implements Stack<E> {

    private E[] elems;
    private int depth;

    @SuppressWarnings("unchecked")
    public ArrayStack (int maxDepth) {
        elems = (E[]) new Object[maxDepth];
        depth = 0;
    }
    public boolean isEmpty () {
        return (depth == 0);
    }
    public E peek () {
        if (depth == 0)
            throw new NoSuchElementException();
        return elems[depth-1];
    }
    public void clear () {
        for (int i = 0; i < depth; i++)  elems[i] = null;
        depth = 0;
    }
    public void push (E x) {
        elems[depth++] = x;
    }
    public E pop () {
        if (depth == 0)
            throw new NoSuchElementException();
        E topmost = elems[--depth];
        elems[depth] = null;
        return topmost;
    }
}

class Process{
	String name;
	int timeLeft;
	int timeIn;
	
	public Process(String _name, int _timeLeft, int _timeIn){
		name = _name;
		timeLeft = _timeLeft;
		timeIn = _timeIn;
	}
	public String toString(){
		return name+" ";
	}
	
}