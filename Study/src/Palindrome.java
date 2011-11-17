
public class Palindrome {
	
	String str = null;
	public Palindrome(String s) {
		this.str = s;
	}

	private boolean isPalindrome() {
		int lastPosition = this.str.length()-1;
		for(int i=0; i <= lastPosition; i++) {		
			if(Character.toLowerCase(this.str.charAt(i)) != Character.toLowerCase(this.str.charAt(lastPosition-i))) {
				return false;
			} 
		}		
		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String p = "Dot saw I was tod";
		Palindrome pd = new Palindrome(p);
		if(pd.isPalindrome())
			System.out.println("String is a palindrome");
		else
			System.out.println("String is not a palindrome");
	}
}
