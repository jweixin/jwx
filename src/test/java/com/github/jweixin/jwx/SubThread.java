package com.github.jweixin.jwx;

public class SubThread implements Runnable {

	@Override
	public void run() {
		System.out.println("子线程启动");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("子线程结束");
	}

}
