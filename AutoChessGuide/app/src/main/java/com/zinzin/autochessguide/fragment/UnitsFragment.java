package com.zinzin.autochessguide.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adroitandroid.chipcloud.ChipCloud;
import com.adroitandroid.chipcloud.ChipListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.zinzin.autochessguide.DetailActivity;
import com.zinzin.autochessguide.R;
import com.zinzin.autochessguide.adapter.UnitsFullAdapter;
import com.zinzin.autochessguide.model.Units;
import com.zinzin.autochessguide.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnitsFragment extends Fragment {
    private RecyclerView rvUnits;
    private EditText edtSearch;
    private ImageView ivFilter;
    private BottomSheetDialog mBottomSheetDialog;
    private UnitsFullAdapter unitsFullAdapter;

    private List<Units> unitsList = new ArrayList<>();
    private List<Units> unitsFilter = new ArrayList<>();

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

    public void setListUnits(List<Units> units) {
        this.unitsList.clear();
        this.unitsList = units;
    }

    private void initView(View view) {
        rvUnits = view.findViewById(R.id.rcv_units);
        edtSearch = view.findViewById(R.id.edt_search);
        ivFilter = view.findViewById(R.id.iv_filter);
        mBottomSheetDialog = new BottomSheetDialog(getActivity());
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
        rvUnits.setLayoutManager(adapterManager);
        unitsFullAdapter = new UnitsFullAdapter(getActivity(), unitsList);
        rvUnits.setAdapter(unitsFullAdapter);
        unitsFullAdapter.setListener(new UnitsFullAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Units item, int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("unit", Utils.convertObjToJson(item));
                startActivity(intent);
            }
        });
    }

    private void setUpBottomSheetDialog() {
        final View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_filter, null);
        final ChipCloud chipCloudCost = sheetView.findViewById(R.id.chip_cloud_cost);
        final ChipCloud chipCloudClass = sheetView.findViewById(R.id.chip_cloud_class);
        final ChipCloud chipCloudRace = sheetView.findViewById(R.id.chip_cloud_race);
        Button btnDone = sheetView.findViewById(R.id.btn_done);
        TextView tvReset = sheetView.findViewById(R.id.tv_reset);
        final String[] listCost = new String[]{"1", "2", "3", "4", "5"};
        final String[] listClass = new String[]{"Assassin", "Druid", "Hunter", "Knight", "Mage", "Mech", "Shaman", "Warlock", "Warrior", "Witcher"};
        final String[] listRace = new String[]{"Beast", "Cave Clan", "Demon", "Dragon", "Dwarf", "Egersis", "Feathered", "Glacier Clan", "Goblin", "Human", "Kira", "Marine", "Spirit"};

        chipCloudCost.addChips(listCost);
        chipCloudClass.addChips(listClass);
        chipCloudRace.addChips(listRace);
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
                chipCloudCost.setMode(ChipCloud.Mode.NONE);
                chipCloudClass.setMode(ChipCloud.Mode.NONE);
                chipCloudRace.setMode(ChipCloud.Mode.NONE);

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
        if (!costSelected.equals("") || !classSelected.equals("") || !racesSelected.equals("")) {
            isFilter = true;
            if (!costSelected.equals("")) {
                for (int i = 0; i < unitsList.size(); i++) {
                    if (unitsList.get(i).getCost().equals(costSelected))
                        unitsFilter.add(unitsList.get(i));
                }
            } else {
                unitsFilter.addAll(unitsList);
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
            unitsFilter = unitsList;

        }
        Collections.sort(unitsFilter, new Comparator<Units>() {
            @Override
            public int compare(Units lhs, Units rhs) {

                return lhs.getName().compareToIgnoreCase(rhs.getName());
            }
        });
        unitsFullAdapter.updateList(unitsFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        edtSearch.clearFocus();
    }

    private void filter(String text) {
        List<Units> unitsBase = new ArrayList<>();
        if (isFilter) {
            unitsBase = unitsFilter;
        } else {
            unitsBase = unitsList;
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
            unitsFullAdapter.updateList(unitsBase);
        } else {
            unitsFullAdapter.updateList(listUnitsFilter);
        }
    }
}
