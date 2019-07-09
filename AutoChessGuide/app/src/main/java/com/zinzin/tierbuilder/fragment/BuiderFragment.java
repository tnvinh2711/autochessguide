package com.zinzin.tierbuilder.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zinzin.tierbuilder.DrawerLocker;
import com.zinzin.tierbuilder.R;
import com.zinzin.tierbuilder.adapter.UnitBuilderSynergyAdapter;
import com.zinzin.tierbuilder.adapter.UnitsBottomAdapter;
import com.zinzin.tierbuilder.adapter.UnitsBuilderAdapter;
import com.zinzin.tierbuilder.model.Bonus;
import com.zinzin.tierbuilder.model.Builder;
import com.zinzin.tierbuilder.model.ClassList;
import com.zinzin.tierbuilder.model.RaceList;
import com.zinzin.tierbuilder.model.Units;
import com.zinzin.tierbuilder.model.UnitsInfo;
import com.zinzin.tierbuilder.utils.Contants;
import com.zinzin.tierbuilder.utils.Preference;
import com.zinzin.tierbuilder.utils.Utils;
import com.zinzin.tierbuilder.view.CustomLayoutManager;

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
    private TextView tvSave;
    private EditText edtName;
    private String name;
    private int idEdit = -998;
    private Dialog mBottomSheetDialog;
    private UnitsBuilderAdapter unitsBuilderAdapter;
    private UnitsBottomAdapter unitsBottomAdapter;
    private UnitBuilderSynergyAdapter unitBuilderSynergyAdapter;
    private List<Units> unitsList = new ArrayList<>();
    private List<Units> unitsListNotClick = new ArrayList<>();
    private List<Units> unitsChoose = new ArrayList<>();
    private List<String> listNameChoose = new ArrayList<>();
    private List<RaceList> raceList = new ArrayList<>();
    private List<ClassList> classList = new ArrayList<>();
    private List<UnitsInfo> unitsInfos = new ArrayList<>();
    private List<Builder> builders = new ArrayList<>();

    public static BuiderFragment newInstance() {
        return new BuiderFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_builder, container, false);
        initView(view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);
        return view;
    }

    public void setData(String name, List<Units> unitsList, List<RaceList> raceList, List<ClassList> classList) {
        this.name = name;
        this.unitsList.addAll(unitsList);
        this.unitsListNotClick.addAll(unitsList);
        this.raceList.addAll(raceList);
        this.classList.addAll(classList);
        for (Units units : unitsListNotClick) {
            units.setClick(false);
        }
    }

    private void initView(View view) {
        rcvChoose = view.findViewById(R.id.rcv_choose);
        tvSave = view.findViewById(R.id.tv_save);
        rcvSynergy = view.findViewById(R.id.rcv_synergy);
        btnAdd = view.findViewById(R.id.btn_add);
        edtName = view.findViewById(R.id.edt_name);
        btnReset = view.findViewById(R.id.btn_reset);
        btnReset.setVisibility(View.VISIBLE);
        mBottomSheetDialog = new Dialog(getActivity(), R.style.AppTheme);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideSoftKeyboard(getActivity());
                edtName.clearFocus();
                saveTeam();
            }
        });
        getDataFromPreferrence();
        setUpBottomSheet();
        setUpBtnAdd();
        setUpBtnReset();
        setUpRcvChoose();
        setUpRcvSynergy();
        setUpEdtName();
    }

    private void setUpEdtName() {
        edtName.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtName.setText(name);
        edtName.setSelection(edtName.getText().length());
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = s.toString();
            }
        });
    }


    private void saveTeam() {
//        if(idEdit!= -998) builders.remove(idEdit);
        if (name.equals("")) {
            Toast.makeText(getActivity(), "Name must be filled", Toast.LENGTH_SHORT).show();
            return;
        }
        listNameChoose.clear();
        for (Units unit : unitsChoose) {
            listNameChoose.add(unit.getName());
        }
        if (listNameChoose.size() == 0) {
            Toast.makeText(getActivity(), "Please choose Hero", Toast.LENGTH_SHORT).show();
            return;
        }
        Builder teamName = new Builder();
        teamName.setHero_name(listNameChoose);
        teamName.setName_team(name);
        for (int i = 0; i < builders.size(); i++) {
            if (name.equals(builders.get(i).getName_team()) && idEdit == -998) {
                Toast.makeText(getActivity(), "Name is existed", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        List<String> typeList = new ArrayList<>();
        for (UnitsInfo unitsInfo : unitsInfos) {
            if (!unitsInfo.getDes().equals("")) {
                typeList.add(unitsInfo.getCount() + " " + unitsInfo.getName());
            }
        }
        if (typeList.size() > 0) teamName.setType(Utils.linkStringFromArray(typeList));
        if (idEdit == -998) {
            builders.add(teamName);
        } else {
            builders.set(idEdit, teamName);
        }
        Preference.save(getActivity(), Contants.KEY_BUILDER_LIST_CHOOSE_NAME, Utils.convertObjToJson(builders));
        getActivity().onBackPressed();
    }

    private void getDataFromPreferrence() {
        if (!Preference.getString(getActivity(), Contants.KEY_BUILDER_LIST_CHOOSE_NAME).equals("")) {
            Gson gson = new Gson();
            builders = gson.fromJson(Preference.getString(getActivity(), Contants.KEY_BUILDER_LIST_CHOOSE_NAME), new TypeToken<List<Builder>>() {
            }.getType());
            unitsChoose.clear();
            listNameChoose.clear();
            for (int i = 0; i < builders.size(); i++) {
                if (builders.get(i).getName_team().equals(name)) {
                    idEdit = i;
                    listNameChoose = builders.get(i).getHero_name();
                }
            }
            for (String name : listNameChoose) {
                for (Units unit : unitsList) {
                    if (unit.getName().equals(name)) {
                        unit.setClick(true);
                        unitsChoose.add(unit);
                    }
                }
            }
        }
    }

    private void setUpRcvSynergy() {
        LinearLayoutManager layoutManager2
                = new CustomLayoutManager(getActivity());
        rcvSynergy.setLayoutManager(layoutManager2);
        unitBuilderSynergyAdapter = new UnitBuilderSynergyAdapter(getActivity(), unitsInfos);
        rcvSynergy.setAdapter(unitBuilderSynergyAdapter);
        setDataSynergy();
    }

    private void setDataSynergy() {
        unitsInfos.clear();
        Map<String, Integer> duplicatesClass = new HashMap<String, Integer>();
        Map<String, Integer> duplicatesRace = new HashMap<String, Integer>();

        for (Units units : unitsChoose) {
            if (units.getType() != null && units.getOrigin() != null) {
                if (duplicatesClass.containsKey(units.getType().get(0))) {
                    duplicatesClass.put(units.getType().get(0), duplicatesClass.get(units.getType().get(0)) + 1);
                } else {
                    duplicatesClass.put(units.getType().get(0), 1);
                }
                if (duplicatesRace.containsKey(units.getOrigin().get(0))) {
                    duplicatesRace.put(units.getOrigin().get(0), duplicatesRace.get(units.getOrigin().get(0)) + 1);
                } else {
                    duplicatesRace.put(units.getOrigin().get(0), 1);
                }
                if (units.getOrigin().size() == 2) {
                    if (duplicatesRace.containsKey(units.getOrigin().get(1))) {
                        duplicatesRace.put(units.getOrigin().get(1), duplicatesRace.get(units.getOrigin().get(1)) + 1);
                    } else {
                        duplicatesRace.put(units.getOrigin().get(1), 1);
                    }
                }
            }
        }
        for (Map.Entry<String, Integer> entry : duplicatesRace.entrySet()) {
            Log.e("duplicatesRace", entry.getKey() + " " + entry.getValue() + "");
            for (RaceList race : raceList) {
                if (race.getName().equals(entry.getKey())) {
                    UnitsInfo unitsInfo = null;
                    switch (race.getBonus().size()) {
                        case 1:
                            int bonus1_1 = Character.getNumericValue(Integer.parseInt(race.getBonus().get(0).getCount()));
                            if (entry.getValue() >= bonus1_1) {
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", race.getBonus().get(0).getCount() + ". " + race.getBonus().get(0).getValue(), entry.getValue());
                            } else {
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", "", entry.getValue());
                            }
                            break;
                        case 2:
                            int bonus2_1 = Character.getNumericValue(Integer.valueOf(race.getBonus().get(0).getCount()));
                            int bonus2_2 = Character.getNumericValue(Integer.valueOf(race.getBonus().get(1).getCount()));
                            if (entry.getValue() < bonus2_1) {
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", "", entry.getValue());
                            }
                            if (entry.getValue() >= bonus2_1 && entry.getValue() < bonus2_2) {
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", race.getBonus().get(0).getCount() + ". " + race.getBonus().get(0).getValue(), entry.getValue());
                            }
                            if (entry.getValue() >= bonus2_2) {
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", getBonus(race.getBonus(), 2), entry.getValue());
                            }
                            break;
                        case 3:
                            int bonus3_1 = Character.getNumericValue(Integer.valueOf(race.getBonus().get(0).getCount()));
                            int bonus3_2 = Character.getNumericValue(Integer.valueOf(race.getBonus().get(1).getCount()));
                            int bonus3_3 = Character.getNumericValue(Integer.valueOf(race.getBonus().get(2).getCount()));
                            if (entry.getValue() < bonus3_1) {
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", "", entry.getValue());
                            }
                            if (entry.getValue() >= bonus3_1 && entry.getValue() < bonus3_2) {
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", race.getBonus().get(0).getCount() + ". " + race.getBonus().get(0), entry.getValue());
                            }
                            if (entry.getValue() >= bonus3_2 && entry.getValue() < bonus3_3) {
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", getBonus(race.getBonus(), 2), entry.getValue());
                            }
                            if (entry.getValue() >= bonus3_3) {
                                unitsInfo = new UnitsInfo(race.getImgRace(), entry.getKey(), "Race", getBonus(race.getBonus(), 3), entry.getValue());
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
                    switch (class_.getBonus().size()) {
                        case 1:
                            int bonus1_1 = Character.getNumericValue(Integer.valueOf(class_.getBonus().get(0).getCount()));
                            if (entry.getValue() >= bonus1_1) {
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", class_.getBonus().get(0).getCount() + ". " + class_.getBonus().get(0).getValue(), entry.getValue());
                            } else {
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", "", entry.getValue());
                            }
                            break;
                        case 2:
                            int bonus2_1 = Character.getNumericValue(Integer.valueOf(class_.getBonus().get(0).getCount()));
                            int bonus2_2 = Character.getNumericValue(Integer.valueOf(class_.getBonus().get(1).getCount()));
                            if (entry.getValue() < bonus2_1) {
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", "", entry.getValue());
                            }
                            if (entry.getValue() >= bonus2_1 && entry.getValue() < bonus2_2) {
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", class_.getBonus().get(0).getCount() + ". " + class_.getBonus().get(0).getValue(), entry.getValue());
                            }
                            if (entry.getValue() >= bonus2_2) {
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", getBonus(class_.getBonus(), 2), entry.getValue());
                            }
                            break;
                        case 3:
                            int bonus3_1 = Character.getNumericValue(Integer.valueOf(class_.getBonus().get(0).getCount()));
                            int bonus3_2 = Character.getNumericValue(Integer.valueOf(class_.getBonus().get(1).getCount()));
                            int bonus3_3 = Character.getNumericValue(Integer.valueOf(class_.getBonus().get(2).getCount()));
                            if (entry.getValue() < bonus3_1) {
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", "", entry.getValue());
                            }
                            if (entry.getValue() >= bonus3_1 && entry.getValue() < bonus3_2) {
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", class_.getBonus().get(0).getCount() + ". " + class_.getBonus().get(0).getValue(), entry.getValue());
                            }
                            if (entry.getValue() >= bonus3_2 && entry.getValue() < bonus3_3) {
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", getBonus(class_.getBonus(), 2), entry.getValue());
                            }
                            if (entry.getValue() >= bonus3_3) {
                                unitsInfo = new UnitsInfo(class_.getImgClass(), entry.getKey(), "Class", getBonus(class_.getBonus(), 3), entry.getValue());
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
                listNameChoose.clear();
                unitsBuilderAdapter.notifyDataSetChanged();
                for (Units units : unitsList) {
                    if (units.isClick()) units.setClick(false);
                }
                unitsBottomAdapter.notifyDataSetChanged();
                unitsInfos.clear();
                unitBuilderSynergyAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setUpRcvChoose() {
        GridLayoutManager adapterManager = new GridLayoutManager(getActivity(), 5);
        rcvChoose.setLayoutManager(adapterManager);
        unitsBuilderAdapter = new UnitsBuilderAdapter(getActivity(), unitsChoose);
        rcvChoose.setAdapter(unitsBuilderAdapter);
        unitsBuilderAdapter.setListener(new UnitsBuilderAdapter.OnItemClickListener() {
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
        if (idEdit != -998) {
            unitsBottomAdapter = new UnitsBottomAdapter(getActivity(), unitsList);
        } else {
            unitsBottomAdapter = new UnitsBottomAdapter(getActivity(), unitsListNotClick);
        }
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
                } else {
                    btnReset.setVisibility(View.GONE);
                }
            }
        });
        mBottomSheetDialog.setContentView(sheetView);

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

    private String getBonus(List<Bonus> bonus, int size) {
        StringBuilder stringBonus = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBonus.append(bonus.get(i).getCount() + ". " + bonus.get(i).getValue()).append("\n");
        }
        return stringBonus.toString().substring(0, stringBonus.toString().length() - 1);
    }

    @Override
    public void onDestroy() {
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        super.onDestroy();
    }
}
