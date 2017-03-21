package LDS;

import java.util.Scanner;

public class LDS {
	

	private static int najdolgaOpagackaSekvenca(int[] a) {
		
        int longest [] = new int [ a.length ];
        for( int i = 0; i < longest.length; i++){
        	longest[i] = 1;
        }
        for(int i = 1; i < a.length; i++){
        	for( int j = 0; j < i; j++){
        		if ( a[i] < a[j] && longest[i] <= longest[j] ){
        			longest[i] = longest[j] + 1;
        		}
        	}
        }
        return max(longest);
	}
	private static int max(int [] a){
		int max = a[0];
		for(int i = 1; i < a.length; i++){
			if(a[i]>max)
				max = a[i];
		}
		return max;
	}
	
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		
		int n = stdin.nextInt();
		int a[] = new int[n];
		for (int i = 0; i < a.length; i++) {
			a[i] = stdin.nextInt();
		}
		System.out.println(najdolgaOpagackaSekvenca(a));
	}


}
