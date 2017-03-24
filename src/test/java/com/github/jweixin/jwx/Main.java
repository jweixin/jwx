package com.github.jweixin.jwx;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("主线程启动");
		
		synchronized (Main.class) {
			System.out.println("同步块执行");
			SubThread sub = new SubThread();
			Thread t = new Thread(sub);
			t.start();
			System.out.println("同步块结束");
		}
		
		System.out.println("主线程结束");
	}

}
