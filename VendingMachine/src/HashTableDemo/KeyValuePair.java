package HashTableDemo;

public class KeyValuePair<K, V> {
	
	private K key;
	private V value;
	
	public KeyValuePair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public V getValue(K key) {
		return this.value;
	}
	
	public K getKey() {
		return this.key;
	}

}
