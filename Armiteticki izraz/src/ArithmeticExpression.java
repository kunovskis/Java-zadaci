import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ArithmeticExpression {
    
    // funkcija za presmetuvanje na izrazot pocnuvajki
    // od indeks l, zavrsuvajki vo indeks r
    static char[] removeC(char c[],int n, int m, char k,int b)
    {
    	char[] p=new char[n+1];
    	int w;
    	if(b==1) w=n-2;
    	else w=n-4;
        for(int i=0; i<=w; i++){
            if(i==m)
            {	
            	if(b==1)
            	{
            		p[i]='0';
            		p[++i]='-';
            		p[++i]=k;
            	}
            	else
            		p[i]=k;
            }
            else if(i<m)
                p[i]=c[i];
            else
            {
            	if(b==1)
            		p[i]=c[i+2];
            	else
            		p[i]=c[i+4];
            }
            if(p[i]=='.')
            	break;
        }
        if(b==1){
        	for(int i=n-1; i<=n; i++)
        	{
        		p[i]='.';
        	}
        }
        else{
        	for(int i=n-3; i<=n; i++)
        	{
        		p[i]='.';
        	}
        }
        for(int i=0; i<n; i++)
        {
        	System.out.print(p[i]+" ");
        }
        System.out.println();
        return p;
    }
    
    static int presmetaj(char c[], int l, int r) {
        int sum=0;
        int sumi[]=new int[r];
        for(int j=0; j<10; j++){
        for(int i=0; i<=r; i++){
        	if(c[i]=='(' && c[i+4]==')'){
        		int a=c[i+1]-'0';
        		int b=c[i+3]-'0';
                int k=0;
        		if(c[i+2]=='+')
        			k+=a+b;
        		else
        			k+=a-b;
        		char f=(char)(k+48);
        		int q=0;
        		if(k<0)
        		{
        			q=1;
        			f=(char)((0-k)+48);
        		}
        		c=removeC(c,r,i,f,q);
        	}
        }
        }
        if(c[0]!='(')
        	sum+=c[0]-'0';
        for(int i=1; i<=r; i++)
        {
        	if(c[i]>='0' && c[i]<='9')
        	{
        		if(c[i-1]=='+'){
        			sum+=c[i]-'0';
        		}
        		else
        			sum-=c[i]-'0';
        	}
        }
        return sum;
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
