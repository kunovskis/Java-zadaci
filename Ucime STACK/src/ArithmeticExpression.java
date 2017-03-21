import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
public class ArithmeticExpression {
    
    static int presmetaj(char c[], int l, int r) {
    	Stack <Character> operandi = new Stack<Character> ();
    	Stack <Integer> vrednost = new Stack <Integer> ();
    	
    	for(int i = l; i <= r; i++){
    		char momentalen = c[i];
    		if( momentalen == '(' || momentalen == '+' || momentalen == '-'){
    			Character ch = new Character (momentalen);
    			operandi.push(ch);
    		}
    		else if(Character.isDigit(momentalen)){
    			int vr = momentalen - '0';
    			Integer vrInt = new Integer (vr);
    			vrednost.push(vrInt);

    		}
    		else{
    			int first = vrednost.pop();
    			int second = vrednost.pop();
    			char op = operandi.pop();
    			int res;
    			operandi.pop();
    			if(op == '+')
        			res = first + second;
        		else
        			res = second - first;
    			Integer vrInt = new Integer (res);
    			vrednost.push(vrInt);
    		}
    	}
    	if(!operandi.empty()){
    		int first = vrednost.pop();
    		int second = vrednost.pop();
    		char op = operandi.pop();
    		operandi.pop();
    		int res;
    		if(op == '+')
    			res = first + second;
    		else
    			res = second - first;
    		Integer vrInt = new Integer (res);
    		vrednost.push(vrInt);
    	}
		return vrednost.pop();
    }
    
    public static void main(String[] args) throws Exception {
        int i,j,k;
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String expression = br.readLine();
        char exp[] = expression.toCharArray();
        
        int rez = presmetaj(exp, 0, exp.length-1);
        System.out.println(rez);
        
        br.close();
        
    }
    
}
