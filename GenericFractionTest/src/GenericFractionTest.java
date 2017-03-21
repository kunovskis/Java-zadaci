import java.util.Scanner;

public class GenericFractionTest {
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
        	GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
        	GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
        	GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch(ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }
        scanner.close();
    }

}

class ZeroDenominatorException extends Exception{
	public String getMessage(){
		return ("Denominator cannot be zero");
	}
}

class GenericFraction<T extends Number, U extends Number>{
	private T numerator;
	private U denominator;
	
	public GenericFraction(T n, U d) throws ZeroDenominatorException{
		if(d.doubleValue()==0)
			throw new ZeroDenominatorException();
		else{
			denominator=d;
			numerator=n;
		}
	}
	
	public GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf){
		double a1=this.numerator.doubleValue();
		double a2=gf.numerator.doubleValue();
		double b1=this.denominator.doubleValue();
		double b2=gf.denominator.doubleValue();
		double f=GenericFraction.NZS(b1, b2);
		double m=f/b1;
		double p=f/b2;
		double a=a1*m+a2*p;
		for(int i=1; i<Math.max(a, f); i++){
			if(a%i==0 && f%i==0){
				a/=i;
				f/=i;
			}
		}
		try{
			GenericFraction<Double,Double> t= new GenericFraction<Double, Double> (a, f);
			return t;
		}
		catch(ZeroDenominatorException e){
			System.out.println(e.getMessage());
		}
		return (GenericFraction<Double, Double>) this;
	}
	
	public double toDouble(){
		return (numerator.doubleValue()/denominator.doubleValue());
	}
	
	public String toString(){
		double a=this.numerator.doubleValue();
		double f=this.denominator.doubleValue();
		for(int i=1; i<Math.max(a, f); i++){
			if(a%i==0 && f%i==0){
				a/=i;
				f/=i;
			}
		}
		return (String.format("%.2f / %.2f", a, f));
	}
	
	public static double NZS(double a, double b){
		double k=1;
		for(int i=2; i<=Math.max(a, b); i++){
			if(a%i==0){
				if(b%i==0)
					{	k*=i;
						a=a/i;
						b=b/i;
						i=1;}
				else{
					k*=i;
					a=a/i;
					i=1;
				}
			}
			else if(b%i==0){
				k*=i;
				b=b/i;
				i=1;
			}
		}
		return k;
	}
	
}