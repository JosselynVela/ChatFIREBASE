package com.example.chat1.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chat1.R;
import com.example.chat1.adapters.AdapterUsuarios;
import com.example.chat1.pojos.Users;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class usuariosFragment extends Fragment {

    public usuariosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //lalamado a la bdd
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        //hacemos referencia a la imagen y el texto del xml usuarios
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);

        TextView tv_user=view.findViewById(R.id.tv_user);
        ImageView img_user=view.findViewById(R.id.img_user);

        // llenaremos con un valor dinamico para mostrar

        assert user != null;
        tv_user.setText(user.getDisplayName());
        //mostraremos la imagen con glide
        Glide.with(this).load(user.getPhotoUrl()).into(img_user);

        RecyclerView rv;
        final ArrayList<Users>usersArrayList;
        final   AdapterUsuarios adapter;
        LinearLayoutManager mLayoutManager;

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rv = view.findViewById (R.id.rv);
        rv.setLayoutManager(mLayoutManager);

        usersArrayList = new ArrayList<>();
        adapter = new AdapterUsuarios(usersArrayList,getContext());

        rv.setAdapter(adapter);

        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference myref=database.getReference("Users");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    // hacemmos para que si un cambio se hizo en tiempo real no se vuelva a mostrar
                    usersArrayList.removeAll(usersArrayList);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Users user = snapshot.getValue(Users.class);
                        usersArrayList.add(user);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(),"No existe usuario", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        return view;
    }
}