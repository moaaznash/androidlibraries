package com.example.moaaznash.userregistrationmozeh;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.moaaznash.mozehlibraryandroid.FirebaseAuthProcessMozeh;
import com.example.moaaznash.mozehlibraryandroid.GlobalFunctionsMozeh;
import com.example.moaaznash.mozehlibraryandroid.OnDataSnapResultListenerMoze;
import com.example.moaaznash.mozehlibraryandroid.ResultSuccessMozeh;
import com.example.moaaznash.mozehlibraryandroid.SettingPrametersMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirDBMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirTblMozeh;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SignInFragment extends Fragment {


    EditText emailET;
    EditText passwordET;
    Button signInBtn;

    Button faceboookBtn;
    Button gooogleBtn;
    Button twittterBtn;
   Toolbar toolbar;
    // Custom button
    //  private Button fbbutton;

    Button testFbBtn;

    // Creating Facebook CallbackManager Value
    // public static CallbackManager callbackmanager;

    private OnFragmentInteractionListener mListener;

    public SignInFragment() {
        // Required empty public constructor
    }


    private void definePars(View view) {

        emailET = (EditText) view.findViewById(R.id.emailET);
        passwordET = (EditText) view.findViewById(R.id.passwordET);
        signInBtn = (Button) view.findViewById(R.id.signInBtn);
        //  fbbutton = (Button) view.findViewById(R.id.facebookBtn);
//testFbBtn = (Button) view.findViewById(R.id.testFbBtn);



        faceboookBtn = (Button) view.findViewById(R.id.faceboookBtn);
        gooogleBtn = (Button) view.findViewById(R.id.gooogleBtn);
        twittterBtn = (Button) view.findViewById(R.id.twittterBtn);


        faceboookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.fr_container, new FacebookLogin()).commit();

            }
        });

        gooogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.fr_container, new GoogleLoginFragment()).commit();

            }
        });
        twittterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.fr_container, new TwitterLoginFragment()).commit();

            }
        });


        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//GlobalFunctionsMozeh.v

                //Log.d("Mozeh",emailET.getText().toString());



                String ema = emailET.getText().toString().trim();
                String pass = passwordET.getText().toString().trim();

                if (!Objects.equals(ema, "")  && !Objects.equals(pass, "")) {




                    FirebaseAuthProcessMozeh.signIn(getActivity(), emailET.getText().toString().trim(), passwordET.getText().toString().trim(), new FirebaseAuthProcessMozeh.OnProcesResultListenerMozeh() {
                        @Override
                        public void onProcessCompleteMozeh(ResultSuccessMozeh resultSuccessMozeh) {
                            if (resultSuccessMozeh.Success) {
                                //
                                UsersFirDBMozeh udb = new UsersFirDBMozeh();
                                udb.getUserInformationById(FirebaseAuth.getInstance().getCurrentUser().getUid().toString(), new OnDataSnapResultListenerMoze() {
                                    @Override
                                    public void onSingleDataSnapShot(UsersFirTblMozeh usersFirTblMozeh) {

                                        SettingPrametersMozeh.setUserCredintial(getContext(), usersFirTblMozeh);
                                      //toolbar.setVisibility(View.VISIBLE);




                                        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.fr_container, new EditSignUpFragment()).commit();

                                    }

                                    @Override
                                    public void onMultiDataSnapShot(List<UsersFirTblMozeh> usersFirTblMozehs) {

                                    }
                                });
                                //  SettingPrametersMozeh.setUserCredintial(getContext(),);

                            } else {
                                //
                                GlobalFunctionsMozeh.showAlert(getContext(), "Email or Password Incorrect, Try Again!", "Ok");

                            }
                        }
                    });
                }else{

                 GlobalFunctionsMozeh.showAlert(getContext(),"يرجى ملأ البريد وكلمة السر","Ok");
                }
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);


        // FacebookSdk.sdkInitialize(getContext());
        definePars(view);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
