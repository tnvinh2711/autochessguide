package com.zinzin.autochessguide.fragment;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adroitandroid.chipcloud.ChipCloud;
import com.adroitandroid.chipcloud.ChipListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.zinzin.autochessguide.R;
import com.zinzin.autochessguide.adapter.UnitsFullAdapter;
import com.zinzin.autochessguide.adapter.UnitsTierAdapter;
import com.zinzin.autochessguide.model.ClassList;
import com.zinzin.autochessguide.model.RaceList;
import com.zinzin.autochessguide.model.Units;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class TierFragment extends Fragment {
    public static String TAG = TierFragment.class.getSimpleName();
    private RecyclerView rvTier1, rvTier2, rvTier3, rvTier4, rvTier5;
    private UnitsTierAdapter unitsTierAdapter1;
    private UnitsTierAdapter unitsTierAdapter2;
    private UnitsTierAdapter unitsTierAdapter3;
    private UnitsTierAdapter unitsTierAdapter4;
    private UnitsTierAdapter unitsTierAdapter5;

    private List<Units> unitsList = new ArrayList<>();
    private List<Units> unitsListTier1 = new ArrayList<>();
    private List<Units> unitsListTier2 = new ArrayList<>();
    private List<Units> unitsListTier3 = new ArrayList<>();
    private List<Units> unitsListTier4 = new ArrayList<>();
    private List<Units> unitsListTier5 = new ArrayList<>();
    private List<RaceList> raceList = new ArrayList<>();
    private List<ClassList> classList = new ArrayList<>();


    public static TierFragment newInstance() {
        return new TierFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tier, container, false);
        loadData();
        initView(view);
        return view;
    }

    private void loadData() {
        for (Units units : unitsList) {
            switch (units.getTier()) {
                case "1":
                    unitsListTier1.add(units);
                    break;
                case "2":
                    unitsListTier2.add(units);
                    break;
                case "3":
                    unitsListTier3.add(units);
                    break;
                case "4":
                    unitsListTier4.add(units);
                    break;
                case "5":
                    unitsListTier5.add(units);
                    break;
            }
        }
    }

    public void setData(List<Units> unitsList, List<RaceList> raceList, List<ClassList> classList) {
        this.unitsList.addAll(unitsList);
        this.raceList.addAll(raceList);
        this.classList.addAll(classList);
    }

    private void initView(View view) {
        rvTier1 = view.findViewById(R.id.rcv_tier_1);
        unitsTierAdapter1 =  new UnitsTierAdapter(getActivity(), unitsListTier1);
        setUpRecycleView(rvTier1,unitsTierAdapter1);

        rvTier2 = view.findViewById(R.id.rcv_tier_2);
        unitsTierAdapter2 =  new UnitsTierAdapter(getActivity(), unitsListTier2);
        setUpRecycleView(rvTier2,unitsTierAdapter2);

        rvTier3 = view.findViewById(R.id.rcv_tier_3);
        unitsTierAdapter3 =  new UnitsTierAdapter(getActivity(), unitsListTier3);
        setUpRecycleView(rvTier3,unitsTierAdapter3);

        rvTier4 = view.findViewById(R.id.rcv_tier_4);
        unitsTierAdapter4 =  new UnitsTierAdapter(getActivity(), unitsListTier4);
        setUpRecycleView(rvTier4,unitsTierAdapter4);

        rvTier5 = view.findViewById(R.id.rcv_tier_5);
        unitsTierAdapter5 =  new UnitsTierAdapter(getActivity(), unitsListTier5);
        setUpRecycleView(rvTier5,unitsTierAdapter5);

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            updateRcv(rvTier1,unitsTierAdapter1,true);
            updateRcv(rvTier2,unitsTierAdapter2,true);
            updateRcv(rvTier3,unitsTierAdapter3,true);
            updateRcv(rvTier4,unitsTierAdapter4,true);
            updateRcv(rvTier5,unitsTierAdapter5,true);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            updateRcv(rvTier1,unitsTierAdapter1,false);
            updateRcv(rvTier2,unitsTierAdapter2,false);
            updateRcv(rvTier3,unitsTierAdapter3,false);
            updateRcv(rvTier4,unitsTierAdapter4,false);
            updateRcv(rvTier5,unitsTierAdapter5,false);
        }
    }

    private void updateRcv(RecyclerView rcv,UnitsTierAdapter tierAdapter, boolean isLandscape) {
        GridLayoutManager adapterManager;
        if(isLandscape){
            adapterManager = new GridLayoutManager(getActivity(), 5);
        } else {
            adapterManager = new GridLayoutManager(getActivity(), 3);
        }
        rcv.setLayoutManager(adapterManager);
        tierAdapter.notifyDataSetChanged();
    }

    private void setUpRecycleView(RecyclerView rcv, UnitsTierAdapter tierAdapter) {
        GridLayoutManager adapterManager;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            adapterManager = new GridLayoutManager(getActivity(), 5);
        } else {
            adapterManager = new GridLayoutManager(getActivity(), 3);
        }
        rcv.setLayoutManager(adapterManager);
        rcv.setAdapter(tierAdapter);
        ViewCompat.setNestedScrollingEnabled(rcv, false);
        tierAdapter.setListener(new UnitsTierAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Units item, int position) {
                DetailFragment detailFragment = DetailFragment.newInstance();
                detailFragment.setData(item, raceList, classList);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.container, detailFragment, DetailFragment.TAG);
                transaction.addToBackStack(DetailFragment.TAG);
                transaction.commit();
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();

    }
}
