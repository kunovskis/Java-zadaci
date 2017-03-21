import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * January 2016 Exam problem 1
 */
public class StudentRecordsTest {
  public static void main(String[] args) {
    System.out.println("=== READING RECORDS ===");
    StudentRecords studentRecords = new StudentRecords();
    int total = studentRecords.readRecords(System.in);
    System.out.printf("Total records: %d\n", total);
    System.out.println("=== WRITING TABLE ===");
    studentRecords.writeTable(System.out);
    System.out.println("=== WRITING DISTRIBUTION ===");
    studentRecords.writeDistribution(System.out);
  }
}

// your code here


class StudentRecords{
	
	class Student{
		String kod;
		String nasoka;
		ArrayList<Integer> ocenki;
		double prosek;
		
		public Student(String k, String n, ArrayList<Integer> o){
			ocenki=o;
			nasoka=n;
			kod=k;
			for(int i: o)
				prosek+=i;
			prosek = ((double) prosek) / o.size();
		}
		
		
		
	}
	
	ArrayList<Student> students;
	ArrayList<String> nasoki;
	
	public StudentRecords(){
		nasoki = new ArrayList<String> ();
		students = new ArrayList<Student> ();
	}
	
	int readRecords(InputStream inputStream){
		Scanner sc = new Scanner(inputStream);
		int b=0;
		while(sc.hasNextLine()){
			String s[] = sc.nextLine().split(" ");
			b++;
			int k=0;
			for(String q : nasoki)
				if(q.equals(s[1]))
					k=1;
			if(k==0)
				nasoki.add(s[1]);
			ArrayList<Integer> o = new ArrayList<Integer> ();
			for(int i=2; i<s.length; i++)
				o.add(Integer.parseInt(s[i]));
			students.add(new Student(s[0], s[1], o));
		}
		
		return b;
	}
	
	public void writeTable(OutputStream outputStream){
		Collections.sort(nasoki);
		Collections.sort(students, new Comparator<Student>(){
			@Override
			public int compare(Student a, Student b){
				if(a.prosek>b.prosek)
					return -1;
				else
					if(a.prosek<b.prosek)
						return 1;
					else
						return a.kod.compareTo(b.kod);
			}
		});
		for(String nasoka : nasoki){
			System.out.println(nasoka);
			for(Student s : students){
				if(s.nasoka.equals(nasoka)){
					System.out.format("%s %.2f\n", s.kod, s.prosek);
				}
			}
		}
		
	}
	
	public void writeDistribution(OutputStream outputStream){
		HashMap<String, ArrayList<Integer>> n = new HashMap<String, ArrayList<Integer>> ();
		for(String nasoka : nasoki){
			int sest=0, sedum=0, osum=0, devet=0, deset=0;
			for(Student s : students){
				if(s.nasoka.equals(nasoka)){
					for(int i : s.ocenki){
						if(i==6) sest++;
						else if(i==7) sedum++;
						else if(i==8) osum++;
						else if(i==9) devet++;
						else if(i==10) deset++;
					}
				}
			}
			ArrayList<Integer> o = new ArrayList<Integer> (5);
			o.add(sest);
			o.add(sedum);
			o.add(osum);
			o.add(devet);
			o.add(deset);
			n.put(nasoka, o);
		}
		
		n = MapUtil.sortByValue(n);
		for(String s: n.keySet()){
			System.out.println(s);
			for(int i=0; i<=4; i++){
				System.out.format("%2d | ", i+6);
				int f;
				if(n.get(s).get(i)%10 == 0)
					f = n.get(s).get(i)/10;
				else
					f = n.get(s).get(i)/10 +1;
				for(int j=0; j<f; j++)
					System.out.print("*");
				System.out.format("(%d)\n",n.get(s).get(i));
			}
		}
		
		
		
	}
	
}

class MapUtil{
    public static HashMap<String, ArrayList<Integer>> sortByValue( HashMap<String, ArrayList<Integer>> map )
    {
        List<Map.Entry<String, ArrayList<Integer>>> list =
            new LinkedList<Map.Entry<String, ArrayList<Integer>>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<String, ArrayList<Integer>>>()
        {
            public int compare( Map.Entry<String, ArrayList<Integer>> a, Map.Entry<String, ArrayList<Integer>> b )
            {
                if(a.getValue().get(4)>b.getValue().get(4))
                	return -1;
                else
                	if(a.getValue().get(4)<b.getValue().get(4))
                		return 1;
                	else
                		return a.getKey().compareTo(b.getKey());
            }
        } );
 
        HashMap<String, ArrayList<Integer>> result = new LinkedHashMap<String, ArrayList<Integer>>();
        for (Map.Entry<String, ArrayList<Integer>> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
}


