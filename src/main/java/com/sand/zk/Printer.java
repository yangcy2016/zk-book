package com.sand.zk;

public class Printer {
	private static StringBuilder sub = null;
	private static Printer instance = null;
	private static void init(){
		sub = new StringBuilder();
		sub.append("Thread:").append(Thread.currentThread().getName());
		instance = new Printer();
	}
	public static Printer newInstance(){
		init();
		return instance;
	}
	
	public Printer appendTag(Object tag){
		if(sub.toString().length()==0){
			sub.append(tag).append(":");
		}else{
			sub.append(",").append(tag).append(":");	
		}
		
		return instance;
	}
	
	public Printer appendValue(Object value){
		sub.append(value);
		return instance;
	}
	
	public Printer blankspace(){
		sub.append(" ");
		return instance;
	}
	
	public Printer newLine(){
		sub.append("\r\n");
		return instance;
	}
	
	public void showMessage(){
		System.out.println(sub.toString());
	}
	
	public static void main(String[] args) {
		Printer.newInstance().appendTag("helloTest").appendValue("hello").showMessage();
	}
}
