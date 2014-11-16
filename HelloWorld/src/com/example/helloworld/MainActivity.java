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
		 int bufsize = AudioTrack.getMinBufferSize(20000, AudioFormat.CHANNEL_OUT_STEREO,//˫����
			           AudioFormat.ENCODING_PCM_8BIT);
		AudioTrack audio = new AudioTrack(
				AudioManager.STREAM_MUSIC,// ָ������������
				20000,// ������Ƶ���ݵĲ����� 32k�������44.1k����44100
				AudioFormat.CHANNEL_OUT_STEREO,// �����������Ϊ˫��������������CHANNEL_OUT_MONO�����ǵ�����
				AudioFormat.ENCODING_PCM_8BIT,// ������Ƶ���ݿ���8λ����16λ����������Ϊ16λ���������ھ����������Ƶ����16λ����
				bufsize * 2, 
				AudioTrack.MODE_STREAM );// ����ģʽ���ͣ�����������Ϊ�����ͣ�����һ��MODE_STATICò��û��ʲôЧ��	
				
		audio.play(); // ������Ƶ�豸������Ϳ���������ʼ��Ƶ���ݵĲ�����
		// ��mp3�ļ�����ȡ���ݣ�����Ȳ���ʡ�� ...
		byte[] buffer = new byte[4096];
		for(int i=0;i<4090;i+=2){
			buffer[i] = (byte) 200;
			buffer[i+1] = (byte) 2;
		}
		 
		audio.setStereoVolume( leftVolume?AudioTrack.getMaxVolume():0, rightVolume?AudioTrack.getMaxVolume():0) ;
		// ��ؼ����ǽ����������ݣ��ӻ�����д�뵽AudioTrack������
		
		audio.write(buffer, 0, 4096);
			 
		
		// �������˹رղ��ͷ���Դ
		audio.stop();
		audio.release();
	}

}
