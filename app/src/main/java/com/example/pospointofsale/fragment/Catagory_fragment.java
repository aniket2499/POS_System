package com.example.pospointofsale.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pospointofsale.R;
import com.example.pospointofsale.activities.add_cat_subcat;
import com.example.pospointofsale.activities.sub_cat;
import com.example.pospointofsale.adapter.CategoryAdp;
import com.example.pospointofsale.model.catagorylistModel;
import com.example.pospointofsale.objects.CatItem;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Catagory_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Catagory_fragment extends Fragment implements CategoryAdp.RecyclerTouchListener {
    private static final String TAG = Catagory_fragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    ArrayList<CatItem> dataholder;
    CategoryAdp adapter;
    TextView add_catagory;
    catagorylistModel CatagoryListmodel;
    TextView mDelete;

    SendDataInterface sendDataInterface;

    public  interface  SendDataInterface
    {
        public void sendData(String a);
    }

    private FirebaseFirestore fb;
    public Catagory_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment Catagory_fragment.
     */
    // TODO: Rename and change types and number of parameters
   /* public static Catagory_fragment newInstance(String param1, String param2) {
        Catagory_fragment fragment = new Catagory_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
   }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            adapter = new CategoryAdp(getActivity(), dataholder, this);
        }
    }

    @Override
    public void onClickItem(String titel) {
       Bundle args = new Bundle();
       args.putString("titel" , titel);
      // sub_cat s1 = (sub_cat) getActivity();
      // s1.add_data(args);
        Fragment fragment = new sub_catagory();
        //fragment.setArguments(args);
        if(fragment != null){
            Log.d(TAG,titel);
            Intent i = new Intent(getActivity(), sub_cat.class);
            startActivity(i);
        }
       else{
           return;
        }
      //Intent i = new Intent(getActivity(), sub_cat.class);
      //i.putExtra("args",titel);
      // startActivity(i);
    }

    @Override
    public void onLongClickItem(View v, int position) {
    //    Intent i = new Intent(getActivity(), add_cat_subcat.class);
    //    startActivity(i);
    }

    @Override
    public void onDeleteClick(int position) {
        dataholder.remove(position);
        adapter.notifyItemRemoved(position);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_catagory_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        dataholder = new ArrayList<>();
        fb = FirebaseFirestore.getInstance();
        DocumentReference dr = fb.collection("compainies").document("aniketpadsala@gmail.com").collection("catagories").document("3sqmdCmjKBZErBLQc1eD ");
        adapter = new CategoryAdp(getActivity(),dataholder,this);
        recyclerView.setAdapter(adapter);
        mDelete = view.findViewById(R.id.image_delete);
        add_catagory = (TextView)view.findViewById(R.id.add_new);

        add_catagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), add_cat_subcat.class);
                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CatagoryListmodel =new ViewModelProvider(getActivity()).get(catagorylistModel.class);
        CatagoryListmodel.getCatagoryListmodel().observe(getViewLifecycleOwner(), new Observer<List<CatItem>>() {
            @Override
            public void onChanged(List<CatItem> catItems) {
                adapter.setCatagorylistmodel(catItems);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;
        try {
           // sendDataInterface = (SendDataInterface) activity;
        }catch (RuntimeException a){
           // throw new RuntimeException(activity.toString());
        }
    }
}