package com.rafael.gradeweb;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

/**
 * Created by Rafael Farias on 19/11/2014.
 */
public class FragmentProfile extends Fragment {

    private GradeWEBApplication mApp;

    private TextView titleTextView1;
    private TextView emailTextView1;
    private TextView nameTextView1;
    //private Button loginOrLogoutButton;

    private final static String URL = "http://google.com";

    public static final String ITEM_NAME = "itemName";

    public FragmentProfile(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_profile, container,false);

        titleTextView1 = (TextView) view.findViewById(R.id.profile_title1);
        emailTextView1 = (TextView) view.findViewById(R.id.profile_email1);
        nameTextView1 = (TextView) view.findViewById(R.id.profile_name1);
        //titleTextView1.setText(R.string.profile_title_logged_in);

        mApp = GradeWEBApplication.getInstance();
        ParseUser usuario = mApp.getUsuario();

        if(usuario == null)
            Log.d("FragmentProfile", "usuario eh null :S caralho");
        else
            Log.d("FragmentProfile", "usuario nao eh null :S");

        titleTextView1.setText(R.string.profile_title_logged_in);
        emailTextView1.setText(usuario.getString("email"));
        nameTextView1.setText(usuario.getString("name"));

        return view;
    }

}

