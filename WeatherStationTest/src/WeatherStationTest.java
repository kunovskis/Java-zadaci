import java.text.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class WeatherStationTest {
	public static void main(String[] args) throws ParseException {
		Scanner scanner = new Scanner(System.in);
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
		WeatherStation ws = new WeatherStation(n);
		while (true) {
			String line = scanner.nextLine();
			if (line.equals("=====")) {
				break;
			}
			String[] parts = line.split(" ");
			float temp = Float.parseFloat(parts[0]);
			float wind = Float.parseFloat(parts[1]);
			float hum = Float.parseFloat(parts[2]);
			float vis = Float.parseFloat(parts[3]);
			line = scanner.nextLine();
			Date date = df.parse(line);
			ws.addMeasurment(temp, wind, hum, vis, date);
		}
		String line = scanner.nextLine();
		Date from = df.parse(line);
		line = scanner.nextLine();
		Date to = df.parse(line);
		scanner.close();
		System.out.println(ws.total());
		try {
			ws.status(from, to);
		} catch (RuntimeException e) {
			System.out.println(e);
		}
	}
}



class Data implements Comparable<Data> {
	 
    private float humidity;
    private float wind;
    private float visibility;
    float temperature;
    Date time;

    public Data(float temperature, float humidity, float wind,
                    float visibility, Date time) {
            this.temperature = temperature;
            this.humidity = humidity;
            this.wind = wind;
            this.visibility = visibility;
            this.time = time;
    }

    public boolean checkMinutes(Date d) {
            if (Double.compare(
                            TimeUnit.MILLISECONDS.toMinutes(d.getTime() - time.getTime()),
                            2.5) < 0)
                    return true;
            else
                    return false;
    }

    public boolean checkDays(Date d, int days) {
            long result = Long.compare(d.getTime() - time.getTime(),
                            TimeUnit.DAYS.toMillis(days));
            if (result >= 0)
                    return true;
            else
                    return false;
    }

    @Override
    public int compareTo(Data o) {
            return time.compareTo(o.time);
    }

    public String toString() {
            return String.format("%.1f %.1f km/h %.1f%% %.1f km %s\n", temperature, wind, humidity, visibility, time.toString());
    }
}

class WeatherStation {
    private int days;
    private int total;
    private ArrayList<Data> datas;

    public WeatherStation(int days) {
            this.days = days;
            this.total = 0;
            this.datas = new ArrayList<Data>();
    }

    public void addMeasurment(float temperature, float wind, float humidity,
                    float visibility, Date time) {
            for (int i = 0; i < datas.size(); i++)
                    if (datas.get(i).checkMinutes(time))
                            return;
            datas.add(new Data(temperature, humidity, wind, visibility, time));
            total++;
            for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).checkDays(time, days)) {
                            datas.remove(i);
            i--;
                            total--;
                    }
            }

    }

    public int total() {
            return total;
    }

    public void status(Date from, Date to) {
            Collections.sort(datas);
            float average = 0;
            int counter = 0, numelements = 0;
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).time.equals(from) || datas.get(i).time.after(from)
                                    || datas.get(i).time.equals(to)) {
                            average += datas.get(i).temperature;
                            s.append(datas.get(i).toString());
                            numelements++;
                            if (datas.get(i).time.equals(from)
                                            || datas.get(i).time.equals(to))
                                    counter++;

                            if (datas.get(i).time.equals(to))
                                    break;
                    }
            }
            if (counter == 0 || from.after(to))
                    throw new RuntimeException();
            s.append(String.format("Average temperature: %.2f", average / numelements));
            System.out.println(s.toString());
    }
}