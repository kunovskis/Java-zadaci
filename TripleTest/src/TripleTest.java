import java.util.*;

public class TripleTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		int b = scanner.nextInt();
		int c = scanner.nextInt();
		Triple<Integer> tInt = new Triple<Integer>(a, b, c);
		System.out.printf("%.2f\n", tInt.max());
		System.out.printf("%.2f\n", tInt.avarage());
		tInt.sort();
		System.out.println(tInt);
		float fa = scanner.nextFloat();
		float fb = scanner.nextFloat();
		float fc = scanner.nextFloat();
		Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
		System.out.printf("%.2f\n", tFloat.max());
		System.out.printf("%.2f\n", tFloat.avarage());
		tFloat.sort();
		System.out.println(tFloat);
		double da = scanner.nextDouble();
		double db = scanner.nextDouble();
		double dc = scanner.nextDouble();
		Triple<Double> tDouble = new Triple<Double>(da, db, dc);
		System.out.printf("%.2f\n", tDouble.max());
		System.out.printf("%.2f\n", tDouble.avarage());
		tDouble.sort();
		System.out.println(tDouble);
	}
}

class Triple<T extends Number> {
    private T first, second, third;
    public Triple(T first, T second, T third) {
            this.first = first;
            this.second = second;
            this.third = third;
    }
    public double max() {
            return Math.max(Math.max(first.doubleValue(), second.doubleValue()),
                            third.doubleValue());
    }
    public double avarage() {
            return (first.doubleValue() + second.doubleValue() + third
                            .doubleValue()) / 3;
    }
    public String toString() {
            return String.format("%.2f %.2f %.2f", first.doubleValue(), second.doubleValue(), third.doubleValue());
    }
    public T swap(T t1, T t2) {
            return t1;
    }
    public void sort() {
            if (first.doubleValue() > second.doubleValue())
                    first = swap(second, second = first);
            if (second.doubleValue() > third.doubleValue())
                    second = swap(third, third = second);
            if (first.doubleValue() > second.doubleValue())
                    first = swap(second, second = first);
    }
}