package com.example.pospointofsale.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pospointofsale.R;
import com.example.pospointofsale.objects.CatItem;
import com.example.pospointofsale.objects.Uploads;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;


public class add_cat_subcat extends AppCompatActivity {
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    ImageView mImageView;
    EditText catagoryname;
    TextView textView;
    TextView textView_save; //saving the data to database is left
    FirebaseStorage fb;
    FirebaseFirestore fd;
    EditText mEditTextFileName;
    private StorageReference mStorageRef;
    EditText catagorty_name;
    private Uri mImageUri;                       //for uploading in firebase storage
    private ProgressBar mProgressbar;
    public Uploads uploads;
    //final CatItem c1 = new CatItem("","","","");

    final int[] flag = new int[1];
    final String[] ImaURL = new String[1];// creating a new Catgory(item) object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cat_subcat);
        mImageView= findViewById(R.id.imageView);
        textView = (TextView)findViewById(R.id.btn_add);
        catagorty_name = findViewById(R.id.ed_username);
        mStorageRef = FirebaseStorage.getInstance().getReference();     // for sotring the image
        textView_save = (TextView)findViewById(R.id.btn_save);
        fb = FirebaseStorage.getInstance();               //for data base connection
        mProgressbar = findViewById(R.id.progress_circular);
        mProgressbar.setVisibility(View.GONE);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                                    // used for checking the runtime addmission
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        //permission not granted , request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show poopup for runtime permissions
                        requestPermissions(permissions,PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        pickImageFromGallery();
                    }
                }
                else{
                    //system os is less then marshmallow
                    pickImageFromGallery();
                }
            }
        });
        textView_save.setOnClickListener(view -> {
            database();

            mProgressbar.setVisibility(View.VISIBLE);
            catagoryname = findViewById(R.id.ed_username);
          //  String cat_name = catagoryname.getText().toString();
        });
    }

    private void database() {
        final CatItem c1 = new CatItem("","","","");

        mStorageRef = FirebaseStorage.getInstance().getReference();
        if (mImageUri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final StorageReference imageRef = storageReference.child("images/" + System.currentTimeMillis());
            final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            Uri file = mImageUri;
            UploadTask uploadTask = imageRef.putFile(file);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return imageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        System.out.println("Upload " + downloadUri);
                        //Toast.makeText(mActivity, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                        if (downloadUri != null) {
                            //String path = getIntent().getStringExtra("refrence");
                            //String[] a = path.split("/",2);
                            String catname = catagorty_name.getText().toString().trim();
                            String photoStringLink = downloadUri.toString(); //YOU WILL GET THE DOWNLOAD URL HERE !!!!
                            c1.setCatimg(photoStringLink);
                            c1.setCatname(catname);
                            c1.setId("");
                            c1.setCount("");
                            HashMap<String, CatItem> document = new HashMap<>();
                            document.put("detais", c1);
                            String doc = getIntent().getStringExtra("document");
                         //   Toast.makeText(add_cat_subcat.this, doc, Toast.LENGTH_SHORT).show();

                            fd = FirebaseFirestore.getInstance();
                            DocumentReference dr = fd.collection("companies").document(doc).collection("catagories").document();
                            dr.set(c1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void v) {
                                    //    Toast.makeText(add_cat_subcat.this, "you have saved the data", Toast.LENGTH_SHORT).show();
                                    String username = getIntent().getStringExtra("username").trim();
                                    String doc = getIntent().getStringExtra("document").trim();
                                    Intent i = new Intent(add_cat_subcat.this, home_new.class);
                                    i.putExtra("doc_name" , doc);
                                    i.putExtra("username" , username);
                                    startActivity(i);
                                    Toast.makeText(add_cat_subcat.this, "success", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }

    }

    private void pickImageFromGallery() {
        //intect to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent , IMAGE_PICK_CODE);
    }

    //handle result of runtime permission

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    pickImageFromGallery();
                }
                else {
                    //permission was denied
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            //set image to image view
            mImageView.setImageURI(data.getData());
            mImageUri = data.getData();
        }
    }
    private String getFileExtension(Uri uri){                  //to get the extension from image
        ContentResolver cR = getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri));
    }
}
