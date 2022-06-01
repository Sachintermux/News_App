package com.sna.newsapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sna.newsapp.R;

public class SignUpFragment extends Fragment {

    private EditText name_edtView, email_edtView, phoneNumber_edtView, password_edtView;
    private Button registerBtn;
    private CheckBox checkBox;
    private FirebaseAuth mAuth;
    private String TAG = "tag";

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        intiView(view);
        allClickHandle();
        return view;
    }

    private void intiView( View view ) {
        name_edtView = view.findViewById(R.id.nameEdtView_signUpFragment);
        email_edtView = view.findViewById(R.id.emailEdtView_signUpFragment);
        phoneNumber_edtView = view.findViewById(R.id.phoneNumber_EditView_signUpFragment);
        password_edtView = view.findViewById(R.id.passwordEdtView_signUpFragment);
        registerBtn = view.findViewById(R.id.registerBtn_signUpFragment);
        checkBox = view.findViewById(R.id.checkBox_signUpFragment);

        mAuth = FirebaseAuth.getInstance();
    }

    private void allClickHandle() {

        registerBtn.setOnClickListener(view -> {
            if (haveDataOrNot(name_edtView, "Please enter your name")
                    || haveDataOrNot(email_edtView, "Please enter your email")
                    || haveDataOrNot(phoneNumber_edtView, "please enter your mobile number"))
                return;
            if (!checkBox.isChecked()) {
                Toast.makeText(requireContext(), "Please click on agree button", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(email_edtView.getText().toString(), password_edtView.getText().toString())
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete( @NonNull Task<AuthResult> task ) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(requireContext(), "Now Login with your email and password", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {

                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(requireContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });


        });

    }

    private void updateUI( FirebaseUser user ) {
    }

    private boolean haveDataOrNot( EditText editText, String errorMessage ) {
        if (editText.getText().length() == 0) {
            editText.setError(errorMessage);
            return true;
        }
        return false;
    }
}