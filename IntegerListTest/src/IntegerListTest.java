import java.util.*;

public class IntegerListTest {
	
	public static void main(String[] args) {
		Scanner jin = new Scanner(System.in);
		int k = jin.nextInt();
		if ( k == 0 ) { //test standard methods
			int subtest = jin.nextInt();
			if ( subtest == 0 ) {
				IntegerList list = new IntegerList();
				while ( true ) {
					int num = jin.nextInt();
					if ( num == 0 ) {
						list.add(jin.nextInt(), jin.nextInt());
					}
					if ( num == 1 ) {
						list.remove(jin.nextInt());
					}
					if ( num == 2 ) {
						print(list);
					}
					if ( num == 3 ) {
						break;
					}
				}
			}
			if ( subtest == 1 ) {
				int n = jin.nextInt();
				Integer a[] = new Integer[n];
				for ( int i = 0 ; i < n ; ++i ) {
					a[i] = jin.nextInt();
				}
				IntegerList list = new IntegerList(a);
				print(list);
			}
		}
		if ( k == 1 ) { //test count,remove duplicates, addValue
			int n = jin.nextInt();
			Integer a[] = new Integer[n];
			for ( int i = 0 ; i < n ; ++i ) {
				a[i] = jin.nextInt();
			}
			IntegerList list = new IntegerList(a);
			while ( true ) {
				int num = jin.nextInt();
				if ( num == 0 ) { //count
					System.out.println(list.count(jin.nextInt()));
				}
				if ( num == 1 ) {
					list.removeDuplicates();
				}
				if ( num == 2 ) {
					print(list.addValue(jin.nextInt()));
				}
				if ( num == 3 ) {
					list.add(jin.nextInt(), jin.nextInt());
				}
				if ( num == 4 ) {
					print(list);
				}
				if ( num == 5 ) {
					break;
				}
			}
		}
		if ( k == 2 ) { //test shiftRight, shiftLeft, sumFirst , sumLast
			int n = jin.nextInt();
			Integer a[] = new Integer[n];
			for ( int i = 0 ; i < n ; ++i ) {
				a[i] = jin.nextInt();
			}
			IntegerList list = new IntegerList(a);
			while ( true ) {
				int num = jin.nextInt();
				if ( num == 0 ) { //count
					list.shiftLeft(jin.nextInt(), jin.nextInt());
				}
				if ( num == 1 ) {
					list.shiftRight(jin.nextInt(), jin.nextInt());
				}
				if ( num == 2 ) {
					System.out.println(list.sumFirst(jin.nextInt()));
				}
				if ( num == 3 ) {
					System.out.println(list.sumLast(jin.nextInt()));
				}
				if ( num == 4 ) {
					print(list);
				}
				if ( num == 5 ) {
					break;
				}
			}
		}
	}
	
	public static void print(IntegerList il) {
		if ( il.size() == 0 ) System.out.print("EMPTY");
		for ( int i = 0 ; i < il.size() ; ++i ) {
			if ( i > 0 ) System.out.print(" ");
			System.out.print(il.get(i));
		}
		System.out.println();
	}

}

class IntegerList{
	private ArrayList<Integer> list;
	
	public IntegerList(){
		list = new ArrayList<Integer> ();
	}
	
	public IntegerList(Integer a[]){
		list = new ArrayList<Integer> ();
		for(Integer i:a)
			list.add(i);
	}
	
	public void add(int el, int idx){
		if(idx>list.size()){
			for(int i=list.size(); i<idx; i++){
				list.add(i, 0);
			}
		}
		list.add(idx,el);
	}
	
	public int remove(int idx){
		int a=list.get(idx);
		list.remove(idx);
		return a;
	}
	
	public void set(int el, int idx){
		list.remove(idx);
		list.add(idx, el);
	}
	
	public int get(int idx){
		return list.get(idx);
	}
	
	public int size(){
		return list.size();
	}
	
	public int count(int el){
		int counter=0;
		for(int i : list)
			if(i==el)
				counter++;
		return counter;
	}
	
	public void removeDuplicates(){
		for(int i=list.size()-1; i>=1; i--){
			for(int j=i-1; j>=0; j--){
				if(list.get(i)==list.get(j)){
					list.remove(j);
					i--;
				}
			}
		}
	}
	
	public int sumFirst(int k){
		int sum=0;
		if(k>list.size())
			k=list.size();
		for(int i=0; i<k; i++)
			sum+=list.get(i);
		return sum;
	}
	
	public int sumLast(int k){
		int sum=0;
		if(k>list.size())
			k=list.size();
		for(int i=0; i<k; i++)
			sum+=list.get(list.size()-1-i);
		return sum;
	}
	
    public void shiftRight(int idx,int k) {
        int golemina_lista=list.size();
       
        int pom=list.remove(idx);
       
        list.add((idx+k)%golemina_lista,pom);
    }
 
    public void shiftLeft(int idx,int k) {
        k=k%list.size();
       
        int size=list.size();
        int pom=list.remove(idx);
        if (idx-k>=0) {
            list.add((idx-k)%size,pom);
        } else {
            list.add((size+(idx-k))%size,pom);
        }
    }
 
    public IntegerList addValue(int value) {
        IntegerList pom=new IntegerList();
       
        for (int i=0; i<list.size(); i++) {
            pom.add(list.get(i)+value,i);
        }
        return pom;
    }
	
}
