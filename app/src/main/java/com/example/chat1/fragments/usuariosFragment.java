package com.example.chat1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chat1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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




        return view;
    }
}