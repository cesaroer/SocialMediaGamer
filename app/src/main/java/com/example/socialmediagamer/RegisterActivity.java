package com.example.socialmediagamer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity {

    CircleImageView mCircleImageViewBack;
    TextInputEditText mTextInputUserName,mTextInputEmail,mTextInputPassword,mTextInputRepeatedPassword;
    Button mBtnRegister;

    // Firebase
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;



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

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();


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

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", email);
                    map.put("userName", userName);

                    mFirestore.collection("Users").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()) {

                                Toast.makeText(RegisterActivity.this, "Usuario almacenado correctamente en DB " , Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(RegisterActivity.this, "Usuario no se pudo almacenar en BD " , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {

                    Toast.makeText(RegisterActivity.this, "No se pude registrar el usuario " , Toast.LENGTH_SHORT).show();
                }
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