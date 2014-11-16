package com.example.helloworld;

import java.util.concurrent.TimeUnit;

import android.media.AudioFormat;
import android.media.AudioTrack;

import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
private boolean flag =true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btFocus,btSnap;  
		btFocus = (Button) findViewById(R.id.FocusId);  
		btSnap = (Button) findViewById(R.id.SnapId);  
		btFocus.setOnClickListener(new View.OnClickListener() {  

			

			@Override  
			public void onClick(View v) {  
				// TODO Auto-generated method stub  
				Log.i("TEST", "Focus onClick");
				
				playAudio(false,true);
				
				 
			}  
		});
		btSnap.setOnClickListener(new View.OnClickListener() {  

			@Override  
			public void onClick(View v) {  
				// TODO Auto-generated method stub  
				Log.i("TEST", "Snap onClick");
				 
				playAudio(true,true);
			}  
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void playAudio(boolean leftVolume, boolean rightVolume)
	{
		 int bufsize = AudioTrack.getMinBufferSize(20000, AudioFormat.CHANNEL_OUT_STEREO,//双声道
			           AudioFormat.ENCODING_PCM_8BIT);
		AudioTrack audio = new AudioTrack(
				AudioManager.STREAM_MUSIC,// 指定在流的类型
				20000,// 设置音频数据的采样率 32k，如果是44.1k就是44100
				AudioFormat.CHANNEL_OUT_STEREO,// 设置输出声道为双声道立体声，而CHANNEL_OUT_MONO类型是单声道
				AudioFormat.ENCODING_PCM_8BIT,// 设置音频数据块是8位还是16位，这里设置为16位。好像现在绝大多数的音频都是16位的了
				bufsize * 2, 
				AudioTrack.MODE_STREAM );// 设置模式类型，在这里设置为流类型，另外一种MODE_STATIC貌似没有什么效果	
				
		audio.play(); // 启动音频设备，下面就可以真正开始音频数据的播放了
		// 打开mp3文件，读取数据，解码等操作省略 ...
		byte[] buffer = new byte[4096];
		for(int i=0;i<4090;i+=2){
			buffer[i] = (byte) 200;
			buffer[i+1] = (byte) 2;
		}
		 
		audio.setStereoVolume( leftVolume?AudioTrack.getMaxVolume():0, rightVolume?AudioTrack.getMaxVolume():0) ;
		// 最关键的是将解码后的数据，从缓冲区写入到AudioTrack对象中
		
		audio.write(buffer, 0, 4096);
			 
		
		// 最后别忘了关闭并释放资源
		audio.stop();
		audio.release();
	}

}
