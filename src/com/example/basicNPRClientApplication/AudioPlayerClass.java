package com.example.basicNPRClientApplication;





import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;

public class AudioPlayerClass implements OnPreparedListener, MediaPlayerControl {
	static Activity mActivity;
	public static MediaPlayer mediaPlayer;
	public static MediaController mediaController;
	public static Handler handler = new Handler();
	public static boolean b = false;
	
	public AudioPlayerClass(Activity mActivity, String url) {
		super();
		b = true;
		AudioPlayerClass.mActivity = mActivity;
		mediaPlayer = new MediaPlayer();
		mediaController = new MediaController(mActivity);
		mediaPlayer.setOnPreparedListener(this);	
	    
	    try {
	    	mediaPlayer.setDataSource(url);
			mediaPlayer.prepareAsync();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    mediaController.show();
	}

	public void playMedia(String url){
		
	}

	@Override
	public boolean canPause() {
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		return true;
	}

	@Override
	public boolean canSeekForward() {
		return true;
	}

	@Override
	public int getBufferPercentage() {
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		return mediaPlayer.getCurrentPosition();
	}

	@Override
	public int getDuration() {
		return mediaPlayer.getDuration();
	}

	@Override
	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}

	@Override
	public void pause() {
		 mediaPlayer.pause();
	}

	@Override
	public void seekTo(int pos) {
		mediaPlayer.seekTo(pos);
	}
	
	@Override
	public void start() {
		mediaPlayer.start();
	}

	
	
	@Override
	public void onPrepared(MediaPlayer mp) {
		mediaPlayer.start();
	    mediaController.setMediaPlayer(this);
	    mediaController.setAnchorView(mActivity.findViewById(R.id.story_Layout));

	    handler.post(new Runnable() {
	      public void run() {
	        mediaController.setEnabled(true);
	        mediaController.show();
	      }
	    });
	}
	
	

	@Override
	public int getAudioSessionId() {
		return 0;
	}
	
	

	

}
