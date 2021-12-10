package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private static String TEXT_VIEW_KEY = "STATE_LIFE_CYCLE";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting) {
            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("________On Create_________", "Create");

        if (savedInstanceState != null) {
            String state = savedInstanceState.getString(TEXT_VIEW_KEY);
            System.out.println(state);
        }

        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_main);

        NavController navController = navHostFragment.getNavController();

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(
                Navigation.findNavController(this, R.id.fragment_main)
                , appBarConfiguration
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Mainpage.setStateView("ON_START");
        Log.d("________On Start_________", "start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Mainpage.setStateView("ON_RESUME");
        Log.d("________On Resume_________", "resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Mainpage.setStateView("ON_PAUSE");
        Log.d("________On Pause_________", "Pause");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("________On Restore State_________", "Restore");
        Mainpage.setStateView(savedInstanceState.getString(TEXT_VIEW_KEY));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(TEXT_VIEW_KEY, Mainpage.getStateView());
        super.onSaveInstanceState(outState);
        Log.d("________On Save State_________", "Save");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Mainpage.setStateView("ON_STOP");
        Log.d("________On Stop_________", "Stop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Mainpage.setStateView("ON_RESTART");
        Log.d("________On Restart_________", "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Mainpage.setStateView("ON_DESTROY");
        Log.d("________On Destroy_________", "Destroy");
    }
}