package com.example.moaaznash.userregistrationmozeh;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moaaznash.mozehlibraryandroid.SettingPrametersMozeh;
import com.example.moaaznash.mozehlibraryandroid.TypesMozeh.GoogleUserTypeMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirDBMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirTblMozeh;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoogleLoginFragment extends Fragment implements
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{
    GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;

    public static final int RC_SIGN_IN = 49404;

    public GoogleLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.fragment_google_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(view.getContext())
                .enableAutoManage(getActivity(), this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
          //  handleSignInResult(result);
            firebaseAuthWithGoogle(result);
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();


            //  mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
/*
            GoogleUserTypeMozeh googleUserTypeMozeh = new GoogleUserTypeMozeh(acct.getId(),acct.getEmail(),acct.getPhotoUrl().toString(),acct.getDisplayName());

            UsersFirDBMozeh usersFirDBMozeh = new UsersFirDBMozeh();
            UsersFirTblMozeh usersFirTblMozeh = UsersFirTblMozeh.convertFacebookUserTypeMozehtoUserFirTblMozeh(go);


            usersFirDBMozeh.createOrUpdateUserInformationsFirebaseMozeh(usersFirTblMozeh);

            SettingPrametersMozeh.setUserCredintial(getContext(), usersFirTblMozeh);
            */
            System.out.println("Success ");
            Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
            System.out.println(acct.getDisplayName().toString());
        } else {
            // Signed out, show unauthenticated UI.
            // updateUI(false);
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInResult result) {
   //     Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            final GoogleSignInAccount acct = result.getSignInAccount();


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d("GoogleFirebaseComplete", "signInWithCredential:onComplete:" + task.isSuccessful());

                        UsersFirDBMozeh usersFirDBMozeh = new UsersFirDBMozeh();
                        GoogleUserTypeMozeh googleUserTypeMozeh = new GoogleUserTypeMozeh(acct.getId(),acct.getEmail(),acct.getPhotoUrl().toString(),acct.getDisplayName());

                        UsersFirTblMozeh usersFirTblMozeh = UsersFirTblMozeh.convertGoogleUserTypeMozehtoUserFirTblMozeh(googleUserTypeMozeh);


                        usersFirDBMozeh.createOrUpdateUserInformationsFirebaseMozeh(usersFirTblMozeh);

                        SettingPrametersMozeh.setUserCredintial(getContext(), usersFirTblMozeh);



                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                         //   Log.w(TAG, "signInWithCredential", task.getException());

                        }
                        // ...
                    }
                });

        }
    }
 //   GoogleSignInActivity.java

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
