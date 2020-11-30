package com.example.pospointofsale.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.pospointofsale.R;
import com.example.pospointofsale.activities.ordersActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class dialogbox extends AppCompatDialogFragment {
    private EditText price ;
    private TextView quant_available;
    public static dialogbox newInstance(String msg , String path , String doc){
        dialogbox da = new dialogbox();
        Bundle bundle = new Bundle();
        bundle.putString("new_price",msg);
        bundle.putString("path",path);
        bundle.putString("doc",doc);
        da.setArguments(bundle);

        return da;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_new_quantity,null);
        price = view.findViewById(R.id.ed_quantity);
        quant_available = view.findViewById(R.id.ed_maxquantity);
        builder.setView(view)
                .setTitle("edit quantity")
                .setNegativeButton("cancel " ,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String ed_path = getArguments().getString("path");
                        String [] a = ed_path.split("/",4);
                        FirebaseFirestore fb = FirebaseFirestore.getInstance();
                        fb.collection(a[0]).document(a[1]).collection(a[2]).document(a[3]).update("mitemnewquantity",price.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            //  Toast.makeText(ordersActivity.this, "saved successfuly", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                             //   Toast.makeText(item_details.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        });
                        String doc = getArguments().getString("doc");
                        ((ordersActivity)getActivity()).update(doc);
                    }
                });
    price.setText("0");
    quant_available.setText("quantity available: " + getArguments().getString("new_price"));
    return builder.create();
    }
}
