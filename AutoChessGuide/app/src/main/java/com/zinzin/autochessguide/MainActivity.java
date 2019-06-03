package com.zinzin.autochessguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zinzin.autochessguide.fragment.BuiderFragment;
import com.zinzin.autochessguide.fragment.CreepsFragment;
import com.zinzin.autochessguide.fragment.ItemFragment;
import com.zinzin.autochessguide.fragment.UnitsFragment;
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
    private UnitsFragment unitsFragment;
    private List<Units> unitsListFull;

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
        String text = Utils.parseFile(this,"Units.txt");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Units>>() {}.getType();
        List<Units> unitsList = gson.fromJson(text, type);
        unitsListFull = SetImage.fullUnitsList(unitsList);
        Log.e("parseUnits: ",unitsListFull.size()+"");
        // Show main fragment in container
        unitsFragment = UnitsFragment.newInstance();
        unitsFragment.setListUnits(unitsListFull);
        goToFragment(unitsFragment, false);

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

    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.replace(R.id.container, fragment).commit();
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        // Set the toolbar title
        setTitle(mTitles.get(position));

        // Set the right options selected
        mMenuAdapter.setViewSelected(position, true);

        // Navigate to the right fragment
        switch (position) {
            case 0:
                unitsFragment = UnitsFragment.newInstance();
                unitsFragment.setListUnits(unitsListFull);
                goToFragment(unitsFragment, false);
                break;
            case 1:
                goToFragment(new ItemFragment(), false);
                break;
            case 2:
                goToFragment(new CreepsFragment(), false);
                break;
            case 3:
                goToFragment(new BuiderFragment(), false);
                break;
            default:
                goToFragment(new UnitsFragment(), false);
                break;
        }

        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
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
    }
}
