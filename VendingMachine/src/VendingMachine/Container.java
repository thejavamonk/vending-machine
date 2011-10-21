package VendingMachine;

import java.util.*;

public class Container <T extends Drink, I> {
	
	private Map<T,I> items = new HashMap<T,I>();
	
	public void addItem(T item, I count) {
		items.put(item, count);
	}
	
	public I getItemCount(T item) {
		return items.get(item);
	}
}
