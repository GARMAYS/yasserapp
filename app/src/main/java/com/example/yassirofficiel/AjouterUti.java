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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AjouterUti extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edName,edEmail,edCin,edPass;
    private Button btn1;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_uti);
        mAuth=FirebaseAuth.getInstance();

        edName=findViewById(R.id.nomut);
        edEmail=findViewById(R.id.Email);
        edCin=findViewById(R.id.CIN);
        edPass=findViewById(R.id.pass);
        btn1=findViewById(R.id.btn_registre);
        progressBar=findViewById(R.id.progress);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


    }
    private void registerUser(){
        String name=edName.getText().toString().trim();
        String email=edEmail.getText().toString().trim();
        String cin=edCin.getText().toString().trim();
        String password=edPass.getText().toString().trim();

        if (name.isEmpty()){
            edName.setError("entrer name");
            edName.requestFocus();
            return;
        }
        if (email.isEmpty()){
            edEmail.setError("entrer email");
            edEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edEmail.setError("entrer email correct");
            edEmail.requestFocus();
            return;
        }
        if (cin.isEmpty()){
            edCin.setError("entrer CIN");
            edCin.requestFocus();
            return;
        }
        if (password.isEmpty()){
            edPass.setError("Password is required ");
            edPass.requestFocus();
            return;
        }
        if (password.length()<6){
            edPass.setError("entrer password correct ");
            edPass.requestFocus();
            return;
        }
        //////////////
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user=new User(name,email,cin);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(AjouterUti.this,"User has been registered successfully!",Toast.LENGTH_LONG).show();
                                        Intent iii=new Intent(AjouterUti.this,MainActivity.class);
                                        startActivity(iii);
                                        progressBar.setVisibility(View.GONE);
                                    }else{
                                        Toast.makeText(AjouterUti.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(AjouterUti.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
}
}