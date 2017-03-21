import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


class DLLNode<E>{
	protected E element;
	protected DLLNode<E> pred, succ;
	
	public DLLNode(E elem, DLLNode<E> pred, DLLNode<E> succ){
		this.element=elem;
		this.succ=succ;
		this.pred=pred;
	}
	
	@Override
	public String toString(){
		return element.toString();
	}
}

class DLL<E>{
	private DLLNode<E> first, last;
	
	public DLL(){
		this.first = null;
		this.last = null;
	}
	
	public void deleteList(){
		first = null;
		last = null;
	}
	
	public void insertFirst(E o){
		DLLNode<E> ins = new DLLNode<E>(o, null, first);
		if(first == null) last = ins;
		else first.pred = ins;
		first = ins;
	}
	
	public void insertLast(E o){
		if(first == null) insertFirst(o);
		else{
			DLLNode<E> ins = new DLLNode<E>(o, last, null);
			last.succ = ins;
			last = ins;
		}
	}
	
	public DLLNode<E> getFirst(){
		return first;
	}
	
}


public class DivideOddEven {
	   
	 
    public static void main(String[] args) throws IOException {
        DLL<Integer> lista=new DLL<Integer>();
        DLL<Integer> parni=new DLL<Integer>();
        DLL<Integer> neparni=new DLL<Integer>();
        Scanner sc=new Scanner(System.in);
        int n,element;
        n=sc.nextInt();
        for(int i=0;i<n;i++)
        {
            element=sc.nextInt();
            lista.insertLast(element);
        }
        DLLNode<Integer> node=lista.getFirst();
       
        while(node!=null)
        {
            if(node.element%2==0)
                parni.insertLast(node.element);
            else
                if(node.element%2==1)
                    neparni.insertLast(node.element);
            node=node.succ;
        }
        node=neparni.getFirst();
        System.out.println();
        while(node!=null)
        {
            if(node.succ==null)
                System.out.print(node.element);
            else
                System.out.print(node.element+" ");
            node=node.succ;
        }
        node=parni.getFirst();
        System.out.println();
        while(node!=null)
        {
            if(node.succ==null)
                System.out.print(node.element);
            else
                System.out.print(node.element+" ");
            node=node.succ;
        }
       
    }
}