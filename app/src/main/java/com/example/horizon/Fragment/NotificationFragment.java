package com.example.horizon.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.horizon.Activity.MainActivity;
import com.example.horizon.Adapters.MyCartAdapter;
import com.example.horizon.Models.MyCartModel;
import com.example.horizon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {

    // this should be cart fragment but i afraid if i change it to name cart fragment
    // it will not gonna work, so i just named it cart notification. Dont get wrong boi

 FirebaseAuth auth;
 FirebaseFirestore db;

RecyclerView cartList;

List<MyCartModel> cartModelList;


private MyCartAdapter cartAdapter;

private ImageView back_button;

public void MyCartsFragment() {

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  view = inflater.inflate(R.layout.fragment_notification, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        back_button  = view.findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });




        cartList = view.findViewById(R.id.cart_list);
        cartList.setLayoutManager(new LinearLayoutManager(getContext()));
        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getActivity(), cartModelList);
        cartList.setAdapter(cartAdapter);

       db.collection("addToCart").document(auth.getCurrentUser().getUid())
               .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {


                       if(task.isSuccessful()){
                         for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                             MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                             cartModelList.add(cartModel);
                             cartAdapter.notifyDataSetChanged();
                         }
                       }
                       else {
                           Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   }
               });



        return view;
    }
}