package project1;

import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class MusicPlayer extends Thread {

	private Player player;
	private FileInputStream fis;
	String filePath;

	public MusicPlayer(String filePath) {

		this.filePath = filePath;
	}

	void play() {
		try {
			fis = new FileInputStream(filePath);
			player = new Player(fis);
			player.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void stop_() {
		if (player != null) {
			player.close();
			player = null;
		}
	}
    @Override
    	public void run() {
    		play();//음악재생
    	}

}// class
