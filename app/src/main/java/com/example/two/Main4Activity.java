package com.example.two;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

public class Main4Activity extends AppCompatActivity {
    ImageView imageView;
    public static final int GELLRY_PIC = 1;
    StorageReference storageReference;
    DatabaseReference databaseReference, databaseReference1;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String s5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                              WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView( R.layout.activity_main4 );
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        s5 = firebaseUser.getPhoneNumber();
        databaseReference = FirebaseDatabase.getInstance().getReference().child( "Userinfo" ).child( s5 );
        imageView = findViewById( R.id.button );
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child( "Userinfo" ).child( s5 );
        showImage();
        imageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gatllryIntent = new Intent();
                gatllryIntent.setType( "image/*" );
                gatllryIntent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( gatllryIntent, "SELECT IMAGE" ), GELLRY_PIC );
            }
        } );
    }

    private void showImage() {
        databaseReference1.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s6 = dataSnapshot.child( "image" ).getValue().toString();
                Picasso.get().load( s6 ).into( imageView );
                Toast.makeText( Main4Activity.this, "Image has been set", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( Main4Activity.this, "Image has not been set", Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == RESULT_OK && requestCode == GELLRY_PIC) {
            Uri uri = data.getData();
            CropImage.activity( uri ).setAspectRatio( 1, 1 )
                    .setMinCropWindowSize( 500, 500 )
                    .start( this );
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult( data );
            if (resultCode == RESULT_OK) {
                Uri cropedimage = result.getUri();
                final StorageReference firebaseStorage = storageReference.child( "deepak_kumar" ).child( s5 + ".png" );
                firebaseStorage.putFile( cropedimage ).addOnCompleteListener( new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            firebaseStorage.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String s4 = uri.toString();
                                    imageurl( s4 );
                                }
                            } );
                        } else {
                            Toast.makeText( getApplicationContext(), "Image not uploaded", Toast.LENGTH_LONG ).show();
                        }
                    }
                } );
            }
        }
    }

    private void imageurl(String s4) {
        databaseReference.child( "image" ).setValue( s4 ).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText( getApplicationContext(),
                                    "Image uploaded successfully", Toast.LENGTH_LONG ).show();
                } else {
                    Toast.makeText( getApplicationContext(),
                                    "Image not uploaded", Toast.LENGTH_LONG ).show();
                }
            }
        } );
    }
}