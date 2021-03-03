package com.example.socialmediagamer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.socialmediagamer.R;
import com.example.socialmediagamer.models.User;
import com.example.socialmediagamer.providers.AuthProvider;
import com.example.socialmediagamer.providers.UserProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CompleteProfileActivity extends AppCompatActivity {

    TextInputEditText mTextInputUserName;
    Button mBtnConfirm;

    AuthProvider mAuthProvider;
    UserProvider mUserProvider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        mTextInputUserName = findViewById(R.id.textInputUserName);
        mBtnConfirm =  findViewById(R.id.btnRegister);

        mAuthProvider = new AuthProvider();
        mUserProvider = new UserProvider();

        mBtnConfirm.setOnClickListener(v -> register());
    }

    private void register() {
        String userName;

        userName = mTextInputUserName.getText().toString();

        if(!userName.isEmpty()){

            updateUser(userName);
        } else{

            Toast.makeText(this,"Para continuar inserta todos los campos",Toast.LENGTH_LONG).show();
        }
    }

    private void updateUser(String userName) {
        String id = mAuthProvider.getUUID();
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        User user = new User();
        user.setUserName(userName);
        user.setId(id);

        mUserProvider.update(user).addOnCompleteListener(task -> {

            if(task.isSuccessful()) {

                Intent intent = new Intent(CompleteProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {

                Toast.makeText(CompleteProfileActivity.this, "Usuario no se pudo almacenar en BD " , Toast.LENGTH_SHORT).show();
            }
        });
    }



}