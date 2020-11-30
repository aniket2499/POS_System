package com.example.pospointofsale.activities;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.pospointofsale.R;
import com.example.pospointofsale.fragment.Catagory_fragment;
import com.example.pospointofsale.fragment.sub_catagory;

public class home_details extends AppCompatActivity implements Catagory_fragment.SendDataInterface {

    public static Object custPrograssbar;
    public static home_details homeActivity = null;
    FragmentManager fm =getSupportFragmentManager();

   // @BindView(R.id.fragment_frame)
    Fragment fragmentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Fragment fa = new Catagory_fragment();
       // FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
       fm.beginTransaction().add(R.id.fragment_frame,new Catagory_fragment(),null).commit();
        Bundle args = new Bundle();
        args.putInt("id", 1);
        args.putString("titel", "");

    }
    public static home_details getInstance() {
        return homeActivity;
    }


    public void callFragment(Fragment fragmentClass) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_frame , fragmentClass).addToBackStack("adds").commit();
        //drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void sendData(String a) {
        Fragment sc =new sub_catagory();
        Bundle bundle = new Bundle();
        bundle.putString("name",a);
        sc.setArguments(bundle);
        fm.beginTransaction().add(R.id.fragment_frame,new sub_catagory(),null).commit();
    }
}