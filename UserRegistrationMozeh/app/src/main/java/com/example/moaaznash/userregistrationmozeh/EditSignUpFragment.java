package com.example.moaaznash.userregistrationmozeh;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.moaaznash.mozehlibraryandroid.FireBaseStorageProcessMozeh;
import com.example.moaaznash.mozehlibraryandroid.FirebaseAuthProcessMozeh;
import com.example.moaaznash.mozehlibraryandroid.GlobalFunctionsMozeh;
import com.example.moaaznash.mozehlibraryandroid.PhotoSelectionMethodMozeh;
import com.example.moaaznash.mozehlibraryandroid.ResultSuccessMozeh;
import com.example.moaaznash.mozehlibraryandroid.SettingPrametersMozeh;
import com.example.moaaznash.mozehlibraryandroid.TypesMozeh.ImageTypeMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirDBMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirTblMozeh;
import com.example.moaaznash.mozehlibraryandroid.WriteFileHandlingMozeh;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditSignUpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EditSignUpFragment extends Fragment {


    UsersFirTblMozeh usersFirTblMozeh;
    UsersFirDBMozeh usersFirDBMozeh = new UsersFirDBMozeh();
    Button submitSignUpBtn;
    EditText nameET;
    //EditText emailET;
    //EditText passwordET;
    //EditText confirmPasswordET;
    EditText phoneET;
    EditText notesET;
    ImageView imageView;
    private Bitmap bitmap;
    Button choosePicButton;
    StorageReference storageRef;
    PhotoSelectionMethodMozeh photoSelectionMethodMozeh;


    public static String UserId = "";

    private void defineParameters(View view) {

        usersFirTblMozeh = SettingPrametersMozeh.getUserCredintials();


        System.out.println(UsersFirTblMozeh.getAllValues(usersFirTblMozeh));
        submitSignUpBtn = (Button) view.findViewById(R.id.editSignUpBtn);
        nameET = (EditText) view.findViewById(R.id.nameET);

        phoneET = (EditText) view.findViewById(R.id.phoneET);
        notesET = (EditText) view.findViewById(R.id.notesET);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        storageRef = FirebaseStorage.getInstance().getReference();
        choosePicButton = (Button) view.findViewById(R.id.choosePictureBtn);

    photoSelectionMethodMozeh = new PhotoSelectionMethodMozeh(getActivity(), getContext(), new ImageTypeMozeh(SettingPrametersMozeh.getUserCredintials().ImageName, SettingPrametersMozeh.getUserCredintials().ProfilePicUrl), imageView);




        choosePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startPhotoLibs();
                photoSelectionMethodMozeh.startPhotoLibs(imageView);
            }
        });

        submitSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ResultSuccessMozeh res = GlobalFunctionsMozeh.validateEmailSignUpForm(nameET.getText().toString(), "sdfsdf@sdf.com", "123456", "123456", usersFirTblMozeh.ProfilePicUrl, usersFirTblMozeh.ImageName);

                if (res.Success) {


                    UsersFirTblMozeh updatedUser = new UsersFirTblMozeh(usersFirTblMozeh.Id, nameET.getText().toString(), nameET.getText().toString(), phoneET.getText().toString(), usersFirTblMozeh.ProfilePicUrl, usersFirTblMozeh.Email, notesET.getText().toString(), usersFirTblMozeh.LoginType, usersFirTblMozeh.ImageName);
                    usersFirDBMozeh.updateUserInformationsFirebaseByIdMozeh(updatedUser);

                    SettingPrametersMozeh.setUserCredintial(getContext(), updatedUser);
                    FirebaseAuthProcessMozeh.updateFirebaseUser(updatedUser.FullName, updatedUser.ProfilePicUrl);
                    GlobalFunctionsMozeh.showAlert(getContext(), "All Good", "Ok");


                } else {

                    GlobalFunctionsMozeh.showAlert(getContext(), res.Message, "حسناً", new GlobalFunctionsMozeh.OnGlobalFunctionsMozehListeners() {
                        @Override
                        public void OnAlertDialogOkPressedMozeh() {

                        }
                    });


                }






            }
        });

        nameET.setText(usersFirTblMozeh.FullName);
        phoneET.setText(usersFirTblMozeh.Phone);
        notesET.setText(usersFirTblMozeh.Notes);


    }

    private OnFragmentInteractionListener mListener;

    public EditSignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_sign_up, container, false);
        defineParameters(view);

        // Log.d("MozehImageName","ss"+SettingPrametersMozeh.getUserCredintials().Id);
        Log.d("Mozeh", SettingPrametersMozeh.getUserCredintials().ProfilePicUrl);


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        photoSelectionMethodMozeh.onActivityResult(requestCode, resultCode, data, new PhotoSelectionMethodMozeh.OnActivityListenerMozeh() {
            @Override
            public void onPickPhotoComplete(String downloadUrl, String fileName) {
                // Log.d("MozehTest1",downloadUrl+" - "+fileName);
                usersFirTblMozeh.ProfilePicUrl = downloadUrl;
                usersFirTblMozeh.ImageName = fileName;
            }
        });


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
        // Log.d("Mozeh",);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void onFragmentInteractionMozeh(SettingPrametersMozeh args) {
        // UsersFirTblMozeh uu = (UsersFirTblMozeh) args.

        // System.out.println("Hello From Fragment"+uu.Id);
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

        /**
         * @param The Paramenters Keys is in the Class SettingPrametersMozeh
         *            example: parameters.get(SettingPrametersMozeh.getUserCredintial)
         */
        void onFragmentInteractionMozeh(SettingPrametersMozeh args);
    }
}
