package Range;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Range {
   	
    static long proveri(long N, long A, long B) {
    	if (A == B)
            return -1;
       
       
        if (A * A + 200*A + sumDigits(A) == N)
            return A;
        if (B*B + 200*B + sumDigits(B) == N )
            return B;
       
        long mid = (A+B)/2;
       
       // System.out.println(A + " " + B + " " + mid);
 
        long temp = mid * mid + 200 * mid + sumDigits(mid);
        if (temp > N)
            return proveri(N,A,mid);
        else  if (temp < N)
            return proveri(N,mid,B);
        else return mid;
    }
    
    static long sumDigits(long n){
        if ( n == 0 ) return 0;
        return n%10+sumDigits(n/10);
    }
    
    public static void main(String[] args) throws Exception {
        int i,j,k;
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        long N = Long.parseLong(br.readLine());
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        long A = Long.parseLong(st.nextToken());
        long B = Long.parseLong(st.nextToken());
        
        long res = proveri(N, A, B);
        System.out.println(sumDigits(N));
        System.out.println(res);
        
        br.close();
        
    }
    
}
