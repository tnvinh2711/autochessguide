package com.zinzin.autochessguide;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.wang.avi.AVLoadingIndicatorView;
import com.zinzin.autochessguide.model.ClassList;
import com.zinzin.autochessguide.model.Creep;
import com.zinzin.autochessguide.model.Item;
import com.zinzin.autochessguide.model.RaceList;
import com.zinzin.autochessguide.model.Units;
import com.zinzin.autochessguide.utils.SetImage;
import com.zinzin.autochessguide.utils.Utils;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private AVLoadingIndicatorView loading;
    private ImageView ivLogo;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ivLogo = findViewById(R.id.iv_logo);
        loading = findViewById(R.id.loading);
        YoYo.with(Techniques.Tada)
                .onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        Thread thread = new Thread(){
                            @Override
                            public void run() {
                                loadData();
                            }
                        };
                        thread.start();
                    }
                })
                .duration(1000)
                .repeat(1)
                .playOn(findViewById(R.id.iv_logo));
    }

    private void loadData() {
        List<RaceList> raceList = SetImage.fullRaceList(this);
        List<ClassList> classList = SetImage.fullClassList(this);
        List<Creep> creepList = SetImage.fullCreepList(this);
        List<Item> itemList = SetImage.fullItemList(this);
        List<Units> unitsList = SetImage.fullUnitsList(this, raceList, classList);
        final String races = Utils.convertObjToJson(raceList);
        final String classl = Utils.convertObjToJson(classList);
        final String creeps = Utils.convertObjToJson(creepList);
        final String units = Utils.convertObjToJson(unitsList);
        final String items = Utils.convertObjToJson(itemList);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                intent.putExtra("races", races);
                intent.putExtra("classl", classl);
                intent.putExtra("creeps", creeps);
                intent.putExtra("units", units);
                intent.putExtra("items", items);
                loading.hide();
                startActivity(intent);
                finish();
            }
        });

    }
}
