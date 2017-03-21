package Azbuka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Azbuka {

	public static void main(String [] args) throws IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		String [] letters = line.split(" ");
		DLL<Character> dll = new DLL<Character>();
		for(int i = 0; i < letters.length; i++){
			dll.insertLast(letters[i].charAt(0));
		}
		DLLNode<Character> curr = dll.getFirst();
		while(curr.succ != null){
			if(curr.succ.element.equals(curr.element) || bothVowel( curr.element, curr.succ.element) || bothCo(curr.element, curr.succ.element)){
				curr.succ = curr.succ.succ;
			}else curr = curr.succ;
		}
		System.out.println(dll.toString());
	}
	
	public static boolean bothVowel( char a, char b ){
		a = Character.toLowerCase(a);
		b = Character.toLowerCase(b);
		if ( ( a=='a' || a =='e' || a =='i' || a=='o' || a == 'u')
				&& (b=='a' || b =='e' || b =='i' || b=='o' || b == 'u')){
			return true;
		}
		return false;
	}
	public static boolean bothCo( char a, char b ){
		a = Character.toLowerCase(a);
		b = Character.toLowerCase(b);
		if ( !( a=='a' || a =='e' || a =='i' || a=='o' || a == 'u')
				&& !(b=='a' || b =='e' || b =='i' || b=='o' || b == 'u')){
			return true;
		}
		return false;
	}
	
}

class DLL<E> {
	private DLLNode<E> first, last;

	public DLL() {
		// Construct an empty SLL
		this.first = null;
		this.last = null;
	}

	public void deleteList() {
		first = null;
		last = null;
	}
	
	public int length() {
		int ret;
		if (first != null) {
			DLLNode<E> tmp = first;
			ret = 1;
			while (tmp.succ != null) {
				tmp = tmp.succ;
				ret++;
			}
			return ret;
		} else
			return 0;

	}

	public DLLNode<E> find(E o) {
		if (first != null) {
			DLLNode<E> tmp = first;
			while (tmp.element != o && tmp.succ != null)
				tmp = tmp.succ;
			if (tmp.element == o) {
				return tmp;
			} else {
				System.out.println("Elementot ne postoi vo listata");
			}
		} else {
			System.out.println("Listata e prazna");
		}
		return first;
	}
	
	public void insertFirst(E o) {
		DLLNode<E> ins = new DLLNode<E>(o, null, first);
		if (first == null)
			last = ins;
		else
			first.pred = ins;
		first = ins;
	}

	public void insertLast(E o) {
		if (first == null)
			insertFirst(o);
		else {
			DLLNode<E> ins = new DLLNode<E>(o, last, null);
			last.succ = ins;
			last = ins;
		}
	}

	public void insertAfter(E o, DLLNode<E> after) {
		if(after==last){
			insertLast(o);
			return;
		}
		DLLNode<E> ins = new DLLNode<E>(o, after, after.succ);
		after.succ.pred = ins;
		after.succ = ins;
	}

	public void insertBefore(E o, DLLNode<E> before) {
		if(before == first){
			insertFirst(o);
			return;
		}
		DLLNode<E> ins = new DLLNode<E>(o, before.pred, before);
		before.pred.succ = ins;
		before.pred = ins;
	}

	public E deleteFirst() {
		if (first != null) {
			DLLNode<E> tmp = first;
			first = first.succ;
			if (first != null) first.pred = null;
			if (first == null)
				last = null;
			return tmp.element;
		} else
			return null;
	}

	public E deleteLast() {
		if (first != null) {
			if (first.succ == null)
				return deleteFirst();
			else {
				DLLNode<E> tmp = last;
				last = last.pred;
				last.succ = null;
				return tmp.element;
			}
		}
		// else throw Exception
		return null;
	}

	public E delete(DLLNode<E> node) {
		if(node==first){
			deleteFirst();
			return node.element;
		}
		if(node==last){
			deleteLast();
			return node.element;
		}
		node.pred.succ = node.succ;
		node.succ.pred = node.pred;
		return node.element;
		
	}

	@Override
	public String toString() {
		String ret = new String();
		if (first != null) {
			DLLNode<E> tmp = first;
			ret += tmp + "<->";
			while (tmp.succ != null) {
				tmp = tmp.succ;
				ret += tmp + "<->";
			}
		} else
			ret = "Prazna lista!!!";
		return ret;
	}
	
	public String toStringR() {
		String ret = new String();
		if (last != null) {
			DLLNode<E> tmp = last;
			ret += tmp + "<->";
			while (tmp.pred != null) {
				tmp = tmp.pred;
				ret += tmp + "<->";
			}
		} else
			ret = "Prazna lista!!!";
		return ret;
	}

	public DLLNode<E> getFirst() {
		return first;
	}

	public DLLNode<E> getLast() {

		return last;
	}
}
class DLLNode<E> {
	protected E element;
	protected DLLNode<E> pred, succ;

	public DLLNode(E elem, DLLNode<E> pred, DLLNode<E> succ) {
		this.element = elem;
		this.pred = pred;
		this.succ = succ;
	}

	@Override
	public String toString() {
		return element.toString();
	}
}
