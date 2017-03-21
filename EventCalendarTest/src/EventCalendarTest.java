import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

public class EventCalendarTest {
	public static void main(String[] args) throws ParseException {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		scanner.nextLine();
		int year = scanner.nextInt();
		scanner.nextLine();
		EventCalendar eventCalendar = new EventCalendar(year);
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		for (int i = 0; i < n; ++i) {
			String line = scanner.nextLine();
			String[] parts = line.split(";");
			String name = parts[0];
			String location = parts[1];
			Date date = df.parse(parts[2]);
			try {
				eventCalendar.addEvent(name, location, date);
			} catch (WrongDateException e) {
				System.out.println(e.getMessage());
			}
		}
		Date date = df.parse(scanner.nextLine());
		eventCalendar.listEvents(date);
		eventCalendar.listByMonth();
	}
}

class Event implements Comparable<Event> {
	
	String name;
	String location;
	Date date;
	
	public Event(String name, String location, Date date) {
		this.name = name;
		this.location = location;
		this.date = date;
	}
	
	@Override
	public int compareTo(Event o) {
		int value = Long.compare(date.getTime(), o.date.getTime());
        if (value == 0)
            value = name.compareTo(o.name);
        return value;
	}
    
    @Override
	public String toString() {
        DateFormat df = new SimpleDateFormat("dd MMM, YYY HH:mm");
        String dateFormatted = df.format(date);
		return String.format("%s at %s, %s", dateFormatted,location,name);
	}
	
}

class EventCalendar {
	
	int year;
	Map<Integer,Integer> monthlyEvents;
	Map<String,TreeSet<Event>> events;
	
	
	public EventCalendar(int year) {
		this.year = year;
		monthlyEvents = new HashMap<>();
		events = new HashMap<>();
	}
	
	public void addEvent(String name, String location, Date date) throws WrongDateException {
		
		if (date.getYear() != (year-1900))
			throw new WrongDateException(date);
		
		monthlyEvents.computeIfPresent(date.getMonth(), (key,value) -> ++value);
		monthlyEvents.putIfAbsent(date.getMonth(), 1);
		
        
        String time = String.format("%d %d",date.getDate(), date.getMonth());
        
		events.computeIfPresent(time, (key,value) -> {
			value.add(new Event(name,location,date));
			return value;
		});
		
		events.computeIfAbsent(time, (key) -> {
			TreeSet<Event> set = new TreeSet<>();
			set.add(new Event(name,location,date));
			return set;
	});
		
	}
	
	public void listEvents(Date date) {
        
		String time = String.format("%d %d",date.getDate(),date.getMonth());		
		
        TreeSet<Event> ev = events.get(time);
		if (ev == null) {
			System.out.println("No events on this day!");
		}
		else {
			ev.stream()
			.forEach(System.out :: println);
		}
	}
	
	public void listByMonth() {
		
		for (int i=0; i<12; ++i) {
			
			if (monthlyEvents.containsKey(i))
				System.out.printf("%d : %d\n",i+1,monthlyEvents.get(i));
			else 
				System.out.printf("%d : %d\n",i+1,0);
			
		}
	}
}

class WrongDateException extends Exception {
	
	WrongDateException(Date date) {
		super(String.format("Wrong date: %s", date.toString().replaceAll("GMT","UTC")));
	}
}