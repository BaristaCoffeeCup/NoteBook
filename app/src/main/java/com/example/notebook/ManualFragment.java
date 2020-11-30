package com.example.notebook;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManualFragment extends Fragment {

    FloatingActionButton floatingActionButton,floatingActionButton2;

    public ManualFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        CollapsingToolbarLayout collapsingToolbarLayout = requireActivity().findViewById(R.id.CollapsingToolbarLayout);
        collapsingToolbarLayout.setTitle(getString(R.string.Manual));
        collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.biankuang8));

        floatingActionButton = requireActivity().findViewById(R.id.floatingActionButton);
        floatingActionButton.setVisibility(View.GONE);
        floatingActionButton2 = requireActivity().findViewById(R.id.floatingActionButton2);
        floatingActionButton2.setVisibility(View.GONE);
        floatingActionButton2.setEnabled(false);

        return inflater.inflate(R.layout.fragment_manual, container, false);
    }

}
