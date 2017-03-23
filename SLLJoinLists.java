//Se sho e komentar e od zadacata SLLJoinLists. Se drugo e od SpecialSLLJoin!!!!!!!

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

class SLLNode<E>{
	protected E element;
	protected SLLNode<E> succ;
	public SLLNode(E _element, SLLNode<E> _succ){
		element = _element;
		succ = _succ;
	}
	public SLLNode<E> getNext(){
		return succ;
	}
	public void setNext(SLLNode<E> obj){
		succ=obj;
	}
	public E getElement(){
		return element;
	}
	public int getValue(){
		return Integer.parseInt(element.toString());
	}
};

class SLL<E>{
	private SLLNode<E> first;
	
	public SLL(){
		first = null;
	}
	public void insertLast(E node){
		if(first==null){
			SLLNode<E> temp = new SLLNode<E>(node, null);
			first = temp;
		}
		else{
			SLLNode<E> curr = first;
			while(curr.getNext() != null)
				curr = curr.getNext();
			SLLNode<E> ins = new SLLNode<E> (node, null);
			curr.setNext(ins);
		}
	}
	@Override
	public String toString() {
		String ret = new String();
		SLLNode <E> curr = first;
		while(curr != null){
			ret += curr.getElement().toString();
			ret += " ";
			curr = curr.getNext();
		}
		return ret;
	}
	public int length(){
		int ret = 0;
		SLLNode <E> curr = first;
		while(curr!=null){
			ret++;
			curr = curr.getNext();
		}
		return ret;
	}
	public SLL<E> joinLists(SLL<E> lista2){
		SLL<E> ret = new SLL<E> ();
		SLLNode<E> curr1 = first;
		SLLNode<E> curr2 = lista2.first;
		while(curr1 != null || curr2 != null){
			if(curr1==null){
				ret.insertLast(curr2.getElement());
				curr2 = curr2.getNext();
			}
			else if(curr2==null){
				ret.insertLast(curr1.getElement());
				curr1 = curr1.getNext();
			}
			else if(curr1.getValue()<curr2.getValue()){
				ret.insertLast(curr1.getElement());
				curr1 = curr1.getNext();
			}
			else if(curr2.getValue()<curr1.getValue()){
				ret.insertLast(curr2.getElement());
				curr2 = curr2.getNext();
			}
			else{
				ret.insertLast(curr2.getElement());
				curr2 = curr2.getNext();
				curr1 = curr1.getNext();
			}
		}
		SLLNode<E> curr = ret.first;
		while(curr!=null){
			if(curr.getNext() != null && curr.getValue()==curr.getNext().getValue())
				curr.setNext(curr.getNext().getNext());
			else
				curr = curr.getNext();
		}
		return ret;
	}
	
	public SLL<E> specialJoin(SLL<E> lista2){
		SLL<E> ret = new SLL<E> ();
		SLLNode<E> curr1 = first;
		SLLNode<E> curr2 = lista2.first;
		boolean prv = true;
		int brojac = 0;
		while(curr1 != null || curr2 != null){
			if(curr1==null){
				ret.insertLast(curr2.getElement());
				curr2 = curr2.getNext();
			}
			else if(curr2==null){
				ret.insertLast(curr1.getElement());
				curr1 = curr1.getNext();
			}
			else if(prv){
				ret.insertLast(curr1.getElement());
				curr1 = curr1.getNext();
			}
			else if(!prv){
				ret.insertLast(curr2.getElement());
				curr2 = curr2.getNext();
			}
			brojac++;
			if(brojac==2){
				brojac=0;
				prv = !prv;
			}
		}
		return ret;
	}
	
	public Iterator<E> iterator () {
	    // Return an iterator that visits all elements of this list, in left-to-right order.
	        return new LRIterator<E>();
	    }
	
	private class LRIterator<E> implements Iterator<E> {

		private SLLNode<E> place, curr;

		private LRIterator() {
			place = (SLLNode<E>) first;
			curr = null;
		}

		public boolean hasNext() {
			return (place != null);
		}

		public E next() {
			if (place == null)
				throw new NoSuchElementException();
			E nextElem = place.element;
			curr = place;
			place = place.succ;
			return nextElem;
		}
	}
};

public class SLLJoinLists {
	
	public static void main(String[] args) throws IOException {

		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String s = stdin.readLine();
		int N = Integer.parseInt(s);
		s = stdin.readLine();
		String[] pomniza = s.split(" ");
		SLL<Integer> lista1 = new SLL<Integer> ();
		for (int i = 0; i < N; i++) {
			lista1.insertLast(Integer.parseInt(pomniza[i]));
		}
		s = stdin.readLine();
		N = Integer.parseInt(s);
		s = stdin.readLine();
		pomniza = s.split(" ");
		SLL<Integer> lista2 = new SLL<Integer> ();
		for (int i = 0; i < N; i++) {
			lista2.insertLast(Integer.parseInt(pomniza[i]));
		}
		SLL<Integer> spoeni;
		//spoeni = lista1.joinLists(lista2);
		//System.out.println(spoeni.toString());
		spoeni = lista1.specialJoin(lista2);
		System.out.println(spoeni.toString());
		/*Iterator<Integer> it = spoeni.iterator();
		while (it.hasNext()) {
			System.out.print(it.next());
            if(it.hasNext())
                System.out.print(" ");
		}
        System.out.println();*/
	}
}
