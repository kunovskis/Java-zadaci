import java.util.*;

public class MinAndMax {
	public static void main(String[] args) throws ClassNotFoundException {
		Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
		System.out.println(strings);
		MinMax<Integer> ints = new MinMax<Integer>();
        for(int i = 0; i < n; ++i) {
           	int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
	}
}

class MinMax<T extends Comparable<T>>{
	T min,max;
	int counter,countMax,countMin;
	
	public MinMax(){
		min=null;
		max=null;
		counter=0;
		countMax=0;
		countMin=0;
	}
	
	public void update(T el){
        if(max == null && min == null){
            max = el;
            min = el;
            countMax++;
            countMin++;
            return;
        }
        if(max.equals(el) || min.equals(el)){
            if(max.equals(el))
                countMax++;
            else countMin++;
            return;
        }
        if(el.compareTo(max) < 0 && el.compareTo(min) > 0){
            counter++;
            return;
        }
        if(el.compareTo(max) > 0){
            if(!max.equals(min)){
                counter+=countMax;
                countMax = 1;
            }
            max = el;
            return;
        }
        if(el.compareTo(min) < 0){
            if(!max.equals(min)){
                counter+=countMin;
                countMin = 1;
            }
            min = el;
            return;
        }
    }
	
	public T max(){
		return max;
	}
	
	public T min(){
		return min;
	}
	
	public String toString(){
		return (min+" "+max+" "+counter+"\n");
	}
	
}

