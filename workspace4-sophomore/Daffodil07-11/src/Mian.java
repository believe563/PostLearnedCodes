
/**
 *水仙花数
 */
public class Mian {
public static void main(String[] args){
	int count=0;
	for (int i = 100; i <=999; i++) {
		int a=i/100;
		int b=i/10%10;
		int c=i%10;
		if (a*a*a+b*b*b+c*c*c==i) {
			count++;
			System.out.println(i);
		}
	}
}
}
