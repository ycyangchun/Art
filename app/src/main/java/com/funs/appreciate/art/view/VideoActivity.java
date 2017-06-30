package com.funs.appreciate.art.view;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.funs.appreciate.art.R;

/**
 * Created by yc on 2017/6/30.
 *  video
 */

public class VideoActivity extends FragmentActivity {

    VideoView videoView;
    String videoUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = (VideoView) findViewById(R.id.video_vv);
        videoUrl = getIntent().getStringExtra("videoUrl");
        if(videoUrl != null) {
            Uri uri = Uri.parse(videoUrl);
            //设置视频控制器
            videoView.setMediaController(new MediaController(this));
            //播放完成回调
            videoView.setOnCompletionListener(new MyPlayerOnCompletionListener());
            videoView.setVideoURI(uri);
            videoView.start();
            videoView.requestFocus();
        }
    }
    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
           finish();
        }
    }
}
