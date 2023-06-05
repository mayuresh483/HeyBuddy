package com.example.heybuddy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heybuddy.ChatDetailActivity;
import com.example.heybuddy.Models.Users;
import com.example.heybuddy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder>{

    ArrayList<Users> list;
    Context context;

    public UserAdapter(ArrayList<Users> list,Context context){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_user_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Users users = list.get(position);
        Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.user).into(holder.imageView);
        holder.userName.setText(users.getUserName().toString());
        //TODO
//        holder.lastMessage.setText(users.getUserName().toString());
//        holder.time.setText(users.getUserName().toString());
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(context, ChatDetailActivity.class);
                 intent.putExtra("userid",users.getUserId());
                 intent.putExtra("username",users.getUserName());
                 intent.putExtra("profilepic",users.getProfilepic());
                 context.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Log.d("list",list.toString());
        if(list.size() != 0){
            for (int i = 0 ; i < list.size();i++){
                if(list.get(i).getUserId().equals(auth.getUid())){
                    list.remove(i);
                }
            }
        }
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView userName, lastMessage, time;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.username);
            lastMessage = itemView.findViewById(R.id.lastmessage);
            time = itemView.findViewById(R.id.time);
        }
    }
}
