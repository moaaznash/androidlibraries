package com.example.moaaznash.userregistrationmozeh;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moaaznash.mozehlibraryandroid.FirebaseAuthProcessMozeh;
import com.example.moaaznash.mozehlibraryandroid.GlobalFunctionsMozeh;
import com.example.moaaznash.mozehlibraryandroid.OnDataSnapResultListenerMoze;
import com.example.moaaznash.mozehlibraryandroid.ResultSuccessMozeh;
import com.example.moaaznash.mozehlibraryandroid.SetAppGlobalVariablesMozeh;
import com.example.moaaznash.mozehlibraryandroid.SettingPrametersMozeh;
import com.example.moaaznash.mozehlibraryandroid.SharedPreferenceMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirDBMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirTblMozeh;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SignUpFragment.OnFragmentInteractionListener, EditSignUpHome.OnFragmentInteractionListener, EditSignUpFragment.OnFragmentInteractionListener, SignInFragment.OnFragmentInteractionListener, EditEmailFragment.OnFragmentInteractionListener, EditPasswordFragment.OnFragmentInteractionListener {

    private static final String TWITTER_KEY = "RFIPS9UG90gJvf0kE8H62niyt";
    private static final String TWITTER_SECRET = "2DT894btATOaWOSv5H0fSy5azB3Xo5aNdKoYwqrS4sOU5MG60C";
    NavigationView navigationView;

    UsersFirTblMozeh usersFirTblMozeh;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);







        SetAppGlobalVariablesMozeh.setVaribales(MainActivity.this, getSupportFragmentManager(), new SetAppGlobalVariablesMozeh.OnProcesResultListenerMozeh() {
            @Override
            public void onProcessCompleteMozeh(ResultSuccessMozeh resultSuccessMozeh) {
                if(resultSuccessMozeh.Success){
                           //
                    GlobalFunctionsMozeh.showAlert(MainActivity.this, "Signed In Successfull", "Ok", new GlobalFunctionsMozeh.OnGlobalFunctionsMozehListeners() {
                        @Override
                        public void OnAlertDialogOkPressedMozeh() {

                            getSupportFragmentManager().beginTransaction().replace(R.id.fr_container, new EditSignUpFragment()).commit();

                        }
                    });

                                            }else{
                                        //


                    GlobalFunctionsMozeh.showAlert(MainActivity.this, "You are not signed in", "Ok", new GlobalFunctionsMozeh.OnGlobalFunctionsMozehListeners() {
                        @Override
                        public void OnAlertDialogOkPressedMozeh() {

                            getSupportFragmentManager().beginTransaction().replace(R.id.fr_container, new SignInFragment()).commit();

                        }
                    });


                }
            }
        });






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }






    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sing_up) {
            //  toggleDrawerVisibility(false);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.fr_container, new SignUpFragment()).commit();
        } else if (id == R.id.nav_gallery) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fr_container, new EditSignUpFragment()).commit();

        } else if (id == R.id.nav_slideshow) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fr_container, new EditSignUpHome()).commit();
        } else if (id == R.id.nav_manage) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fr_container, new SignInFragment()).commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteractionMozeh(SettingPrametersMozeh args) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GoogleLoginFragment.RC_SIGN_IN) {
            SignInFragment fragment = (SignInFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fr_container);
            fragment.onActivityResult(requestCode, resultCode, data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        getSupportFragmentManager().findFragmentById(R.id.fr_container).onActivityResult(requestCode, resultCode, data);

    }
}
