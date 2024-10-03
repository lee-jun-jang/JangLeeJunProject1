package project1;

import java.io.Serializable;

public class Person implements Serializable{
	//필드
	public String name;
	public int age;
	public String addr;
	public String num;
	//기본 생성자]
	public Person() {}
	//인자 생성자]
	public Person(String name, int age, String addr, String num) {
		super();
		this.name = name;
		this.age = age;
		this.addr = addr;
		this.num = num;
	}
	//[멤버 메소드]
	String get() {
		String formattedNum = AcademyLogic.formatPhoneNumber(num);
		return String.format("이름:%s,나이:%s,주소:%s,번호:%s",name,age,addr,formattedNum);
	}
	void print() {
		System.out.println(get());
	}
	 
}



