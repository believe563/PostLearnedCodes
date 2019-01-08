import java.util.Random;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n;
//		Random random=new Random();
//		int a=random.nextInt(20);
		n = sc.nextInt();
//		System.out.println(a);
		int[] f1=new int[n+2];
		f1[1]=1;
		f1[2]=1;
		if (n >= 3 && n <= 1000000) {
			for(int i=3;i<=n;i++){
				f1[i]=(f1[i-1]+f1[i-2])%10007;
			}
		}
		System.out.println(f1[n]);
		sc.close();
	}

}
