package wechat;

public class Test {

	public static void main(String[] args) {
		Test1 t1 = Test1.getInstence();
		System.err.println("t1 num1:"+t1.num1);
		System.err.println("t1 num2:"+t1.num2);
		
		System.err.println("-----------------------------");
		Test2 t2 = Test2.getInstence();
		System.err.println("t2 num1:"+t2.num1);
		System.err.println("t2 num2:"+t2.num2);
	}
	
}

class Test1 {
	private static Test1 t1 = new Test1();
	public static int num1;
	public static int num2=0;
	private Test1(){
		num1++;
		num2++;
	}
	public static Test1 getInstence(){
		return t1;
	}
	
}

class Test2 {
	public static int num1;
	public static int num2=0;
	private static Test2 t2 = new Test2();
	private Test2(){
		num1++;
		num2++;
	}
	public static Test2 getInstence(){
		return t2;
	}
}
