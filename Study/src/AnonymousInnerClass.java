
public class AnonymousInnerClass {
	private int i = 1;
	public int getValue() { return i; } 
	public static void main(String args[]) {
		AnonymousInnerClass ac = new AnonymousInnerClass() { 
			public int getValue() {return 42;}
		};
		
		System.out.println(ac.getValue());
	}	
}
