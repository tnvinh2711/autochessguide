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
public class CreepsFragment extends Fragment {
    public static String TAG = CreepsFragment.class.getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creeps, container, false);
        return view;
    }
}
