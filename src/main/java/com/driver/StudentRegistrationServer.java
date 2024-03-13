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
	    }

	    private static void registerStudent(int studentId) {
	    	// your code goes here
	    }

	    private static void waitForShutdownSignal() throws InterruptedException {
	    	// your code goes here
	    }

	    private static void stopServer() {
	    	// your code goes here
	    }
}
