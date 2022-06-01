package com.sna.newsapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sna.newsapp.R;


public class LoginFragment extends Fragment {
    private static final int RC_SIGN_IN = 3;
    private ImageView googleSignIn_imv, facebookSignIn_imv;
    private Button loginBtn;
    private EditText email_edt, password_edt;
    private FirebaseAuth mAuth;
    private String TAG = "tag";
    private LoginData loginData;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initView(view);
        allClickHandle();
        return view;
    }

    private void initView( View view ) {
        googleSignIn_imv = view.findViewById(R.id.googleSingIn_imageV_loginFragment);
        facebookSignIn_imv = view.findViewById(R.id.faceBookSingIn_imageV_loginFragment);
        loginBtn = view.findViewById(R.id.loginBtn_loginFragment);
        email_edt = view.findViewById(R.id.emailEdtView_loginFragment);
        password_edt = view.findViewById(R.id.passwordEdtView_loginFragment);
        mAuth = FirebaseAuth.getInstance();
        loginData = (LoginData) requireActivity();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
//        signInRequest = BeginSignInRequest.builder()
//                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                        .setSupported(true)
//                        // Your server's client ID, not your Android client ID.
//                        .setServerClientId(getString(R.string.default_web_client_id))
//                        // Only show accounts previously used to sign in.
//                        .setFilterByAuthorizedAccounts(true)
//                        .build())
//                .build();


    }

    private void allClickHandle() {
        loginBtn.setOnClickListener(view -> {
            if (haveDataOrNot(email_edt, "Please enter you Email")
                    || haveDataOrNot(password_edt, "Please enter you Password")) return;
            mAuth.signInWithEmailAndPassword(email_edt.getText().toString(), password_edt.getText().toString())
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete( @NonNull Task<AuthResult> task ) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                Toast.makeText(requireContext(), "Sign in Successfully", Toast.LENGTH_SHORT).show();
                                loginData.Success();
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());

                                Toast.makeText(requireContext(), "Authentication failed." + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        googleSignIn_imv.setOnClickListener(view -> googleSignInClick());
        facebookSignIn_imv.setOnClickListener(view -> facebookSignInClick());
    }

    private void facebookSignInClick() {

    }

    private void googleSignInClick() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(requireContext(), "Google sign in failed " + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle( String idToken ) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( @NonNull Task<AuthResult> task ) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            loginData.Success();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(requireContext(), "Sign In with Google Failed, " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean haveDataOrNot( EditText editText, String errorMessage ) {
        if (editText.getText().length() == 0) {
            editText.setError(errorMessage);
            return true;
        }
        return false;
    }

    public interface LoginData {
        void Success();
    }
}