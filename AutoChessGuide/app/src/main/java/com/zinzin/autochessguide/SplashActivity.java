package com.zinzin.autochessguide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wang.avi.AVLoadingIndicatorView;
import com.zinzin.autochessguide.model.ClassList;
import com.zinzin.autochessguide.model.Creep;
import com.zinzin.autochessguide.model.Item;
import com.zinzin.autochessguide.model.RaceList;
import com.zinzin.autochessguide.model.Units;
import com.zinzin.autochessguide.utils.SetImage;
import com.zinzin.autochessguide.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private List<Units> unitsList = new ArrayList<>();
    private List<RaceList> raceList = new ArrayList<>();
    private List<ClassList> classList = new ArrayList<>();
    private List<Creep> creepList = new ArrayList<>();
    private List<Item> itemList = new ArrayList<>();
    private AVLoadingIndicatorView loading;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.e("race", "vào app");
        loading = findViewById(R.id.loading);
        Thread thread = new Thread(){
            @Override
            public void run() {
                loadData();
            }
        };
        thread.start();
    }

    private void loadData() {
        raceList = SetImage.fullRaceList(this);
        classList = SetImage.fullClassList(this);
        creepList = SetImage.fullCreepList(this);
        itemList = SetImage.fullItemList(this);
        unitsList = SetImage.fullUnitsList(this, raceList, classList);
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
