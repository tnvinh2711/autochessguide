package com.zinzin.autochessguide.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zinzin.autochessguide.R;
import com.zinzin.autochessguide.adapter.ItemAdapter;
import com.zinzin.autochessguide.model.Item;
import com.zinzin.autochessguide.view.CustomLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {
    public static String TAG = ItemFragment.class.getSimpleName();
    private List<Item> itemList = new ArrayList<>();
    private RecyclerView rcvItem;
    private ItemAdapter itemAdapter;
    public static ItemFragment newInstance() {
        return new ItemFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        initView(view);
        setUpRcvItem();
        return view;
    }
    private void initView(View view) {
        rcvItem = view.findViewById(R.id.rcv_items);
    }

    private void setUpRcvItem() {
        LinearLayoutManager layoutManager
                = new CustomLayoutManager(getActivity());
        rcvItem.setLayoutManager(layoutManager);
        ViewCompat.setNestedScrollingEnabled(rcvItem, false);
        itemAdapter = new ItemAdapter(getActivity(), itemList);
        rcvItem.setAdapter(itemAdapter);
    }

    public void setData(List<Item> items) {
        this.itemList.addAll(items);
    }
}
