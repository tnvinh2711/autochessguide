package com.zinzin.tierbuilder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zinzin.tierbuilder.fragment.BuiderFragment;
import com.zinzin.tierbuilder.fragment.CreepsFragment;
import com.zinzin.tierbuilder.fragment.DetailFragment;
import com.zinzin.tierbuilder.fragment.ItemFragment;
import com.zinzin.tierbuilder.fragment.ListBuilderFragment;
import com.zinzin.tierbuilder.fragment.UnitsFragment;
import com.zinzin.tierbuilder.model.ClassList;
import com.zinzin.tierbuilder.model.Creep;
import com.zinzin.tierbuilder.model.Item;
import com.zinzin.tierbuilder.model.RaceList;
import com.zinzin.tierbuilder.model.Units;
import com.zinzin.tierbuilder.utils.Contants;
import com.zinzin.tierbuilder.utils.Preference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener, DrawerLocker {
    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;
    private AdView mAdView;
    private LinearLayout llLoading;
    private List<Units> unitsList = new ArrayList<>();
    private List<RaceList> raceList = new ArrayList<>();
    private List<ClassList> classList = new ArrayList<>();
    private List<Creep> creepList = new ArrayList<>();
    private List<Item> itemList = new ArrayList<>();
    private UnitsFragment unitsFragment;
    private ListBuilderFragment buiderFragment;
    private CreepsFragment creepsFragment;
    private ItemFragment itemFragment;
    private Fragment[] fragments;
    private ArrayList<String> mTitles = new ArrayList<>();
    private InterstitialAd mInterstitialAd, mInterstitialAdClick;
    private Animation slide_down;
    private Animation slide_up;
    private LinearLayout llFooter;
    private String versionN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slide_down = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        slide_up = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        MobileAds.initialize(this, "ca-app-pub-5796098881172039~1464434456");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAdClick = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5796098881172039/8642896953");
        mInterstitialAdClick.setAdUnitId("ca-app-pub-5796098881172039/3125000488");
        mInterstitialAdClick.loadAd(new AdRequest.Builder().build());
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionN = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (Preference.getBoolean(this, "firstrun", true)) {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        } else {
            long timeOld = Preference.getLong(this, "Time", 0);
            long timeNew = System.currentTimeMillis();
            if (timeOld != 0 && timeNew - timeOld >= 86400000) {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            } else {
                if (!Preference.getBoolean(MainActivity.this, "LoadAds", false)) {
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
            }
        }
        mAdView = findViewById(R.id.adView);
        mAdView.setVisibility(View.GONE);
        loadAd();
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));
        // Initialize the views
        mViewHolder = new ViewHolder();

        // Handle toolbar actions
        handleToolbar();

        // Handle menu actions
        handleMenu();

        // Handle drawer actions
        handleDrawer();

        mMenuAdapter.setViewSelected(0, true);
        setTitle(mTitles.get(0));
        llLoading = findViewById(R.id.ll_loading);
        loadData();


    }

    private void loadAd() {
        mAdView.loadAd(new AdRequest.Builder().build());
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
                mAdView.startAnimation(slide_up);
            }

            @Override
            public void onAdOpened() {
                mAdView.setVisibility(View.GONE);
                mAdView.startAnimation(slide_down);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdView.loadAd(new AdRequest.Builder().build());
                    }
                }, 1800000);
            }
        });
        mInterstitialAdClick.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                llFooter.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClosed() {
                llFooter.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mInterstitialAdClick.loadAd(new AdRequest.Builder().build());
                    }
                }, 1800000);
            }

        });
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Preference.save(MainActivity.this, "firstrun", false);
                        Preference.save(MainActivity.this, "Time", System.currentTimeMillis());
                        Preference.save(MainActivity.this, "LoadAds", true);
                        mInterstitialAd.show();
                    }
                }, 60000);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Preference.getBoolean(MainActivity.this, "LoadAds", false);
            }

        });
    }

    private void initFragment() {
        unitsFragment = UnitsFragment.newInstance();
        buiderFragment = ListBuilderFragment.newInstance();
        itemFragment = ItemFragment.newInstance();
        creepsFragment = CreepsFragment.newInstance();
        fragments = new Fragment[]{unitsFragment, buiderFragment, creepsFragment, itemFragment};
    }

    private void loadData() {
        int isSubmit = getIntent().getIntExtra("submit", 1);
        String versionName = getIntent().getStringExtra("version_name");
        if (isSubmit == 0 && versionName.equals(versionN)) {
            return;
        }
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("autochess").child("upload");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot ds : dataSnapshot.child("unitList").getChildren()) {
                        Units unit = ds.getValue(Units.class);
                        unitsList.add(unit);
                    }
                    for (DataSnapshot ds : dataSnapshot.child("classList").getChildren()) {
                        ClassList classL = ds.getValue(ClassList.class);
                        classList.add(classL);
                    }
                    for (DataSnapshot ds : dataSnapshot.child("raceList").getChildren()) {
                        RaceList raceL = ds.getValue(RaceList.class);
                        raceList.add(raceL);
                    }
                    for (DataSnapshot ds : dataSnapshot.child("itemList").getChildren()) {
                        Item item = ds.getValue(Item.class);
                        itemList.add(item);
                    }
                    for (DataSnapshot ds : dataSnapshot.child("creepList").getChildren()) {
                        Creep creep = ds.getValue(Creep.class);
                        creepList.add(creep);
                    }
                    for(Units units: unitsList){
                        for (ClassList class_ : classList) {
                            if (units.getType()!= null && class_.getName().equals(units.getType().get(0))) {
                                units.setClass_image(class_.getImgClass());
                            }
                        }
                        for (RaceList race : raceList) {
                            if (units.getOrigin()!= null &&  race.getName().equals(units.getOrigin().get(0))) {
                                units.setRace_image(race.getImgRace());
                            }
                            if ( units.getOrigin()!= null && units.getOrigin().size() > 1) {
                                if (units.getOrigin()!= null &&  race.getName().equals(units.getOrigin().get(1))) {
                                    units.setRace_image2(race.getImgRace());
                                }
                            }
                        }
                    }
                    initFragment();
                    unitsFragment.setData(unitsList, raceList, classList);
                    buiderFragment.setData(unitsList, raceList, classList);
                    creepsFragment.setData(creepList);
                    itemFragment.setData(itemList);

                    // Show main fragment in container
                    myRef.removeEventListener(this);
                    llLoading.setVisibility(View.GONE);
                    goToFragment(unitsFragment, UnitsFragment.TAG);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);

        duoDrawerToggle.syncState();

    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(mTitles);
        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
        llFooter = mViewHolder.mDuoMenuView.findViewById(R.id.footer);
        llFooter.setVisibility(View.GONE);
        YoYo.with(Techniques.Wobble)
                .duration(1500)
                .repeat(5000)
                .playOn(mViewHolder.mDuoMenuView.findViewById(R.id.iv_footer));
        llFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterstitialAdClick.show();
            }
        });
    }

    @Override
    public void onFooterClicked() {

    }

    @Override
    public void onHeaderClicked() {
    }

    private void goToFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            transaction.add(R.id.container, fragment, tag);
            transaction.addToBackStack(tag);
        }
        for (Fragment fragment1 : fragments) {
            if (fragment1.getTag() != null) {
                if (fragment1.getTag().equals(tag)) {
                    transaction.show(fragment1);
                } else {
                    if (getSupportFragmentManager().findFragmentByTag(fragment1.getTag()) != null) {
                        transaction.hide(fragment1);
                    }
                }
            }
        }
        transaction.commit();
    }

    @Override
    public void onOptionClicked(final int position, Object objectClicked) {
        // Set the toolbar title
        setTitle(mTitles.get(position));

        // Set the right options selected
        mMenuAdapter.setViewSelected(position, true);


        // Navigate to the right fragment
        switch (position) {
            case 0:
                goToFragment(unitsFragment, UnitsFragment.TAG);
                break;
            case 1:
                goToFragment(itemFragment, ItemFragment.TAG);
                break;
            case 2:
                goToFragment(creepsFragment, CreepsFragment.TAG);
                break;
            case 3:
                goToFragment(buiderFragment, BuiderFragment.TAG);
                break;
            case 4:
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Attention");
                alertDialog.setMessage("Are you want exit ?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                break;
            default:
                goToFragment(unitsFragment, UnitsFragment.TAG);
                break;
        }

        // Close the drawer
        if (position != 4) {
            mViewHolder.mDuoDrawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mViewHolder.mDuoDrawerLayout.closeDrawer();
                }
            }, 50);
        }
    }

    @Override
    public void setDrawerEnabled(boolean enabled) {
        if (enabled) {
            mViewHolder.mDuoDrawerLayout.setDrawerLockMode(mViewHolder.mDuoDrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            mViewHolder.mDuoDrawerLayout.setDrawerLockMode(mViewHolder.mDuoDrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = findViewById(R.id.toolbar);
            mToolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        }
    }

    @Override
    public void onBackPressed() {
        try {
            Fragment detailF = getSupportFragmentManager().findFragmentByTag(DetailFragment.TAG);
            Fragment builderF = getSupportFragmentManager().findFragmentByTag(BuiderFragment.TAG);

            if (detailF instanceof DetailFragment && detailF.isVisible()) {
                if (getSupportActionBar() != null) getSupportActionBar().show();
                super.onBackPressed();

            } else if (builderF instanceof BuiderFragment && builderF.isVisible()) {
                if (getSupportActionBar() != null) getSupportActionBar().show();
                buiderFragment.updateData();
                super.onBackPressed();
            } else {
                mViewHolder.mDuoDrawerLayout.openDrawer();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
