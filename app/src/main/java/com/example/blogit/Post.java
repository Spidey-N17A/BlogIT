package com.example.blogit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.net.URL;


public class Post extends AppCompatActivity {
    private ImageButton mImage;
    private static final int GALLERY_REQUEST =1;
    private Button mSubmit;
    private TextView mTitle;
    private TextView mStory;
    private Uri ImageUri = null;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private ProgressDialog mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        mprogress= new ProgressDialog(this);
        mStorage= FirebaseStorage.getInstance().getReference();
        mStory=(TextView)findViewById(R.id.story);
        mTitle=(TextView)findViewById(R.id.title);
        mSubmit=(Button)findViewById(R.id.Submit_button);
        mImage=(ImageButton) findViewById(R.id.Image_Select) ;
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadingActivity();
            }
        });
    }

    private void UploadingActivity() {
        mprogress.setMessage("Uploading...");
        mprogress.show();
        final String title_Val=mTitle.getText().toString().trim();
        final String Story_Val=mStory.getText().toString().trim();
        if(!TextUtils.isEmpty(title_Val) && !TextUtils.isEmpty(Story_Val) && ImageUri!=null)
        {
            StorageReference filepath= mStorage.child("Blog_Image").child(ImageUri.getLastPathSegment());
            filepath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String downloadurl = taskSnapshot.getStorage().getDownloadUrl().toString();
                    DatabaseReference Post = mDatabase.push();
                    Post.child("title").setValue(title_Val);
                    Post.child("story").setValue(Story_Val);
                    Post.child("Image").setValue(downloadurl);
                    mprogress.dismiss();
                    startActivity(new Intent(Post.this,MainActivity.class));

                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,@NotNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== GALLERY_REQUEST && resultCode== RESULT_OK)
        {
            ImageUri = data.getData();
            mImage.setImageURI(ImageUri);
        }
    }
}
