package assignment;

import java.util.*;

// 도서와 사용자 관리하는 Library 클래스
// 6. 참조 타입 (배열)
// 7. 컬렉션 프레임워크 (Map)
public class Library {
	private Book[] books;
	private int bookCount;
	private Map<String, User> users;
	
	public Library() {
		this.books = new Book[100];		// 초기에 100권의 책 저장할 수 있는 배열 생성
		this.bookCount = 0;				// 현재 저장된 책의 수
		this.users = new HashMap<>();
	}
	
	// 도서 추가 메서드
	public void addBook(Book book) {
		if(bookCount < books.length) {
			books[bookCount] = book;
			bookCount++;
		} else {
			System.out.println("도서관이 꽉 찼습니다. 책을 추가할 수 없습니다.");
		}
	}
	
	// 사용자 추가 메서드
	public void addUser(String userName) {
		users.put(userName, new User(userName));
	}
	
	public void addUser(String userName, UserType userType) {
		User user = new User(userName, userType);
		users.put(userName, user);
	}
	
	public User getUser(String userName) {
		return users.get(userName);
	}
	
	// 도서 검색 메서드
	public Book searchBook(String keyword) {
		for(int i=0; i < bookCount; i++) {
			if(books[i].search(keyword) != null) {
				return books[i];
			}
		}
		System.out.println("해당 도서는 존재하지 않습니다.");
		return null;
	}
	
	// 도서 대출 메서드
	public boolean borrowBook(String userName, String bookTitle) {	
		User user = users.get(userName);
		
		if(user != null) {
			if(user.getBorrowedCount() < user.getMaxCheckoutBooks()) {
				for(Book book : books) {
					if(book == null) {
						continue;
					}
					if(book.getTitle().equals(bookTitle) && !book.getisBorrowed()) {
						book.borrow(userName);
						user.increaseBorrowedCount();
						return true;
					}
				}
				return false;
			} else {
				System.out.println(userName + "님은 이미 최대 대출 가능 수에 도달하셨습니다.");
				return false;
			}
		} else {
			System.out.println("등록된 사용자가 아닙니다.");
			return false;
		}
	}
	
	// 도서 반납 메서드
	public boolean returnBook(String userName, String bookTitle) {
		User user = users.get(userName);
		if(user != null) {
			for(Book book : books) {
				if(book.getTitle().equals(bookTitle)) {
					if(book.getisBorrowed()) {
						book.returnBook(userName);
						user.decreaseBorrowedCount();
						return true;
					} else {
						System.out.println("해당 책은 대출되지 않았습니다.");
						return false;
					}
				}
			}
			System.out.println("해당 제목의 책이 없습니다.");
			return false;
		} else {
			System.out.println("등록된 사용자가 아닙니다.");
			return false;
		}
	}
	
}
