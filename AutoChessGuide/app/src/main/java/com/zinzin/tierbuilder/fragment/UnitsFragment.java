package com.zinzin.tierbuilder.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.adroitandroid.chipcloud.ChipCloud;
import com.adroitandroid.chipcloud.ChipListener;
import com.zinzin.tierbuilder.R;
import com.zinzin.tierbuilder.adapter.HeaderRecyclerViewSection;
import com.zinzin.tierbuilder.model.ClassList;
import com.zinzin.tierbuilder.model.RaceList;
import com.zinzin.tierbuilder.model.Units;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnitsFragment extends Fragment {
    public static String TAG = UnitsFragment.class.getSimpleName();
    private RecyclerView rvUnits;
    private EditText edtSearch;
    private ImageView ivFilter;
    private SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
    private Dialog mBottomSheetDialog;

    private List<Units> unitsList = new ArrayList<>();
    private List<RaceList> raceList = new ArrayList<>();
    private List<ClassList> classList = new ArrayList<>();
    private List<Units> unitsFilter = new ArrayList<>();
    private String[] listRace;
    private String[] listClass;

    private List<Units> unitsListTier1 = new ArrayList<>();
    private List<Units> unitsListTier2 = new ArrayList<>();
    private List<Units> unitsListTier3 = new ArrayList<>();
    private List<Units> unitsListTier4 = new ArrayList<>();
    private List<Units> unitsListTier5 = new ArrayList<>();

    private String statusSelected = "";
    private String costSelected = "";
    private String classSelected = "";
    private String racesSelected = "";

    private boolean isFilter = false;

    public static UnitsFragment newInstance() {
        return new UnitsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_units, container, false);

        initView(view);
        return view;
    }

    public void setData(List<Units> unitsList, List<RaceList> raceList, List<ClassList> classList) {
        this.unitsList.addAll(unitsList);
        this.raceList.addAll(raceList);
        this.classList.addAll(classList);
    }

    private void initView(View view) {
        rvUnits = view.findViewById(R.id.rcv_units);
        edtSearch = view.findViewById(R.id.edt_search);
        ivFilter = view.findViewById(R.id.iv_filter);
        mBottomSheetDialog = new Dialog(getActivity(), R.style.AppTheme);
        setUpRecycleView();
        setUpEditText();
        setUpBottomSheetDialog();
        setUpFilter();
    }

    private void setUpFilter() {
        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setText("");
                mBottomSheetDialog.show();
            }
        });
    }

    private void setUpEditText() {
        edtSearch.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void setUpRecycleView() {
        GridLayoutManager adapterManager = new GridLayoutManager(getActivity(), 3);
        getListSection(unitsList);
        HeaderRecyclerViewSection viewSection_S = new HeaderRecyclerViewSection(getActivity(), "Tier S", unitsListTier1, R.color.color_tier_s);
        viewSection_S.setListener(new HeaderRecyclerViewSection.OnItemClickListener() {
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

        HeaderRecyclerViewSection viewSection_A = new HeaderRecyclerViewSection(getActivity(), "Tier A", unitsListTier2, R.color.color_tier_a);
        viewSection_A.setListener(new HeaderRecyclerViewSection.OnItemClickListener() {
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

        HeaderRecyclerViewSection viewSection_B = new HeaderRecyclerViewSection(getActivity(), "Tier B", unitsListTier3, R.color.color_tier_b);
        viewSection_B.setListener(new HeaderRecyclerViewSection.OnItemClickListener() {
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

        HeaderRecyclerViewSection viewSection_C = new HeaderRecyclerViewSection(getActivity(), "Tier C", unitsListTier4, R.color.color_tier_c);
        viewSection_C.setListener(new HeaderRecyclerViewSection.OnItemClickListener() {
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

        HeaderRecyclerViewSection viewSection_D = new HeaderRecyclerViewSection(getActivity(), "Tier D", unitsListTier5, R.color.color_tier_d);
        viewSection_D.setListener(new HeaderRecyclerViewSection.OnItemClickListener() {
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

        sectionAdapter.addSection(viewSection_S);
        sectionAdapter.addSection(viewSection_A);
        sectionAdapter.addSection(viewSection_B);
        sectionAdapter.addSection(viewSection_C);
        sectionAdapter.addSection(viewSection_D);
        adapterManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (sectionAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        int orientation = getResources().getConfiguration().orientation;
                        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            return 5;
                        } else {
                            return 3;
                        }
                    default:
                        return 1;
                }
            }
        });
        rvUnits.setLayoutManager(adapterManager);
        rvUnits.setAdapter(sectionAdapter);
    }

    private void setUpBottomSheetDialog() {
        final View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_filter, null);
        final ChipCloud chipCloudStatus = sheetView.findViewById(R.id.chip_cloud_status);
        final ChipCloud chipCloudCost = sheetView.findViewById(R.id.chip_cloud_cost);
        final ChipCloud chipCloudClass = sheetView.findViewById(R.id.chip_cloud_class);
        final ChipCloud chipCloudRace = sheetView.findViewById(R.id.chip_cloud_race);
        Button btnDone = sheetView.findViewById(R.id.btn_done);
        TextView tvReset = sheetView.findViewById(R.id.tv_reset);
        final String[] listCost = new String[]{"1", "2", "3", "4", "5"};
        final String[] listStatus = new String[]{"New", "Buff", "Nerf"};
        listRace = new String[raceList.size()];
        listClass = new String[classList.size()];
        for (int i = 0; i < raceList.size(); i++) {
            listRace[i] = raceList.get(i).getName();
        }
        for (int i = 0; i < classList.size(); i++) {
            listClass[i] = classList.get(i).getName();
        }

        chipCloudStatus.addChips(listStatus);
        chipCloudCost.addChips(listCost);
        chipCloudClass.addChips(listClass);
        chipCloudRace.addChips(listRace);
        chipCloudStatus.setChipListener(new ChipListener() {
            @Override
            public void chipSelected(int index) {
                statusSelected = listStatus[index];
            }

            @Override
            public void chipDeselected(int index) {
                statusSelected = "";
            }
        });
        chipCloudCost.setChipListener(new ChipListener() {
            @Override
            public void chipSelected(int index) {
                costSelected = listCost[index];
            }

            @Override
            public void chipDeselected(int index) {
                costSelected = "";
            }
        });
        chipCloudClass.setChipListener(new ChipListener() {
            @Override
            public void chipSelected(int index) {
                classSelected = (listClass[index]);
            }

            @Override
            public void chipDeselected(int index) {
                classSelected = "";
            }
        });
        chipCloudRace.setChipListener(new ChipListener() {
            @Override
            public void chipSelected(int index) {
                racesSelected = listRace[index];
            }

            @Override
            public void chipDeselected(int index) {
                racesSelected = "";
            }
        });
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipCloudStatus.setMode(ChipCloud.Mode.NONE);
                chipCloudCost.setMode(ChipCloud.Mode.NONE);
                chipCloudClass.setMode(ChipCloud.Mode.NONE);
                chipCloudRace.setMode(ChipCloud.Mode.NONE);
                chipCloudRace.setMode(ChipCloud.Mode.NONE);
                chipCloudStatus.setMode(ChipCloud.Mode.SINGLE);
                chipCloudCost.setMode(ChipCloud.Mode.SINGLE);
                chipCloudClass.setMode(ChipCloud.Mode.SINGLE);
                chipCloudRace.setMode(ChipCloud.Mode.SINGLE);
                chipCloudRace.setMode(ChipCloud.Mode.SINGLE);
                statusSelected = "";
                costSelected = "";
                classSelected = "";
                racesSelected = "";

            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                getListFilter();
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
    }

    private void getListFilter() {
        unitsFilter.clear();
        if (!statusSelected.equals("") || !costSelected.equals("") || !classSelected.equals("") || !racesSelected.equals("")) {
            isFilter = true;
            if (!statusSelected.equals("")) {
                for (int i = 0; i < unitsList.size(); i++) {
                    switch (statusSelected) {
                        case "New":
                            if (unitsList.get(i).getNew() == 0)
                                unitsFilter.add(unitsList.get(i));
                            break;
                        case "Buff":
                            if (unitsList.get(i).getBuff() == 0)
                                unitsFilter.add(unitsList.get(i));
                            break;
                        case "Nerf":
                            if (unitsList.get(i).getNerf() == 0)
                                unitsFilter.add(unitsList.get(i));
                            break;
                    }
                }
            } else {
                unitsFilter.addAll(unitsList);
            }
            if (!costSelected.equals("")) {
                for (int i = unitsFilter.size()-1; i >= 0; i--) {
                    if (!unitsFilter.get(i).getCost().equals(costSelected))
                        unitsFilter.remove(i);
                }
            }
            if (!classSelected.equals("")) {
                for (int i = unitsFilter.size() - 1; i >= 0; i--) {
                    if (!unitsFilter.get(i).getClass_().equals(classSelected)) {
                        unitsFilter.remove(i);
                    }
                }
            }
            if (!racesSelected.equals("")) {
                for (int i = unitsFilter.size() - 1; i >= 0; i--) {
                    if (unitsFilter.get(i).getRace().size() > 1) {
                        if (!unitsFilter.get(i).getRace().get(0).equals(racesSelected) && !unitsFilter.get(i).getRace().get(1).equals(racesSelected))
                            unitsFilter.remove(i);
                    } else {
                        if (!unitsFilter.get(i).getRace().get(0).equals(racesSelected))
                            unitsFilter.remove(i);
                    }

                }
            }
            Set<Units> set = new HashSet<>(unitsFilter);
            unitsFilter.clear();
            unitsFilter.addAll(set);
        } else {
            //reset All
            isFilter = false;
            unitsFilter.addAll(unitsList);

        }
        Collections.sort(unitsFilter, new Comparator<Units>() {
            @Override
            public int compare(Units lhs, Units rhs) {

                return lhs.getName().compareToIgnoreCase(rhs.getName());
            }
        });
        getListSection(unitsFilter);
        sectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        edtSearch.clearFocus();
    }

    private void filter(String text) {
        List<Units> unitsBase = new ArrayList<>();
        if (isFilter) {
            unitsBase.addAll(unitsFilter);
        } else {
            unitsBase.addAll(unitsList);
        }
        List<Units> listUnitsFilter = new ArrayList();
        for (Units units : unitsBase) {
            //filter theo tên
            if (units.getName().toLowerCase().contains(text.toLowerCase())) {
                listUnitsFilter.add(units);
            } else if (units.getDotaConvert().toLowerCase().contains(text.toLowerCase())) {
                listUnitsFilter.add(units);
            }
        }
        if (text.equals("")) {
            listUnitsFilter.clear();
            getListSection(unitsBase);
            sectionAdapter.notifyDataSetChanged();
        } else {
            getListSection(listUnitsFilter);
            sectionAdapter.notifyDataSetChanged();
        }
    }

    private void getListSection(List<Units> heroList) {
        unitsListTier1.clear();
        unitsListTier2.clear();
        unitsListTier3.clear();
        unitsListTier4.clear();
        unitsListTier5.clear();
        for (Units units : heroList) {
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
}
