package com.example.socialmediagamer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.socialmediagamer.R;
import com.example.socialmediagamer.models.User;
import com.example.socialmediagamer.providers.AuthProvider;
import com.example.socialmediagamer.providers.UserProvider;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {

    CircleImageView mCircleImageViewBack;
    TextInputEditText mTextInputUserName,mTextInputEmail,mTextInputPassword,mTextInputRepeatedPassword;
    Button mBtnRegister;

    AuthProvider mAuthProvider;
    UserProvider mUserProvider;
    AlertDialog mDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mCircleImageViewBack = findViewById(R.id.circle_back);
        mTextInputUserName = findViewById(R.id.textInputUserName);
        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputPassword = findViewById(R.id.textInputPassword);
        mTextInputRepeatedPassword = findViewById(R.id.textInputRepeatedPassword);
        mBtnRegister =  findViewById(R.id.btnRegister);

        mAuthProvider = new AuthProvider();
        mUserProvider = new UserProvider();

        mDialog =  new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Cargando...")
                .setCancelable(false)
                .build();


        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


        mCircleImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void register() {
        String userName, password, email, repeatedPassword;

        email    = mTextInputEmail.getText().toString();
        userName = mTextInputUserName.getText().toString();
        password = mTextInputPassword.getText().toString();
        repeatedPassword = mTextInputRepeatedPassword.getText().toString();

        if(!userName.isEmpty() && !email.isEmpty() && !password.isEmpty() && !repeatedPassword.isEmpty()){
            if (isEmailValid(email)){
                if(password.equals(repeatedPassword)) {
                    if(password.length() >= 6 ){

                        // Send email and pass to Firebase
                        createUser(email,password, userName);
                    }else {

                        Toast.makeText(this,"Las contraseña debe tener al menos 6 caracteres",Toast.LENGTH_LONG).show();
                    }
                } else{

                    Toast.makeText(this,"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
                }
            }else{

                Toast.makeText(this,"Has insertado todos los campos pero email no es valido",Toast.LENGTH_LONG).show();
            }
        } else{

            Toast.makeText(this,"Para continuar inserta todos los campos",Toast.LENGTH_LONG).show();
        }
    }

    private void createUser(String email, String password, String userName) {
        mDialog.show();

        mAuthProvider.register(email,password).addOnCompleteListener(task -> {


            if (task.isSuccessful()) {

                String id = mAuthProvider.getUUID();
                Map<String, Object> map = new HashMap<>();
                map.put("email", email);
                map.put("userName", userName);

                User user = new User();
                user.setId(id);
                user.setEmail(email);
                user.setUserName(userName);
                //user.setPassword(password);

                mUserProvider.create(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mDialog.dismiss();

                        if(task.isSuccessful()) {

                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {

                            Toast.makeText(RegisterActivity.this, "Usuario no se pudo almacenar en BD " , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {

                mDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "No se pude registrar el usuario " , Toast.LENGTH_SHORT).show();
            }
        });
    }


    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}