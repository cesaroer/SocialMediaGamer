package com.example.socialmediagamer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    CircleImageView mCircleImageViewBack;
    TextInputEditText mTextInputUserName,mTextInputEmail,mTextInputPassword,mTextInputRepeatedPassword;
    Button mBtnRegister;


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
                Toast.makeText(this,"Insertaste todos los camposEmail es valido",Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(this,"Has insertado todos los campos pero email no es valido",Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(this,"Para continuar inserta todos los campos",Toast.LENGTH_LONG).show();
        }
    }


    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}