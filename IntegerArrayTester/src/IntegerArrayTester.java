import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

final class IntegerArray{
	private int[] a;
	
	IntegerArray(int[] b){
		this.a=new int[b.length];
		for(int i=0; i<b.length; i++)
			this.a[i]=b[i];
	}
	
	public int length(){
		return this.a.length;
	}
	
	public int getElementAt(int i){
		return this.a[i];
	}
	
	public int sum(){
		int suma=0;
		for(int i=0; i<this.a.length; i++)
			suma+=this.a[i];
		return suma;
	}
	
	public double average(){
		return this.sum()*1.0/this.a.length;
	}
	
	public IntegerArray getSorted(){
		int[] b=new int[this.a.length];
		for(int i=0; i<this.a.length; i++)
			b[i]=this.a[i];
		Arrays.sort(b);
		IntegerArray q=new IntegerArray(b);
		return q;
	}
	
	public IntegerArray concat(IntegerArray ia){
		int[] b=new int [this.a.length+ia.a.length];
		for(int i=0; i<this.a.length; i++)
			b[i]=this.a[i];
		for(int i=0; i<ia.a.length; i++)
			b[i+this.a.length]=ia.a[i];
		IntegerArray q=new IntegerArray(b);
		return q;
	}
	
	public String toString(){
		String s=new String();
		s+="[";
		for(int i=0; i<this.a.length-1; i++)
			s+=this.a[i]+", ";
		s+=this.a[this.a.length-1]+"]";
		return s;
	}
	
	public boolean equals(IntegerArray ia){
		if(this.a.length!=ia.a.length)
			return false;
		else{
			for(int i=0; i<this.a.length; i++){
				if(this.a[i]!=ia.a[i])
					return false;
			}
		}
		return true;
	}
	
}

class ArrayReader {
    public static IntegerArray readIntegerArray(InputStream input) throws IOException {
 
        int arrLength = 0;
        String number = "";
        char digitChar = (char)input.read();
 
        if(Character.isDigit(digitChar)){
            number += digitChar;
            digitChar = (char)input.read();
            while (Character.isDigit(digitChar)){
                number += digitChar;
                digitChar = (char)input.read();
            }
        }
        else
        if(!Character.isDigit(digitChar)) {
            while (!Character.isDigit((char) digitChar)) {
                digitChar = (char) input.read();
            }
 
            if(Character.isDigit(digitChar)){
                number += digitChar;
                digitChar = (char)input.read();
                while (Character.isDigit(digitChar)){
                    number += digitChar;
                    digitChar = (char)input.read();
                }
            }
        }
 
        for(int i=0; i<number.length(); i++){
            arrLength += (int)number.charAt(i)-48;
            if(i<number.length()-1){
                arrLength *= 10;
            }
        }
 
        int[] arr = new int[arrLength];
 
        number = "";
 
        for(int counter = 0; counter < arr.length;) {
            boolean isNegative = false;
 
            digitChar = (char) input.read();
            while (!Character.isDigit(digitChar) && digitChar != '-') {
                digitChar = (char) input.read();
            }
 
            if (digitChar == '-') isNegative = true;
 
            if(isNegative){
                digitChar = (char)input.read();
                while (Character.isDigit(digitChar)){
                    number += digitChar;
                    digitChar = (char)input.read();
                }
            }
 
            else
            {
                number += digitChar;
                digitChar = (char)input.read();
                while (Character.isDigit(digitChar)){
                    number += digitChar;
                    digitChar = (char)input.read();
                }
            }
 
            int digit = 0;
            digit = Integer.parseInt(number);
 
            if(isNegative) {
                arr[counter] = -digit;
            }
            else {
                arr[counter] = digit;
            }
            counter++;
            number = "";
        }
 
        IntegerArray newIntArr = new IntegerArray(arr);
        return newIntArr;
    }
}


public class IntegerArrayTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        IntegerArray ia = null;
        switch (s) {
            case "testSimpleMethods":
                ia = new IntegerArray(generateRandomArray(scanner.nextInt()));
                testSimpleMethods(ia);
                break;
            case "testConcat":
                testConcat(scanner);
                break;
            case "testEquals":
                testEquals(scanner);
                break;
            case "testSorting":
                testSorting(scanner);
                break;
            case "testReading":
                testReading(new ByteArrayInputStream(scanner.nextLine().getBytes()));
                break;
            case "testImmutability":
                int a[] = generateRandomArray(scanner.nextInt());
                ia = new IntegerArray(a);
                testSimpleMethods(ia);
                testSimpleMethods(ia);
                IntegerArray sorted_ia = ia.getSorted();
                testSimpleMethods(ia);
                testSimpleMethods(sorted_ia);
                sorted_ia.getSorted();
                testSimpleMethods(sorted_ia);
                testSimpleMethods(ia);
                a[0] += 2;
                testSimpleMethods(ia);
            try{ia = ArrayReader.readIntegerArray(new ByteArrayInputStream(integerArrayToString(ia).getBytes()));
                testSimpleMethods(ia);}
            catch(Exception e){System.out.println("Exception");}
                break;
        }
        scanner.close();
    }

    static void testReading(InputStream in) {
        try {
            IntegerArray read = ArrayReader.readIntegerArray(in);
                    System.out.println(read);
        }
        catch(IOException e){System.out.println("exception");}
    }

    static void testSorting(Scanner scanner) {
        int[] a = readArray(scanner);
        try{IntegerArray ia = new IntegerArray(a);
            System.out.println(ia.getSorted());}
        catch(Exception e){
        	System.out.println("Exception!");
        }
    }

    static void testEquals(Scanner scanner) {
        int[] a = readArray(scanner);
        int[] b = readArray(scanner);
        int[] c = readArray(scanner);
        IntegerArray ia = new IntegerArray(a);
        IntegerArray ib = new IntegerArray(b);
        IntegerArray ic = new IntegerArray(c);
        System.out.println(ia.equals(ib));
        System.out.println(ia.equals(ic));
        System.out.println(ib.equals(ic));
    }

    static void testConcat(Scanner scanner) {
        int[] a = readArray(scanner);
        int[] b = readArray(scanner);
        IntegerArray array1 = new IntegerArray(a);
        IntegerArray array2 = new IntegerArray(b);
        IntegerArray concatenated = array1.concat(array2);
        System.out.println(concatenated);
    }

    static void testSimpleMethods(IntegerArray ia) {
        System.out.print(integerArrayToString(ia));
        System.out.println(ia);
        System.out.println(ia.sum());
        System.out.printf("%.2f\n", ia.average());
    }


    static String integerArrayToString(IntegerArray ia) {
        StringBuilder sb = new StringBuilder();
        sb.append(ia.length()).append('\n');
        for (int i = 0; i < ia.length(); ++i)
            sb.append(ia.getElementAt(i)).append(' ');
        sb.append('\n');
        return sb.toString();
    }

    static int[] readArray(Scanner scanner) {
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = scanner.nextInt();
        }
        return a;
    }


    static int[] generateRandomArray(int k) {
        Random rnd = new Random(k);
        int n = rnd.nextInt(8) + 2;
        int a[] = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = rnd.nextInt(20) - 5;
        }
        return a;
    }

}
