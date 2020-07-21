package com.example.chat1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.zip.Inflater;

public class homeActivity extends AppCompatActivity {
    ImageView img_foto;
    TextView tv_usuario,tv_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        img_foto = findViewById(R.id.image_user);
        tv_usuario= findViewById(R.id.tv_usuario);
        tv_mail=findViewById(R.id.tv_mail);
        //llamando datos por medio de glide
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Glide.with(this).load(user.getPhotoUrl()).into(img_foto);
        tv_usuario.setText(user.getDisplayName());
        tv_mail.setText(user.getEmail());

        //mostrando datos del usuario


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