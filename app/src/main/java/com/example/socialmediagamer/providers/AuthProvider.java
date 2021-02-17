package com.example.socialmediagamer.providers;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthProvider {

    private FirebaseAuth mAuth;

    // Constructor
    public AuthProvider() {

        mAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> login(String email, String password) {
        return mAuth.signInWithEmailAndPassword(email,password);
    }

    public Task<AuthResult> googleLogin (GoogleSignInAccount googleSignInAccount) {

        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        return mAuth.signInWithCredential(credential);
    }

    public String getUUID() {

        if(mAuth.getCurrentUser() != null) {

            return mAuth.getCurrentUser().getUid();
        }else{

            return null;
        }
    }

    public String getEmail() {
        if(mAuth.getCurrentUser() != null) {
            return mAuth.getCurrentUser().getEmail();
        }else{
            return null;
        }
    }

}
