package org.razkevich.testapi.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TestClass {
	private static final Object monitor = new Object();

	public static void main(String[] args) {
		Thread t1 = new Thread(() -> {
			while (true) {
				LocalDateTime localDateTime = LocalDateTime.now();
				if (LocalDateTime.now().minus(1, ChronoUnit.SECONDS).compareTo(localDateTime) < 0) {
					Thread.yield();
				} else {
					localDateTime = LocalDateTime.now();
					System.out.println(Thread.currentThread().getName());
				}
			}
		});
		t1.start();
	}
}
