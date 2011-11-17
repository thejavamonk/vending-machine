
public class Library <T extends Media> {
	
	private T item;
	
	public void checkInItem(T item) {
		this.item = item;
	}
	
	public T checkOutItem() {
		return item;
	}
	
	public static void main(String[] args) {
		Library<Book> libB = new Library<Book>();
		libB.checkInItem(new Book());
		Book book = libB.checkOutItem();
		book.describeMe();

		Library<NewsPaper> libN = new Library<NewsPaper>();
		libN.checkInItem(new NewsPaper());
		NewsPaper np = libN.checkOutItem();
		np.describeMe();
		
		Library<Video> libV = new Library<Video>();
		libV.checkInItem(new Video());
		Video vi = libV.checkOutItem();
		vi.describeMe();
	}
}
