package com.zinzin.autochessguide.fragment;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zinzin.autochessguide.R;
import com.zinzin.autochessguide.adapter.UnitsFullAdapter;
import com.zinzin.autochessguide.model.Units;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnitsFragment extends Fragment {
    private RecyclerView rvUnits;
    private UnitsFullAdapter unitsFullAdapter;
    private List<Units> unitsList = new ArrayList<>();

    public static UnitsFragment newInstance() {
        return new UnitsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_units, container, false);
        initView(view);
        return view;
    }
    public void setListUnits(List<Units> units){
        this.unitsList.clear();
        this.unitsList = units;
    }
    private void initView(View view) {
        rvUnits = view.findViewById(R.id.rcv_units);
        setUpRecycleView();
    }

    private void setUpRecycleView() {
        GridLayoutManager adapterManager = new GridLayoutManager(getActivity(), 3);
        rvUnits.setLayoutManager(adapterManager);
        unitsFullAdapter = new UnitsFullAdapter(getActivity(), unitsList);
        rvUnits.setAdapter(unitsFullAdapter);
        unitsFullAdapter.setListener(new UnitsFullAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Units item, int position) {
                Toast.makeText(getActivity(), item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
