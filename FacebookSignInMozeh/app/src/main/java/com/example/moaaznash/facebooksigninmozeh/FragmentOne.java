package com.example.moaaznash.facebooksigninmozeh;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moaaznash.facebooksigninmozeh.Mozeh.GlobalFunctionsMozeh;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import android.support.v7.app.AppCompatActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {

    LoginButton loginButton;

    public FragmentOne() {
        // Required empty public constructor
    }

    //@Override
   // public View onCreate




    // Custom button
    private Button fbbutton;

    // Creating Facebook CallbackManager Value
    public static CallbackManager callbackmanager;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize SDK before setContentView(Layout ID)
        //FacebookSdk.sdkInitialize(getApplicationContext());

        //setContentView(R.layout.activity_main);

        // Initialize layout button
        fbbutton = (Button) getView().findViewById(R.id.button3);

        fbbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Call private method
                onFblogin();
            }
        });

    }

    // Private method to handle Facebook login and callback
    private void onFblogin()
    {
        System.out.println("Hi");
        fbbutton.setText("Log Out 55");
        callbackmanager = CallbackManager.Factory.create();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","user_photos","public_profile"));

        LoginManager.getInstance().registerCallback(callbackmanager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        fbbutton.setText("Log Out");
                        System.out.println("Success");

                        GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject json, GraphResponse response) {
                                        if (response.getError() != null) {
                                            // handle error
                                            System.out.println("ERROR");
                                        } else {
                                            System.out.println("Success");
                                            try {

                                                String jsonresult = String.valueOf(json);
                                                System.out.println("JSON Result"+jsonresult);

                                                String str_email = json.getString("email");
                                                String str_id = json.getString("id");
                                                String str_firstname = json.getString("first_name");
                                                String str_lastname = json.getString("last_name");
                                                fbbutton.setText(str_id);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                }).executeAsync();


                    }

                    @Override
                    public void onCancel() {
                        //  Log.d(TAG_CANCEL,"On cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        //  Log.d(TAG_ERROR,error.toString());
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackmanager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GlobalFunctionsMozeh.showAlert("Bye",getActivity());

        View view = inflater.inflate(R.layout.fragment_fragment_one, container, false);


        return view;
    }





}
