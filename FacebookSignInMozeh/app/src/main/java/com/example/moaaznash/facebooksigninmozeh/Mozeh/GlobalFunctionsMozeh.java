package com.example.moaaznash.facebooksigninmozeh.Mozeh;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by moaaznash on 3/6/17.
 */

public class GlobalFunctionsMozeh extends AppCompatActivity {


    /**
     *
     * @param message
     * @param context should be getApplicationContext() if Activity, and getActivity() if inside Fragment
     */
    public static void showAlert(String message, Context context){

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }
}
