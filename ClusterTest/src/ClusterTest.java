import java.util.*;
import java.lang.*;

/**
 * January 2016 Exam problem 2
 */
public class ClusterTest {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Cluster<Point2D> cluster = new Cluster<>();
    int n = scanner.nextInt();
    scanner.nextLine();
    for (int i = 0; i < n; ++i) {
      String line = scanner.nextLine();
      String[] parts = line.split(" ");
      long id = Long.parseLong(parts[0]);
      float x = Float.parseFloat(parts[1]);
      float y = Float.parseFloat(parts[2]);
      cluster.addItem(new Point2D(id, x, y));
    }
    int id = scanner.nextInt();
    int top = scanner.nextInt();
    cluster.near(id, top);
    scanner.close();
  }
}

class Cluster<T extends Point2D>{
	ArrayList<T> elements;
	
	public Cluster(){
		elements = new ArrayList<T> ();
	}
	
	public void addItem(T element){
		elements.add(element);
	}
	
	public void near(long id, int top){
		T now = elements.get(0);
		for(T element : elements){
			if (element.getID() == id){
				now = element;
			}
		}
		
		float x = now.getX();
		float y = now.getY();
		
		/*ArrayList<Double> distance = new ArrayList<Double> ();
		int i=0;
		
		
		for(T element : elements){
			double dist = (double) Math.sqrt( ((x - element.getX()) * (x - element.getX())) + ((y - element.getY()) * (y - element.getY())) );
			distance.add(dist);
			
			Collections.sort(distance);
				
		}
		
		for(int j=1; j<=top; j++){
			System.out.format("%d -> %.3f\n", j, distance.get(j));
		}*/
		
		HashMap<Long, Double> distance = new HashMap<Long, Double>();
		
		for(T element : elements){
			double dist = (double) Math.sqrt( ((x - element.getX()) * (x - element.getX())) + ((y - element.getY()) * (y - element.getY())) );
			distance.put(element.getID(), dist);
		}
		
		distance = MapUtil.sortByValue(distance);
		
		int i=0;
		
		for(HashMap.Entry<Long, Double> entry : distance.entrySet()){
			if(i==0){
				i++;
			}
			else if(i<=top){
				System.out.format("%d. %d -> %.3f\n", i, entry.getKey(), entry.getValue());
				i++;
			}
			else 
				return;
		}
		
	}
}
	

class Point2D{
	long id;
	float x;
	float y;
	
	Point2D(long id, float x, float y){
		this.id=id;
		this.x=x;
		this.y=y;
	}
	
	public long getID(){
		return id;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
}

class MapUtil
{
    public static <K, V extends Comparable<? super V>> HashMap<K, V> 
        sortByValue( HashMap<K, V> map )
    {
        List<Map.Entry<K, V>> list =
            new LinkedList<Map.Entry<K, V>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return (o1.getValue()).compareTo( o2.getValue() );
            }
        } );

        HashMap<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
}
