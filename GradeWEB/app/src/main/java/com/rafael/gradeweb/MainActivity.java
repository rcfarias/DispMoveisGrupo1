package com.rafael.gradeweb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

import java.util.Arrays;

/**
 * Created by Rafael Farias on 10/11/2014.
 */
public class MainActivity extends Activity {
    private static final int LOGIN_REQUEST = 0;
    private final String TAG = "TKT";

    private Button visualizarButton;
    private Button cursosButton;
    private Button disciplinasButton;
    private Button logoutButton;

    private ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()@MainActivity");

        setContentView(R.layout.activity_main);
        visualizarButton = (Button) findViewById(R.id.btn_con_grade);
        cursosButton = (Button) findViewById(R.id.btn_cons_cursos);
        disciplinasButton = (Button) findViewById(R.id.btn_con_disciplinas);
        logoutButton = (Button) findViewById(R.id.btn_logout);
        //Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));

        //ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    // User clicked to log out.
                    ParseUser.logOut();
                    currentUser = null;
                    showProfileLoggedOut();
                    finish();
                }
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart()@MainActivity");

        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            showProfileLoggedIn();
        } else {
            showProfileLoggedOut();
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart()@MainActivity");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume()@MainActivity");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause()@MainActivity");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop()@MainActivity");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy()@MainActivity");
    }

    /**
     * Shows the profile of the given user.
     */
    private void showProfileLoggedIn() {
        Log.d(TAG, "still have the user value @MainActivity");

    }

    /**
     * Show a message asking the user to log in, toggle login/logout button text.
     */
    private void showProfileLoggedOut() {
        Log.d(TAG, "don't have the user value :(  @MainActivity");
    }

}
