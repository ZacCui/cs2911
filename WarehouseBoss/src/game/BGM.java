package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class BGM {
	private AudioPlayer MGP;
	private boolean MUSIC_ON;
	private AudioStream BGM;
	private ContinuousAudioDataStream loop;
	
	public void newBGM(){
		this.MGP = AudioPlayer.player;
		this.MUSIC_ON = false;
		
	}
	public void Get_Music(){
		AudioData MD = null;
		try{
			this.BGM = new AudioStream(new FileInputStream(new File("src/music/bgm.wav").getAbsoluteFile()));
			MD = BGM.getData();
		this.loop = new ContinuousAudioDataStream(MD);
		}catch(IOException error){
			System.out.print("file not found");
		}
	}
	public void BGM(){
		if(!MUSIC_ON){
			this.MGP.start(loop);
			this.MUSIC_ON = true;
		}else{	
			this.MGP.stop(BGM);
			this.MGP.stop(loop);
			this.MUSIC_ON = false;
		}
	}
	
	public boolean isMUSIC_ON() {
		return MUSIC_ON;
	}
	public void setMUSIC_ON(boolean mUSIC_ON) {
		MUSIC_ON = mUSIC_ON;
	}
	public void winMusic() {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/music/win.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
}
