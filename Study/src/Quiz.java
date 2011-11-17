public class Quiz {

	void print(Integer i) {
		System.out.println("Input is an Integer");
	}
	
	void print(String s) {
		System.out.println("Input is String");
	}
	
	<T> void callPrint(T t) {
		print(1);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Quiz g = new Quiz();
		g.callPrint(new Integer(5));
		
		// TODO Auto-generated method stub
		/*
		String a = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		String b = a;
		a = a.substring(5, 10);
		System.out.println(a);
		System.out.println(b);
		*/
		
		/*
		Object a = new Object();
		Object b = new Object();
		System.out.println(a.equals(b));
		
		String x = new String("XYZ");
		String y = new String("XYZ");
		System.out.println(x.equals(y));
		*/
		
		/*
		try {
			System.out.println("Start");
			throw new RuntimeException("Oops");
			System.out.println("End");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {	
			System.out.println("Finally");
		}
		
		*/
		
		/*
		Parent p = new Parent();
		Child c = new Child();
		p.print();
		p = c;
		p.print();
		*/
		
		/*
		Child c = new Child();
		c.print(5);
		*/
	}
}