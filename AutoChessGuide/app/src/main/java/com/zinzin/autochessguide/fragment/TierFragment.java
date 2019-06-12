package com.zinzin.autochessguide.fragment;

import android.content.DialogInterface;
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
    private UnitsTierAdapter unitsTierAdapter;

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
        setUpRecycleView(rvTier1,unitsListTier1);

        rvTier2 = view.findViewById(R.id.rcv_tier_2);
        setUpRecycleView(rvTier2,unitsListTier2);

        rvTier3 = view.findViewById(R.id.rcv_tier_3);
        setUpRecycleView(rvTier3,unitsListTier3);

        rvTier4 = view.findViewById(R.id.rcv_tier_4);
        setUpRecycleView(rvTier4,unitsListTier4);

        rvTier5 = view.findViewById(R.id.rcv_tier_5);
        setUpRecycleView(rvTier5,unitsListTier5);

    }


    private void setUpRecycleView(RecyclerView rcv, List<Units> units) {
        GridLayoutManager adapterManager = new GridLayoutManager(getActivity(), 3);
        rcv.setLayoutManager(adapterManager);
        unitsTierAdapter = new UnitsTierAdapter(getActivity(), units);
        rcv.setAdapter(unitsTierAdapter);
        ViewCompat.setNestedScrollingEnabled(rcv, false);
        unitsTierAdapter.setListener(new UnitsTierAdapter.OnItemClickListener() {
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
