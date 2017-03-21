import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;


class DLLNode<E extends Comparable<E>> {
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

class DLL<E extends Comparable<E>> {
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
        if(after==last) {
            insertLast(o);
            return;
        }
        DLLNode<E> ins = new DLLNode<E>(o, after, after.succ);
        after.succ.pred = ins;
        after.succ = ins;
    }
    public DLLNode<E> find(E o) {
        if (first != null) {
            DLLNode<E> tmp = first;
            while (tmp.element != o&&tmp.succ != null)
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
        if(node==first) {
            deleteFirst();
            return node.element;
        }
        if(node==last) {
            deleteLast();
            return node.element;
        }
        node.pred.succ = node.succ;
        node.succ.pred = node.pred;
        return node.element;

    }
    public void insertBefore(E o, DLLNode<E> before) {
        if(before == first) {
            insertFirst(o);
            return;
        }
        DLLNode<E> ins = new DLLNode<E>(o, before.pred, before);
        before.pred.succ = ins;
        before.pred = ins;
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
    public DLLNode<E> getFirst() {
        return first;
    }

    public DLLNode<E> getLast() {

        return last;
    }

}

class SortedLinkedList<T extends Comparable<T>> {
    private DLL<T> list;
    public SortedLinkedList() {
        list = new DLL<>();
    }
    public void add(T element) {
        DLLNode<T> tmp = list.getFirst();
        while(tmp != null) {
            if(tmp.element.compareTo(element) == 0)
                return;
            if(tmp.element.compareTo(element) > 0) {
                list.insertBefore(element, tmp);
                return;
            }
            tmp = tmp.succ;
        }
        if(tmp == null)
            list.insertLast(element);
    }
    public boolean remove(T element) {
       DLLNode<T> tmp = list.getFirst();
       while(tmp != null){
    	   if(tmp.element.compareTo(element) == 0){
    		   list.delete(tmp);
    		   return true;
    	   }
    	   if(tmp.element.compareTo(element) > 0)
    		   return false;
    	   tmp = tmp.succ;
       }
       return false;
    }
    public boolean contains(T element) {
        DLLNode<T> tmp = list.getFirst();
        while(tmp != null) {
            if(tmp.element.compareTo(element) == 0)
                return true;
            if(tmp.element.compareTo(element) > 0)
                return false;
            tmp = tmp.succ;
        }
        return false;
    }
    public boolean isEmpty() {
        return (list.getFirst() == null);
    }
    public int size() {
        return list.length();
    }
    public ArrayList<T> toArrayList() {
        ArrayList<T> tmp = new ArrayList<>();
        DLLNode<T> node = list.getFirst();
        while(node != null) {
            tmp.add(node.element);
            node = node.succ;
        }
        return tmp;
    }
    public void addAll(SortedLinkedList<? extends T> sll) {
        DLLNode<T> tmp = (DLLNode<T>) sll.list.getFirst();
    	DLLNode<T> position = list.getFirst();
    	while(tmp != null&&position != null){
    		if(position.element.compareTo(tmp.element) == 0){
    			position = position.succ;
    			tmp = tmp.succ;
    			continue;
    		}
    		else if(position.element.compareTo(tmp.element) > 0){
    			list.insertBefore(tmp.element, position);
                tmp = tmp.succ;
    		}
    		else{
    			position = position.succ;
    		}
    	}
    	if(position == null&&tmp != null){
    		while(tmp != null){
    			list.insertLast(tmp.element);
    			tmp = tmp.succ;
    		}
    	}
       	
    }
    public boolean containsAll(SortedLinkedList<? extends T> sll) {
       DLLNode<T> tmp = (DLLNode<T>) sll.list.getFirst();
        DLLNode<T> position = list.getFirst();
        while(tmp != null && position != null){
            if(position.element.compareTo(tmp.element) == 0){
            	tmp = tmp.succ;
                position = position.succ;
            }
            else{
            	position = position.succ;
            }
        }
        if(position == null && tmp != null)
            return false;
        else
            return true;
    }
}



public class SortedLinkedListTest {
    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        System.out.println("Test#"+k);
        System.out.print("testing SortedLinkedList::toArrayList():ArrayList<T> ,");
        if ( k == 0 ) {
            System.out.println(" SortedLinkedList::add(T), SortedLinkedList::isEmpty():boolean , SortedLinkedList::remove(T):boolean , SortedLinkedList::size():int , T is Integer");

            SortedLinkedList<Integer> list = new SortedLinkedList<Integer>();
            System.out.println("List is empty? "+list.isEmpty());
            System.out.println("Adding elements:");
            boolean flag = false;
            while ( jin.hasNextInt() ) {
                System.out.print(flag?" ":"");
                int i = jin.nextInt();
                list.add(i);
                System.out.print(i);
                flag = true;
            }
            System.out.println();
            System.out.println("List size? "+list.size());
            jin.next();
            flag = false;
            System.out.println("Removing elements:");
            while ( jin.hasNextInt() ) {
                System.out.print(flag?" ":"");
                int i = jin.nextInt();
                list.remove(i);
                System.out.print(i);
                flag = true;
            }
            System.out.println();
            System.out.println("List size? "+list.size());
            System.out.println("Final list: "+list.toArrayList());
        }
        if ( k == 1 ) {
            System.out.println(" SortedLinkedList::add(T) , T is Integer");
            System.out.println("Adding elements:");
            SortedLinkedList<Integer> list = new SortedLinkedList<Integer>();
            boolean flag = false;
            while ( jin.hasNextInt() ) {
                System.out.print(flag?" ":"");
                int i = jin.nextInt();
                list.add(i);
                System.out.print(i);
                flag = true;
            }
            System.out.println();
            System.out.print("Final list: ");
            System.out.println(list.toArrayList());
        }
        if ( k == 2 ) {
            System.out.println(" SortedLinkedList::add(T) , SortedLinkedList::addAll(SortedLinkedList<? etends T>) , T is Integer");

            int num_testcases = jin.nextInt();
            for ( int w = 0 ; w < num_testcases ; ++w ) {
                System.out.println("Subtest #"+(w+1));
                SortedLinkedList<Integer> list = new SortedLinkedList<Integer>();
                while ( jin.hasNextInt() ) {
                    list.add(jin.nextInt());
                }
                SortedLinkedList<Integer> query = new SortedLinkedList<Integer>();
                jin.next();
                while ( jin.hasNextInt() ) {
                    query.add(jin.nextInt());
                }
                System.out.println("List a="+list.toArrayList());
                System.out.println("List b="+query.toArrayList());
                list.addAll(query);
                System.out.println("Add all elements from b to a");
                System.out.println("List a="+list.toArrayList());
                jin.next();
            }
        }
        if ( k == 3 ) {
            System.out.println(" SortedLinkedList::add(T) , SortedLinkedList::containsAll(SortedLinkedList<? etends T>) , T is Integer");
            int num_testcases = jin.nextInt();
            for ( int w = 0 ; w < num_testcases ; ++w ) {
                System.out.println("Subtest #"+(w+1));
                SortedLinkedList<Integer> list = new SortedLinkedList<Integer>();
                while ( jin.hasNextInt() ) {
                    list.add(jin.nextInt());
                }
                SortedLinkedList<Integer> query = new SortedLinkedList<Integer>();
                jin.next();
                while ( jin.hasNextInt() ) {
                    query.add(jin.nextInt());
                }
                System.out.println("List a="+list.toArrayList());
                System.out.println("List b="+query.toArrayList());
                System.out.println("List a contains all elements in list b? "+list.containsAll(query));
                jin.next();
            }
        }
        if ( k == 4 ) {
            System.out.println(" SortedLinkedList::add(T) , SortedLinkedList::remove(T):boolean , SortedLinkedList::contains(T) , T is String");
            SortedLinkedList<String> list = new SortedLinkedList<String>();
            TreeSet<String> control_list = new TreeSet<String>();
            ArrayList<String> all = new ArrayList<String>();
            all.add("Sample");
            boolean same = true;
            for ( int i = 0 ; i < 1000 ; ++i ) {
                double rand = Math.random();
                if ( rand > 0.3 ) { //addelement
                    String srand = randomString();
                    if ( Math.random() < 0.1 ) {
                        srand = all.get((int)(Math.random()*all.size()));
                    }
                    control_list.add(srand);
                    list.add(srand);
                }
                if ( rand >= 0.3&&rand < 0.8 ) {//query
                    String srand = randomString();
                    if ( Math.random() < 0.6 ) {
                        srand = all.get((int)(Math.random()*all.size()));
                    }
                    same &= control_list.contains(srand)==list.contains(srand);
                }
                if ( rand >= 0.8 ) {//remove
                    String srand = randomString();
                    if ( Math.random() < 0.8 ) {
                        srand = all.get((int)(Math.random()*all.size()));
                    }
                    control_list.remove(srand);
                    list.remove(srand);
                }
            }
            System.out.println("Your list outputs compared to the built in java structure were the same? "+same);

        }
        if ( k == 5 ) {
            System.out.println(" SortedLinkedList::add(T) , SortedLinkedList::remove(T):boolean , T is Long");
            int n = jin.nextInt();
            SortedLinkedList<Long> list = new SortedLinkedList<Long>();
            ArrayList<Long> all = new ArrayList<Long>();
            all.add(684165189745L);
            for ( int i = 0 ; i < n ; ++i ) {
                double rand = Math.random();
                if ( rand < 0.7 ) { //addelement
                    Long srand = (long) (Math.random()*45668948941984L);
                    if ( Math.random() < 0.1 ) {
                        srand = all.get((int)(Math.random()*all.size()));
                    }
                    list.add(srand);
                }
                if ( rand >= 0.7 ) {
                    Long srand = (long) (Math.random()*45668948941984L);
                    if ( Math.random() < 0.1 ) {
                        srand = all.get((int)(Math.random()*all.size()));
                    }
                    list.remove(srand);
                }
            }
            System.out.println("Your program was really fast. You are a great developer!");
        }
    }
    private static String randomString() {
        byte buf[] = new byte[(int)(Math.random()*10)+1];
        new Random().nextBytes(buf);
        return new String(buf);
    }
}