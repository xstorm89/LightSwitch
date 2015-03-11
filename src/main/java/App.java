

import gpio.GpioControl;
import gpio.MyLcd;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javaFlacEncoder.FLACStreamOutputStream;

import javax.servlet.MultipartConfigElement;
import javax.sound.sampled.AudioInputStream;

import javazoom.jl.player.Player;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import sun.audio.*;

import com.darkprograms.speech.recognizer.FlacEncoder;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.RecognizerChunked;
import com.darkprograms.speech.synthesiser.SynthesiserV2;
import com.gearcode.stt.capture.CaptureVoice;
import com.gearcode.stt.capture.VoiceClipListener;
import com.gearcode.stt.capture.VoiceLevelListener;


@Configuration
@EnableAutoConfiguration
@ComponentScan
public class App {                

	static boolean status =false;

    public static void main(String[] args) throws Exception {
        System.setProperty(Logger.ROOT_LOGGER_NAME, "TRACE");

    	// Please ignore the following system property for now.
    	// This will be used and explained in the later assignments.
        System.setProperty("java.awt.headless", "false");
        // Run Spring Boot
        SpringApplication.run(App.class, args);
        
        final CaptureVoice captureVoice = new CaptureVoice();
		final SynthesiserV2 synthesiser= new SynthesiserV2("AIzaSyCdTL2nO3LuLK1jwE6cctvN1zkcBmtTN8A");
		
//		final GpioControl pin_voice =new GpioControl(0,0);//voice led
//		final GpioControl pin_start =new GpioControl(1,0);//startup led
//		final GpioControl pin_light =new GpioControl(2,1);
//		final GpioControl pin_tv =new GpioControl(3,1);
		
//
//		final MyLcd lcd= new MyLcd();
//		lcd.setText("Echo v1.0");

		synthesiser.setLanguage("en-US");
		/*
		 * 监听声音大小变化
		 */
		captureVoice.levelListener = new VoiceLevelListener() {
			public void captureLevel(int level) {
				System.out.println(level);
				if(level>=8){
//						pin_voice.turnOnPin();
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						pin_voice.turnOffPin();

				}
					
			}
		};
		/*
		 * 监听到声音片段
		 */
		captureVoice.clipListener = new VoiceClipListener() {
			public void captureClip(AudioInputStream clipAIS) {
				/*
				 * convert waveStream to flacStream
				 */
				ByteArrayOutputStream flacOS = new ByteArrayOutputStream();
				FlacEncoder flacEncoder = new FlacEncoder();
				try {
					flacEncoder.convertWaveToFlac(clipAIS, new FLACStreamOutputStream(flacOS));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				/*
				 * 语音识别
				 */
//				RecognizerChunked recognizer = new RecognizerChunked("AIzaSyDWR3mIAv6IJuvOXcfRrDBIxylYkQBJOdU","en-US");
				RecognizerChunked recognizer = new RecognizerChunked("AIzaSyCdTL2nO3LuLK1jwE6cctvN1zkcBmtTN8A","en-US");

				
				try {
					 recognizer.getRecognizedDataForFlac(flacOS.toByteArray(),8000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				recognizer.responseListener = new GSpeechResponseListener(){

					@Override
					public void  onResponse(GoogleResponse gr)  {
						String recongnizedString="";
						String synthText="";
						recongnizedString=gr.getResponse();	
//						lcd.setText(recongnizedString);


						System.out.println(gr.getResponse()+ " "+ gr.getConfidence());

						
						if(recongnizedString.contains("echo")|| recongnizedString.contains("hi")|| recongnizedString.contains("hey")|| recongnizedString.contains("hello")){
//							pin_start.turnOnPin();
							synthText="Hi, what can I do for you?";
//							lcd.setText(synthText);
							status =true;
							
						}
						else if(status&&recongnizedString.contains("light")&&recongnizedString.contains("on")){
							//pin_light.turnOffPin();
							synthText="ok, turn on the light";
							//lcd.setText(synthText);

						}
						
						else if(status&&recongnizedString.contains("light")&&recongnizedString.contains("off")){
							
//							pin_light.turnOnPin();
							synthText="alright, light off";
//							lcd.setText(synthText);


						}
						else if(status&&recongnizedString.contains("thank you")){
							
//							pin_start.turnOffPin();
							synthText= "not a problem ";	
//							lcd.setText(synthText);
							status= false;
//							pin_start.turnOffPin();


						}
						else if(status&&recongnizedString.contains("on")&&recongnizedString.contains("TV")){
							
//							pin_tv.turnOffPin();							
							synthText= "ok, TV on";	
							//lcd.setText(synthText);
							
						}
						else if(status&&recongnizedString.contains("off")&&recongnizedString.contains("TV")){
//							pin_tv.turnOnPin();							
							synthText= "ok ,TV off";	
//							lcd.setText(synthText);
						}


						else if( status&&recongnizedString.contains("music"))
						{

						 
						    // create an audiostream from the inputstream
							System.out.println("Playing: <<Merry Christmas>>...");
							synthText="Music Playing: Merry Christmas...";
//							lcd.setText(synthText);
							AudioStream audioStream;
							try {
							    String songFile = "christmas.wav";
							    InputStream in = new FileInputStream(songFile);;
								audioStream = new AudioStream(in);
							    AudioPlayer.player.start(audioStream);
							    
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					
						}
						else{
							synthText="";
//							lcd.setText("I don't understand");
						}

							
						
						try {
							
							Player player; 

							System.out.println(synthText);
							
							if(!synthText.equals("")){
								BufferedInputStream bis = new BufferedInputStream(synthesiser.getMP3Data(synthText)); 	
								player = new Player(bis);
								player.play();
							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}	
				};
			}
		};
		
		captureVoice.start();
	}
 
}

