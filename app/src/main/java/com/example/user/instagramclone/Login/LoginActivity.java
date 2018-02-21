package com.example.user.instagramclone.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.instagramclone.Home.MainActivity;
import com.example.user.instagramclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by user on 22/01/2018.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private Context mContext;
    private ProgressBar mProgressbar;
    private EditText mEmail, mPassword;
    private TextView mPlaseWait, mCrateAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mProgressbar = findViewById(R.id.progressBar);
        mPlaseWait = findViewById(R.id.pleaseWait);
        mEmail = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);
        mContext = LoginActivity.this;
        Log.d(TAG, "onCreate: stared.");

        mPlaseWait.setVisibility(View.GONE);
        mProgressbar.setVisibility(View.GONE);

        //Toast.makeText(mContext, "Hola", Toast.LENGTH_SHORT).show();
        setupFirebaseAuth();
        init();
    }

    private Boolean isStringNull(String string){
        Log.d(TAG, "isStringNull: checking string if null.");

        if (string.equals("")){
            return true;
        }else{
            return false;
        }

    }

    /**
     * -------------------------------------Firebase ----------------------------------
     */

    private void init(){
        //initialize the button for logging in
        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: attempting to log in");

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (isStringNull(email) && isStringNull(password)){
                    Toast.makeText(mContext, "You must fill out all the fields", Toast.LENGTH_LONG).show();
                }else{
                    mProgressbar.setVisibility(View.VISIBLE);
                    mPlaseWait.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    if (task.isSuccessful()) {
                                        /*// Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success login complete");
                                        Toast.makeText(LoginActivity.this, getString(R.string.sign_successs), Toast.LENGTH_SHORT).show();

                                        mProgressbar.setVisibility(View.GONE);
                                        mPlaseWait.setVisibility(View.GONE);
                                        //FirebaseUser user = mAuth.getCurrentUser();
                                        //updateUI(user);*/
                                        try{
                                            if (user.isEmailVerified()){
                                                Log.d(TAG, "onComplete: success. email is verified");
                                                Intent intent = new Intent(mContext,MainActivity.class);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(mContext, "Email is not verified \n check your email inbox.", Toast.LENGTH_LONG).show();
                                                mProgressbar.setVisibility(View.GONE);
                                                mPlaseWait.setVisibility(View.GONE);
                                                mAuth.signOut();
                                            }
                                        } catch (NullPointerException e){
                                            Log.e(TAG, "onComplete: NullPointerException: " + e.getMessage() );
                                        }
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();

                                        mProgressbar.setVisibility(View.GONE);
                                        mPlaseWait.setVisibility(View.GONE);
                                        //updateUI(null);
                                    }

                                    // ...
                                }
                            });
                }
            }
        });

        TextView linkSignUp = findViewById(R.id.link_signup);
        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: navigatinh to register screen");
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });
        /*
        if the user is logged in then navigation to MainActivity and call 'finish()'
         */
        if (mAuth.getCurrentUser() != null){
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Setup the firebase Auth Objects
     */

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null){
                    //User is Sign in
                    Log.d(TAG, "onAuthStateChanged: signed_in:"+ user.getUid());
                    //Toast.makeText(mContext, "Conectado", Toast.LENGTH_SHORT).show();
                }else{
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                    //Toast.makeText(mContext, "Desconectado", Toast.LENGTH_SHORT).show();
                }
                //...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

}
