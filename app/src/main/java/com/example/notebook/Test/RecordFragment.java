package com.example.notebook.Test;


import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notebook.MyAdapter;
import com.example.notebook.R;
import com.example.notebook.mainViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends Fragment {


    private com.example.notebook.mainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private RecordAdapter myAdapter;
    private LiveData<List<TestItem>> AllTestRecord;
    private List<TestItem>AllTestRecordTemp;
    FloatingActionButton floatingActionButton,floatingActionButton2;
    private boolean undoAction;

    public RecordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        CollapsingToolbarLayout collapsingToolbarLayout = requireActivity().findViewById(R.id.CollapsingToolbarLayout);
        collapsingToolbarLayout.setTitle("测试记录");
        collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.titleboder));
        floatingActionButton = requireActivity().findViewById(R.id.floatingActionButton);
        floatingActionButton.setVisibility(View.GONE);
        floatingActionButton2 = requireActivity().findViewById(R.id.floatingActionButton2);
        floatingActionButton2.setVisibility(View.GONE);
        floatingActionButton2.setEnabled(false);

        return inflater.inflate(R.layout.fragment_record, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(requireActivity().getApplication(), this))
                .get(mainViewModel.class);
        recyclerView = requireActivity().findViewById(R.id.RecycleViewRecordBest);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        myAdapter = new RecordAdapter(mainViewModel);
        recyclerView.setAdapter(myAdapter);
        recyclerView.getItemAnimator().setChangeDuration(200);
        recyclerView.getItemAnimator().setMoveDuration(200);

        AllTestRecord = mainViewModel.getAllTestItems();
        AllTestRecord.observe(getViewLifecycleOwner(), new Observer<List<TestItem>>() {
            @Override
            public void onChanged(List<TestItem> testItems) {
                int temp = myAdapter.getItemCount();
                AllTestRecordTemp = testItems;
                if(temp!=testItems.size()){
                    myAdapter.submitList(testItems);
                }
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final TestItem itemDelete = AllTestRecordTemp.get(viewHolder.getBindingAdapterPosition());
                mainViewModel.DeleteTestItems(itemDelete);
                Snackbar.make(getView(),"删除一项历史测试记录",Snackbar.LENGTH_SHORT)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mainViewModel.InsertTestItems(itemDelete);
                            }
                        }).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}
