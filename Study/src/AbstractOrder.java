
public abstract class AbstractOrder {

	static final double COMMISSION_RATE = 0.0005;
	
	abstract double getNotional();
	
	double getCommission() {
		return getNotional() * COMMISSION_RATE;
	}
}
