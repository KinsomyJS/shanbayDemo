package com.green.kinsomy.interview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.green.kinsomy.interview.GlideApp;
import com.green.kinsomy.interview.R;
import com.green.kinsomy.interview.model.ImageModel;
import com.green.kinsomy.interview.util.FileUtils;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kinsomy on 2017/10/21.
 */

public class ImageAdapter extends BaseQuickAdapter<ImageModel,BaseViewHolder> {

    private Context context;

    public ImageAdapter(int layoutResId, List<ImageModel> data) {
        super(layoutResId, data);
    }

    public ImageAdapter(Context context,int layoutResId, List<ImageModel> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ImageModel item) {
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                if(FileUtils.findFile(context,item.getImageFilename())){
                    String filePath = context.getApplicationContext().getExternalFilesDir("Images") + File.separator +item.getImageFilename();
                    Log.d("adapter",filePath);
                    observableEmitter.onNext(filePath);
                }else{
                    Log.d("adapter",item.getImageUrl());
                    observableEmitter.onNext(item.getImageUrl());
                }
            }

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(String image) {
                Log.d("adapter onnext",image);
                if(image.startsWith("http")){
                    helper.setImageResource(R.id.image,R.mipmap.placeholder);
                    Target<Bitmap> target = GlideApp.with(context)
                            .asBitmap()
                            .load(item.getImageUrl())
                            .placeholder(R.mipmap.placeholder)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(final Bitmap bitmap, Transition<? super Bitmap> transition) {
                                    helper.setImageBitmap(R.id.image,bitmap);
                                    Log.d("adapter","show");
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            FileUtils.saveImage(context,item.getImageFilename(),bitmap);
                                            Log.d("adapter","save");
                                        }
                                    }).start();

                                }
                            });
                }else{
                    GlideApp.with(context)
                            .load(image)
                            .placeholder(R.mipmap.placeholder)
                            .into((ImageView) helper.getView(R.id.image));
                }
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
