package com.example.yassirofficiel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class update extends AppCompatActivity {
   private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    EditText ed1,ed2,ed3;
    private Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ed1=findViewById(R.id.nomutu);
        ed2=findViewById(R.id.Emaill);
        ed3=findViewById(R.id.CINn);
        btn2=findViewById(R.id.update);
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("users");
        userId=user.getUid();
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);
                if(userProfile != null){
                    String name=userProfile.nom;
                    String email=userProfile.email;
                    String cin=userProfile.cin;
                    ed1.setText(name);
                    ed2.setText(email);
                    ed3.setText(cin);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(update.this,"error",Toast.LENGTH_SHORT).show();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(userId).child("nom").setValue(ed1.getText().toString());
                reference.child(userId).child("email").setValue(ed2.getText().toString());
                reference.child(userId).child("cin").setValue(ed3.getText().toString());
                Toast.makeText(update.this,"Update ",Toast.LENGTH_LONG).show();
            }
        });
    }
}