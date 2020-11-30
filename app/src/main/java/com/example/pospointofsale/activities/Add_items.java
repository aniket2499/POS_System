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
import com.example.pospointofsale.objects.Item_details;
import com.example.pospointofsale.objects.Uploads;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;


public class Add_items extends AppCompatActivity {
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    ImageView mImageView;
    EditText itemname;
    EditText itemprice;
    EditText itembarcode;
    EditText itemmeasurement_unit;
    EditText itemquantity;
    TextView textView;
    TextView textView_save; //saving the data to database is left
    FirebaseStorage fb;
    FirebaseFirestore fd;
    EditText mEditTextFileName;
    private StorageReference mStorageRef;
    private Uri mImageUri;                       //for uploading in firebase storage
    private ProgressBar mProgressbar;
    public Uploads uploads;
    //final CatItem c1 = new CatItem("","","","");

    final int[] flag = new int[1];
    final String[] ImaURL = new String[1];// creating a new Catgory(item) object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        mImageView= findViewById(R.id.imageView);
        itemname = findViewById(R.id.ed_itemname);
        itemprice = findViewById(R.id.ed_itemprice);
        itembarcode = findViewById(R.id.ed_itembarcode);
        itemquantity = findViewById(R.id.ed_itemquantity);
        itemmeasurement_unit = findViewById(R.id.ed_itemmunit);
        textView = (TextView)findViewById(R.id.btn_add);

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
            //catagoryname = findViewById(R.id.ed_username);
            //  String cat_name = catagoryname.getText().toString();
        });
    }

    private void database() {
        final Item_details i1 = new Item_details();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        if (mImageUri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final StorageReference imageRef = storageReference.child("images/" + System.currentTimeMillis());
            //final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
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
                            String photoStringLink = downloadUri.toString(); //YOU WILL GET THE DOWNLOAD URL HERE !!!!
                            i1.setMitemimage(photoStringLink);
                            String name = itemname.getText().toString();
                            i1.setMitemname(name);
                            String quantity = itemquantity.getText().toString();
                            i1.setMitemquantity(quantity);
                            String price = itemprice.getText().toString();
                            i1.setMitemprice(price);
                            String mes_unit = itemmeasurement_unit.getText().toString();
                            i1.setMitemunit(mes_unit);
                            String barcode = itembarcode.getText().toString();
                            i1.setMitembarcode(barcode);
                            String path = getIntent().getStringExtra("refrence");
                            String[] a = path.split("/",6);
                            String doc = "aniketpadsala@gmail.com";
                        //    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        //    DatabaseReference reference = firebaseDatabase.getReference().child("users").child("jenish").child("item_details").child(barcode);
                         //   Order_items oi = new Order_items("0","0",price,path,name);
                            fd = FirebaseFirestore.getInstance();
                            DocumentReference dr = fd.collection(a[0]).document(a[1]).collection(a[2]).document(a[3]).collection(a[4]).document(a[5]).collection("items_details").document();
                            dr.set(i1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void v) {
                                    //    Toast.makeText(add_cat_subcat.this, "you have saved the data", Toast.LENGTH_SHORT).show();
                                    String path = getIntent().getStringExtra("refrence");
                                    String username = getIntent().getStringExtra("username").trim();
                                    //Intent i = new Intent(Add_items.this, items_list.class);
                                    //i.putExtra("refrence",path);
                                    //i.putExtra("username",username);
                                    //startActivity(i);
                                    Toast.makeText(Add_items.this, "success", Toast.LENGTH_SHORT).show();
                                    String path2 = dr.getPath();
                                    String price = itemprice.getText().toString().trim();
                                    String quantity = itemquantity.getText().toString().trim();
                                    String name = itemname.getText().toString().trim();
                                    String barcode = itembarcode.getText().toString().trim();
                                    String image = i1.getMitemimage();                                       // adding image
                                    Map<String, String> hm = new HashMap<>();
                                    hm.put("name" , name);
                                    hm.put("price" , price);
                                    hm.put("quantity",quantity);
                                    hm.put("path" , path2);
                                    hm.put("barcode" , barcode);
                                    hm.put("user", username);
                                    hm.put("image" , image);                                                // image
                                    FirebaseDatabase fd = FirebaseDatabase.getInstance();
                                    DatabaseReference dr = fd.getReference().child("users").child(username).child("itemdetails").child(barcode);
                                    dr.setValue(hm);
                                    Intent i = new Intent(Add_items.this, items_list.class);
                                    i.putExtra("refrence",path);
                                    i.putExtra("username",username);
                                    startActivity(i);
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