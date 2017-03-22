package com.example.moaaznash.userregistrationmozeh;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moaaznash.mozehlibraryandroid.SettingPrametersMozeh;
import com.example.moaaznash.mozehlibraryandroid.TypesMozeh.GoogleUserTypeMozeh;
import com.example.moaaznash.mozehlibraryandroid.TypesMozeh.TwitterUserTypeMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirDBMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirTblMozeh;
import com.facebook.internal.ImageDownloader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import retrofit2.Call;

import static android.R.attr.name;


/**
 * A simple {@link Fragment} subclass.
 */
public class TwitterLoginFragment extends Fragment {
    private FirebaseAuth mAuth;

    private TwitterLoginButton loginButton;
    public TwitterLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_twitter_login, container, false);
        mAuth = FirebaseAuth.getInstance();
//TwitterSession mm = new TwitterSess
        loginButton = (TwitterLoginButton) view.findViewById(R.id.twitter_login_button);

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                //result.data.
                final TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
             //   session.

//getTwitterData(session);
               // Twitter.getInstance().core.




                Call<User> user = TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(false, false);
                user.enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> userResult) {
                        String name = userResult.data.name;
                        String email = userResult.data.email;
                        String id = String.valueOf(userResult.data.id);

                        // _normal (48x48px) | _bigger (73x73px) | _mini (24x24px)
                        String photoUrlNormalSize   = userResult.data.profileImageUrl;
                        String photoUrlBiggerSize   = userResult.data.profileImageUrl.replace("_normal", "_bigger");
                        String photoUrlMiniSize     = userResult.data.profileImageUrl.replace("_normal", "_mini");
                        String photoUrlOriginalSize = userResult.data.profileImageUrl.replace("_normal", "");

                       // Log.d("Mozeh",photoUrlBiggerSize);
TwitterUserTypeMozeh twitterUserTypeMozeh = new TwitterUserTypeMozeh(id,email,photoUrlNormalSize,name);
                        handleTwitterSession(session,twitterUserTypeMozeh);
                    }

                    @Override
                    public void failure(TwitterException exc) {
                        Log.d("TwitterKit", "Verify Credentials Failure", exc);
                    }
                });

                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                // tex.setText(msg);
                System.out.println(msg);
                // Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });


        loginButton.performClick();
    return view;
    }


    private void handleTwitterSession(TwitterSession session, final TwitterUserTypeMozeh twitterUserTypeMozeh) {
        Log.d("", "handleTwitterSession:" + session);

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Mozeh", "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.


                        UsersFirDBMozeh usersFirDBMozeh = new UsersFirDBMozeh();
                     //   GoogleUserTypeMozeh googleUserTypeMozeh = new GoogleUserTypeMozeh(acct.getId(),acct.getEmail(),acct.getPhotoUrl().toString(),acct.getDisplayName());

                        UsersFirTblMozeh usersFirTblMozeh = UsersFirTblMozeh.convertTwitterUserTypeMozehtoUserFirTblMozeh(twitterUserTypeMozeh);


                        usersFirDBMozeh.createOrUpdateUserInformationsFirebaseMozeh(usersFirTblMozeh);

                        SettingPrametersMozeh.setUserCredintial(getContext(), usersFirTblMozeh);




                        if (!task.isSuccessful()) {
                            Log.w("Mozeh", "signInWithCredential", task.getException());

                        }

                        // ...
                    }
                });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }


    /*public void getTwitterData(final TwitterSession session) {
        tapiclient.getCustomService().show(session.getUserId(),
                new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {

                        TwitterAuthToken authToken = session.getAuthToken();
                        String token = authToken.token;
                        String secret = authToken.secret;
                        // name.setText(result.data.name);
                        // location.setText(result.data.location);
                        // new ImageDownloader(profileImageView)
                        //  .execute(result.data.profileImageUrl);

                       // Log.d("Name", name);
                        Log.d("city", result.data.profileImageUrl);

                    }

                    public void failure(TwitterException exception) {
                        // Do something on failure
                        exception.printStackTrace();
                    }
                });
    }*/
}
