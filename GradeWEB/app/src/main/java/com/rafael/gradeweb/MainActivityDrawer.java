package com.rafael.gradeweb;

//import com.rafael.gradeweb.GradeWEBApplication;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.String;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MainActivityDrawer extends Activity {
    private final String TAG = "TKT";

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    CustomDrawerAdapter adapter;

    List<DrawerItem> dataList;

    FragmentManager frgManager;
    Fragment fragment;

    private ParseUser currentUser;
    private  GradeWEBApplication myApplication;

    private List<ParseObject> disciplinas;
    private List<ParseObject> horarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        Log.d("GradeWEB", "@onCreate");

        myApplication = GradeWEBApplication.getInstance();//(GradeWEBApplication) getApplicationContext();
        myApplication.updateUsuario();
        currentUser = myApplication.getUsuario();


        // initial query for the user schedule


        // Initializing
        dataList = new ArrayList<DrawerItem>();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,GravityCompat.START);

        // Add Drawer Item to dataList
        dataList.add(new DrawerItem("User", R.drawable.ic_action_user));
        dataList.add(new DrawerItem("Semestres", R.drawable.ic_action_account));
        dataList.add(new DrawerItem("Consulta Dsicplina", R.drawable.ic_action_video));
        //dataList.add(new DrawerItem("Import & Export", R.drawable.ic_action_import_export));
        dataList.add(new DrawerItem("About", R.drawable.ic_action_about));
        dataList.add(new DrawerItem("Settings", R.drawable.ic_action_settings));
        dataList.add(new DrawerItem("Help", R.drawable.ic_action_help));
        dataList.add(new DrawerItem("Log Out", R.drawable.ic_action_logout_dark));

        adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
                dataList);

        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            SelectItem(0);
        }

    }

    public void SelectItem(int possition) {

        fragment = null;
        Bundle args = new Bundle();
        switch (possition) {
            case 0:
                fragment = new FragmentProfile();
                args.putString("USER_NAME", currentUser.getString("name"));
                args.putString("EMAIL", currentUser.getEmail());
                break;
            case 1:
                fragment = new PlaceholderFragment();
                break;
            case 2:
                fragment = new FragmentoDetalhesDisciplina();
                break;
            case 3:
                fragment = new FragmentOne();
                args.putString(FragmentOne.ITEM_NAME, dataList.get(possition).getItemName());
                args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition).getImgResID());
                break;
            case 4:
                fragment = new FragmentOne();
                args.putString(FragmentOne.ITEM_NAME, dataList.get(possition).getItemName());
                args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition).getImgResID());
                break;
            case 5:
                fragment = new FragmentOne();
                args.putString(FragmentOne.ITEM_NAME, dataList.get(possition).getItemName());
                args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition).getImgResID());
                break;
            case 6:
                fragment = new FragmentOne();
                args.putString(FragmentOne.ITEM_NAME, dataList.get(possition).getItemName());
                args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition).getImgResID());
                break;
            default:
                break;
        }

        fragment.setArguments(args);
        frgManager = getFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment)
                .commit();

        mDrawerList.setItemChecked(possition, true);
        setTitle(dataList.get(possition).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return false;
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            if(position != 6){
                Log.d(TAG, "position != 6 @OnItemClickListener");
                SelectItem(position);
            }
            else {
                Log.d(TAG, "position == 6 @OnItemClickListener");
                if(currentUser != null) {
                    Log.d(TAG, "currentUser != null @OnItemClickListener");
                    //ParseUser.logOut();
                    //currentUser = null;
                    myApplication.logoutUsuario();
                    currentUser = myApplication.getUsuario();
                    finish();
                }
                else {
                    Log.d(TAG, "currentUser == null @OnItemClickListener");
                }
            }


        }
    }

    public class PlaceholderFragment extends Fragment {

        private GradeWEBApplication myApp;

        private ListView myListView;

        private String[] strListView;

        private List semestreList;

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView( LayoutInflater inflater,
                                  ViewGroup container,
                                  Bundle savedInstanceState) {

            myApp = GradeWEBApplication.getInstance();


            ParseQuery<ParseObject> horarioQuery = ParseQuery.getQuery("Horario");
            //horarioQuery.whereEqualTo("usuario", myApp.getUsuario());
            horarioQuery.selectKeys(Arrays.asList("semestre"));
            horarioQuery.orderByAscending("semestre");
            horarioQuery.fromLocalDatastore();

            List<ParseObject> horarioObjects = null;
            try {
                horarioObjects = horarioQuery.find();
            }
            catch (com.parse.ParseException e) {
                e.printStackTrace();
            }

            semestreList = new ArrayList();

            int k;
            for(k = 0; k < horarioObjects.size(); k++) {
                ParseObject cada = horarioObjects.get(k);

                semestreList.add((String) cada.get("semestre"));

            }

            View rootView = inflater.inflate(R.layout.semester_main_list_fragment, container, false);

            myListView = (ListView) rootView.findViewById(R.id.semestre_list_view);

            //strListView = getResources().getStringArray(R.array.data_listView);
            //ArrayAdapter<String> objAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_expandable_list_item_1, strListView);
            //myListView.setAdapter(objAdapter);


            ArrayAdapter<List> semestreAdapter = new ArrayAdapter<List>(this.getActivity(),
                                                                                 android.R.layout.simple_expandable_list_item_1,
                                                                                 semestreList);
            myListView.setAdapter(semestreAdapter);

            myListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MainActivityDrawer.this, "You clicked" + (String) semestreList.get(position), 5000).show();

                    fragment = new FragmentSemesterList();
                    Bundle args = new Bundle();

                    args.putString("SEMESTRE",(String) semestreList.get(position));

                    fragment.setArguments(args);
                    frgManager = getFragmentManager();
                    frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    

                }
            });

            return rootView;
        } // end of onCreateView

    } // end of PlaceholderFragment

} //end of MainActivityDrawer