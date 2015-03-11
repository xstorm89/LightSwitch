package test;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javaFlacEncoder.FLACStreamOutputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;

import javazoom.jl.player.Player;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.darkprograms.speech.recognizer.FlacEncoder;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.RecognizerChunked;
import com.darkprograms.speech.synthesiser.SynthesiserV2;
import com.gearcode.stt.capture.CaptureVoice;
import com.gearcode.stt.capture.VoiceClipListener;
import com.gearcode.stt.capture.VoiceLevelListener;


public class voiceTest {
	
	final CaptureVoice captureVoice = new CaptureVoice();
	final SynthesiserV2 synthesiser= new SynthesiserV2("AIzaSyCdTL2nO3LuLK1jwE6cctvN1zkcBmtTN8A");
	String recongnizedString;
	boolean returned;


	@Before
	public void setup() throws LineUnavailableException{
		
		returned =false;
		recongnizedString =" ";
		synthesiser.setLanguage("en-US");
	
	
		captureVoice.levelListener = new VoiceLevelListener() {
			public void captureLevel(int level) {
				System.out.println(level);
				if(level>=8){
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

				}
					
			}
		};
		
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
				RecognizerChunked recognizer = new RecognizerChunked("AIzaSyCdTL2nO3LuLK1jwE6cctvN1zkcBmtTN8A","en-US");

				
				try {
					 recognizer.getRecognizedDataForFlac(flacOS.toByteArray(),8000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				recognizer.responseListener = new GSpeechResponseListener(){

					@Override
					public void  onResponse(GoogleResponse gr)  {
						recongnizedString=gr.getResponse();
						System.out.println(gr.getResponse()+ " "+ gr.getConfidence());
						
						returned = true;
						captureVoice.stop();

					}	
				};
			}
		};
		
		captureVoice.start();

	}//end of setup
	
	@Test
	public void testHello1(){	
		
		System.out.println("test1:");
		Assert.assertEquals("hello echo", recongnizedString);

	}
	
	@Test
	public void testHello2(){
		System.out.println("test1_1:");
		Assert.assertTrue(recongnizedString.contains("hello")||recongnizedString.contains("echo"));	

	}
	
	@Test
	public void testLight1(){		
		
		System.out.println("test2");

		Assert.assertEquals("turn on the light", recongnizedString);

	}
	
	@Test
	public void testLight2(){
		
		System.out.println("test2_1");
		Assert.assertTrue(recongnizedString.contains("light")&&recongnizedString.contains("on"));	

	}
	

	
}