package com.example.notebook.Test;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notebook.R;
import com.example.notebook.mainViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.notebook.R.drawable.biankaung1;

/**
 * A simple {@link Fragment} subclass.
 */
public class testFragment extends Fragment {

    private FloatingActionButton floatingActionButton,floatingActionButton2;
    private TestAdapter testAdapter;
    private LiveData<List<Record>>AllRecord;
    private List<Record>AllRecordTemp;
    private ConstraintLayout constraintLayoutAdd;
    private TimePicker timePicker1;
    private Button buttonTestSubmit,buttonTestCancel;
    private EditText editTextTestTitle;
    private mainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private boolean undoAction;

    public testFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        CollapsingToolbarLayout collapsingToolbarLayout = requireActivity().findViewById(R.id.CollapsingToolbarLayout);
        collapsingToolbarLayout.setTitle(getString(R.string.selfTest));
        collapsingToolbarLayout.setBackground(getResources().getDrawable(biankaung1));
        floatingActionButton = requireActivity().findViewById(R.id.floatingActionButton);
        floatingActionButton.setVisibility(View.GONE);
        floatingActionButton2 = requireActivity().findViewById(R.id.floatingActionButton2);
        floatingActionButton2.setVisibility(View.VISIBLE);
        floatingActionButton2.setEnabled(true);

