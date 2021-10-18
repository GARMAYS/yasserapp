package com.example.yassirofficiel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
   private TextView register;
    private EditText editTextemail,editTextpassword;
    private ProgressBar progressBar;
    private Button btn1;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ///////////////
        register=findViewById(R.id.register);
        btn1=findViewById(R.id.btn_login);
        editTextemail=findViewById(R.id.email);
        editTextpassword=findViewById(R.id.password);
        progressBar=findViewById(R.id.progressBar);
        mAuth=FirebaseAuth.getInstance();
        ///////////////
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,AjouterUti.class);
                startActivity(i);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }
    private void userLogin(){
        String email=editTextemail.getText().toString().trim();
        String password=editTextpassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextemail.setError("Email is required !");
            editTextemail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editTextpassword.setError("Email is required !");
            editTextpassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextemail.setError("Please entert a valid email!");
            editTextemail.requestFocus();
            return;
        }
        if (password.length()<6){
            editTextpassword.setError("Min password length is 6 characters!");
            editTextpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent ii=new Intent(MainActivity.this,Dashbord.class);
                    startActivity(ii);
                }else{
                    Toast.makeText(MainActivity.this,"Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}