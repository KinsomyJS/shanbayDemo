package com.green.kinsomy.interview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.green.kinsomy.interview.adapter.ImageAdapter;
import com.green.kinsomy.interview.model.ImageModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kinsomy on 2017/10/20.
 */

public class Task2Activity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.rv_img)
    RecyclerView rvImg;

    ImageAdapter imageAdapter;

    String[] imageUrl = {
            "https://static.baydn.com/media/media_store/image/f1672263006c6e28bb9dee7652fa4cf6.jpg",
            "https://static.baydn.com/media/media_store/image/8c997fae9ebb2b22ecc098a379cc2ca3.jpg",
            "https://static.baydn.com/media/media_store/image/2a4616f067285b4bd59fe5401cd7106b.jpeg",
            "https://static.baydn.com/media/media_store/image/b0e3ab329c8d8218d2af5c8dfdc21125.jpg",
            "https://static.baydn.com/media/media_store/image/670abb28408a9a0fc3dd9666e5ca1584.jpeg",
            "https://static.baydn.com/media/media_store/image/1e8d675468ab61f4e5bdebd4bcb5f007.jpeg",
            "https://static.baydn.com/media/media_store/image/9b2f93cbfa104dae1e67f540ff14a4c2.jpg",
            "https://static.baydn.com/media/media_store/image/f5e0631e00a09edbbf2eb21eb71b4d3c.jpeg"
    };
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout srRefresh;

    private String[] imageFileName = new String[8];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        rvImg.setLayoutManager(new LinearLayoutManager(this));
        srRefresh.setOnRefreshListener(this);
    }

    private void initData() {
        imageAdapter = new ImageAdapter(this, R.layout.item_image, loadData());
        rvImg.setAdapter(imageAdapter);
    }

    //初始化图片本地文件名 并生成model
    private List<ImageModel> loadData() {
        List<ImageModel> imageModels = new ArrayList<>();
        for (int i = 0; i < imageUrl.length; i++) {
            String url = imageUrl[i];
            imageFileName[i] = url.substring(url.length() - 10, url.length());
            imageModels.add(new ImageModel(url, imageFileName[i]));
            Log.d("imagefile", imageFileName[i]);
        }

        return imageModels;
    }

    @Override
    public void onRefresh() {
        Log.d("onrefresh","onrefresh");
        imageAdapter.setNewData(loadData());
        srRefresh.setRefreshing(false);
    }
}
