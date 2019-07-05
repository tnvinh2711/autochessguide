package com.zinzin.tierbuilder.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zinzin.tierbuilder.DrawerLocker;
import com.zinzin.tierbuilder.R;
import com.zinzin.tierbuilder.adapter.BuilderAdapter;
import com.zinzin.tierbuilder.model.Builder;
import com.zinzin.tierbuilder.model.ClassList;
import com.zinzin.tierbuilder.model.RaceList;
import com.zinzin.tierbuilder.model.Units;
import com.zinzin.tierbuilder.utils.Contants;
import com.zinzin.tierbuilder.utils.Preference;
import com.zinzin.tierbuilder.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListBuilderFragment extends Fragment {
    public static String TAG = ListBuilderFragment.class.getSimpleName();
    private RecyclerView rcvChoose;
    private ImageView btnAdd;
    private BuilderAdapter builderAdapter;
    private List<Units> unitsList = new ArrayList<>();
    private List<RaceList> raceList = new ArrayList<>();
    private List<ClassList> classList = new ArrayList<>();
    private List<Builder> builders = new ArrayList<>();
    private List<String>  listNameChoose = new ArrayList<>();


    public static ListBuilderFragment newInstance() {
        return new ListBuilderFragment();
    }

    public void setData(List<Units> unitsList, List<RaceList> raceList, List<ClassList> classList) {
        this.unitsList.addAll(unitsList);
        this.raceList.addAll(raceList);
        this.classList.addAll(classList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_builder, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        initView(view);
        getDataPreferrence();
        return view;
    }

    private void getDataPreferrence() {
        builders.clear();
        if (!Preference.getString(getActivity(), Contants.KEY_BUILDER_LIST_CHOOSE_NAME).equals("")) {
            Gson gson = new Gson();
            builders = gson.fromJson(Preference.getString(getActivity(), Contants.KEY_BUILDER_LIST_CHOOSE_NAME), new TypeToken<List<Builder>>() {
            }.getType());
            listNameChoose.clear();
            for(Builder builder:builders){
                List<String> nameList = builder.getHero_name();
                List<Units> unitsChoose = new ArrayList<>();
                for (Units unit: unitsList){
                    for (String name: nameList){
                        if(name.equals(unit.getName())){
                            unitsChoose.add(unit);
                        }
                    }
                }
                builder.setUnitList(unitsChoose);
            }
            setUpRcvChoose();
            Log.e("doneRCVBUILDER","doneRCVBUILDER");
        }
    }

    private void initView(View view) {
        rcvChoose = view.findViewById(R.id.rcv_choose);
        rcvChoose.setVisibility(View.GONE);
        btnAdd = view.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuiderFragment buiderFragment = BuiderFragment.newInstance();
                buiderFragment.setData("",unitsList, raceList, classList);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.container, buiderFragment, BuiderFragment.TAG);
                transaction.addToBackStack(BuiderFragment.TAG);
                transaction.commit();
            }
        });
    }

    private void setUpRcvChoose() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvChoose.setLayoutManager(layoutManager);
        builderAdapter = new BuilderAdapter(getActivity(), builders);
        rcvChoose.setAdapter(builderAdapter);
        builderAdapter.setListener(new BuilderAdapter.OnItemClickListener() {
            @Override
            public void OnEditClick(String name, int position) {
                BuiderFragment buiderFragment = BuiderFragment.newInstance();
                buiderFragment.setData(name,unitsList, raceList, classList);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.container, buiderFragment, BuiderFragment.TAG);
                transaction.addToBackStack(BuiderFragment.TAG);
                transaction.commit();
            }

            @Override
            public void OnDeteleClick(int position) {
                builders.remove(position);
                builderAdapter.updateList(builders);
                Preference.save(getActivity(), Contants.KEY_BUILDER_LIST_CHOOSE_NAME, Utils.convertObjToJson(builders));
            }
        });
        rcvChoose.setVisibility(View.VISIBLE);
        Log.e("doneRCVBUILDER","doneRCVBUILDER");
    }
}
