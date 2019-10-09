package com.example.two;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {
    Button b3, b4;
    EditText ed1, ed2, ed3, ed4, ed5, ed6, ed7;
    FirebaseAuth fa;
    DatabaseReference dr;
    HashMap<String, String> data = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main2);
        fa = FirebaseAuth.getInstance();
        final FirebaseUser fu = fa.getCurrentUser();
        String phoneNumber = fu.getPhoneNumber();
        dr = FirebaseDatabase.getInstance().getReference
                ().child("Userinfo").child(phoneNumber);

        ConstraintLayout constraintLayout = findViewById(R.id.lay);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        b3 = findViewById(R.id.logout);
        b4 = findViewById(R.id.done);
        ed1 = findViewById(R.id.editText3);
        ed2 = findViewById(R.id.editText);
        ed3 = findViewById(R.id.editText6);

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent in = new Intent(Main2Activity.this, MainActivity.class);
                Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_LONG).show();
                startActivity(in);
                finish();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = ed1.getText().toString();
                String s2 = ed2.getText().toString();
                String s3 = ed3.getText().toString();

                if (s1.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_LONG).show();
                } else if (s2.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_LONG).show();
                } else if (s3.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_LONG).show();
                } else {
                    data.put("name", s1);
                    data.put("email", s2);
                    data.put("password", s3);
                    data.put("image", "img");

                    dataEntry();
                }
            }
        });
    }

    public void dataEntry() {
        dr.setValue(data).addOnCompleteListener
                (new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent in = new Intent(Main2Activity.this, Main3Activity.class);
                    Toast.makeText(getApplicationContext(), "Data sent to Firebase", Toast.LENGTH_LONG).show();
                    startActivity(in);
                } else {
                    Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}