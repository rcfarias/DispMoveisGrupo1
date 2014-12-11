package com.rafael.gradeweb;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseUser;

/**
 * Created by Rafael on 11/23/2014.
 */
public class FragmentSemesterList extends Fragment {

    private GradeWEBApplication mApp;

    //private TextView titleTextView1;
    //private TextView emailTextView1;
    //private TextView nameTextView1;
    //private Button loginOrLogoutButton;

    private ListView listView;

    private final static String URL = "http://google.com";

    public static final String ITEM_NAME = "itemName";

    public FragmentSemesterList(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.semester_main_list_fragment, container,false);

        Log.d("FragmentSemesterList", "entrei CARALHO");



        mApp = GradeWEBApplication.getInstance();
        ParseUser usuario = mApp.getUsuario();

        //populateListView();

        //create list of items
        String[] myItems = { "2009-1", "2009-2", "2010-1", "2010-2", "2011-1", "2011-2",
                "2012-2", "2013-1", "2013-2", "2014-1", "2014-2"};

        // build adapter
        ArrayAdapter<String> myAdapter;
        myAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.semester_item,
                myItems);

        //configure the listView
        listView = (ListView) view.findViewById(R.id.semestre_list_view);
        listView.setAdapter(myAdapter);


        return view;
    }

    private void populateListView() {


    }

}


/*
*
@Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
        "Linux", "OS/2" };
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
        android.R.layout.simple_list_item_1, values);
    setListAdapter(adapter);
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    // do something with the data
  }



*
*
* */