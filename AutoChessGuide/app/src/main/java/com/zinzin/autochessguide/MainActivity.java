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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zinzin.autochessguide.fragment.BuiderFragment;
import com.zinzin.autochessguide.fragment.CreepsFragment;
import com.zinzin.autochessguide.fragment.ItemFragment;
import com.zinzin.autochessguide.fragment.UnitsFragment;
import com.zinzin.autochessguide.model.ClassList;
import com.zinzin.autochessguide.model.RaceList;
import com.zinzin.autochessguide.model.Units;
import com.zinzin.autochessguide.utils.SetImage;
import com.zinzin.autochessguide.utils.Utils;

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
    private List<Units> unitsList = new ArrayList<>();
    private List<RaceList> raceList = new ArrayList<>();
    private List<ClassList> classList = new ArrayList<>();
    private UnitsFragment unitsFragment;
    private BuiderFragment buiderFragment;
    private CreepsFragment creepsFragment;
    private ItemFragment itemFragment;
    Fragment[] fragments ;
    String[] fragmentTAGS = new String[]{UnitsFragment.TAG,BuiderFragment.TAG,CreepsFragment.TAG,ItemFragment.TAG};
    private ArrayList<String> mTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        // Show main fragment in container
        unitsList = SetImage.fullUnitsList(this);
        raceList = SetImage.fullRaceList(this);
        classList = SetImage.fullClassList(this);
        unitsFragment = UnitsFragment.newInstance();
        buiderFragment = BuiderFragment.newInstance();
        itemFragment = new ItemFragment();
        creepsFragment = new CreepsFragment();
        fragments = new Fragment[]{unitsFragment,buiderFragment,creepsFragment,itemFragment};
        unitsFragment.setData(unitsList,raceList,classList);
        buiderFragment.setData(unitsList,raceList,classList);
        goToFragment(unitsFragment, UnitsFragment.TAG);


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
    }

    @Override
    public void onFooterClicked() {
        Toast.makeText(this, "onFooterClicked", Toast.LENGTH_SHORT).show();
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
//                unitsFragment = UnitsFragment.newInstance();
//                unitsFragment.setData(unitsList,raceList,classList);
                goToFragment(unitsFragment ,UnitsFragment.TAG);
                break;
            case 1:
                goToFragment(itemFragment,ItemFragment.TAG);
                break;
            case 2:
                goToFragment(creepsFragment,CreepsFragment.TAG);
                break;
            case 3:
                goToFragment(buiderFragment,BuiderFragment.TAG);
                break;
            default:
                goToFragment(unitsFragment,UnitsFragment.TAG);
                break;
        }

        // Close the drawer
        mViewHolder.mDuoDrawerLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewHolder.mDuoDrawerLayout.closeDrawer();
            }
        }, 50);
    }

    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        }
    }

    @Override
    public void onBackPressed() {
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
    }
}
