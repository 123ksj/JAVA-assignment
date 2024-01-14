package assignment;

// 대출 가능(Borrowable) 인터페이스 & 검색 가능(Searchable) 인터페이스
// 3. 인터페이스
interface Borrowable {
	boolean borrow(String userName);
	void returnBook(String userName);
}

interface Searchable {
	Object search(String keyword);
}

// 도서에 대한 정보 담고 있는 Book 클래스
// 5. 다형성 (인터페이스)
// Borrowable, Searchable 인터페이스의 구현 클래스
public class Book implements Borrowable, Searchable {
	protected String title;
	protected String author;
	private boolean isBorrowed;
	
	public Book(String title, String author) {
		this.title = title;
		this.author = author;
		this.isBorrowed = false;
	}
	
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	
	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }
	
	public boolean getisBorrowed() { return isBorrowed; }
	
	public String getInfo() {
		return "제목 : " + title + ", 저자 : " + author;
	}
	
	// 대출
	@Override
	public boolean borrow(String userName) {
		if(!isBorrowed) {
			isBorrowed = true;
			System.out.println(title + "이(가) " + userName + "님에게 대출되었습니다.");
			return true;
		} else {
			System.out.println(title + "은(는) 이미 대출된 상태입니다.");
			return false;
		}
	}
	
	// 반납
	@Override
	public void returnBook(String userName) {
		if(isBorrowed) {
			isBorrowed = false;
			System.out.println(title + "이(가) " + " 반납되었습니다.");
		} else {
			System.out.println(title + "은(는) 대출된 책이 아닙니다.");
		}
	}
	
	// 검색
	@Override
	public Object search(String keyword) {
		if(title.contains(keyword) || author.contains(keyword)) {
			return this;
		} else {
			return null;
		}
	}
	
}

// 2. 클래스 상속
// 5. 다형성 (상속)
// Book 클래스를 상속받은 AcademicBook
// 학문 분야 속성 추가
class AcademicBook extends Book {
	private String fieldOfStudy;
	
	public AcademicBook(String title, String author, String fieldOfStudy) {
		super(title, author);
		this.fieldOfStudy = fieldOfStudy;
	}

	public String getFieldOfStudy() { return fieldOfStudy; }
	public void setFieldOfStudy(String fieldOfStudy) { this.fieldOfStudy = fieldOfStudy; }

	@Override
	public String getInfo() {
		return "제목 : " + getTitle() + ", 저자 : " + getAuthor() + ", 학문 분야 : " + fieldOfStudy;	
	}
}

// 2. 클래스 상속
// 5. 다형성 (상속)
// Book 클래스를 상속받은 ReferenceBook
// 참고자료 휴형 속성 추가
class ReferenceBook extends Book {
	private String referenceType;
	
	public ReferenceBook(String title, String author, String referenceType) {
		super(title, author);
		this.referenceType = referenceType;
	}
	
	public String getReferenceType() { return referenceType; }
	public void setReferenceType(String referenceType) { this.referenceType = referenceType; }

	@Override
	public String getInfo() {
		return "제목 : " + getTitle() + ", 저자 : " + getAuthor() + ", 참고자료 유형 : " + referenceType;
	}
}
