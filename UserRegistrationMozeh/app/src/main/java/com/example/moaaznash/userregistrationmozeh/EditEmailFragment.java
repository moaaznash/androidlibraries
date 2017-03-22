package com.example.moaaznash.userregistrationmozeh;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.moaaznash.mozehlibraryandroid.FirebaseAuthProcessMozeh;
import com.example.moaaznash.mozehlibraryandroid.GlobalFunctionsMozeh;
import com.example.moaaznash.mozehlibraryandroid.ResultSuccessMozeh;
import com.example.moaaznash.mozehlibraryandroid.SettingPrametersMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirDBMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirTblMozeh;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditEmailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EditEmailFragment extends Fragment {

    EditText emailET;
    EditText passwordET;
    EditText newEmailET;
    Button changeEmailBtn;
    private OnFragmentInteractionListener mListener;

    public EditEmailFragment() {
        // Required empty public constructor
    }


    private void definePars(View view) {
        emailET = (EditText) view.findViewById(R.id.emailET);
        passwordET = (EditText) view.findViewById(R.id.passwordET);
        changeEmailBtn = (Button) view.findViewById(R.id.editEmailBtn);
        newEmailET = (EditText) view.findViewById(R.id.newEmailET);

        changeEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ResultSuccessMozeh res = GlobalFunctionsMozeh.validateEmailSignUpForm("Anythin", emailET.getText().toString().trim(), passwordET.getText().toString().trim(), passwordET.getText().toString().trim(), "sdfsdf", "sdfsfd");

                if (res.Success) {







                FirebaseAuthProcessMozeh.updateEmail(getActivity(), emailET.getText().toString().trim(), newEmailET.getText().toString().trim(), passwordET.getText().toString().trim(), new FirebaseAuthProcessMozeh.OnProcesResultListenerMozeh() {
                    @Override
                    public void onProcessCompleteMozeh(ResultSuccessMozeh resultSuccessMozeh) {
                        if (resultSuccessMozeh.Success) {
                            //

                            UsersFirTblMozeh uu = SettingPrametersMozeh.getUserCredintials();
                            uu.Email = newEmailET.getText().toString().trim();

                            UsersFirDBMozeh udb = new UsersFirDBMozeh();
                            udb.updateUserInformationsFirebaseByIdMozeh(uu);

                            SettingPrametersMozeh.setUserCredintial(getContext(), uu);
                            GlobalFunctionsMozeh.showAlert(getContext(), "Email Updated", "Ok");
                        } else {
                            //
                            GlobalFunctionsMozeh.showAlert(getContext(), "Email Not Updated", "Ok");

                        }
                    }
                });


                } else {

                    GlobalFunctionsMozeh.showAlert(getContext(), res.Message, "حسناً", new GlobalFunctionsMozeh.OnGlobalFunctionsMozehListeners() {
                        @Override
                        public void OnAlertDialogOkPressedMozeh() {

                        }
                    });


                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_email, container, false);
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
