package com.example.notebook;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.notebook.Review.ReviewFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FloatingActionButton floatingActionButton,floatingActionButton2;
    NavController controller;
    mainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置工具栏
        Toolbar toolbar = findViewById(R.id.toolbar);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton2 = findViewById(R.id.floatingActionButton2);
        setSupportActionBar(toolbar);

        mainViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(this.getApplication(), this))
                .get(mainViewModel.class);

        //声明侧面抽屉导航栏并绑定
        DrawerLayout drawer = findViewById(R.id.drawerlayout);
        NavigationView navigationView = findViewById(R.id.navi_view);
        //导航栏绑定每个页面
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.mathFragment, R.id.englishFragment, R.id.politicsFragment,
                R.id.CSFragment, R.id.reviewFragment, R.id.manualFragment,R.id.testFragment)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.fragmentMain);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.addFragment);
            }
        });

        //设置复习通知

    }


    public boolean onSupportNavigateUp() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.fragmentMain).getWindowToken(),0);
        NavController navController = Navigation.findNavController(this, R.id.fragmentMain);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
