package com.example.moaaznash.userregistrationmozeh;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moaaznash.mozehlibraryandroid.FireBaseStorageProcessMozeh;
import com.example.moaaznash.mozehlibraryandroid.FirebaseAuthProcessMozeh;
import com.example.moaaznash.mozehlibraryandroid.GlobalFunctionsMozeh;
//import com.example.moaaznash.mozehlibraryandroid.OnBitmapsProcessListenerMozeh;
import com.example.moaaznash.mozehlibraryandroid.PhotoSelectionMethodMozeh;
import com.example.moaaznash.mozehlibraryandroid.ResultSuccessMozeh;
import com.example.moaaznash.mozehlibraryandroid.SettingPrametersMozeh;
import com.example.moaaznash.mozehlibraryandroid.TypesMozeh.ImageTypeMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirDBMozeh;
import com.example.moaaznash.mozehlibraryandroid.UsersFirTblMozeh;
import com.example.moaaznash.mozehlibraryandroid.WriteFileHandlingMozeh;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;


public class SignUpFragment extends Fragment implements View.OnClickListener{
    UsersFirTblMozeh usersFirTblMozeh = new UsersFirTblMozeh();
    UsersFirDBMozeh usersFirDBMozeh = new UsersFirDBMozeh();
    Button submitSignUpBtn;
    EditText nameET;
    EditText emailET;
    EditText passwordET;
    EditText confirmPasswordET;
    EditText phoneET;
    EditText notesET;
    ImageView imageView;
    private Bitmap bitmap;
    Button choosePicButton;
    StorageReference storageRef;
    FirebaseAuth firebaseAuth;
    PhotoSelectionMethodMozeh photoSelectionMethodMozeh;

    private OnFragmentInteractionListener mListener;




    public SignUpFragment() {
        // Required empty public constructor
    }

    private void defineParameters(View view) {
        submitSignUpBtn = (Button) view.findViewById(R.id.submitSignUpBtn);
        nameET = (EditText) view.findViewById(R.id.nameET);
        emailET = (EditText) view.findViewById(R.id.emailET);
        passwordET = (EditText) view.findViewById(R.id.passwordET);
        confirmPasswordET = (EditText) view.findViewById(R.id.confirmPasswordET);
        phoneET = (EditText) view.findViewById(R.id.phoneET);
        notesET = (EditText) view.findViewById(R.id.notesET);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        storageRef = FirebaseStorage.getInstance().getReference();
        choosePicButton = (Button) view.findViewById(R.id.choosePictureBtn);

        firebaseAuth = FirebaseAuth.getInstance();
        photoSelectionMethodMozeh = new PhotoSelectionMethodMozeh(getActivity(),getContext(),new ImageTypeMozeh("",""),imageView);

        choosePicButton.setOnClickListener(this);
        submitSignUpBtn.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);


        defineParameters(view);


        return view;
    }

    private void getUIValues() {
        //  Log.d("MozehSignUpForm","Done");
        usersFirTblMozeh.Email = emailET.getText().toString();
        usersFirTblMozeh.FullName = nameET.getText().toString();

        usersFirTblMozeh.Phone = phoneET.getText().toString();
        usersFirTblMozeh.Notes = notesET.getText().toString();

    }

    private void startSignUpProcess() {

        ResultSuccessMozeh res = GlobalFunctionsMozeh.validateEmailSignUpForm(nameET.getText().toString(), emailET.getText().toString(), passwordET.getText().toString(), confirmPasswordET.getText().toString(), usersFirTblMozeh.ProfilePicUrl, usersFirTblMozeh.ImageName);

        if (res.Success) {

            getUIValues();
            usersFirTblMozeh.LoginType = "EMAIL";

            firebaseAuth.createUserWithEmailAndPassword(emailET.getText().toString().trim(), passwordET.getText().toString().trim()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        usersFirTblMozeh.Id = firebaseAuth.getCurrentUser().getUid();
                        usersFirDBMozeh.createOrUpdateUserInformationsFirebaseMozeh(usersFirTblMozeh);
                        SettingPrametersMozeh.setUserCredintial(getContext(), usersFirTblMozeh);
                        FirebaseAuthProcessMozeh.updateFirebaseUser(usersFirTblMozeh.FullName, usersFirTblMozeh.ProfilePicUrl);


                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
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




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        photoSelectionMethodMozeh.onActivityResult(requestCode, resultCode, data,new PhotoSelectionMethodMozeh.OnActivityListenerMozeh() {
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
      if (v.equals(choosePicButton)) {


          photoSelectionMethodMozeh.startPhotoLibs(imageView);
      }else if(v.equals(submitSignUpBtn)){

          startSignUpProcess();
      }


    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
