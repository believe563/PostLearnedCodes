import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		if(n>=1&&n<=200){
		int[] num=new int[n];
		int a;
		for(int i=0;i<n;i++){
			a=sc.nextInt();
			if(Math.abs(a)<10000)
			num[i]=a;
		}
		for(int i=0;i<n;i++){
			for(int j=0;j<n-i-1;j++){
				if(num[j]>num[j+1]){
					 a=num[j];
					 num[j]=num[j+1];
					 num[j+1]=a;
				}
			}
		}
		for(int i=0;i<n;i++){
			System.out.print(num[i]+" ");
		}
		}
		System.out.println(Math.sin(3.14/2+"在这里"));
		sc.close();
	}
}
