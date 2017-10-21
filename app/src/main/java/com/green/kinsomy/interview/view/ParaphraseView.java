package com.green.kinsomy.interview.view;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.green.kinsomy.interview.R;
import com.green.kinsomy.interview.model.WordResult;
import com.green.kinsomy.interview.util.HiddenAnimUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kinsomy on 2017/10/19.
 */

public class ParaphraseView extends RelativeLayout implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {


    @BindView(R.id.tv_word)
    TextView tvWord;
    @BindView(R.id.tv_phonogram)
    TextView tvPhonogram;
    @BindView(R.id.tv_pronunciation)
    TextView tvPronunciation;
    @BindView(R.id.tv_definition)
    TextView tvDefinition;
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.cl_result)
    ConstraintLayout clResult;
    @BindView(R.id.iv_expand)
    ImageView ivExpand;
    @BindView(R.id.tv_sentence)
    TextView tvSentence;
    @BindView(R.id.cl_sentence)
    ConstraintLayout clSentence;

    private Context context;

    private WordResult word;

    private MediaPlayer mediaPlayer;

    public ParaphraseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.paraphraseview, this, true);
        ButterKnife.bind(this);
    }


    //控件底部弹出动作
    public void show(int code, boolean isAnim, Animation animation) {
        getView();
        if (isAnim) {
            if (code == 0) {
                clResult.setVisibility(VISIBLE);
                tvFail.setVisibility(GONE);
            } else {
                clResult.setVisibility(GONE);
                tvFail.setVisibility(VISIBLE);
            }
            setVisibility(VISIBLE);
            startAnimation(animation);
        } else {
            clResult.setVisibility(VISIBLE);
        }
    }

    //控件隐藏
    public void hide(boolean isAnim, Animation animation) {
        if (isAnim) {
            setVisibility(GONE);
            startAnimation(animation);
        } else {
            clResult.setVisibility(GONE);
        }
    }

    public void setData(WordResult wordResult) {
        this.word = wordResult;
    }

    private void getView() {
        tvWord.setText(word.getContent());
        tvPhonogram.setText("/" + word.getPronunciation() + "/");
        tvDefinition.setText(word.getDefinition());
    }

    //播放网络音频
    public void playUrl(String videoUrl) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(videoUrl);
            mediaPlayer.prepare();//prepare之后自动播放
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            //mediaPlayer.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //播放完毕释放资源
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.release();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @OnClick({R.id.tv_pronunciation, R.id.iv_expand})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_pronunciation:
                if (word.getAudio_addresses().getUs().size() > 0) {
                    playUrl(word.getAudio_addresses().getUs().get(0));
                } else {
                    Toast.makeText(context, "暂无发音", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_expand:
                Log.d("tv_expand", "tv_expand");
                HiddenAnimUtils.newInstance(context,clSentence,ivExpand,150).toggle();
                break;
        }
    }

}
