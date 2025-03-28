package com.example;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;

// Class to access mic, record audio and return it.
// No Google API info in this class
public class MicAccess {
    /*
     * Capture audio and return an array of bytes representing the audio clip
     * The audio clip is captured for a duration of 3 seconds, This can be changed
     * by modifying the end variable
     * No PARAMS
     * RETURNS: byte[] audioData
     */
    public static byte[] getAudioData() {

        // The format of the audio clip, NEEDS TO BE COMPATIBLE WITH GOOGLE SPEECH TO TEXT
        AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        // Check if the microphone is supported / Available, if not return null
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line not supported");
            return null;
        }
        //Try with resources to open the microphone and capture audio
        try (TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info)) {
            microphone.open(format);
            microphone.start();

            // Array of bytes to hold the audio clip
            byte[] data = new byte[microphone.getBufferSize() / 5];
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            // Capture audio for a specified duration 3000ms or 3 seconds
            long recordLength = System.currentTimeMillis() + 3000;

            // Capture audio for a specified duration -> end, writing the audio to the out stream
            while (System.currentTimeMillis() < recordLength) {
                int numBytesRead = microphone.read(data, 0, data.length);
                out.write(data, 0, numBytesRead);
            }

            // Save the audio clip in form of a byte[] and return results.
            byte[] audioData = out.toByteArray();
            return audioData;

        } catch (LineUnavailableException e) {
            LoggerUtil.logError(e, "Error accessing microphone");
            e.printStackTrace();
        }
        return null;
    }
}
