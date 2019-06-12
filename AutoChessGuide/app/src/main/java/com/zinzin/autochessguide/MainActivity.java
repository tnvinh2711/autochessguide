package com.zinzin.autochessguide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zinzin.autochessguide.fragment.BuiderFragment;
import com.zinzin.autochessguide.fragment.CreepsFragment;
import com.zinzin.autochessguide.fragment.DetailFragment;
import com.zinzin.autochessguide.fragment.ItemFragment;
import com.zinzin.autochessguide.fragment.TierFragment;
import com.zinzin.autochessguide.fragment.UnitsFragment;
import com.zinzin.autochessguide.model.ClassList;
import com.zinzin.autochessguide.model.Creep;
import com.zinzin.autochessguide.model.Item;
import com.zinzin.autochessguide.model.RaceList;
import com.zinzin.autochessguide.model.Units;
import com.zinzin.autochessguide.utils.Preference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener {
    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;
    private AdView mAdView;
    private List<Units> unitsList = new ArrayList<>();
    private List<RaceList> raceList = new ArrayList<>();
    private List<ClassList> classList = new ArrayList<>();
    private List<Creep> creepList = new ArrayList<>();
    private List<Item> itemList = new ArrayList<>();
    private UnitsFragment unitsFragment;
    private BuiderFragment buiderFragment;
    private CreepsFragment creepsFragment;
    private ItemFragment itemFragment;
    private TierFragment tierFragment;
    private Fragment[] fragments;
    private ArrayList<String> mTitles = new ArrayList<>();
    private InterstitialAd mInterstitialAd,mInterstitialAdClick;
    private Animation slide_down;
    private Animation slide_up;
    private LinearLayout llFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slide_down = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        slide_up = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        MobileAds.initialize(this, "ca-app-pub-7188826417129130~8743853789");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAdClick = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7188826417129130/6429515781");
        mInterstitialAdClick.setAdUnitId("ca-app-pub-7188826417129130/8978166813");
        mInterstitialAdClick.loadAd(new AdRequest.Builder().build());

        if (Preference.getBoolean(this, "firstrun", true)) {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            Log.e("Timer", "first");
        } else {
            long timeOld = Preference.getLong(this, "Time", 0);
            long timeNew = System.currentTimeMillis();
            if (timeOld != 0 && timeNew - timeOld >= 86400000) {
                Log.e("Timer", "Round 2");
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
        Log.e("vào main","vào main");
        loadData();
        initFragment();

        unitsFragment.setData(unitsList, raceList, classList);
        buiderFragment.setData(unitsList, raceList, classList);
        tierFragment.setData(unitsList, raceList, classList);
        creepsFragment.setData(creepList);
        itemFragment.setData(itemList);
        // Show main fragment in container
        goToFragment(tierFragment, TierFragment.TAG);


    }

    private void loadAd() {
        final AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
                mAdView.startAnimation(slide_up);
            }

            @Override
            public void onAdClicked() {
                mAdView.setVisibility(View.GONE);
                mAdView.startAnimation(slide_down);
            }
        });
        mInterstitialAdClick.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                llFooter.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClosed() {
                llFooter.setVisibility(View.GONE);
                mInterstitialAdClick.loadAd(new AdRequest.Builder().build());
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
        buiderFragment = BuiderFragment.newInstance();
        itemFragment = ItemFragment.newInstance();
        creepsFragment = CreepsFragment.newInstance();
        tierFragment = TierFragment.newInstance();
        fragments = new Fragment[]{tierFragment,unitsFragment, buiderFragment, creepsFragment, itemFragment};
    }

    private void loadData() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Units>>() {}.getType();
        unitsList = gson.fromJson(getIntent().getStringExtra("units"), type);

        Gson gson2 = new Gson();
        Type type2 = new TypeToken<List<RaceList>>() {}.getType();
        raceList = gson2.fromJson(getIntent().getStringExtra("races"), type2);

        Gson gson3 = new Gson();
        Type type3 = new TypeToken<List<ClassList>>() {}.getType();
        classList = gson3.fromJson(getIntent().getStringExtra("classl"), type3);

        Gson gson4 = new Gson();
        Type type4 = new TypeToken<List<Creep>>() {}.getType();
        creepList = gson4.fromJson(getIntent().getStringExtra("creeps"), type4);

        Gson gson5 = new Gson();
        Type type5 = new TypeToken<List<Item>>() {}.getType();
        itemList = gson5.fromJson(getIntent().getStringExtra("items"), type5);
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
        ImageView ivFooter = mViewHolder.mDuoMenuView.findViewById(R.id.iv_footer);
        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
        ivFooter.startAnimation(animShake);
        llFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("onFooterClicked",""+mInterstitialAdClick.isLoaded());
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
                goToFragment(tierFragment, TierFragment.TAG);
                break;
            case 1:
                goToFragment(unitsFragment, UnitsFragment.TAG);
                break;
            case 2:
                goToFragment(itemFragment, ItemFragment.TAG);
                break;
            case 3:
                goToFragment(creepsFragment, CreepsFragment.TAG);
                break;
            case 4:
                goToFragment(buiderFragment, BuiderFragment.TAG);
                break;
            case 5:
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
        if (position != 5) {
            mViewHolder.mDuoDrawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mViewHolder.mDuoDrawerLayout.closeDrawer();
                }
            }, 50);
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

            if (detailF instanceof DetailFragment && detailF.isVisible()) {
                if(getSupportActionBar()!= null)getSupportActionBar().show();
                super.onBackPressed();

            } else {
                mViewHolder.mDuoDrawerLayout.openDrawer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
