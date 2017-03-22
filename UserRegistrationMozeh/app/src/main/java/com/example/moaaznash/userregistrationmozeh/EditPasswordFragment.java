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
 * {@link EditPasswordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EditPasswordFragment extends Fragment {

    EditText currentPasswordET;
    EditText newPasswordET;
    EditText confirmNewPasswordET;
    Button changePasswordBtn;
    private OnFragmentInteractionListener mListener;

    public EditPasswordFragment() {
        // Required empty public constructor
    }


    private void definePars(View view) {
        currentPasswordET = (EditText) view.findViewById(R.id.currentPasswordET);
        newPasswordET = (EditText) view.findViewById(R.id.newPasswordET);
        confirmNewPasswordET = (EditText) view.findViewById(R.id.confirmNewPasswordET);
        changePasswordBtn = (Button) view.findViewById(R.id.changePasswordBtn);

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResultSuccessMozeh res = GlobalFunctionsMozeh.validateEmailSignUpForm("Anythin", "sdfsf@sddf.com", newPasswordET.getText().toString().trim(), confirmNewPasswordET.getText().toString().trim(), "sdfsdf", "sdfsfd");

                if (res.Success) {


                    FirebaseAuthProcessMozeh.updatePassword(getActivity(), SettingPrametersMozeh.getUserCredintials().Email, currentPasswordET.getText().toString().trim(), newPasswordET.getText().toString().trim(), new FirebaseAuthProcessMozeh.OnProcesResultListenerMozeh() {
                        @Override
                        public void onProcessCompleteMozeh(ResultSuccessMozeh resultSuccessMozeh) {
                            if (resultSuccessMozeh.Success) {
                                //
                                GlobalFunctionsMozeh.showAlert(getContext(), "Password Changed", "Ok");
                            } else {
                                //
                                GlobalFunctionsMozeh.showAlert(getContext(), "Password Not Changed", "Ok");
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
        View view = inflater.inflate(R.layout.fragment_edit_password, container, false);
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
