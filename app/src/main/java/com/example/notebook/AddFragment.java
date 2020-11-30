package com.example.notebook;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {

    private Button buttonSubmit,buttonCancel;
    private EditText editTextTitle,editTextContent;
    private Spinner spinner;
    private FloatingActionButton floatingActionButton;
    private mainViewModel mainViewModel;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        CollapsingToolbarLayout collapsingToolbarLayout = requireActivity().findViewById(R.id.CollapsingToolbarLayout);
        collapsingToolbarLayout.setTitle(getString(R.string.addNew));
        collapsingToolbarLayout.setBackground(getResources().getDrawable(R.drawable.titleboder));

        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = requireActivity();
        buttonSubmit = activity.findViewById(R.id.buttonSubmit);
        buttonCancel = activity.findViewById(R.id.buttonCancel);
        editTextTitle = activity.findViewById(R.id.editTextTitle);
        editTextContent = activity.findViewById(R.id.editTextContent);
        spinner = activity.findViewById(R.id.spinner);
        floatingActionButton = activity.findViewById(R.id.floatingActionButton);
        mainViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(requireActivity().getApplication(), this))
                .get(mainViewModel.class);

        floatingActionButton.setVisibility(View.GONE);

        buttonSubmit.setEnabled(false);
        editTextTitle.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editTextTitle,0);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String title = editTextTitle.getText().toString().trim();
                String content = editTextContent.getText().toString().trim();
                buttonSubmit.setEnabled(!title.isEmpty() && !content.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editTextTitle.addTextChangedListener(textWatcher);
        editTextContent.addTextChangedListener(textWatcher);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString().trim();
                String content = editTextContent.getText().toString().trim();
                String itemName = spinner.getSelectedItem().toString().trim();
                int Type;
                switch (itemName){
                    case "数学":
                        Type = 100;
                        break;
                    case "英语":
                        Type = 101;
                        break;
                    case "政治":
                        Type = 102;
                        break;
                    default:
                        Type = 103;
                        break;
                }

                Calendar calendar = Calendar.getInstance();
                int Month = calendar.get(Calendar.MONTH)+1;
                int Day = calendar.get(Calendar.DAY_OF_MONTH);
                item item = new item(title,content,Month,Day,Type);
                mainViewModel.inserItems(item);
                buttonCancel.setText("返回");
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                Toast.makeText(requireActivity().getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
                editTextTitle.setText("");
                editTextContent.setText("");
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigateUp();
            }
        });
    }
}
