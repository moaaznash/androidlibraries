package com.example.moaaznash.userregistrationmozeh;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.moaaznash.mozehlibraryandroid.FirebaseAuthProcessMozeh;
import com.example.moaaznash.mozehlibraryandroid.GlobalFunctionsMozeh;
import com.example.moaaznash.mozehlibraryandroid.ResultSuccessMozeh;
import com.example.moaaznash.mozehlibraryandroid.SettingPrametersMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirTblMozeh;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditSignUpHome.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EditSignUpHome extends Fragment {

    private OnFragmentInteractionListener mListener;
    Button editUserInfosBtn;
    Button editEmailBtn;
    Button editPasswordBtn;
    Toolbar toolbar;

    Button signOutBtn;

    public EditSignUpHome() {
        // Required empty public constructor
    }

    private void definePars(View view) {

        editUserInfosBtn = (Button) view.findViewById(R.id.editUserInfoBtn);
signOutBtn = (Button) view.findViewById(R.id.signOutBtn);

        editEmailBtn = (Button) view.findViewById(R.id.changeEmailBtn);
        editPasswordBtn = (Button) view.findViewById(R.id.changePasswordBtn);
//toolbar = (Toolbar) view.findViewById(R.id.toolbar);

         editEmailBtn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.fr_container, new EditEmailFragment()).commit();

                     }
                 });

         editPasswordBtn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.fr_container, new EditPasswordFragment()).commit();

                     }
                 });


         signOutBtn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         SettingPrametersMozeh.removeUserCredintial(getContext());
                         FirebaseAuth.getInstance().signOut();
//toolbar.setVisibility(View.INVISIBLE);



                       getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.fr_container, new SignInFragment()).commit();



                        // GlobalFunctionsMozeh.checkIfSignedIn(getActivity().getSupportFragmentManager());

                     }
                 });





        editUserInfosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditSignUpFragment editSignUpFragment = new EditSignUpFragment();

               // SettingPrametersMozeh args = new SettingPrametersMozeh();
               // args.setUserCredintial(new UsersFirTblMozeh("888765", "", "", "", "", "", "", "", ""));
              //  editSignUpFragment.onFragmentInteractionMozeh(args);


                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.fr_container, editSignUpFragment).commit();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_sign_up_home, container, false);
        definePars(view);

        if(Objects.equals(SettingPrametersMozeh.getUserCredintials().LoginType, "EMAIL")){


        }else{
            editEmailBtn.setVisibility(View.INVISIBLE);
            editPasswordBtn.setVisibility(View.INVISIBLE);

        }

//        Log.d("Mozeh",SettingPrametersMozeh.getUserCredintials().Id);

       // SettingPrametersMozeh.getUserCredintials().Id = "Shis";
       // Log.d("Mozeh",SettingPrametersMozeh.getUserCredintials().Id);

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
