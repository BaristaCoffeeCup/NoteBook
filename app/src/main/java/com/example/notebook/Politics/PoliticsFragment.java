package com.example.notebook.Politics;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.SearchView;

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
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.notebook.MyAdapter;
import com.example.notebook.R;
import com.example.notebook.item;
import com.example.notebook.mainViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PoliticsFragment extends Fragment {

    private mainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private LiveData<List<item>> AllTypeItems;
    private FloatingActionButton floatingActionButton,floatingActionButton2;
    private List<item>allItems;
    private boolean undoAction;
    private WebView webView;

    public PoliticsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.ClearDataMenuItem) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setTitle("清空全部记录");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mainViewModel.clearItems();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create();
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setMaxWidth(700);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String Pattern = newText.trim();
                AllTypeItems.removeObservers(getViewLifecycleOwner());
                AllTypeItems = mainViewModel.getItemWithPattern(Pattern,102);
                AllTypeItems.observe(getViewLifecycleOwner(), new Observer<List<item>>() {
                    @Override
                    public void onChanged(List<item> items) {
                        int temp = myAdapter.getItemCount();
                        allItems = items;
                        if(temp!=items.size()){
                            myAdapter.submitList(items);
                        }
                    }
                });
                return true;
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        CollapsingToolbarLayout collapsingToolbarLayout = requireActivity().findViewById(R.id.CollapsingToolbarLayout);
        collapsingToolbarLayout.setTitle(getString(R.string.Politics));
        collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.biankuang3));
        floatingActionButton = requireActivity().findViewById(R.id.floatingActionButton);
        floatingActionButton.setVisibility(View.VISIBLE);
        floatingActionButton2 = requireActivity().findViewById(R.id.floatingActionButton2);
        floatingActionButton2.setVisibility(View.GONE);
        floatingActionButton2.setEnabled(false);

        webView = requireActivity().findViewById(R.id.webView);

        return inflater.inflate(R.layout.fragment_politics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(requireActivity().getApplication(), this))
                .get(mainViewModel.class);
        recyclerView = requireActivity().findViewById(R.id.RecycleViewPolitics);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        myAdapter = new MyAdapter(mainViewModel,webView);
        recyclerView.setAdapter(myAdapter);
        recyclerView.getItemAnimator().setChangeDuration(200);
        recyclerView.getItemAnimator().setMoveDuration(200);

        //绑定动态数据
        AllTypeItems = mainViewModel.getAllItemTypeLive(102);
        AllTypeItems.observe(getViewLifecycleOwner(), new Observer<List<item>>() {
            @Override
            public void onChanged(List<item> items) {
                int temp = myAdapter.getItemCount();
                allItems = items;
                if(temp!=items.size()){
                    if(temp<items.size() && !undoAction){
                        recyclerView.smoothScrollBy(0,-200);
                    }
                    undoAction = false;
                    myAdapter.submitList(items);
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
                final item itemDelete = allItems.get(viewHolder.getBindingAdapterPosition());
                mainViewModel.deleteItems(itemDelete);
                Snackbar.make(getView(),"删除一条记录",Snackbar.LENGTH_SHORT)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                undoAction = true;
                                mainViewModel.inserItems(itemDelete);
                            }
                        }).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}
