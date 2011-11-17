
public class Test {
	private int i = giveMeJ();
	private int j = 10;
	private final int a = 10;
	private byte k = a;
	
	private int giveMeJ() {
		return j;
	}
	
	public static void main(String args[]) {
		System.out.println((new Test()).i);
		
		try {
			System.out.println((new Test()).k);	
			System.out.println(Math.abs(Integer.MIN_VALUE));
			System.out.println(Math.min(0.0, -0.0));
			System.out.println(Math.round(Float.MAX_VALUE));
			System.out.println(Integer.MAX_VALUE);
		} catch (Throwable t) { System.out.println("In catch."); }
		
		System.out.println("End");
	}

}
