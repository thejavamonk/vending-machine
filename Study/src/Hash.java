import java.util.HashMap;


public class Hash {
	
	private char letter;
	
	public Hash(char c) {
		this.letter = c;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Hash && ((Hash) obj).letter == this.letter) {
			return true;
		}
		return false;
	}
	
	@Override 
	public int hashCode() {
		return 1;
	}
	
	public static void alphabet() {
		HashMap m = new HashMap();
		m.put(new Hash('a'), "Alpha");
		m.put(new Hash('b'), "Beta");
		m.put(new Hash('c'), "Alpha");
		m.put(new Hash('a'), "Beta");
		System.out.println(m.size());
	}
	
	public static void main(String[] args) {
		Hash.alphabet();
	}

}
