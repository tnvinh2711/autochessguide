package com.zinzin.autochessguide;

import android.animation.Animator;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;
import com.zinzin.autochessguide.utils.Contants;
import com.zinzin.autochessguide.utils.Preference;

public class SplashActivity extends AppCompatActivity {
    private AVLoadingIndicatorView loading;
    private ValueEventListener valueEventListener;
    private String classlist, unitlist, racelist, itemlist, creeplist, version, versionN, versionNFB;
    private Integer isSubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loading = findViewById(R.id.loading);
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionN = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        YoYo.with(Techniques.Tada)
                .onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        Thread thread = new Thread() {
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
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("chess");
        myRef.child("info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey() != null) {
                        switch (ds.getKey()) {
                            case "submit":
                                isSubmit = ds.getValue(Integer.class);
                                break;
                            case "version":
                                version = ds.getValue(String.class);
                                break;
                            case "versionName":
                                versionNFB = ds.getValue(String.class);
                                break;
                        }
                    }
                }
                if (isSubmit != null) {
                    if (isSubmit == 0 && versionNFB.equals(versionN)) {
                        gotoMain();
                    } else {
                        String versionOld = Preference.getString(SplashActivity.this, Contants.VERSION_CHESS);
                        if (!versionOld.equals(version)) {
                            Preference.save(SplashActivity.this, Contants.VERSION_CHESS, version);
                            valueEventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        if (ds.getKey() != null) {
                                            switch (ds.getKey()) {
                                                case "classlist":
                                                    classlist = ds.getValue(String.class);
                                                    Preference.save(SplashActivity.this, Contants.FIREBASE_LIST_CLASS, classlist);
                                                    break;
                                                case "creeplist":
                                                    creeplist = ds.getValue(String.class);
                                                    Preference.save(SplashActivity.this, Contants.FIREBASE_LIST_CREEP, creeplist);
                                                    break;
                                                case "itemlist":
                                                    itemlist = ds.getValue(String.class);
                                                    Preference.save(SplashActivity.this, Contants.FIREBASE_LIST_ITEM, itemlist);
                                                    break;
                                                case "racelist":
                                                    racelist = ds.getValue(String.class);
                                                    Preference.save(SplashActivity.this, Contants.FIREBASE_LIST_RACE, racelist);
                                                    break;
                                                case "unitlist":
                                                    unitlist = ds.getValue(String.class);
                                                    Preference.save(SplashActivity.this, Contants.FIREBASE_LIST_UNIT, unitlist);
                                                    break;
                                                case "submit":
                                                    isSubmit = ds.getValue(Integer.class);
                                                    break;
                                            }
                                        }
                                    }

                                    gotoMain();
                                    myRef.removeEventListener(valueEventListener);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            };
                            myRef.addValueEventListener(valueEventListener);
                        } else {
                            gotoMain();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void gotoMain() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra("submit", isSubmit);
                intent.putExtra("version_name", versionNFB);
                loading.hide();
                startActivity(intent);
                finish();
            }
        });
    }
}
