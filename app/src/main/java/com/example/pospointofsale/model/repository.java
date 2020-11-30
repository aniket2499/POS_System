package com.example.pospointofsale.model;

import androidx.annotation.NonNull;

import com.example.pospointofsale.objects.CatItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class repository {
   private onFirestoreTaskComplete onFirestoreTaskComplete;

   FirebaseFirestore fb = FirebaseFirestore.getInstance();
    SessionManager sessionManager;
   public repository(onFirestoreTaskComplete onFirestoreTaskComplete){
       this.onFirestoreTaskComplete = onFirestoreTaskComplete;
   }
   private CollectionReference cr = fb.collection("companies").document("aniketpadsala@gmail.com").collection("catagories");
   public void getCatgoryData(){
       cr.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    onFirestoreTaskComplete.catagoriesDataAdded(task.getResult().toObjects(CatItem.class));
                }else {

                }
           }
       });
   }
   public interface onFirestoreTaskComplete{
       void catagoriesDataAdded(List<CatItem> catagorylist);
       void onerror(Exception e);
   }

}
