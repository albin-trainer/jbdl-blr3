package com.geek.threads;
public class MultipleThreadsDemo {
public static void main(String[] args) {
	System.out.println("Main thread started .....");
	Thread curThread=  Thread.currentThread();
	curThread.setPriority(10);
	Task task =new Task();
	//NEW Thread
	Thread t1= new Thread(task);// new thread created and assigned as task
	t1.setPriority(5);
	t1.start(); //Runnable
	//CANT predict the output ....
	for(int i=1;i<=5000;i++) {
		System.out.println("Main : "+i);
	}
	System.out.println("Main thread ends ....");
}
}
class Task implements Runnable{
	public void run() {
		System.out.println("Child thread started running .....");
		for(int i=1;i<=5000;i++) {
			System.out.println("Child : "+i);
		}
	}
}