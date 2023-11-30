package com.example.horizon.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.horizon.Activity.LoginActivity;
import com.example.horizon.Adapters.MyCartAdapter;
import com.example.horizon.Models.MyCartModel;
import com.example.horizon.R;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {

 FirebaseAuth auth;
 FirebaseFirestore db;

RecyclerView recyclerView;
MyCartAdapter myCartAdapter;
List<MyCartModel> cartModelList


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  view = inflater.inflate(R.layout.fragment_notification, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        auth = FirebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.cart_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cartModelList = new ArrayList<>();
        myCartAdapter = new MyCartAdapter(getActivity(),cartModelList);
        return view;
    }
}