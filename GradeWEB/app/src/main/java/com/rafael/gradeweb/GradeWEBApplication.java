package com.rafael.gradeweb;

/**
 * Created by Rafael on 11/23/2014.
 */

import android.app.Application;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.rafael.gradeweb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 11/20/2014.
 */
public class GradeWEBApplication extends Application {

    private static GradeWEBApplication singleton;

    private ParseUser usuario;

    //private List<ParseObject> disciplinas;
    //private List<ParseObject> horarios;

    @Override
    public void onCreate(){
        super.onCreate();
        singleton = this;

        Parse.enableLocalDatastore(this);
        Parse.initialize(this,
                getString(R.string.parse_app_id),
                getString(R.string.parse_client_key)
        );

        ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));

        setUsuario(ParseUser.getCurrentUser());

        Log.d("GradeWEB", "GradeWEBApplication @onCreate");


    }


    public static GradeWEBApplication getInstance() {
        return singleton;
    }

    public ParseUser getUsuario() {
        return usuario;
    }

    public void setUsuario(ParseUser usuario) {
        this.usuario = usuario;
    }



    public void updateUsuario() {
        this.usuario = ParseUser.getCurrentUser();

    }

    public void logoutUsuario() {
        ParseUser.logOut();
        updateUsuario();
    }

}
