package project1;

import java.io.IOException;


public class AcademyApp extends Printer{
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		//모래시계모양 출력 
        DiamondPrinter diamondPrinter = new AcademyApp().new DiamondPrinter(9, 300);
        diamondPrinter.start();
	    try {
	        diamondPrinter.join();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    System.out.println();
	    
	    //프로그램명 출력 
	    CharacterPrinter printer = new AcademyApp().new CharacterPrinter("[ 주소록 관리 프로그램 ]", 100);
        printer.start();
        
        try {
            printer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      
        System.out.println();
             
		AcademyLogic logic = new AcademyLogic();
		while(true) {
		//1.메인 메뉴 출력
		logic.printMainMenu();
		//2.메인메뉴 번호 입력받기
		int mainMenu=logic.getMenuNumber();
		//3.메인메뉴에 따른 분기
		logic.seperateMainMenu(mainMenu);
		}//while
	
	
	}//main

}//class
