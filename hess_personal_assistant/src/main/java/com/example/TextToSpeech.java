package com.example;

import java.io.ByteArrayInputStream;

import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;

import javazoom.jl.player.Player;

public class TextToSpeech {
      /**
   * Demonstrates using the Text to Speech client to synthesize text or ssml.
   * Retrieved this code from the Google Cloud Text-to-Speech Java documentation.
   * Slight modifications to play the audio using JLayer instead of saving it to a file.
   * @param text the raw text to be synthesized. In this case its the "AI" response to the user.
   * @throws Exception on TextToSpeechClient Errors.
   */
  public static void synthesizeText(String text) throws Exception {
    // Instantiates a client
    try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
      // Set the text input to be synthesized
      SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

      // Build the voice request
      VoiceSelectionParams voice =
          VoiceSelectionParams.newBuilder()
              .setLanguageCode("en-US") // languageCode = "en_us"
              .setSsmlGender(SsmlVoiceGender.FEMALE) // ssmlVoiceGender = SsmlVoiceGender.FEMALE
              .build();

      // Select the type of audio file you want returned
      AudioConfig audioConfig =
          AudioConfig.newBuilder()
              .setAudioEncoding(AudioEncoding.MP3) // MP3 audio.
              .build();

      // Perform the text-to-speech request
      SynthesizeSpeechResponse response =
          textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

      // Get the audio contents from the response
      ByteString audioContents = response.getAudioContent();

      playAudio(audioContents); // Play the audio
    }
  }

  /*
   * This method is called above to play the audio with Jlayer.
   * This means we don't need to save the audio to a file, we can play it directly from memory.
   * This is a more efficient way to handle audio playback, especially for short responses.
   * PARAMETERS: audioContents - ByteString containing the audio data.
   * Nothing is returned, the audio is played directly.
   */
  public static void playAudio(ByteString audioContents) {
        try {
            // Convert ByteString to InputStream
            ByteArrayInputStream inputStream = new ByteArrayInputStream(audioContents.toByteArray());

            // Use JLayer's Player to play the audio
            Player player = new Player(inputStream);
            player.play(); // Play the audio
        } catch (Exception e) {
            System.err.println("Error playing audio: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
