package com.example.chat1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chat1.R;
import com.example.chat1.pojos.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.viewHolderAdapter> {

    //el list va a ser de los usuarios que ya han iniciado sesion
    List<Users> usersList;
    Context context;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //creamos el constructor
    public AdapterUsuarios(List<Users> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_usuarios,parent,false);
        viewHolderAdapter holder = new viewHolderAdapter(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderAdapter holder, int position) {
        Users users = usersList.get(position);
        Glide.with(context).load(users.getFoto()).into(holder.img_user);

        //declarar el nombre de usuario
        holder.tv_usuario.setText(users.getNombre());
        //excluimos a los usuarios que esten en la aplicacion
        //para decir que si el usuario q esta recorriendo es igualal que hicimos login q lo oculte y sino que lo muestre
        if (users.getId().equals(user.getUid())){
            holder.cardView.setVisibility(View.GONE);
        }else{
            holder.cardView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        //cambiamor el return para q tome nuestros valores
        return usersList.size();
    }

    public class viewHolderAdapter extends RecyclerView.ViewHolder {

        // tomara los valores de row_usuarios
        TextView tv_usuario;
        ImageView img_user;
        CardView cardView;
        public viewHolderAdapter(@NonNull View itemView) {
            super(itemView);
            //inicializamos los valores
            tv_usuario = itemView.findViewById(R.id.tv_user);
            img_user=itemView.findViewById(R.id.img_user);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
