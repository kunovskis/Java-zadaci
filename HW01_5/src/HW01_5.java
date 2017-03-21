import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class HW01_5 {
	
	static List<Integer> getGrades(String[] n){
		ArrayList<Integer> list = new ArrayList<Integer> ();
		for(int i=1; i<n.length; i++)
			list.add(Integer.parseInt(n[i]));
		return list;
	}
	
	public static void main(String[] args) throws IOException{
		RandomAccessFile file = new RandomAccessFile("rezultati.csv", "r");
		String[] n = file.readLine().split(",");
		List<Subject> subjects = new ArrayList<Subject> ();
		List<Student> students = new ArrayList<Student> ();
		String line;
		for(int i = 1; i < n.length; i++){
			subjects.add(new Subject(n[i]));
		}
		while((line = file.readLine()) != null){
			n = line.split(",");
			students.add(new Student(Integer.parseInt(n[0]), getGrades(n)));
			for(int i=1; i<n.length; i++){
				subjects.get(i-1).addGrade(Integer.parseInt(n[i]));
			}
		}
		
		RandomAccessFile out = new RandomAccessFile("rezultati.tsv", "rw");
        System.out.print(String.format("Indeks\tProsek\n"));
		out.writeChars(String.format("Indeks\tProsek\n"));
		 
		for(Student k : students){
			System.out.print(k.pecati());
            out.writeChars(k.pecati());
		}
		 
        System.out.print(String.format("Predmet\tProsek\n"));
		out.writeChars(String.format("Predmet\tProsek\n"));
		for(Subject k : subjects){
            System.out.print(k.pecati());
			out.writeChars(k.pecati());
		}
		
		file.close();
		out.close();
	}
}

class Student{
	int index;
	List<Integer> grades;
	
	public Student(int i, List<Integer> l){
		index = i;
		grades = new ArrayList<Integer> ();
		for(int k : l)
			grades.add(k);
	}
	
	public String pecati(){
		double avg = 0;
		for(int i : grades){
			avg += (double) i;
		}
		avg /= grades.size();
		return String.format("%d\t%f\n", index, avg);
	}
}

class Subject{
	String name;
	List<Integer> grades;
	
	public Subject(String n){
		name = n;
		grades = new ArrayList<Integer> ();
	}
	
	public void addGrade(int k){
		grades.add(k);
	}
	
	public String pecati(){
		double avg = 0;
		for(int i : grades){
			avg += (double) i;
		}
		avg /= grades.size();
		return String.format("%s\t%f\n", name, avg);
	}
	
}