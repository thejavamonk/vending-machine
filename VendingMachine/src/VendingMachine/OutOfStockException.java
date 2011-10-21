package VendingMachine;

public class OutOfStockException extends Exception {
	private String selection;
	
	public OutOfStockException(String selection) {
		this.selection = selection;
	}
	
	public String toString() {
		return "\nSorry, we run out of stock on " + selection + ".";
	}
}