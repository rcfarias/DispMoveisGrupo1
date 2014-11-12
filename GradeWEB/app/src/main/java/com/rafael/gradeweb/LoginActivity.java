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

    public ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");

        setContentView(R.layout.activity_profile);
        titleTextView = (TextView) findViewById(R.id.profile_title);
        emailTextView = (TextView) findViewById(R.id.profile_email);
        nameTextView = (TextView) findViewById(R.id.profile_name);
        loginOrLogoutButton = (Button) findViewById(R.id.login_or_logout_button);
        titleTextView.setText(R.string.profile_title_logged_in);


        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));

        //ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));

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
                    //startActivityForResult(loginBuilder.build(), LOGIN_REQUEST);

                    // This example customizes ParseLoginActivity in code.
                    Intent parseLoginIntent = loginBuilder.setParseLoginEnabled(true)
                            .setParseLoginButtonText("Login")
                            .setParseSignupButtonText("Cadastrar")
                            .setParseLoginHelpText("Esqueceu a senha?")
                            .setParseLoginInvalidCredentialsToastText("Seu email e/ou senha não está correto")
                            .setParseLoginEmailAsUsername(true)
                            .setParseSignupSubmitButtonText("Registrar")
                            .setFacebookLoginEnabled(true)
                            .setFacebookLoginButtonText("Facebook")
                            .setFacebookLoginPermissions(Arrays.asList("public_profile", "user_friends", "user_about_me",
                                    "user_relationships", "user_birthday", "user_location", "user_status", "read_stream"))
                            .setTwitterLoginEnabled(false)
                            .setTwitterLoginButtontext("Twitter")
                            .build();
                    startActivityForResult(parseLoginIntent, LOGIN_REQUEST);
                }
            }
        });


        //ParseLoginBuilder builder = new ParseLoginBuilder(LoginActivity.this);
        //startActivityForResult(builder.build(), 0);


        //ParseUser user = new ParseUser();
        //user.setUsername("Mary Doe");
        //user.setPassword("testando");
        //user.setEmail("email3@example.com");

// other fields can be set just like with ParseObject
        //user.put("phone", "650-555-0000");

        /*user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });*/

    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart()");

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
        Intent intent = new Intent(this, MainActivity.class);
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
