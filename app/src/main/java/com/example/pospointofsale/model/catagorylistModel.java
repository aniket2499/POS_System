package com.example.pospointofsale.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pospointofsale.objects.CatItem;

import java.util.List;

public class catagorylistModel extends ViewModel implements repository.onFirestoreTaskComplete{
    private repository firebaserepository = new repository(this);
    public MutableLiveData<List<CatItem>> CatagoryListmodel = new MutableLiveData<List<CatItem>>();

    public LiveData<List<CatItem>> getCatagoryListmodel() {
        return CatagoryListmodel;
    }

    public catagorylistModel(){
        firebaserepository.getCatgoryData();
    }
    @Override
    public void catagoriesDataAdded(List<CatItem> catagorylist) {
        CatagoryListmodel.setValue(catagorylist);
    }

    @Override
    public void onerror(Exception e) {

    }
}
