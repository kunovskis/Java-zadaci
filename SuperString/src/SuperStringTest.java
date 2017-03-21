import java.util.*;
import java.io.*;

class SuperString {
    private LinkedList<String> list;
    private LinkedList<Integer> ints;
    int i = 0;

    public SuperString() {
            list = new LinkedList<String>();
            ints = new LinkedList<Integer>();
    }

    public void append(String s) {
            list.addLast(s);
            ints.addLast(i++);
    }

    public void insert(String s) {
            list.addFirst(s);
            ints.addFirst(i++);
    }

    public String toString() {
            StringBuilder sb = new StringBuilder();
            for (String s : list) {
                    sb.append(s);
            }
            return sb.toString();
    }
   
    public boolean contains (String s){
            return toString().contains(s);
    }
   
    public String reverseWord(String s){
            String rev = "";
            for (int i=s.length()-1; i>=0; i--){
                    rev+=s.charAt(i);
            }
            return rev;
    }
   
    public void reverse (){
            LinkedList<String> tmp  = new LinkedList<String>();
            Iterator<String> it = list.descendingIterator();
            while (it.hasNext()){
                    tmp.add(reverseWord(it.next()));
            }
            list.clear();
            list = new LinkedList<String>(tmp);
           
            LinkedList<Integer> tmp1  = new LinkedList<Integer>();
            Iterator<Integer> it2 = ints.descendingIterator();
            while (it2.hasNext()){
                    tmp1.add(it2.next());
            }
            ints.clear();
            ints = new LinkedList<Integer>(tmp1);          
    }
   
    public void removeLast(int k){
            for (int i=0; i<k; i++){
                    if (ints.getFirst()>ints.getLast()){
                            list.removeFirst();
                            ints.removeFirst();
                    }
                    else {
                            list.removeLast();
                            ints.removeLast();
                    }
            }
    }

}

public class SuperStringTest {
	
	public static void main(String[] args) {
		Scanner jin = new Scanner(System.in);
		int k = jin.nextInt();
		if (  k == 0 ) {
			SuperString s = new SuperString();
			while ( true ) {
				int command = jin.nextInt();
				if ( command == 0 ) {//append(String s)
					s.append(jin.next());
				}
				if ( command == 1 ) {//insert(String s)
					s.insert(jin.next());
				}
				if ( command == 2 ) {//contains(String s)
					System.out.println(s.contains(jin.next()));
				}
				if ( command == 3 ) {//reverse()
					s.reverse();
				}
				if ( command == 4 ) {//toString()
					System.out.println(s);
				}
				if ( command == 5 ) {//removeLast(int k)
					s.removeLast(jin.nextInt());
				}
				if ( command == 6 ) {//end
					break;
				}
			}
		}
	}

}
