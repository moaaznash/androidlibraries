package com.example.moaaznash.userregistrationmozeh;


import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.params.Face;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.moaaznash.mozehlibraryandroid.GlobalFunctionsMozeh;
import com.example.moaaznash.mozehlibraryandroid.ResultSuccessMozeh;
import com.example.moaaznash.mozehlibraryandroid.SettingPrametersMozeh;
import com.example.moaaznash.mozehlibraryandroid.TypesMozeh.FacebookUserTypeMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirDBMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirTblMozeh;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class FacebookLogin extends Fragment {
    private FirebaseAuth mAuth;
    // Custom button
    // private Button fbbutton;

    // Creating Facebook CallbackManager Value
    public static CallbackManager callbackmanager;


    public FacebookLogin() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facebook_login, container, false);


        onFblogin();


        return view;
    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("", "handleFacebookAccessToken:" + token);

        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());


        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //   Log.d("MozehFacebook","Success");

              getProfileInfos(getActivity());


            }
        });


    }

    public static void getProfileInfos(final Context context){

        Bundle params = new Bundle();
        params.putString("fields", "id,email,gender,cover,picture.type(large),first_name,last_name");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // FacebookUserTypeMozeh facebookUserTypeMozeh = new FacebookUserTypeMozeh();
                        String id = "";
                        String email = "";
                        String profilePicUrl = "";
                        String firstName = "";
                        String lastName = "";

                        if (response != null) {
                            try {
                                JSONObject data = response.getJSONObject();

                                Log.d("MozehFbJson",response.toString());

                                if(data.has("email")){

                                    email = data.getString("email");
                                    // facebookUserTypeMozeh;
                                    //  facebookUserTypeMozeh.

                                    //  Log.d("Mozeh Json Facebook",response.getJSONObject().toString());

                                }
                                if (data.has("picture")) {
                                    profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");

                                    // Log.d("MozehFacebook Pic",profilePicUrl);
                                }

                                if (data.has("id")) {
                                    id = data.getString("id");

                                    // Log.d("MozehFacebook Pic",profilePicUrl);
                                }

                                if (data.has("first_name")) {
                                    firstName = data.getString("first_name");

                                    // Log.d("MozehFacebook Pic",profilePicUrl);
                                }


                                if (data.has("last_name")) {
                                    lastName = data.getString("last_name");

                                    // Log.d("MozehFacebook Pic",profilePicUrl);
                                }


                              //  onProcesResultListenerMozeh.onProcessCompleteMozeh(new ResultSuccessMozeh(true,""),new FacebookUserTypeMozeh(id,email,profilePicUrl,firstName,lastName));


                                UsersFirDBMozeh usersFirDBMozeh = new UsersFirDBMozeh();
                                UsersFirTblMozeh usersFirTblMozeh = UsersFirTblMozeh.convertFacebookUserTypeMozehtoUserFirTblMozeh(new FacebookUserTypeMozeh(id,email,profilePicUrl,firstName,lastName));


                                usersFirDBMozeh.createOrUpdateUserInformationsFirebaseMozeh(usersFirTblMozeh);

                                SettingPrametersMozeh.setUserCredintial(context, usersFirTblMozeh);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).executeAsync();

    }
    // Private method to handle Facebook login and callback
    private void onFblogin() {

        // fbbutton.setText("Log Out 55");
        callbackmanager = CallbackManager.Factory.create();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));

        LoginManager.getInstance().registerCallback(callbackmanager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        handleFacebookAccessToken(loginResult.getAccessToken());


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

}
