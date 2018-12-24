package com.example.dhanam.wechat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity {


    private Button LoginButton, PhoneLoginButton;
    private EditText userEmail, userPassword;
    private TextView NeedNewAccount, ForgetPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();


        InitializeFields();

        NeedNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToRegister();


            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllowUserToLogin();

            }
        });

        PhoneLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginintent = new Intent(LoginActivity.this, PhoneLoginActivity.class);
                startActivity(loginintent);
            }
        });

    }

    private void ToRegister() {
        Intent toregister = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(toregister);

    }


    private void AllowUserToLogin() {

        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();


        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please enter your Email", LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your Password", LENGTH_SHORT).show();
        }

        else
        {
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                ToMain();
                                Toast.makeText(LoginActivity.this, "Login Successful", LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Error:" + message, LENGTH_SHORT).show();
                                loadingBar.dismiss();

                            }

                        }
                    });

        }

    }



    private void ToMain() {
        Intent tologin = new Intent(LoginActivity.this, MainActivity.class);
        tologin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(tologin);
        finish();
    }


    private void InitializeFields() {

        LoginButton = (Button)findViewById(R.id.login_button);
        PhoneLoginButton = (Button)findViewById(R.id.phone_login_button);
        userEmail = (EditText)findViewById(R.id.login_email);
        userPassword = (EditText)findViewById(R.id.login_password);
        NeedNewAccount = (TextView)findViewById(R.id.need_new_account_link);
        ForgetPassword = (TextView)findViewById(R.id.forget_password_link);
        loadingBar = new ProgressDialog(this);


    }


}
