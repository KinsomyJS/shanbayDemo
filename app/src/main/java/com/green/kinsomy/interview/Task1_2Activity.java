package com.green.kinsomy.interview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.green.kinsomy.interview.model.ResponseResult;
import com.green.kinsomy.interview.model.WordResult;
import com.green.kinsomy.interview.network.observer.CommonObserver;
import com.green.kinsomy.interview.network.service.ApiService;
import com.green.kinsomy.interview.view.NewsText;
import com.green.kinsomy.interview.view.ParaphraseView;

import java.text.BreakIterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Task1_2Activity extends AppCompatActivity {

    @BindView(R.id.tv_News)
    NewsText tvNews;

    private ApiService apiService;
    private ParaphraseView paraphraseView;

    //分词类
    BreakIterator breakIterator;
    SpannableStringBuilder style;
    ForegroundColorSpan whiteSpan = new ForegroundColorSpan(Color.WHITE);
    BackgroundColorSpan AccentSpan = new BackgroundColorSpan(Color.parseColor("#FF4081"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1_2);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        tvNews.setText(news, TextView.BufferType.SPANNABLE);
        tvNews.setTextSize(20);
        setClickableSpan(tvNews);
        tvNews.setMovementMethod(LinkMovementMethod.getInstance());
        tvNews.setHighlightColor(Color.WHITE);
        paraphraseView = (ParaphraseView) findViewById(R.id.bottom_view);


    }

    private void initData() {
        apiService = HttpControl.getInstance().getRetrofit().create(ApiService.class);
    }

    private void setClickableSpan(TextView textView){
        Spannable spans = (Spannable)textView.getText();
        String str = textView.getText().toString();
        breakIterator = BreakIterator.getWordInstance();
        breakIterator.setText(str);
        int start = breakIterator.first();
        for (int end = breakIterator.next(); end != BreakIterator.DONE; start = end, end = breakIterator.next()) {

            //筛选出只含单词的项
            if(str.substring(start, end).matches("[A-Za-z]+")){
                ClickableSpan clickSpan = getClickableSpan();
                Log.d("breakIterator", str.substring(start, end));
                spans.setSpan(clickSpan, start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private ClickableSpan getClickableSpan(){
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                TextView tv = (TextView) widget;
                int s_index = tv.getSelectionStart();
                int e_index = tv.getSelectionEnd();
                String word= tv.getText()
                        .subSequence(s_index, e_index).toString();

                Log.d("tapped on:", word + tv.getSelectionStart() + "--" +tv.getSelectionEnd());
                style = new SpannableStringBuilder(tv.getText());
                style.setSpan(AccentSpan,s_index,e_index,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv.setText(style);
                searchWord(word);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                Log.d("updateDrawState","updateDrawState");
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };
    }

    private void searchWord(String word){
        apiService.search(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<WordResult>() {

                    @Override
                    protected void onSuccees(ResponseResult<WordResult> t) throws Exception {
                        paraphraseView.setData(t.getData());
                        paraphraseView.show(t.getStatus_code(),true,getShowAnim());
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Toast.makeText(Task1_2Activity.this,"请求失败",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //拦截返回
    @Override
    public void onBackPressed() {
        if(paraphraseView.getVisibility() == View.VISIBLE)
            paraphraseView.hide(true,getHideAnim());
        else
            super.onBackPressed();
    }

    //配置展示动画
    private Animation getShowAnim(){
        Animation show = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 1,
                Animation.RELATIVE_TO_PARENT, 0);

        show.setDuration(500);
        show.setInterpolator(AnimationUtils
                .loadInterpolator(Task1_2Activity.this,
                        android.R.anim.accelerate_decelerate_interpolator));
        return show;
    }

    //可配置隐藏动画
    private Animation getHideAnim(){
        Animation hide = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 1);

        hide.setDuration(500);
        hide.setInterpolator(AnimationUtils
                .loadInterpolator(Task1_2Activity.this,
                        android.R.anim.accelerate_decelerate_interpolator));
        return hide;
    }

    String news = "KIEV — The European Union warned Ukraine on Thursday time was running out to revive shelved deals on free trade and political association by meeting the bloc's concerns over the jailing of opposition leader Yulia Tymoshenko and bringing in reforms.\n" +
            "A senior EU official also made it clear the agreements would fall through if Ukraine joined the Russia-led post-Soviet Customs Union trade bloc. \"We have a window of opportunity. But time is short,'' Stefan Fuele, the European Commissioner for Enlargement and European Neighbourhood Policy, said on a visit to Ukraine.\n" +
            "Brussels put off signing the landmark agreements after a Ukrainian court jailed former prime minister Tymoshenko, President Viktor Yanukovich's main opponent, on an abuse- of-office charge in October 2011.\n" +
            "The EU says the Tymoshenko case and those of other prosecuted opposition politicians are examples of selective justice and are a barrier to Ukraine's ambition of European integration.\n" +
            "Two other issues raised by the bloc are related to the electoral system, which came under fire from Western observers following the parliamentary election in October, and legal reforms needed to bring Ukraine closer to EU standards.\n" +
            "\"The European Union is committed to signing the association agreement...provided there is determined action and tangible progress on the three key issues: selective justice, addressing the shortcomings of the October election and advancing the association agenda reforms,'' Fuele told reporters. \"After several recent setbacks in Ukraine there is a need to regain confidence that Ukraine could emerge as a modern European country.''\n" +
            "Fuele, whose visit may set the tone of a Feb. 25 EU-Ukraine summit, said the two agreements could be signed at the EU's Eastern Partnership summit in November if the former Soviet republic met the bloc's conditions.\n" +
            "But he warned the Kiev government that joining a customs union with Russia, aggressively promoted by Moscow, would ruin those prospects.\n" +
            "\"Joining any structure which would imply transferring the ability to set tariffs and define its trade policy to a supranational body would mean that Ukraine would no longer be able to implement the tariff dismantling agreed with the European Union in the context of the DCFTA [Deep and Comprehensive Free Trade Agreement],'' Fuele said in a speech at the Ukrainian parliament.\n" +
            "\"It would also not be able anymore to regulate areas such as food standards, or technical product standards, all of them important in the framework of the DCFTA. It will not be able to integrate economically with the European Union,\" he continued.\n" +
            "Ukrainian officials say they are committed to European integration. But they say they are also looking for a way to cooperate with the Customs Union because both blocs are Ukraine's major trade partners.\n" +
            "Fuele urged Ukraine to make sure it adopts and implements laws that actually work and adhere to European standards, citing as an example the law on state procurement - purchases of goods and services by the government.\n" +
            "The EU suspended some of its Ukraine financial aid programs after Kiev adopted a law on state procurement which Brussels said was riddled with loopholes and thus failed to ensure transparent and competitive procedures.";

}
