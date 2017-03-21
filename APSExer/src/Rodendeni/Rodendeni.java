package Rodendeni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Rodendeni {

	public static void main(String [] args) throws NumberFormatException, IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		CBHT<Date, Person> map = new CBHT<Date, Person>(3*N/2);
		for(int i = 0; i < N; i++){
			String line = br.readLine();
			String parts [] = line.split(" ");
			String dateParts[] = parts[2].split("/");
			
			Date dateKey = new Date( Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]) );
			Person person = new Person(parts[0]+" "+parts[1], dateKey);
			map.insert(dateKey, person);
			
		}
		String onDate = br.readLine();
		String onDateParts [] = onDate.split("/");
		Date targetDate = new Date( Integer.parseInt(onDateParts[0]), Integer.parseInt(onDateParts[1]), Integer.parseInt(onDateParts[2]));
		SLL<Person> personsOn = new SLL<Person>();
		SLLNode<MapEntry<Date, Person>> currNode = map.search(targetDate);
		while(currNode != null){
			if( targetDate.equals( currNode.element.value.birth ))
				personsOn.insertLast(currNode.element.value);
			currNode = currNode.succ;
		}
		
		SLL<Person> fList = new SLL<Person>();
 		SLLNode<Person> curr = personsOn.getFirst();
 		
 		while(curr != null){
 			
 			if( fList.getFirst() == null || fList.getFirst().element.name.compareTo(curr.element.name) > 0 ){
 				fList.insertFirst(curr.element);
 			}else{
 				
 				SLLNode<Person> currInner = fList.getFirst();
 				boolean inserted = false;
 				while(currInner.succ != null){
 					if( currInner.succ.element.name.compareTo(curr.element.name) > 0 ){
 						fList.insertAfter(curr.element, currInner);
 						inserted = true;
 						break;
 					}
 					currInner = currInner.succ;
 				}
 				if(!inserted){
 					fList.insertLast(curr.element);
 				}
 			}
 			
 			curr = curr.succ;
 		}
 		
 		
		
		curr = fList.getFirst();
		
		if(curr == null){
			System.out.println("nema");
			return;
		}
		
		while( curr != null ){
			System.out.println(curr.element.name +" "+ curr.element.birth.yearsOld(targetDate.year));
			curr = curr.succ;
		}
	}
	
}
class Date implements Comparable<Date>{
	int day, month, year;
	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	@Override
	public int compareTo(Date o) {
		if(year < o.year) return -1;
		else if( year == o.year){
			if ( month < o.month ) return -1;
			else if(month == o.month){
				if( day < o.day ) return -1;
				if( day == o.day) return 0;
				else return 1;
			}
			else return 1;
		}else return 1;
	}
	public int yearsOld(int yearNew){
		return yearNew - year;
	}
	public String toString(){
		return day+ "/"+month+"/"+year;
	}
	public int hashCode(){
		return 2*(day + month)-day;
	}
	public boolean equals(Date ob){
		if( ob == null ) return false;
		if( this == ob ) return true;
		if( day == ob.day && month == ob.month) return true;
		return false;
	}
}
class Person{
	String name;
	Date birth;
	public Person(String name, Date birth) {
		this.name = name;
		this.birth = birth;
	}
	public String toString(){
		return name + " " + birth.toString();
	}
}
class CBHT<K extends Comparable<K>, E> {

	// An object of class CBHT is a closed-bucket hash table, containing
	// entries of class MapEntry.
	private SLLNode<MapEntry<K,E>>[] buckets;

	@SuppressWarnings("unchecked")
	public CBHT(int m) {
		// Construct an empty CBHT with m buckets.
		buckets = (SLLNode<MapEntry<K,E>>[]) new SLLNode[m];
	}

	private int hash(K key) {
		// Translate key to an index of the array buckets.
		return Math.abs(key.hashCode()) % buckets.length;
	}

	public SLLNode<MapEntry<K,E>> search(K targetKey) {
		// Find which if any node of this CBHT contains an entry whose key is
		// equal
		// to targetKey. Return a link to that node (or null if there is none).
		int b = hash(targetKey);
		return buckets[b];
	}

	public void insert(K key, E val) {		// Insert the entry <key, val> into this CBHT.
		MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
		int b = hash(key);
		for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
			if (key.equals(((MapEntry<K, E>) curr.element).key)) {
				// Make newEntry replace the existing entry ...
				curr.element = newEntry;
				return;
			}
		}
		// Insert newEntry at the front of the 1WLL in bucket b ...
		buckets[b] = new SLLNode<MapEntry<K,E>>(newEntry, buckets[b]);
	}

	public void delete(K key) {
		// Delete the entry (if any) whose key is equal to key from this CBHT.
		int b = hash(key);
		for (SLLNode<MapEntry<K,E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {
			if (key.equals(((MapEntry<K,E>) curr.element).key)) {
				if (pred == null)
					buckets[b] = curr.succ;
				else
					pred.succ = curr.succ;
				return;
			}
		}
	}

	public String toString() {
		String temp = "";
		for (int i = 0; i < buckets.length; i++) {
			temp += i + ":";
			for (SLLNode<MapEntry<K,E>> curr = buckets[i]; curr != null; curr = curr.succ) {
				temp += curr.element.toString() + " ";
			}
			temp += "\n";
		}
		return temp;
	}

}

