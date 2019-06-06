package com.zinzin.autochessguide.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.zinzin.autochessguide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {
    public static String TAG = ItemFragment.class.getSimpleName();

    public static ItemFragment newInstance() {
        return new ItemFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_units, container, false);
        return view;
    }
}
