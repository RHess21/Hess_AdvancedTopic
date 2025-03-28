package com.example;

// Imports the Google Cloud client library
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;

import java.util.List;

public class Transcribe {

    /**
     * Performs speech recognition on raw audio data
     *
     * Created by GOOGLE, Modified by Myself for personal uses, further modification
     * will be done
     * This is their sample code for Syncronous Speech Recognition
     * https://cloud.google.com/speech-to-text/docs/sync-recognize
     * 
     */
    public static String syncRecognizeFile() throws Exception {
        try (SpeechClient speech = SpeechClient.create()) {

            // Get audio data from the mic
            byte[] data = MicAccess.getAudioData();
            if (data == null || data.length == 0) {
                LoggerUtil.logError(new Exception("No Audio"), "No audio data captured.");
                return "";
            }
            ByteString audioBytes = ByteString.copyFrom(data);

            // Configure request with local raw PCM audio
            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(AudioEncoding.LINEAR16)
                    .setLanguageCode("en-US")
                    .setSampleRateHertz(16000)
                    .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();

            // Use blocking call to get audio transcript
            RecognizeResponse response = speech.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();
            
            //Check if the results are empty
            //IF THEY ARE CHECK YOUR DAMN MIC
            if (results.isEmpty()) {
                LoggerUtil.logError(new Exception("No transcription results returned."),
                        "No transcription results returned.");
            }
            
            for (SpeechRecognitionResult result : results) {
                // There can be several alternative transcripts for a given chunk of speech.
                // Just use the first (most likely) one here.
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                return alternative.getTranscript();
            }
        } catch (Exception e) {
            LoggerUtil.logError(e, "Error during speech recognition");
            e.printStackTrace();
            
        }
        return "";
    }
}
