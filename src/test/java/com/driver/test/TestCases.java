package com.driver.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.driver.*;
public class TestCases {
    private final ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;

    @Test
    void testServerStartsAndStopsGracefully() {
        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputContent));

        // Mock user input to simulate 'shutdown' command
        String simulatedUserInput = "shutdown\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(inputStream);

        // Run the server in a separate thread
        Thread serverThread = new Thread(() -> StudentRegistrationServer.main(null));
        serverThread.start();

        // Allow time for the server to start and user input to be processed
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop the server by joining with the server thread
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Reset System.in and System.out
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);

        // Verify the output contains the expected shutdown message
        assertTrue(outputContent.toString().contains("Shutting down the server gracefully"));
    }
}