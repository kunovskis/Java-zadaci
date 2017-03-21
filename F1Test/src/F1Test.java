import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class F1Test {

	public static void main(String[] args) {
		F1Race f1Race = new F1Race();
		f1Race.readResults(System.in);
		f1Race.printSorted(System.out);
	}

}

class F1Race {
	private List<String> name;
	private List<Time> lap1;
	private List<Time> lap2;
	private List<Time> lap3;
    
	public F1Race(){
		name=new ArrayList<String>();
		lap1=new ArrayList<Time>();
		lap2=new ArrayList<Time>();
		lap3=new ArrayList<Time>();
	}
	
	public void readResults(InputStream inputStream){
		Scanner in = new Scanner(inputStream);
		while(in.hasNextLine()){
			String s=in.nextLine();
			String niza[]=s.split(" ");
			name.add(niza[0]);
			Time t=new Time(niza[1]);
			lap1.add(t);
			t=new Time(niza[2]);
			lap2.add(t);
			t=new Time(niza[3]);
			lap3.add(t);
		}
		in.close();
	}
	
	public void printSorted(OutputStream otputStream){
		List<Time> fastest=new ArrayList<Time>();
		for(int i=0; i<name.size(); i++){
			Time t=new Time();
			t=Time.fastestThree(lap1.get(i), lap2.get(i), lap3.get(i));
			fastest.add(t);
		}
		for(int i=0; i<name.size()-1; i++){
			for(int j=i; j<name.size(); j++){
				if(!fastest.get(i).isFaster(fastest.get(j))){
					String s=name.get(i);
					name.set(i, name.get(j));
					name.set(j, s);
					Time t=new Time();
					t=fastest.get(i);
					fastest.set(i, fastest.get(j));
					fastest.set(j, t);
				}
			}
		}
		for(int i=0; i<name.size(); i++){
			System.out.println(String.format("%d. %-10s%10s", (i+1), name.get(i), fastest.get(i).toString()));
		}
	}
	
}

class Time{
	private int minutes;
	private int seconds;
	private int nanoseconds;
	
	public Time(){
		minutes=0;
		seconds=0;
		nanoseconds=0;
	}
	
	public Time(String s){
		String niza[]=s.split(":");
		this.minutes=Integer.parseInt(niza[0]);
		this.seconds=Integer.parseInt(niza[1]);
		this.nanoseconds=Integer.parseInt(niza[2]);
	}
	
	public String toString(){
		return String.format("%2d:%02d:%03d", minutes, seconds, nanoseconds);
	}
	
	public static Time fastestTwo(Time a, Time b){
		if(a.minutes<b.minutes)
			return a;
		else if(a.minutes>b.minutes)
			return b;
		else if(a.seconds<b.seconds)
			return a;
		else if(a.seconds>b.seconds)
			return b;
		else if(a.nanoseconds<b.nanoseconds)
			return a;
		else if(a.nanoseconds>b.nanoseconds)
			return b;
		else
			return a;
	}
	
	public static Time fastestThree(Time a, Time b, Time c){
		Time fast=Time.fastestTwo(a, b);
		return Time.fastestTwo(fast, c);
	}
	
	public boolean isFaster(Time a){
		if(this.minutes<a.minutes)
			return true;
		else if(minutes>a.minutes)
			return false;
		else if(seconds<a.seconds)
			return true;
		else if(seconds>a.seconds)
			return false;
		else if(nanoseconds<a.nanoseconds)
			return true;
		else if(nanoseconds>a.nanoseconds)
			return false;
		return true;
	}
	
}