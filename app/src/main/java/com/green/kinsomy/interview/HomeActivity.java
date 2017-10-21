package com.green.kinsomy.interview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kinsomy on 2017/10/20.
 */

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_home);
        ButterKnife.bind(this);
        Log.d("filePath",getApplicationContext().getExternalFilesDir("Images").getAbsolutePath());
    }

    @OnClick({R.id.btn_task1, R.id.btn_task2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_task1:
                startActivity(new Intent(this,Task1Activity.class));
                break;
            case R.id.btn_task2:
                startActivity(new Intent(this,Task2Activity.class));
                break;
        }
    }
}
