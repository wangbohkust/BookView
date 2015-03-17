package activity.bookview;

public class BookViewInfo {
	private String originalText;
	private String viewText;
	private int imageId;
	private int bookId;
	private String bookname;
	
	public BookViewInfo(String originalText, String viewText, int imageText, int bookId,String bookname) {
		this.originalText = originalText;
		this.viewText = viewText;
		this.imageId = imageText;
		this.bookId = bookId;
		this.bookname = bookname;
	}
	
	public String getOriginalText() {
		return originalText;
	}
	
	public String getReviewText() {
		return viewText;
	}
	
	public int getImageId() {
		return imageId;
	}
	
	public String getBookname() {
		return bookname;
	}
	
	public int getBookId() {
		return bookId;
	}
	
	
}
