import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Double PI = 3.14159265358979323;
		int r = sc.nextInt();
		if (r >= 1 && r <= 10000) {
			Double S = PI * r * r;
			String s = String.format("%.7f", S);
			System.out.println(s);
		}
		sc.close();

	}
}
