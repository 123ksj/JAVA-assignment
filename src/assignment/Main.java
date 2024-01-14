package assignment;

import java.io.*;
import java.util.Scanner;

// 사용자의 입력을 받아 Library 클래스의 기능 호출하는 Main 클래스
// 4. 예외처리
// 8. 파일 입/출력 사용
public class Main {

	public static void main(String[] args) {
		// Library 인스턴스 생성
		Library library = new Library();
		Scanner sc = new Scanner(System.in);
		
		// 파일로부터 도서 정보 읽어오기
		// 도서 종류, 도서 유형, 제목, 작가
		try(BufferedReader br = new BufferedReader(new FileReader("/Users/lilly/eclipse-workspace/JAVA assignment/src/Books.txt"))) {
			String line;
			while((line = br.readLine()) != null) {
			String[] info = line.split(",");
	
				if(info[0].equals("AcademicBook")) {
					library.addBook(new AcademicBook(info[2].trim(), info[3].trim(), info[1].trim()));
				} else if (info[0].equals("ReferenceBook")) {
					library.addBook(new ReferenceBook(info[2].trim(), info[3].trim(), info[1].trim()));
				} else {
					library.addBook(new Book(info[2].trim(), info[3].trim()));
				}
			}
		} catch(IOException e) {
				e.printStackTrace();
		}
		
		// 파일로부터 사용자 정보 읽어오기
		// 이름, 사용자유형
		try{
			File file = new File("/Users/lilly/eclipse-workspace/JAVA assignment/src/User.txt");
			Scanner scanner = new Scanner(file);
		
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
			
				String[] info = line.split(",");
				String userName = info[0].trim();				
				UserType userType = UserType.valueOf(info[1].toUpperCase().trim());
			
				library.addUser(userName, userType);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
		// 사용자 입력받기
		User user = null;
		String userName;
		do {
			System.out.println("사용자 이름을 입력하세요: ");
			userName = sc.nextLine();
			user = library.getUser(userName);
			if(user == null) {
				System.out.println("해당 이름의 사용자가 존재하지 않습니다.");
			}else {
				// UserType 확인 및 출력
				UserType userType = user.getUserType();
				System.out.println("사용자 유형: " + userType);
				switch (userType) {
				case STUDENT:
					System.out.println("이 사용자는 학생입니다.");
					break;
				case STAFF:
					System.out.println("이 사용자는 직원입니다.");
					break;
				case PUBLIC:
					System.out.println("이 사용자는 일반인입니다.");
					break;
				}
			}
		} while(user == null);
		
		while(true) {
			System.out.println("-------------------------");
			System.out.println("1. 도서 검색 및 대출");
			System.out.println("2. 도서 반납");
			System.out.println("3. 종료");
			System.out.println("원하시는 작업 번호를 입력하세요: ");
			int action = sc.nextInt();
			sc.nextLine();		// Int 입력 후 String 입력을 위한 버퍼 비우기
			
			switch(action) {
			case 1:
				// 도서 검색 및 대출
				System.out.println("대출하고 싶은 도서 제목을 입려갛세요: ");
				String bookTitle = sc.nextLine();
				Searchable result = library.searchBook(bookTitle);
				
				if(result != null && result instanceof Borrowable) {
					if(library.borrowBook(userName, bookTitle)) {
						System.out.println("도서 대출 성공");
					} else {
						System.out.println("도서 대출 실패");
					}
				} else {
					System.out.println("도서를 찾지 못했습니다.");
				}
				break;
			case 2:
				// 도서 반납
				System.out.println("반납하고 싶은 도서 제목을 입력하세요: ");
				String returnBookTitle = sc.nextLine();
				Book book = library.searchBook(returnBookTitle);
				
				// 파일에 해당 도서가 존재하는지 확인
				if(book == null) {
					System.out.println("도서를 찾지 못했습니다.");
					break;
				}
					
				if(library.returnBook(userName, returnBookTitle)) {
					System.out.println("도서 반납 성공");
				} else {
					System.out.println("도서 반납 실패");
				}
				break;
			case 3:
				// 종료
				System.out.println("프로그램 종료합니다.");
				sc.close();
				System.exit(0);
			default:
				System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
			}
		}
	}

}
