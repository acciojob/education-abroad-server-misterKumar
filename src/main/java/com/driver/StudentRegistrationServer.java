package com.driver;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StudentRegistrationServer {
	  private static final int THREAD_POOL_SIZE = 5;
	    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	    private static final CountDownLatch shutdownLatch = new CountDownLatch(1);

	    public static void main(String[] args) {
	        try {
	            startServer();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } finally {
	            stopServer();
	        }
	    }

	    private static void startServer() throws InterruptedException {
	    	// your code goes here
			System.out.println("Registration server started. Enter 'shutdown' to stop the server.");

			Thread shutdownThread = new Thread(() -> {
				try {
					waitForShutdownSignal();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			shutdownThread.start();

			int studentId = 1;
			while (!shutdownLatch.await(1, TimeUnit.SECONDS)) {
				registerStudent(studentId++);
			}
	    }

	    private static void registerStudent(int studentId) {
	    	// your code goes here
			System.out.println("Student " + studentId + " registered.");
	    }

	    private static void waitForShutdownSignal() throws InterruptedException {
	    	// your code goes here
			try (Scanner scanner = new Scanner(System.in)) {
				while (!Thread.currentThread().isInterrupted()) {
					String input = scanner.nextLine();
					if (input.equalsIgnoreCase("shutdown")) {
						if (shutdownLatch.getCount() > 0) {
							shutdownLatch.countDown(); // Signal the server to stop
						}
						Thread.currentThread().interrupt(); // Interrupt the shutdownThread
						return;
					}
				}
			} catch (IllegalStateException e) {
				// Scanner was closed, ignoring
			}
	    }

	    private static void stopServer() {
	    	// your code goes here
			executorService.shutdown();
			try {
				if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
					executorService.shutdownNow();
				}
			} catch (InterruptedException e) {
				executorService.shutdownNow();
			}
	    }
}
