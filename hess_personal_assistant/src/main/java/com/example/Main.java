package com.example;

import java.util.Scanner;

public class Main {

    // FYI: Can have the main method set the Google Cloud credentials as the enviroment variable
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter to start transcription, or type 'q' to quit.");

        // runs until the user tells it goodbye or presses q
        // Classic terminal checking for user input, only needs the user to press enter to start the recording. 
        while (true) {
            String input = scanner.nextLine(); // Read user input

            if (input.equalsIgnoreCase("q")) {
                try {
                    TextToSpeech.synthesizeText("Goodbye.");
                } catch (Exception ex) {
                    LoggerUtil.logError(ex, "Error during goodbye speech");
                    System.out.println("Error during speech. Please try again.");
                }
                break;
            }

            System.out.println("Recording started...");
            try {
                String transcript = Transcribe.syncRecognizeFile();
                System.out.println("Transcript: " + transcript);
                if (transcript.isEmpty()) {
                    TextToSpeech.synthesizeText("Sorry, I didn't catch that. Could you repeat it?");
                } else {
                    Commands.processCommand(transcript);
                }
            } catch (Exception ex) {
                LoggerUtil.logError(ex, "Error during speech recognition");
                ex.printStackTrace();
            }

            System.out.println("Press Enter to continue, or type 'q' to quit.");
        }

        scanner.close(); // Clean up scanner
    }
}