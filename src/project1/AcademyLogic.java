package project1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class AcademyLogic {
	//[멤버상수]
	public static final int MAX_PERSON = 10;
	//[멤버변수]
	List<Person> person;
	MusicPlayer musicPlayer;
	//[생성자]
	public AcademyLogic() throws IOException, ClassNotFoundException {
		person = new Vector<>();
		loadPerson();
		musicPlayer = new MusicPlayer("music/music.mp3");
	}

	public void playMusic() {
        if (musicPlayer != null && !musicPlayer.isAlive()) { 
            musicPlayer = new MusicPlayer("music/music.mp3"); 
            musicPlayer.start();
        } else {
            System.out.println("음악이 이미 재생중입니다");
        }
    }

    public void stopMusic() {
        if (musicPlayer != null && musicPlayer.isAlive()) { 
            musicPlayer.stop_(); 
        } else {
            System.out.println("종료할 음악이 없습니다");
        }
    }

	//1]메인메뉴 프린트하기
	public void printMainMenu() {
		System.out.println("=======================주소록 메뉴=======================");
		System.out.println("\u001B[32m1.입력 2.출력 3.수정\u001B[0m \u001B[31m4.삭제\u001B[0m \u001B[32m5.검색 6.파일저장\u001B[0m \u001B[36m7.음악재생\u001B[0m \u001B[30m9.종료\u001B[0m");
		System.out.println("=======================================================");
		System.out.println("메인 메뉴번호를 입력하세요");
	}
	
	//2]메뉴 번호 입력
	public int getMenuNumber() {
		Scanner sc = new Scanner(System.in);
		int menu;
		while (true) {
			try {
				String menuStr = sc.nextLine().trim();
				menu=Integer.parseInt(menuStr);
				break;
			} 
			catch (Exception e) {
				System.out.println("메뉴 번호만 입력해주세요");
			}
		}
			return menu;
	}
		
	//3]메뉴 번호 에따른 분기용 메소드
	public void seperateMainMenu(int mainMenu) throws IOException {
		switch(mainMenu) {
		case 1://입력
		    setPerson();
			break;
		case 2://출력
			printPerson();
			break;
		case 3://수정
			updatePerson();
			break;
		case 4://삭제
			deletePerson();
			break;
		case 5://검색
			searchPerson();
			break;
		case 6://파일저장
			savePerson();
			break;
		case 7: 
		    System.out.println("1.재생 2.종료");
		    int musicChoice = getMenuNumber();
		    switch (musicChoice) {
		        case 1:
		            playMusic();
		            break;	    
		        case 2:
		            stopMusic();
		            break;
		        default:
		            System.out.println("메뉴번호를 입력해주세요");
		            break;
		    }
		    break;	
		case 9://종료
			System.out.println("프로그램을 종료합니다");
			System.exit(0);
			break;
		default:System.out.println("메뉴에 없는 번호입니다");
		
		}
		
	}
	
	private void setPerson() {
		if(person.size()==MAX_PERSON) {
			System.out.println("정원이 찼아요 더이상 입력할 수 없어요");
			return;
		}
		Scanner sc = new Scanner(System.in);
		System.out.println("이름 입력>>");
		String name = sc.nextLine().trim();
		System.out.println("나이 입력>>");
		int age = -1;
		while (true) {
			try {
				age = Integer.parseInt(sc.nextLine().trim());
				break;
			} catch (NumberFormatException e) {
				System.out.println("나이는 숫자만 입력해주세요");
			}
		}
		System.out.println("주소 입력>>");
		String addr = sc.nextLine().trim();
		System.out.println("번호 입력>>");
		String num = sc.nextLine().trim();
        
		person.add(new Person(name,age,addr,formatPhoneNumber(num)));
	}
	
	public static String formatPhoneNumber(String num) {
	    if (num.length() == 10) {
	        return num.substring(0, 2) + "-" + num.substring(2, 6) + "-" + num.substring(6);
	    } else if (num.length() == 11) {
	        return num.substring(0, 3) + "-" + num.substring(3, 7) + "-" + num.substring(7);
	    } else {
	        return num; 
	    }
	}
	
	//초성 메소드
	private char getInitial(String name) {
	
	    final String[] initialTable = {
	            "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
	    };
	    final int BASE_CODE = 44032;
	    char ch = name.charAt(0);
	    int index = (ch - BASE_CODE) / 28 / 21;
	    return initialTable[index].charAt(0);
	}
	private void printPerson() {
		int index = 1;
	    StringBuffer strInitialList = new StringBuffer("[명단]\r\n");
	    Map<Character, List<Person>> initialMap = new HashMap<>(); // Person 객체를 저장하는 리스트로 수정

	    //이름 목록을 초성별로 그룹화
	    for (Person p : person) {
	        if (p instanceof Person) {
	            char initial = getInitial(p.name);
	            if (!initialMap.containsKey(initial)) {
	                initialMap.put(initial, new ArrayList<>());
	            }
	            initialMap.get(initial).add(p);
	        }
	    }

	    // 초성별 명단 출력
	    for (Map.Entry<Character, List<Person>> entry : initialMap.entrySet()) {
	        strInitialList.append("["+entry.getKey()+ "으로 시작하는 명단]\r\n"); 
	     // 각 명단에 해당하는 사용자의 정보 출력
	        for (Person p : entry.getValue()) {
	            strInitialList.append(index).append(") ").append("이름: ").append(p.name)
	                          .append(", 나이: ").append(p.age)
	                          .append(", 주소: ").append(p.addr)
	                          .append(", 번호: ").append(formatPhoneNumber(p.num))
	                          .append("\r\n");
	            index++;
	        }
	        index = 1;
	    }
	    System.out.print(strInitialList.toString());
	}
	
	//7검색용 메소드
	private void searchPerson() {
	    List<Person> foundPersons = findPersonsByName("검색");
	    if (!foundPersons.isEmpty()) {
	        System.out.println(String.format("[%s(으)로 검색한 결과]", foundPersons.get(0).name));
	        int index = 1;
	        for (Person person : foundPersons) {
	            System.out.printf("%d) 이름: %s, 나이: %d, 주소: %s, 번호: %s%n", index++, person.name, person.age, person.addr, formatPhoneNumber(person.num));
	        }
	    }
	}
	//7-1]검색
	private List<Person> findPersonsByName(String message) {
	    System.out.println(message + "할 사람의 이름을 입력하세요");
	    Scanner sc = new Scanner(System.in);
	    String name = sc.nextLine().trim();
	    
	    List<Person> foundPersons = new ArrayList<>();
	    for(Person p : person) {
	        if(p.name.equals(name)) {
	            foundPersons.add(p);
	        }
	    }
	    
	    if(foundPersons.isEmpty()) {
	        System.out.println(name + "(으)로 검색된 정보가 없습니다");
	    }
	    
	    return foundPersons;
	}
	
	//9]수정용 메소드
	private void updatePerson() {
	    List<Person> foundPersons = findPersonsByName("수정");
	    if (!foundPersons.isEmpty()) {
	        Scanner sc = new Scanner(System.in);

	        if (foundPersons.size() == 1) { // 이름이 중복되지 않으면
	            Person selectedPerson = foundPersons.get(0);
	            updateSelectedPerson(selectedPerson);
	        } else { // 이름이 중복되면
	            System.out.println("[검색된 사람 목록]");
	            int index = 1;
	            for (Person person : foundPersons) {
	                System.out.printf("%d) 이름: %s, 나이: %d, 주소: %s, 번호: %s%n", index++, person.name, person.age, person.addr, formatPhoneNumber(person.num));
	            }
	            System.out.println("수정할 사람의 번호를 선택하세요");
	            int choice;
	            while (true) {
	                try {
	                    choice = Integer.parseInt(sc.nextLine().trim());
	                    if (choice < 1 || choice > foundPersons.size()) {
	                        System.out.println("존재하지 않는 번호입니다");
	                    } else {
	                        break;
	                    }
	                } catch (NumberFormatException e) {
	                    System.out.println("번호를 입력하세요");
	                }
	            }

	            Person selectedPerson = foundPersons.get(choice - 1);
	            updateSelectedPerson(selectedPerson);
	        }
	    }
	}

	private void updateSelectedPerson(Person selectedPerson) {
	    Scanner sc = new Scanner(System.in);
	    System.out.printf("(%s님의 현재 정보)%n", selectedPerson.name);
	    selectedPerson.print();

	            System.out.println("수정할 나이를 입력하세요");
	            while(true) {
	                try {
	                    int age = Integer.parseInt(sc.nextLine().trim());
	                    selectedPerson.age = age;
	                    break;
	                } catch(NumberFormatException e) {
	                    System.out.println("숫자를 입력하세요");
	                }
	            }	        
	            System.out.println("수정할 주소를 입력하세요");
	            selectedPerson.addr = sc.nextLine().trim();

	            System.out.println("수정할 번호를 입력하세요");
	            selectedPerson.num = sc.nextLine().trim();
	        
	        System.out.printf("[%s님의 정보가 아래와 같이 수정되었습니다]%n", selectedPerson.name);
	        selectedPerson.print();
	    }
		
	//10]삭제용
	private void deletePerson() {
		 List<Person> foundPersons = findPersonsByName("삭제");
		    if (!foundPersons.isEmpty()) {
		    	  if (foundPersons.size() == 1) { 
		              Person personToRemove = foundPersons.get(0);
		              person.remove(personToRemove);
		              System.out.printf("[%s가(이) 삭제되었습니다]%n", personToRemove.name);
		              return;
		          }
    	
		        System.out.println("삭제할 사람의 번호를 선택하세요");
		        int index = 1;
		        for (Person person : foundPersons) {
		            System.out.printf("%d) 이름: %s, 나이: %d, 주소: %s, 번호: %s%n", index++, person.name, person.age, person.addr, formatPhoneNumber(person.num));
		        }
		        
		        Scanner sc = new Scanner(System.in);
		        int choice;
		        while (true) {
		            try {
		                choice = Integer.parseInt(sc.nextLine().trim());
		                if (choice < 1 || choice > foundPersons.size()) {
		                    System.out.println("존재하지 않는 번호입니다");
		                } else {
		                    break;
		                }
		            } catch (Exception e) {
		                System.out.println("번호를 입력하세요");
		            }
		        }
		        
		        Person personToRemove = foundPersons.get(choice - 1);
		        person.remove(personToRemove);
		        System.out.printf("[%s가(이) 삭제되었습니다]%n", personToRemove.name);
		    }
		}

	private void savePerson() throws IOException {
		//컬렉션에 객체가 저장되었는지 판단
		if(person.isEmpty()) {
			System.out.println("파일로 저장할 명단이 없어요");
			return;
		}
		//컬렉션에 저장된 객체가 있는 경우
		ObjectOutputStream out= null;
		try {	
			out = new ObjectOutputStream(new FileOutputStream("src/project1/Persons.dat"));
			//컬렉션에 저장된 데이타를 파일(out)로 출력
			out.writeObject(person);
			System.out.println("파일이 저장되었습니다");
		}
		catch(IOException e) {
			System.out.println("파일 저장시 오류:"+e.getMessage());
		}
		finally {
			try {
			if(out != null) out.close();
			}
			catch(Exception e) {}
		}
	}
	
	private void loadPerson() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("src/project1/Persons.dat"));
			person=(List<Person>)ois.readObject();
		}
		catch (Exception e) {} 
		finally {
			try {
				if (ois != null) ois.close();
					
			} 
			catch (IOException e) {}
		}
	}
}