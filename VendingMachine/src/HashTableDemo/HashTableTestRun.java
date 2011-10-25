package HashTableDemo;

public class HashTableTestRun {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String drivingLicense1 = "C123-789";
		String drivingLicense2 = "D567-990";
		String drivingLicense3 = "E444-001";
		String person1 = "David";
		String person2 = "John";
		String person3 = "Steve";
		HashTable<String, String> stringHashTable = new HashTable<String, String>();
		stringHashTable.put(drivingLicense1, person1);
		stringHashTable.put(drivingLicense2, person2);
		stringHashTable.put(drivingLicense3, person3);
		//collision
		stringHashTable.put(drivingLicense2, person2);
		stringHashTable.printHashTable();
		
		Integer employeeNo1 = new Integer(1111);
		Integer employeeNo2 = new Integer(2222);
		Integer employeeNo3 = new Integer(3333);
		Integer departmentNo1 = new Integer(1201);
		Integer departmentNo2 = new Integer(1301);
		Integer departmentNo3 = new Integer(1401);
		HashTable<Integer, Integer> intHashTable = new HashTable<Integer, Integer>();
		intHashTable.put(employeeNo1, departmentNo1);
		intHashTable.put(employeeNo2, departmentNo2);
		intHashTable.put(employeeNo3, departmentNo3);
		//collision
		intHashTable.put(employeeNo2, departmentNo2);
		intHashTable.printHashTable();
	}

}
