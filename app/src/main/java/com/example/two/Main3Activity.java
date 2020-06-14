package com.example.two;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main3Activity extends AppCompatActivity {
    ListView lv;
    FirebaseAuth fa1;
    FirebaseUser fbu;
    DatabaseReference dr1;
    String phoneNumber;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        fa1 = FirebaseAuth.getInstance();
        fbu = fa1.getCurrentUser();
        phoneNumber = fbu.getPhoneNumber();
        dr1 = FirebaseDatabase.getInstance().getReference().child("Userinfo");
        lv = findViewById(R.id.lv);
        btn1 = findViewById(R.id.btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity.this, Main4Activity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        dr1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ListAdopter listAdopter = new ListAdopter
                        (Main3Activity.this, R.layout.listitem);
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Listmodel listmodel = dataSnapshot1.getValue(Listmodel.class);
                    listAdopter.add(listmodel);
                }
                lv.setAdapter(listAdopter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Connection failed", Toast.LENGTH_LONG).show();
            }
        });

    }
}