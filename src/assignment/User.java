package assignment;

import java.util.*;

// 사용자 유형 열거 타입
// 6. 참조 타입 (열거타입)
enum UserType {
	STUDENT,
	STAFF,
	PUBLIC
}

// 대출조건 인터페이스
// 3. 인터페이스
interface BorrowStrategy {
	int getMaxCheckoutBooks();
	int getMaxCheckoutDays();
}

// BorrowStrategy 인터페이스의 구현 클래스
// 대출 가능 도서 수, 대출 기간 다르게 설정
class StudentBorrowStrategy implements BorrowStrategy {
	public int getMaxCheckoutBooks() { return 3; }
	public int getMaxCheckoutDays() { return 14; }
}

class StaffBorrowStrategy implements BorrowStrategy {
	public int getMaxCheckoutBooks() { return 5; }
	public int getMaxCheckoutDays() { return 30; }
}

class PublicBorrowStrategy implements BorrowStrategy {
	public int getMaxCheckoutBooks() { return 2; }
	public int getMaxCheckoutDays() { return 14; }
}

// 도서관 이용자에 대한 정보 담고 있는 User 클래스
// 7. 컬렉션 프레임워크 (List)
public class User {
	// 이용자 정보
	private String name;
	private List<Book> borrowedBooks;
	
	// 이용자에 따른 대출 가능 도서 수와 대출 기간 설정
	private UserType userType;
	private BorrowStrategy borrowStrategy;
	private int borrowedCount;
	
	// 이용자 생성자
	public User(String name) {
		this.name = name;
	}
	
	public User(String name, UserType userType) {
		this.name = name;
		this.userType = userType;
		this.borrowedBooks = new ArrayList<>();
		this.borrowedCount = 0;
		
		switch(userType) {
		case STUDENT:
			this.borrowStrategy = new StudentBorrowStrategy();
			break;
		case STAFF:
			this.borrowStrategy = new StaffBorrowStrategy();
			break;
		case PUBLIC:
			this.borrowStrategy = new PublicBorrowStrategy();
			break;
		}
	}
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public List<Book> getBorrowedBooks(){ return borrowedBooks; }
	
	public UserType  getUserType() { return userType; }
	public int getBorrowedCount() { return borrowedCount; }
	
	public void increaseBorrowedCount() {
		this.borrowedCount++;
	}
	
	public void decreaseBorrowedCount() {
		if(this.borrowedCount > 0) {
			this.borrowedCount--;
		}
	}
	
	// 도서 대출 메서드
	public void borrowBook(Book book) {		
		borrowedBooks.add(book);
	}
	
	// 도서 반납 메서드
	public void returnBook(Book book) {		
		borrowedBooks.remove(book);
	}
	
	// 대출 가능 도서 수와 대출 기간을 가져오는 메서드
	public int getMaxCheckoutBooks() {
		return borrowStrategy.getMaxCheckoutBooks();
	}
	public int getMaxCheckoutDays() {
		return borrowStrategy.getMaxCheckoutDays();
	}
	
}
