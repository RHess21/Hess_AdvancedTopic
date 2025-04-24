package com.example;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class Main {
    public static void main(String[] args) throws Exception {
        Terminal terminal = TerminalBuilder.terminal();
        System.out.println("Press any key to start transcription, or 'q' to quit.");

        while (true) {
            int key = terminal.reader().read();  // Captures a single key press (no Enter needed)

            if (key == 'q' || key == 'Q') {
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

            System.out.println("Press any key to continue, or 'q' to quit.");
        }

        terminal.close(); // Clean up terminal
    }
}
