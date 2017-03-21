import java.util.*;
import java.io.*;

public class TimesTest {

	public static void main(String[] args) {
		TimeTable timeTable = new TimeTable();
		try {
			timeTable.readTimes(System.in);
		} catch (UnsupportedFormatException e) {
			System.out.println("UnsupportedFormatException: " + e.getMessage());
		} catch (InvalidTimeException e) {
			System.out.println("InvalidTimeException: " + e.getMessage());
		}
		System.out.println("24 HOUR FORMAT");
		timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
		System.out.println("AM/PM FORMAT");
		timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
	}

}

enum TimeFormat {
	FORMAT_24, FORMAT_AMPM
}

class TimeTable{
	private List<Time> time;
	
	public TimeTable(){
		time=new ArrayList<Time>();
	}
	
	void readTimes(InputStream inputStream) throws UnsupportedFormatException, InvalidTimeException{
		Scanner in=new Scanner(System.in);
		String s;
		String n[],p[];
		int h,m;
		while(in.hasNextLine()){
			s=in.nextLine();
			n=s.split(" ");
			for(int i=0; i<n.length; i++){
				if(n[i].contains(":")){
					p=n[i].split(":");
					h=Integer.parseInt(p[0]);
					m=Integer.parseInt(p[1]);
					if(0<=h && h<=23 && 0<=m && m<=59)
						time.add(new Time(h, m));
					else
						throw new InvalidTimeException(n[i]);
				}
				else if(n[i].contains(".")){
					p=n[i].split("\\.");
					h=Integer.parseInt(p[0]);
					m=Integer.parseInt(p[1]);
					if(0<=h && h<=23 && 0<=m && m<=59)
						time.add(new Time(h, m));
					else
						throw new InvalidTimeException(n[i]);
				}
				else
					throw new UnsupportedFormatException(n[i]);
			}
		}
		in.close();
	}
	
	public void writeTimes(OutputStream optputStream, TimeFormat format){
		for(int i=0; i<time.size()-1; i++){
			for(int j=i; j<time.size(); j++){
				if(!time.get(j).isAfter(time.get(i))){
					Time t=time.get(j);
					time.set(j, time.get(i));
					time.set(i, t);
				}
			}
		}
		if(format==TimeFormat.FORMAT_24){
			for(int i=0; i<time.size(); i++){
				System.out.println(time.get(i).tf());
			}
		}
		else{
			for(int i=0; i<time.size(); i++){
				System.out.println(time.get(i).AMPM());
			}
		}
	}
	
}

class UnsupportedFormatException extends Exception{
	private String s;
	
	public UnsupportedFormatException(String a){
		s=a;
	}
	
	@Override
	public String getMessage(){
		return (s);
	}
}

class InvalidTimeException extends Exception{
	private String s;
	
	public InvalidTimeException(String a){
		s=a;
	}
	
	@Override
	public String getMessage(){
		return (s);
	}
}

class Time{
	int hours,minutes;
	
	public Time(int h, int m){
		this.hours=h;
		this.minutes=m;
	}
	
	public String tf(){
		return (String.format("%2d:%02d", hours, minutes));
	}
	
	public boolean isAfter(Time t){
		if(this.hours>t.hours) return true;
		else if(this.hours<t.hours) return false;
		else if(this.minutes>=t.minutes) return true;
		else return false;
	}
	
	public String AMPM(){
		if(hours==0) return (String.format("%2d:%02d AM", hours+12, minutes));
		else if(1<=hours && hours<=11) return (String.format("%2d:%02d AM", hours, minutes));
		else if(hours==12) return (String.format("%2d:%02d PM", hours, minutes));
		else return (String.format("%2d:%02d PM", hours-12, minutes));
	}
}