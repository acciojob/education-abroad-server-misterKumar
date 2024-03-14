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

			/*Thread shutdownThread = new Thread(() -> {
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

			 */
			for(int i=1 ;i<=10;i++){
				int studentId=i;
				executorService.submit(()->registerStudent(studentId));
			}
			waitForShutdownSignal();
	    }

	    private static void registerStudent(int studentId) {
	    	// your code goes here
			System.out.println("Student " + studentId + " registered.");
	    }

	    private static void waitForShutdownSignal() throws InterruptedException {
	    	// your code goes here
			/*try (Scanner scanner = new Scanner(System.in)) {
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
			}*/
			Scanner sc=new Scanner(System.in);
			while(true){
				String in=sc.nextLine().trim().toLowerCase();
				if(in.equals("shutdown")){
					shutdownLatch.countDown();
					break;
				}
			}
			sc.close();
	    }

	    private static void stopServer() {
	    	// your code goes here

			try {
				executorService.shutdown();

				if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
					System.out.println("Forcing shutdown due to pending tasks...");
					executorService.shutdownNow();
				}
			} catch (InterruptedException e) {
				//executorService.shutdownNow();
				e.printStackTrace();
			}
	    }
}
