package HashTableDemo;

import java.util.*;

public class HashTable<K,V> {

	private static final int NO_INDEX_WITH_KEY = -2;
	private Map<Integer, KeyValuePair<K,V>> hashTable;
	
	public HashTable() {
		hashTable = new TreeMap<Integer, KeyValuePair<K,V>>(); 
	}
	
	public V get(K key) {
		if (key == null)
			throw new NullPointerException();
		if (findIndexWithKey(key) == NO_INDEX_WITH_KEY) 
			return null;
		else
			return hashTable.get(findIndexWithKey(key)).getValue(key);
	}
	
	public void put(K key, V value) {
		int index = hashFunction(key);
		try {
			if (hashTable.get(index).getValue(key) != null) {
				// Collision happened, linear probing to resolve it
				int newIndex = resolveCollision(index, key);
				hashTable.put(newIndex, new KeyValuePair<K,V>(key, value));
			}
		} catch (NullPointerException e) {
			hashTable.put(index, new KeyValuePair<K,V>(key, value));
		}
	}
	
	public void printHashTable() {		
		System.out.println("Size of hash table is: " + hashTable.size() + " and content is:"); 
		Set<Integer> indexes = hashTable.keySet();
		Iterator<Integer> indexItr = indexes.iterator();
		while (indexItr.hasNext()) {
			Integer index = indexItr.next();
			System.out.println("Index:" + index + 
			" Key: " + hashTable.get(index).getKey() + 
			" Value: " + hashTable.get(index).getValue(hashTable.get(index).getKey()));
		}
	}
	
	private int findIndexWithKey(K key) {
		int index = hashFunction(key);
		if (hashTable.get(index).getKey() == key) 
			return index;
		else 
			return NO_INDEX_WITH_KEY;
	}
	
	private int resolveCollision(int index, K key) {
		while (hashTable.get(index).getValue(key) != null) {
			try {
				hashTable.get(++index).getValue(key); 
			} catch (NullPointerException e) {
				break;
			}
		}
		return index;
	}
	
	private int hashFunction(K key) {
	    int hash = 0;
	 
	    for (int i = 0; i < key.toString().length(); i++) {
	        hash += key.toString().charAt(i);
	        hash += (hash << 10);
	        hash ^= (hash >> 6);
	    }
	    hash += (hash << 3);
	    hash ^= (hash >> 11);
	    hash += (hash << 15);
	    return hash;
	}
	
}