class MapEntry<K extends Comparable<K>,E> implements Comparable<K> {

    // Each MapEntry object is a pair consisting of a key (a Comparable 
    // object) and a value (an arbitrary object).
    K key;
    E value;

    public MapEntry (K key, E val) {
        this.key = key;
        this.value = val;
    }
    
    public int compareTo (K that) {
    // Compare this map entry to that map entry.
        @SuppressWarnings("unchecked")
		MapEntry<K,E> other = (MapEntry<K,E>) that;
        return this.key.compareTo(other.key);
    }

    public String toString () {
        return "<" + key + "," + value + ">";
    }
}

 class SLLNode<E> {
	protected E element;
	protected SLLNode<E> succ;

	public SLLNode(E elem, SLLNode<E> succ) {
		this.element = elem;
		this.succ = succ;
	}

	@Override
	public String toString() {
		return element.toString();
	}
}

class SLL<E> {
 	private SLLNode<E> first;

 	public SLL() {
 		// Construct an empty SLL
 		this.first = null;
 	}

 	public void deleteList() {
 		first = null;
 	}

 	public int length() {
 		int ret;
 		if (first != null) {
 			SLLNode<E> tmp = first;
 			ret = 1;
 			while (tmp.succ != null) {
 				tmp = tmp.succ;
 				ret++;
 			}
 			return ret;
 		} else
 			return 0;

 	}

 	@Override
 	public String toString() {
 		String ret = new String();
 		if (first != null) {
 			SLLNode<E> tmp = first;
 			ret += tmp + "->";
 			while (tmp.succ != null) {
 				tmp = tmp.succ;
 				ret += tmp + "->";
 			}
 		} else
 			ret = "Prazna lista!!!";
 		return ret;
 	}

 	public void insertFirst(E o) {
 		SLLNode<E> ins = new SLLNode<E>(o, first);
 		first = ins;
 	}

 	public void insertAfter(E o, SLLNode<E> node) {
 		if (node != null) {
 			SLLNode<E> ins = new SLLNode<E>(o, node.succ);
 			node.succ = ins;
 		} else {
 			System.out.println("Dadenot jazol e null");
 		}
 	}

 	public void insertBefore(E o, SLLNode<E> before) {
 		
 		if (first != null) {
 			SLLNode<E> tmp = first;
 			if(first==before){
 				this.insertFirst(o);
 				return;
 			}
 			//ako first!=before
 			while (tmp.succ != before)
 				tmp = tmp.succ;
 			if (tmp.succ == before) {
 				SLLNode<E> ins = new SLLNode<E>(o, before);
 				tmp.succ = ins;
 			} else {
 				System.out.println("Elementot ne postoi vo listata");
 			}
 		} else {
 			System.out.println("Listata e prazna");
 		}
 	}

 	public void insertLast(E o) {
 		if (first != null) {
 			SLLNode<E> tmp = first;
 			while (tmp.succ != null)
 				tmp = tmp.succ;
 			SLLNode<E> ins = new SLLNode<E>(o, null);
 			tmp.succ = ins;
 		} else {
 			insertFirst(o);
 		}
 	}

 	public E deleteFirst() {
 		if (first != null) {
 			SLLNode<E> tmp = first;
 			first = first.succ;
 			return tmp.element;
 		} else {
 			System.out.println("Listata e prazna");
 			return null;
 		}
 	}

 	public E delete(SLLNode<E> node) {
 		if (first != null) {
 			SLLNode<E> tmp = first;
 			if(first ==node){
 				return this.deleteFirst();
 			}
 			while (tmp.succ != node && tmp.succ.succ != null)
 				tmp = tmp.succ;
 			if (tmp.succ == node) {
 				tmp.succ = tmp.succ.succ;
 				return node.element;
 			} else {
 				System.out.println("Elementot ne postoi vo listata");
 				return null;
 			}
 		} else {
 			System.out.println("Listata e prazna");
 			return null;
 		}

 	}

 	public SLLNode<E> getFirst() {
 		return first;
 	}
 	
 	public SLLNode<E> find(E o) {
 		if (first != null) {
 			SLLNode<E> tmp = first;
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
 	
 	
 }
