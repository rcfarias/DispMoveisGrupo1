package com.rafael.gradeweb;

import java.util.ArrayList;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class MainActivityDrawer extends Activity {
    private final String TAG = "TKT";

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private CustomDrawerAdapter adapter;

    List<DrawerItem> dataList;

    FragmentManager frgManager;
    Fragment fragment;

    private ParseUser currentUser;
    private  GradeWEBApplication myApplication;

    private List<ParseObject> disciplinas;
    private List<ParseObject> horarios;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        //savedInstanceState.putBoolean("MyBoolean", true);
        //savedInstanceState.putDouble("myDouble", 1.9);
        savedInstanceState.putInt("position", mDrawerList.getSelectedItemPosition());
        //savedInstanceState.putString("MyString", "Welcome back to Android");
        // etc.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        Log.d("GradeWEB", "@onCreate");

        myApplication = GradeWEBApplication.getInstance();//(GradeWEBApplication) getApplicationContext();
        myApplication.updateUsuario();
        currentUser = myApplication.getUsuario();

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

        adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,dataList);

        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this,
                                                  mDrawerLayout,
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
        else {
            SelectItem(savedInstanceState.getInt("position"));
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
                fragment = new FragmentoListaSemestresUsuario();
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

} //end of MainActivityDrawer