package com.example.moaaznash.facebooksigninmozeh;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by moaaznash on 3/6/17.
 */

public class FragmentMozeh extends AppCompatActivity {

    /***
     *
     * @param s1 should be getSupportFragmentManager().beginTransaction();
     *
     */

    public String FragmentSlidShow = "SlideShowFragment";


    public void loadFragment(android.support.v4.app.FragmentTransaction s1,String fragmentStringName){




if(fragmentStringName == "SlideShowFragment") {
    Bundle args = new Bundle();
    FragmentOne fr1 = new FragmentOne();

    args.putString("index", "Hi Yahomar");
    fr1.setArguments(args);
    s1.replace(R.id.fr_container, fr1);
    s1.commit();
}
    }
}
