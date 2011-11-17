import java.io.*;
import java.util.*;

public class IntegerInput {
	public static void main(String[] args) {
	//this program requires two arguments on the command line  
		
		System.out.println("Please enter some numbers (more than 1):\n");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ArrayList al = new ArrayList();
		String numbers = null;
		try {
			numbers = br.readLine();	
		} catch (IOException e) {
			System.out.println("Error in reading input.");
			System.exit(1);
		}
	
		if (numbers != null) {
			StringTokenizer st = new StringTokenizer(numbers);
			while(st.hasMoreElements()) {
				String sToken = st.nextToken();
				System.out.println("Current token is:" + sToken);
				al.add(sToken);
			}
			
			int sum = 0;
			Iterator it = al.iterator();
			while(it.hasNext()) {
				sum += Integer.valueOf((String)it.next());
			}
			System.out.println("The final sum is: " + sum);
		} else {
	       
			System.out.println("This program requires two command-line arguments.");
	    }
	}
}
