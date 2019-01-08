import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		long n = sc.nextLong();
			long s;
			s=(n-1)*n/2+n;
			System.out.println(s);
	}
}
