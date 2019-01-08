import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int m, n;
		m = scanner.nextInt();
		n = scanner.nextInt();
//		if (m > 3 && m < 100 && n > 3 && n < 100) {
			int[][] table = new int[m][n];
			for (int i = 0; i <m; i++) {
				for (int j = 0; j < n; j++) {
					table[i][j] = scanner.nextInt();
				}
			}
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			char s = scanner.next().charAt(0);
			int k = scanner.nextInt();
			String orientation="URDL";
			int index;
			for (int a = 0; a < k; a++) {
				index=orientation.indexOf(s);
				if(table[x][y]==0){
					index=(index+3)%4;
					table[x][y]=1;
					switch(index){
					case 0:
						y=(y-1+n)%n;
						break;
					case 1:
						x=(x++)%m;
						break;
					case 2:
						y=(y++)%n;
						break;
					case 4:
						x=(x-1+m)%m;
						break;
					}
				}else {
						index=(index+1)%4;
						table[x][y]=0;
						switch(index){
						case 0:
							y--;
							break;
						case 1:
							x++;
							break;
						case 2:
							y++;
							break;
						case 4:
							x--;
							break;
						}
				}
			}
			System.out.println(x + " " + y);
//		}

	}

}
//3 3
//0 0 0
//1 1 1
//1 1 1
//1 1 U 6
