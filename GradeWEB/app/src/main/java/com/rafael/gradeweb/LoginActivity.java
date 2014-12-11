package com.rafael.gradeweb;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import com.parse.ParseAnalytics;
import com.parse.SignUpCallback;
import com.parse.ParseException;

import com.parse.ui.ParseLoginBuilder;

import com.parse.ParseFacebookUtils;

import java.util.Arrays;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends Activity {
    private static final int LOGIN_REQUEST = 0;
    private final String TAG = "TKT";

    private TextView titleTextView;
    private TextView emailTextView;
    private TextView nameTextView;
    private Button loginOrLogoutButton;

    private GradeWEBApplication myApplication;

    public ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");

        myApplication = GradeWEBApplication.getInstance();

        setContentView(R.layout.activity_login);
        titleTextView = (TextView) findViewById(R.id.profile_title);
        emailTextView = (TextView) findViewById(R.id.profile_email);
        nameTextView = (TextView) findViewById(R.id.profile_name);
        loginOrLogoutButton = (Button) findViewById(R.id.login_or_logout_button);
        titleTextView.setText(R.string.profile_title_logged_in);


        loginOrLogoutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    // User clicked to log out.
                    ParseUser.logOut();
                    currentUser = null;
                    showProfileLoggedOut();
                } else {

                    // User clicked to log in.
                    ParseLoginBuilder loginBuilder = new ParseLoginBuilder(
                            LoginActivity.this);

                    //customizes ParseLoginActivity in code.
                    Intent parseLoginIntent = loginBuilder.setParseLoginEnabled(true)
                            .setParseLoginButtonText("Login")
                            .setParseSignupButtonText("Cadastrar")
                            .setParseLoginHelpText("Esqueceu a senha?")
                            .setParseLoginInvalidCredentialsToastText("Seu email e/ou senha não está correto")
                            .setParseLoginEmailAsUsername(true)
                            .setParseSignupSubmitButtonText("Registrar")
                            .setFacebookLoginEnabled(true)
                            .setFacebookLoginButtonText("Facebook")
                            .setFacebookLoginPermissions(Arrays.asList("public_profile", "user_birthday","user_status", "read_stream"))
                            .setTwitterLoginEnabled(false)
                            .setTwitterLoginButtontext("Twitter")
                            .build();

                    startActivityForResult(parseLoginIntent, LOGIN_REQUEST);
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart()");

        myApplication.updateUsuario();
        currentUser = myApplication.getUsuario();

        if (currentUser != null) {
            showProfileLoggedIn();
        } else {
            showProfileLoggedOut();
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    /**
     * Shows the profile of the given user.
     */
    private void showProfileLoggedIn() {
        titleTextView.setText(R.string.profile_title_logged_in);
        emailTextView.setText(currentUser.getString("email"));
        String fullName = currentUser.getString("name");//currentUser.getString("name");
        if (fullName != null) {
            nameTextView.setText(fullName);
        }
        loginOrLogoutButton.setText(R.string.profile_logout_button_label);

        Intent intent = new Intent(this, MainActivityDrawer.class);
        startActivity(intent);

    }

    /**
     * Show a message asking the user to log in, toggle login/logout button text.
     */
    private void showProfileLoggedOut() {
        titleTextView.setText(R.string.profile_title_logged_out);
        emailTextView.setText("");
        nameTextView.setText("");
        loginOrLogoutButton.setText(R.string.profile_login_button_label);
    }

}
