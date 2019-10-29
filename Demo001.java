package com.educative.io;
//Demonstration - The task is to compute the sum of all the integers from 0 to Integer.MAX_VALUE. 
//In the first scenario, we have a single thread doing the summation while in the second scenario we split the 
//range into two parts and have one thread sum for each range. 
//In the end, we add the two half sums to get the combined sum. We measure the time taken for each scenario and print it.

public class Demo001 {
	public static void main(String[] args) throws InterruptedException {
		SumUpExample.runTest();
	}
}

class SumUpExample {
	long startRange;
	long endRange;
	long counter=0;
	static long MAX_NUM = Integer.MAX_VALUE;
	
	private SumUpExample(long startRange, long endRange) {
		this.startRange = startRange;
		this.endRange = endRange;
	}
	
	public static void runTest() throws InterruptedException {
		oneThread();
		twoThread();
	}

	private static void twoThread() throws InterruptedException {
		long startTime = System.currentTimeMillis();
		SumUpExample s1 = new SumUpExample(1, MAX_NUM/2);
		SumUpExample s2 = new SumUpExample(1+MAX_NUM/2, MAX_NUM);
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				s1.add();
			}
		});
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				s2.add();
			}
		});
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		long endTime = System.currentTimeMillis();
		
		System.out.print("time taken for two threads is "+(endTime-startTime));
		System.out.println(" and the sum is "+(s1.counter+s2.counter));
	}

	private static void oneThread() {
		long startTime = System.currentTimeMillis();
		SumUpExample s1 = new SumUpExample(1, MAX_NUM);
		s1.add();
		long endTime = System.currentTimeMillis();
		System.out.print("time taken for single thread is "+(endTime-startTime));
		System.out.println(" and the sum is "+s1.counter);
	}
	
	private void add() {
		for (long i=startRange; i<=endRange; i++) {
			counter += i;
		}
	}
}
