import java.util.*;

public class BlockContainerTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int size = scanner.nextInt();
		BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
		scanner.nextLine();
		Integer lastInteger = null;
		for(int i = 0; i < n; ++i) {
			int element = scanner.nextInt();
			lastInteger = element;
			integerBC.add(element);
		}
		System.out.println("+++++ Integer Block Container +++++");
		System.out.println(integerBC);
		System.out.println("+++++ Removing element +++++");
		integerBC.remove(lastInteger);
		System.out.println("+++++ Sorting container +++++");
		integerBC.sort();
		System.out.println(integerBC);
		BlockContainer<String> stringBC = new BlockContainer<String>(size);
		String lastString = null;
		for(int i = 0; i < n; ++i) {
			String element = scanner.next();
			lastString = element;
			stringBC.add(element);
		}
		System.out.println("+++++ String Block Container +++++");
		System.out.println(stringBC);
		System.out.println("+++++ Removing element +++++");
		stringBC.remove(lastString);
		System.out.println("+++++ Sorting container +++++");
		stringBC.sort();
		System.out.println(stringBC);
	}
}

// Вашиот код овде

class BlockContainer<T extends Comparable<T>>{
	ArrayList<Block<T>> container;
	int max;
	
	public BlockContainer(int n){
		this.max=n;
		container = new ArrayList<Block<T>>();
	}
	
	public void add(T a){
		if(container==null){
			container.add(new Block<T> ());
			container.get(0).add(a);
		}
		else{
			for(int i=0; i<container.size(); i++){
				if(container.get(i).size()<max){
					container.get(i).add(a);
					container.get(i).sort();
					return;
				}
			}
		}
		container.add(new Block<T> ());
		container.get(container.size()-1).add(a);
	}
	
	public boolean remove(T a){
		boolean k = container.get(container.size()-1).remove(a);
		if(container.get(container.size()-1).isEmpty())
			container.remove(container.size()-1);
		return k;
	}
	
	public void sort(){
		ArrayList<T> temp = new ArrayList<T> ();
		for(Block b : container){
			temp.addAll(b.elements());
		}
		
		Collections.sort(temp);
		
		container = new ArrayList<Block<T>> ();
		
		for(T t:temp)
			add(t);
		
	}
	
	@Override
	public String toString(){
		String s="";
		for(Block<T> element : container){
			s+=element.toString();
			s+=",";
		}
		s=s.substring(0, s.length()-1);
		return s;
	}
	
}

class Block<T extends Comparable<T>> implements Comparator<T>{
	ArrayList<T> block;
	
	public Block(){
		block = new ArrayList<T> ();
	}

	public ArrayList<T> elements(){
		return block;
	}
	
	public void add(T e){
		block.add(e);
	}
	
	public int size(){
		return block.size();
	}
	
	public void sort(){
		Collections.sort(block);
	}
	
	public boolean remove(T e){
		int k=block.size();
		for(int i=0; i<block.size(); i++){
			if(block.get(i).equals(e)){
				block.remove(i);
				i--;
			}
		}
		return k == block.size() ? false:true;
	}
	
	public boolean isEmpty(){
		return block.isEmpty();
	}
	
	public String toString(){
		String s = "";
		s+="[";
		for(T element : block)
			{
				s+=element;
				s+=", ";
			}
		s=s.substring(0, s.length()-2);
		s+="]";
		return s;
	}

	@Override
	public int compare(T a, T b) {
		return a.compareTo(b);
	}
	
}
