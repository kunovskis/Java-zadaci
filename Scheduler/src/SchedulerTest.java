import java.util.*;

public class SchedulerTest {
	
	
	public static void main(String[] args) {
		Scanner jin = new Scanner(System.in);
		int k = jin.nextInt();
		if ( k == 0 ) {
			Scheduler<String> scheduler = new Scheduler<String>();
			Date now = new Date();
			scheduler.add(new Date(now.getTime()-7200000), jin.next());
			scheduler.add(new Date(now.getTime()-3600000), jin.next());
			scheduler.add(new Date(now.getTime()-14400000), jin.next());
			scheduler.add(new Date(now.getTime()+7200000), jin.next());
			scheduler.add(new Date(now.getTime()+14400000), jin.next());
			scheduler.add(new Date(now.getTime()+3600000), jin.next());
			scheduler.add(new Date(now.getTime()+18000000), jin.next());
			System.out.println(scheduler.getFirst());
			System.out.println(scheduler.getLast());
		}
		if ( k == 3 ) { //test Scheduler with String
			Scheduler<String> scheduler = new Scheduler<String>();
			Date now = new Date();
			scheduler.add(new Date(now.getTime()-7200000), jin.next());
			scheduler.add(new Date(now.getTime()-3600000), jin.next());
			scheduler.add(new Date(now.getTime()-14400000), jin.next());
			scheduler.add(new Date(now.getTime()+7200000), jin.next());
			scheduler.add(new Date(now.getTime()+14400000), jin.next());
			scheduler.add(new Date(now.getTime()+3600000), jin.next());
			scheduler.add(new Date(now.getTime()+18000000), jin.next());
			System.out.println(scheduler.next());
			System.out.println(scheduler.last());
			ArrayList<String> res = scheduler.getAll(new Date(now.getTime()-10000000), new Date(now.getTime()+17000000));
			Collections.sort(res);
			for ( String t : res ) {
				System.out.print(t+" , ");
			}
		}
		if ( k == 4 ) {//test Scheduler with ints complex
			Scheduler<Integer> scheduler = new Scheduler<Integer>();
			int counter = 0;
			ArrayList<Date> to_remove = new ArrayList<Date>();
			
			while ( jin.hasNextLong() ) {
				Date d = new Date(jin.nextLong());
				int i = jin.nextInt();
				if ( (counter&7) == 0 ) {
					to_remove.add(d);
				}
				scheduler.add(d,i);
				++counter;
			}
			jin.next();
			
			while ( jin.hasNextLong() ) {
				Date l = new Date(jin.nextLong());
				Date h = new Date(jin.nextLong());
				ArrayList<Integer> res = scheduler.getAll(l,h);
				Collections.sort(res);
				System.out.println(l+" <: "+print(res)+" >: "+h);
			}
			System.out.println("test");
			ArrayList<Integer> res = scheduler.getAll(new Date(0),new Date(Long.MAX_VALUE));
			Collections.sort(res);
			System.out.println(print(res));
			for ( Date d : to_remove ) {
				scheduler.remove(d);
			}
			res = scheduler.getAll(new Date(0),new Date(Long.MAX_VALUE));
			Collections.sort(res);
			System.out.println(print(res));
		}
	}

	private static <T> String print(ArrayList<T> res) {
		if ( res == null || res.size() == 0 ) return "NONE";
		StringBuffer sb = new StringBuffer();
		for ( T t : res ) {
			sb.append(t+" , ");
		}
		return sb.substring(0, sb.length()-3);
	}

    
}

class Scheduler<T>{
	private Map<Date, T> map;
	
	public Scheduler(){
		map = new HashMap<Date, T> ();
	}
	
	public void add(Date d, T t){
		map.put(d, t);
	}
	
	public boolean remove(Date d){
		if(map.containsKey(d)){
			map.remove(d);
			return true;
		}
		return false;
	}
	
	public T next(){
		int k=0;
		Date current = new Date();
		Date date = new Date();
		for(Date key : map.keySet()){
			if(k==0 && key.after(current)){
				date=key;
				k=1;
			}
			else{
				if(key.after(current) && key.before(date))
					date=key;
			}
		}
		return map.get(date);
	}
	
	public T last(){
		int k=0;
		Date current = new Date();
		Date date = new Date();
		for(Date key : map.keySet()){
			if(k==0 && key.before(current)){
				date=key;
				k=1;
			}
			else{
				if(key.before(current) && key.after(date))
					date=key;
			}
		}
		return map.get(date);
	}
	
	public ArrayList<T> getAll(Date begin, Date end){
		ArrayList<T> list=new ArrayList<T> ();
		for(Date key : map.keySet()){
			if(key.after(begin) && key.before(end))
				list.add(map.get(key));
		}
		return list;
	}
	
	public T getFirst(){
		int k=0;
		Date date=new Date();
		T first = null;
		for(Date key : map.keySet()){
			if(k==0){
				date = key;
				first=map.get(date);
				k=1;
			}
			else{
				if(date.after(key)){
					date = key;
					first=map.get(date);
				}
			}
		}
		return first;
	}
	
	public T getLast(){
		int k=0;
		Date date=new Date();
		T last = null;
		for(Date key : map.keySet()){
			if(k==0){
				date = key;
				last=map.get(date);
				k=1;
			}
			else{
				if(date.before(key)){
					date = key;
					last=map.get(date);
				}
			}
		}
		return last;
	}
	
	
}

