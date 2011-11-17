
public class DerivOrder extends AbstractOrder {
	double getNotional() { return 1.0000; }
	
	public static void main(String[] args) {
		DerivOrder d = new DerivOrder();
		d.getCommission();
	}
}
