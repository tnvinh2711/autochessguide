package com.zinzin.tierbuilder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zinzin.tierbuilder.R;
import com.zinzin.tierbuilder.adapter.CreepsAdapter;
import com.zinzin.tierbuilder.model.Creep;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreepsFragment extends Fragment {
    public static String TAG = CreepsFragment.class.getSimpleName();
    private List<Creep> creepList = new ArrayList<>();
    private RecyclerView rcvCreep;
    private CreepsAdapter creepsAdapter;

    public static CreepsFragment newInstance() {
        return new CreepsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creeps, container, false);
        initView(view);
        setUpRcvCreep();
        return view;
    }



    private void initView(View view) {
        rcvCreep = view.findViewById(R.id.rcv_creeps);
    }

    private void setUpRcvCreep() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvCreep.setLayoutManager(layoutManager);
        creepsAdapter = new CreepsAdapter(getActivity(), creepList);
        rcvCreep.setAdapter(creepsAdapter);
    }

    public void setData(List<Creep> creepList) {
        this.creepList.addAll(creepList);
    }
}
