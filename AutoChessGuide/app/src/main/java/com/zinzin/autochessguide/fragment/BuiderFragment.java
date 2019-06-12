package com.zinzin.autochessguide.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zinzin.autochessguide.R;
import com.zinzin.autochessguide.adapter.UnitBuilderSynergyAdapter;
import com.zinzin.autochessguide.adapter.UnitsBottomAdapter;
import com.zinzin.autochessguide.adapter.UnitsBuilderAdapter;
import com.zinzin.autochessguide.model.ClassList;
import com.zinzin.autochessguide.model.RaceList;
import com.zinzin.autochessguide.model.Units;
import com.zinzin.autochessguide.model.UnitsInfo;
import com.zinzin.autochessguide.utils.Contants;
import com.zinzin.autochessguide.utils.Preference;
import com.zinzin.autochessguide.utils.Utils;
import com.zinzin.autochessguide.view.CustomLayoutManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuiderFragment extends Fragment {
    public static String TAG = BuiderFragment.class.getSimpleName();
    private RecyclerView rcvChoose, rcvSynergy;
    private Button btnAdd;
    private Button btnReset;
    private BottomSheetDialog mBottomSheetDialog;
    private UnitsBuilderAdapter unitsBuilderAdapter;
    private UnitsBottomAdapter unitsBottomAdapter;
    private UnitBuilderSynergyAdapter unitBuilderSynergyAdapter;
    private List<Units> unitsList = new ArrayList<>();
    private List<Units> unitsChoose = new ArrayList<>();
    private List<RaceList> raceList = new ArrayList<>();
    private List<ClassList> classList = new ArrayList<>();
    List<UnitsInfo> unitsInfos = new ArrayList<>();

    public static BuiderFragment newInstance() {
        return new BuiderFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_builder, container, false);
        initView(view);
        return view;
    }

    public void setData(List<Units> unitsList, List<RaceList> raceList, List<ClassList> classList) {
        this.unitsList.addAll(unitsList);
        this.raceList.addAll(raceList);
        this.classList.addAll(classList);
    }

    private void initView(View view) {
        rcvChoose = view.findViewById(R.id.rcv_choose);
        rcvSynergy = view.findViewById(R.id.rcv_synergy);
        btnAdd = view.findViewById(R.id.btn_add);
        btnReset = view.findViewById(R.id.btn_reset);
        mBottomSheetDialog = new BottomSheetDialog(getActivity());
        setUpBottomSheet();
        setUpBtnAdd();
        setUpBtnReset();
        setUpRcvChoose();
        setUpRcvSynergy();
    }

    private void setUpRcvSynergy() {
        if(!Preference.getString(getActivity(),Contants.KEY_BUILDER_LIST_SYNERGY).equals("")){
            Gson gson = new Gson();
            Type type = new TypeToken<List<UnitsInfo>>() {}.getType();
            unitsInfos = gson.fromJson(Preference.getString(getActivity(),Contants.KEY_BUILDER_LIST_SYNERGY), type);
        }
        LinearLayoutManager layoutManager2
                = new CustomLayoutManager(getActivity());
        rcvSynergy.setLayoutManager(layoutManager2);
        unitBuilderSynergyAdapter = new UnitBuilderSynergyAdapter(getActivity(), unitsInfos);
        rcvSynergy.setAdapter(unitBuilderSynergyAdapter);
    }

    private void setDataSynergy() {
        unitsInfos.clear();
        Map<String, Integer> duplicatesClass = new HashMap<String, Integer>();
        Map<String, Integer> duplicatesRace = new HashMap<String, Integer>();

        for (Units units : unitsChoose) {
            if (duplicatesClass.containsKey(units.getClass_())) {
                duplicatesClass.put(units.getClass_(), duplicatesClass.get(units.getClass_()) + 1);
            } else {
                duplicatesClass.put(units.getClass_(), 1);
            }
            if (duplicatesRace.containsKey(units.getRace().get(0))) {
                duplicatesRace.put(units.getRace().get(0), duplicatesRace.get(units.getRace().get(0)) + 1);
            } else {
                duplicatesRace.put(units.getRace().get(0), 1);
            }
            if (units.getRace().size() == 2) {
                if (duplicatesRace.containsKey(units.getRace().get(1))) {
                    duplicatesRace.put(units.getRace().get(1), duplicatesRace.get(units.getRace().get(1)) + 1);
                } else {
                    duplicatesRace.put(units.getRace().get(1), 1);
                }
            }
        }
        for (Map.Entry<String, Integer> entry : duplicatesRace.entrySet()) {
            Log.e("duplicatesRace", entry.getKey() + " " + entry.getValue() + "");
            for (RaceList race : raceList) {
                if (race.getName().equals(entry.getKey())) {
                    UnitsInfo unitsInfo = null;
                    switch (race.getBonus().size()){
                        case 1:
                            int bonus1_1 = Character.getNumericValue(race.getBonus().get(0).charAt(1));
                            if(entry.getValue() >= bonus1_1){
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", race.getBonus().get(0), entry.getValue());
                            } else {
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", "", entry.getValue());
                            }
                            break;
                        case 2:
                            int bonus2_1 = Character.getNumericValue(race.getBonus().get(0).charAt(1));
                            int bonus2_2 = Character.getNumericValue(race.getBonus().get(1).charAt(1));
                            if(entry.getValue() < bonus2_1){
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", "", entry.getValue());
                            }
                            if(entry.getValue()>= bonus2_1 && entry.getValue() < bonus2_2){
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", race.getBonus().get(0), entry.getValue());
                            }
                            if(entry.getValue() == bonus2_2){
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", getBonus(race.getBonus(),2), entry.getValue());
                            }
                            break;
                        case 3:
                            int bonus3_1 = Character.getNumericValue(race.getBonus().get(0).charAt(1));
                            int bonus3_2 = Character.getNumericValue(race.getBonus().get(1).charAt(1));
                            int bonus3_3 = Character.getNumericValue(race.getBonus().get(2).charAt(1));
                            if(entry.getValue() < bonus3_1){
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", "", entry.getValue());
                            }
                            if(entry.getValue()>= bonus3_1 && entry.getValue() < bonus3_2){
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", race.getBonus().get(0), entry.getValue());
                            }
                            if(entry.getValue() >= bonus3_2 && entry.getValue() < bonus3_3){
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", getBonus(race.getBonus(),2), entry.getValue());
                            }
                            if(entry.getValue() == bonus3_3){
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", getBonus(race.getBonus(),3), entry.getValue());
                            }
                            break;
                    }
                    unitsInfos.add(unitsInfo);
                }
            }
        }
        for (Map.Entry<String, Integer> entry : duplicatesClass.entrySet()) {
            Log.e("duplicatesClass", entry.getKey() + " " + entry.getValue() + "");
            for (ClassList class_ : classList) {
                if (class_.getName().equals(entry.getKey())) {
                    UnitsInfo unitsInfo = null;
                    switch (class_.getBonus().size()){
                        case 1:
                            int bonus1_1 = Character.getNumericValue(class_.getBonus().get(0).charAt(1));
                            if(entry.getValue() >= bonus1_1){
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", class_.getBonus().get(0), entry.getValue());
                            } else {
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", "", entry.getValue());
                            }
                            break;
                        case 2:
                            int bonus2_1 = Character.getNumericValue(class_.getBonus().get(0).charAt(1));
                            int bonus2_2 = Character.getNumericValue(class_.getBonus().get(1).charAt(1));
                            if(entry.getValue() < bonus2_1){
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", "", entry.getValue());
                            }
                            if(entry.getValue()>= bonus2_1 && entry.getValue() < bonus2_2){
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", class_.getBonus().get(0), entry.getValue());
                            }
                            if(entry.getValue() == bonus2_2){
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", getBonus(class_.getBonus(),2), entry.getValue());
                            }
                            break;
                        case 3:
                            int bonus3_1 = Character.getNumericValue(class_.getBonus().get(0).charAt(1));
                            int bonus3_2 = Character.getNumericValue(class_.getBonus().get(1).charAt(1));
                            int bonus3_3 = Character.getNumericValue(class_.getBonus().get(2).charAt(1));
                            if(entry.getValue() < bonus3_1){
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", "", entry.getValue());
                            }
                            if(entry.getValue()>= bonus3_1 && entry.getValue() < bonus3_2){
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", class_.getBonus().get(0), entry.getValue());
                            }
                            if(entry.getValue() >= bonus3_2 && entry.getValue() < bonus3_3){
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", getBonus(class_.getBonus(),2), entry.getValue());
                            }
                            if(entry.getValue() == bonus3_3){
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", getBonus(class_.getBonus(),3), entry.getValue());
                            }
                            break;
                    }
                    unitsInfos.add(unitsInfo);
                }
            }
        }
        unitBuilderSynergyAdapter.notifyDataSetChanged();
    }

    private void setUpBtnReset() {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnReset.setVisibility(View.GONE);
                unitsChoose.clear();
                unitsBuilderAdapter.notifyDataSetChanged();
                for (Units units : unitsList) {
                    if (units.isClick()) units.setClick(false);
                }
                unitsBottomAdapter.notifyDataSetChanged();
                unitsInfos.clear();
                unitBuilderSynergyAdapter.notifyDataSetChanged();
                removePreference();
            }
        });
    }

    private void setUpRcvChoose() {
        if(!Preference.getString(getActivity(),Contants.KEY_BUILDER_LIST_CHOOSE).equals("")){
            Gson gson = new Gson();
            Type type = new TypeToken<List<Units>>() {}.getType();
            unitsChoose = gson.fromJson(Preference.getString(getActivity(),Contants.KEY_BUILDER_LIST_CHOOSE), type);
            btnReset.setVisibility(View.VISIBLE);
        }
        GridLayoutManager adapterManager = new GridLayoutManager(getActivity(), 5);
        rcvChoose.setLayoutManager(adapterManager);
        unitsBuilderAdapter = new UnitsBuilderAdapter(getActivity(), unitsChoose);
        rcvChoose.setAdapter(unitsBuilderAdapter);
        unitsBuilderAdapter.setListener(new UnitsBuilderAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Units item, int position) {
                DetailFragment detailFragment = DetailFragment.newInstance();
                detailFragment.setData(item,raceList,classList);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.container, detailFragment, DetailFragment.TAG);
                transaction.addToBackStack(DetailFragment.TAG);
                transaction.commit();
            }
        });
    }

    private void setUpBottomSheet() {
        final View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_units, null);
        Button btnDone = sheetView.findViewById(R.id.btn_done);
        RecyclerView rvUnits = sheetView.findViewById(R.id.rcv_units);
        EditText edtSearch = sheetView.findViewById(R.id.edt_search);
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
        GridLayoutManager adapterManager = new GridLayoutManager(getActivity(), 4);
        rvUnits.setLayoutManager(adapterManager);
        if(!Preference.getString(getActivity(),Contants.KEY_BUILDER_LIST_UNITS).equals("")){
            unitsList.clear();
            Gson gson = new Gson();
            Type type = new TypeToken<List<Units>>() {}.getType();
            unitsList = gson.fromJson(Preference.getString(getActivity(),Contants.KEY_BUILDER_LIST_UNITS), type);
        }
        unitsBottomAdapter = new UnitsBottomAdapter(getActivity(), unitsList);
        rvUnits.setAdapter(unitsBottomAdapter);
        unitsBottomAdapter.setListener(new UnitsBottomAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Units item, int position) {
                if (!item.isClick()) {
                    if (unitsChoose.size() < 10) {
                        item.setClick(true);
                        unitsChoose.add(item);
                    } else {
                        Toast.makeText(getActivity(), "Max units", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    item.setClick(false);
                    unitsChoose.remove(item);
                }
                unitsBottomAdapter.notifyItemChanged(position);
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
                unitsBuilderAdapter.notifyDataSetChanged();
                if (unitsChoose.size() > 0) {
                    btnReset.setVisibility(View.VISIBLE);
                    setDataSynergy();
                    savePreference();
                } else {
                    removePreference();
                    btnReset.setVisibility(View.GONE);
                }
            }
        });
        mBottomSheetDialog.setContentView(sheetView);

    }

    private void savePreference() {
        Preference.save(getActivity(), Contants.KEY_BUILDER_LIST_UNITS,Utils.convertObjToJson(unitsList));
        Preference.save(getActivity(), Contants.KEY_BUILDER_LIST_CHOOSE,Utils.convertObjToJson(unitsChoose));
        Preference.save(getActivity(), Contants.KEY_BUILDER_LIST_SYNERGY,Utils.convertObjToJson(unitsInfos));
    }
    private void removePreference() {
        Preference.remove(getActivity(), Contants.KEY_BUILDER_LIST_UNITS);
        Preference.remove(getActivity(), Contants.KEY_BUILDER_LIST_CHOOSE);
        Preference.remove(getActivity(), Contants.KEY_BUILDER_LIST_SYNERGY);
    }

    private void filter(String text) {
        List<Units> unitsBase = new ArrayList<>();
        unitsBase.addAll(unitsList);
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
            unitsBottomAdapter.updateList(unitsBase);
        } else {
            unitsBottomAdapter.updateList(listUnitsFilter);
        }
    }

    private void setUpBtnAdd() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.show();
            }
        });
    }

    private String getBonus(List<String> bonus, int size){
        StringBuilder stringBonus = new StringBuilder();
        for(int i = 0; i < size; i++){
            stringBonus.append(bonus.get(i)).append("\n");
        }
        return stringBonus.toString().substring(0,stringBonus.toString().length()-2);
    }
}
