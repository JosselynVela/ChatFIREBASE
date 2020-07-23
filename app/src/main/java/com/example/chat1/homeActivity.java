package com.example.chat1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.chat1.adapters.PaginasAdapter;
import com.example.chat1.pojos.Users;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

public class homeActivity extends AppCompatActivity {
    //refrecnia al login para obtener informacion del usuario
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //hacemos la referencia para saber donde apunta FirebaseDatabase
    // le llamaremos a nuestro registro Users que tendra un hijo que sera el id del usuario que ya hizo login
    DatabaseReference ref_user=database.getReference("Users").child(user.getUid());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //DECLARAR VALORES
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new PaginasAdapter(this));

        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        //lo llamamos al tabloyout
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:{
                        tab.setText("Users");
                        tab.setIcon(R.drawable.ic_usuarios);
                        break;
                    }
                    case 1:{
                        tab.setText("Chats");
                        tab.setIcon(R.drawable.ic_chats);
                        break;
                    }
                    case 2:{
                        tab.setText("Solicitudes");
                        tab.setIcon(R.drawable.ic_solicitudes);
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(
                                ContextCompat.getColor(getApplicationContext(),R.color.colorAccent)
                        );
                        badgeDrawable.setVisible(true);
                        badgeDrawable.setNumber(1);
                        break;
                    }
                    case 3:{
                        tab.setText("Mis solicitudes");
                        tab.setIcon(R.drawable.ic_mis_solicitudes);
                        break;
                    }

                }

            }
        });

        tabLayoutMediator.attach();

        //me muestra el numero de solicitudes que recibo

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BadgeDrawable badgeDrawable=tabLayout.getTabAt(position).getOrCreateBadge();
                badgeDrawable.setVisible(false);
            }
        });


        //llamando datos por medio de glide
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //creamos un metodo

        userunico();
        //mostrando datos del usuario


    }

    //hacemos refrencia la bbd para crear un registro unico despues del login

    private void userunico() {

        //se debe usar una referencia unica para no crear bucles
        ref_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //debemos consultar
                if (!dataSnapshot.exists()){
                    Users uu = new Users(
                      //ponemos la forma que queremos estructurar la informacion y en q orden de los datos declarados en Users
                            //en este orden se vera en firebase
                      user.getUid(),
                      user.getDisplayName(),
                      user.getEmail(),
                      user.getPhotoUrl().toString(),
                      "desconectado",
                      "22/07/2020",
                      "23:39",
                      0,
                      0);

                    /*el ref_user toma todo el obeto de arriba y se lo pasa a la referencia*/
                    ref_user.setValue(uu);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_cerrar:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                finish();
                                Toast.makeText(homeActivity.this, "Cerrando sesion..",Toast.LENGTH_SHORT).show();
                                vamosalogin();
                            }
                        });

            Toast.makeText(this,"Cerrando Sesion", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
     //esta funcion hace que pueda redireccionarme a login cuando cierro sesion
    private void vamosalogin() {
        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |  Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity(i);
    }
}