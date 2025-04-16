package com.example;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    /*
     * Main method to start the program.
     * Experimented with chatGPT to find the best way to capture the input from the user.
     * The program listens for key events and starts the transcription when the shift key is pressed.
     * The program exits when the ESC key is pressed.
     * 
     * Has to be done this way instead of streaming all audio until program stops because THIS ISNT FREE.
     */

    public static void main(String[] args) {
        // Creates a JFRAME to capture the key events 
        JFrame frame = new JFrame("Key Listener");
        frame.setSize(100, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    System.out.println("Shift key pressed, starting transcription...");
                    try {
                        String transcript = Transcribe.syncRecognizeFile();
                        System.out.println("Transcript: " + transcript);
                        if(transcript == ""){
                            TextToSpeech.synthesizeText("Sorry, I didn't catch that. Could you repeat it?");
                        }
                        else{
                            Commands.processCommand(transcript);
                        }
                    } catch (Exception ex) {
                        LoggerUtil.logError(ex, "Error during speech recognition");
                        ex.printStackTrace();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.out.println("ESC key pressed, exiting program...");
                    System.exit(0);
                }
            }
        });
        System.out.println("Press Shift to start transcription or ESC to exit.");
    }
}
