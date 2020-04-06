package com.example.asm_firebase.Fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.asm_firebase.Model.Song;
import com.example.asm_firebase.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_MediaPlayer extends Fragment {
    private View view;
    ImageView imgPlay, imgPrev, imgNext;
    TextView txtName, txtStart, txtEnd;
    SeekBar seekBar;
    List<Song> songList;
    MediaPlayer mMediaPlayer;
    AnimatorSet moving;
    int index = 0;


    public Fragment_MediaPlayer() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_media_player, container, false);
        initView();
        return view;
    }
    private void initView() {
        imgPlay = (ImageView) view.findViewById(R.id.fMedia_ImgPlay);
        imgNext = (ImageView) view.findViewById(R.id.fMedia_ImgNext);
        imgPrev = (ImageView) view.findViewById(R.id.fMedia_ImgPrev);
        txtName = (TextView) view.findViewById(R.id.fMedia_TxtSongName);
        txtStart = (TextView) view.findViewById(R.id.fMedia_TxtStart);
        txtEnd= (TextView) view.findViewById(R.id.fMedia_TxtEnd);
        seekBar = (SeekBar) view.findViewById(R.id.fMedia_SeekBar);

        ObjectAnimator move1 = ObjectAnimator.ofFloat(txtName, View.TRANSLATION_X, 0, 1000);
        ObjectAnimator move2 = ObjectAnimator.ofFloat(txtName, View.TRANSLATION_X, -100, 1000);
        moving = new AnimatorSet();
        moving.setDuration(3000);
        moving.playSequentially(move1, move2);
        moving.setInterpolator(new LinearInterpolator());
        moving.start();
        moving.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                moving.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        addSong();
        createMedia();
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();

            }
        });

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev();
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void updateSeekBar(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                txtStart.setText(format.format(mMediaPlayer.getCurrentPosition()));
                seekBar.setProgress(mMediaPlayer.getCurrentPosition());
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        next();
                    }
                });

                handler.postDelayed(this,500);
            }
        },500);
    }
    private void setTimeTotal(){
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        txtEnd.setText(format.format(mMediaPlayer.getDuration()));
        seekBar.setMax(mMediaPlayer.getDuration());
    }

    private void play() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            imgPlay.setImageResource(R.drawable.play);
        } else {
            mMediaPlayer.start();
            imgPlay.setImageResource(R.drawable.pause);
        }
        setTimeTotal();
        updateSeekBar();
    }

    public void next(){
        index++;
        if (index > songList.size()-1){
            index = 0;
        }
        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
        }
        createMedia();
        mMediaPlayer.start();
        imgPlay.setImageResource(R.drawable.pause);
        setTimeTotal();
        updateSeekBar();

    }

    public void prev(){
        index--;
        if (index < 0){
            index = songList.size()-1;
        }
        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
        }
        createMedia();
        mMediaPlayer.start();
        imgPlay.setImageResource(R.drawable.pause);
        setTimeTotal();
        updateSeekBar();

    }
    private void createMedia(){
        mMediaPlayer = MediaPlayer.create(getActivity(),songList.get(index).getFile());
        txtName.setText(songList.get(index).getTitle());
    }
    private void addSong(){
        songList = new ArrayList<>();
        songList.add(new Song("Bad Day",R.raw.bad_day));
        songList.add(new Song("Beautiful Girls",R.raw.beautiful_girls));
        songList.add(new Song("Can't Take My Eyes Off you",R.raw.cant_take_my_eyes_off_you));
        songList.add(new Song("Shape Of You",R.raw.shape_of_you));
    }

    @Override
    public void onPause() {
        mMediaPlayer.stop();
        imgPlay.setImageResource(R.drawable.play);
        super.onPause();
    }
}