        View view = inflater.inflate(R.layout.fragment_test, container, false);
        constraintLayoutAdd = view.findViewById(R.id.RecordAddLayout);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayoutAdd.setVisibility(View.VISIBLE);
            }
        });

        timePicker1 = view.findViewById(R.id.TimePicker1);
        timePicker1.setIs24HourView(true);
        timePicker1.setHour(0);
        timePicker1.setMinute(0);

        buttonTestSubmit = view.findViewById(R.id.buttonTestSubmit);
        buttonTestCancel = view.findViewById(R.id.buttonTestCancel);
        editTextTestTitle = view.findViewById(R.id.editTextTestTitle);

        return view;
    }

    @Override//创建菜单项
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.test_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.clearRecord:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("清空现有项目");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mainViewModel.ClearRecord();

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create();
                builder.show();
                break;

            case R.id.saveRecord:

                final EditText editTextInput = new EditText(requireContext());
                editTextInput.setBackground(getResources().getDrawable(R.drawable.background1));
                new AlertDialog.Builder(requireContext()).setTitle("请输入测试标题")
                        .setView(editTextInput)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                //将测试记录的标题和使用时间保存下来
                                StringBuilder titleRecord = new StringBuilder();
                                StringBuilder timeFinish = new StringBuilder();
                                StringBuilder timeMax = new StringBuilder();
                                for(int i=0;i<AllRecordTemp.size();i++){
                                    if(AllRecordTemp.get(i).isFinished()){
                                        titleRecord.append(AllRecordTemp.get(i).getTitle()).append("&");
                                        timeFinish.append(AllRecordTemp.get(i).getTimeUse1()).append("&");
                                        timeMax.append(AllRecordTemp.get(i).getTimeMax()).append("&");
                                    }
                                }
                                if(titleRecord.length() == 0){
                                    Toast toast = Toast.makeText(requireActivity(),null,Toast.LENGTH_LONG);
                                    toast.setText("当前没有完成的测试项目");
                                    toast.show();
                                }else{
                                    String allTitle = titleRecord.substring(0,titleRecord.length()-1);
                                    String allFinish = timeFinish.substring(0,timeFinish.length()-1);
                                    String allMax = timeMax.substring(0,timeMax.length()-1);
                                    //获取当前日期
                                    Calendar calendar = Calendar.getInstance();
                                    int month = calendar.get(Calendar.MONTH);
                                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                                    String dateTest = month + "月" + day + "日  " + AllRecordTemp.get(0).getTimeStart();
                                    String testTitle = editTextInput.getText().toString().trim();

                                    //判断当前的记录是否存在 从数据库中根据用户输入的标题进行查询
                                    TestItem testItem = null;
                                    try {
                                        testItem = mainViewModel.getTestItemWithTitle(testTitle);
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    if(testItem != null){
                                        testItem.setTitle(allTitle);
                                        testItem.setFinishtime(allFinish);
                                        mainViewModel.UpdateTestItems(testItem);
                                    }else{
                                        testItem = new TestItem(dateTest,testTitle,allTitle,allFinish,allMax);
                                        mainViewModel.InsertTestItems(testItem);
                                    }

                                    //按下确定键后的事件
                                    Toast.makeText(getContext(), "保存成功",Toast.LENGTH_LONG).show();
                                }

                            }
                        }).setNegativeButton("取消",null).show();


                break;

            case R.id.checkTestRecord:

                NavController navController = Navigation.findNavController(getActivity(),R.id.fragmentMain);
                navController.navigate(R.id.action_testFragment_to_recordFragment);

                break;

            case R.id.clearTestRecord:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(requireActivity());
                builder2.setTitle("清空历史测试记录？");
                builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mainViewModel.ClearTestItems();
                        testAdapter.positionNow = 0;
                    }
                });
                builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder2.create();
                builder2.show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final FragmentActivity activity = requireActivity();
        constraintLayoutAdd.setVisibility(View.GONE);

        mainViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(requireActivity().getApplication(), this))
                .get(mainViewModel.class);
        recyclerView = activity.findViewById(R.id.RecycleViewRecord);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        testAdapter = new TestAdapter(mainViewModel);
        recyclerView.setAdapter(testAdapter);
        recyclerView.getItemAnimator().setChangeDuration(200);
        recyclerView.getItemAnimator().setMoveDuration(200);

        //绑定动态数据
        AllRecord = mainViewModel.getAllRecord();
        AllRecord.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
            @Override
            public void onChanged(List<Record> records) {
                int temp = testAdapter.getItemCount();
                AllRecordTemp = records;
                if(temp!=records.size()){
                    if(temp<records.size() && !undoAction){
                        recyclerView.smoothScrollBy(0,-200);
                    }
                    undoAction = false;
                    testAdapter.submitList(records);
                }
            }
        });


        final TranslateAnimation showAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        showAnim.setDuration(300);
        final TranslateAnimation hideAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        hideAnim.setDuration(300);

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {

                if(testAdapter.timeIng){
                    Toast.makeText(activity,"正在进行一项测试，请完成后添加新项目",Toast.LENGTH_LONG);
                }else{
                    constraintLayoutAdd.startAnimation(showAnim);
                    constraintLayoutAdd.setVisibility(View.VISIBLE);
                    floatingActionButton2.startAnimation(hideAnim);
                    floatingActionButton2.setVisibility(View.GONE);
                    buttonTestSubmit.setEnabled(false);
                    final TextWatcher textWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String title = editTextTestTitle.getText().toString().trim();
                            buttonTestSubmit.setEnabled(!title.isEmpty());
                        }
                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    };
                    editTextTestTitle.addTextChangedListener(textWatcher);
                }

            }
        });

        //取消添加测试记录
        buttonTestCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                constraintLayoutAdd.startAnimation(hideAnim);
                constraintLayoutAdd.setVisibility(View.GONE);
                floatingActionButton2.startAnimation(showAnim);
                floatingActionButton2.setVisibility(View.VISIBLE);
            }
        });

        //确认添加测试记录
        buttonTestSubmit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String title = editTextTestTitle.getText().toString().trim();
                int hour = timePicker1.getHour();
                int minute = timePicker1.getMinute();
                String timeMax;
                if(hour == 0){
                    timeMax = "限时:" + minute + "分";
                }else{
                    timeMax = "限时:" + hour + "时" + minute + "分";
                }
                Record record = new Record(title,timeMax);
                record.setTimeMax1(60*hour+minute);

                mainViewModel.InsertRecord(record);
                editTextTestTitle.setText("");
                timePicker1.setHour(0);
                timePicker1.setMinute(0);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final Record itemDelete = AllRecordTemp.get(viewHolder.getBindingAdapterPosition());
                mainViewModel.DeleteRecord(itemDelete);
                testAdapter.positionNow -= 1;
                Snackbar.make(getView(),"删除一项学习项目",Snackbar.LENGTH_SHORT)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                undoAction = true;
                                mainViewModel.InsertRecord(itemDelete);
                                testAdapter.positionNow += 1;
                            }
                        }).show();
            }
        }).attachToRecyclerView(recyclerView);

    }


}
