package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private Button LogOut;
    private DatabaseReference databaseReference;
    private Button SaveInformation;
    private EditText editTextName, editTextAddress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        firebaseAuth= FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference= FirebaseDatabase.getInstance().getReference();
        editTextAddress= (EditText) findViewById(R.id.editTextAddress);
        editTextName= (EditText) findViewById(R.id.editTextName);
        SaveInformation= (Button) findViewById(R.id.SaveInformation);
        SaveInformation.setOnClickListener(this);

        FirebaseUser user= firebaseAuth.getCurrentUser();


        textViewUserEmail=(TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText("Welcome "+ user.getEmail());
        LogOut= (Button) findViewById(R.id.LogOut);

        LogOut.setOnClickListener(this);
    }

    private void SaveUserInformation(){

        String name= editTextName.getText().toString().trim();
        String address= editTextAddress.getText().toString().trim();

        UserInformation userInformation= new UserInformation(name, address);

        FirebaseUser user= firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this, "Information Saved!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {

        if(view==LogOut){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));

        }

        if(view==SaveInformation){
            SaveUserInformation();
        }

    }
}
