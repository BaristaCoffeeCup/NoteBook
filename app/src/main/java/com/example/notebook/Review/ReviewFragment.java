package com.example.notebook.Review;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notebook.MyAdapter;
import com.example.notebook.R;
import com.example.notebook.item;
import com.example.notebook.mainViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {

    private mainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private LiveData<List<item>> AllTypeItems;
    FloatingActionButton floatingActionButton,floatingActionButton2;
    private WebView webView;

    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        CollapsingToolbarLayout collapsingToolbarLayout = requireActivity().findViewById(R.id.CollapsingToolbarLayout);
        collapsingToolbarLayout.setTitle(getString(R.string.Review));
        collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.biankuang7));
        floatingActionButton = requireActivity().findViewById(R.id.floatingActionButton);
        floatingActionButton.setVisibility(View.GONE);
        floatingActionButton2 = requireActivity().findViewById(R.id.floatingActionButton2);
        floatingActionButton2.setVisibility(View.GONE);
        floatingActionButton2.setEnabled(false);

        webView = requireActivity().findViewById(R.id.webView);

        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-1);
        int Month1 = calendar.get(Calendar.MONTH)+1;
        int Day1 = calendar.get(Calendar.DAY_OF_MONTH);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-2);
        int Month2 = calendar.get(Calendar.MONTH)+1;
        int Day2 = calendar.get(Calendar.DAY_OF_MONTH);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-4);
        int Month3 = calendar.get(Calendar.MONTH)+1;
        int Day3 = calendar.get(Calendar.DAY_OF_MONTH);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-7);
        int Month4 = calendar.get(Calendar.MONTH)+1;
        int Day4 = calendar.get(Calendar.DAY_OF_MONTH);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-15);
        int Month5 = calendar.get(Calendar.MONTH)+1;
        int Day5 = calendar.get(Calendar.DAY_OF_MONTH);

        mainViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(requireActivity().getApplication(), this))
                .get(mainViewModel.class);
        recyclerView = requireActivity().findViewById(R.id.RecycleViewReview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        myAdapter = new MyAdapter(mainViewModel,webView);
        recyclerView.setAdapter(myAdapter);
        recyclerView.getItemAnimator().setChangeDuration(200);
        recyclerView.getItemAnimator().setMoveDuration(200);

        //绑定动态数据
        AllTypeItems = mainViewModel.getReviewItem(Month1,Day1,Month2,Day2,Month3,Day3,Month4,Day4,Month5,Day5);
        AllTypeItems.observe(getViewLifecycleOwner(), new Observer<List<item>>() {
            @Override
            public void onChanged(List<item> items) {
                int temp = myAdapter.getItemCount();
                if(temp!=items.size()){
                    myAdapter.submitList(items);
                }
            }
        });

    }
}
