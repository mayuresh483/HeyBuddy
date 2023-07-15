package com.example.heybuddy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heybuddy.ChatDetailActivity;
import com.example.heybuddy.CreateNewGroupActivity;
import com.example.heybuddy.Models.Users;
import com.example.heybuddy.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CreateGroupAdapter extends RecyclerView.Adapter<CreateGroupAdapter.viewHolder>{

    ArrayList<Users> list;
    Context context;
    boolean isSelectedMode = false;
    int participantSelectedCount = 0;
    ArrayList<Users> selectedData = new ArrayList<>();
    CreateNewGroupActivity createNewGroupActivity;

    public CreateGroupAdapter(ArrayList<Users> list, Context context){
        this.context = context;
        this.list = list;
        createNewGroupActivity = new CreateNewGroupActivity();
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
        if(users.getProfilepic()!=null){
            holder.imageView2.setVisibility(View.VISIBLE);
            Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.user).into(holder.imageView2);
        }else{
            holder.imageView.setVisibility(View.VISIBLE);
            Picasso.get().load((String) null).placeholder(R.drawable.user).into(holder.imageView);
        }
        holder.userName.setText(users.getUserName().toString());
        holder.lastMessage.setText(users.getUserStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectedMode = true;
                if(isSelectedMode){
                    if(selectedData.contains(list.get(holder.getAdapterPosition()))){
                        holder.countHolder.setVisibility(View.GONE);
                        participantSelectedCount--;
                        setSelectedMembers(participantSelectedCount);
                        selectedData.remove(list.get(holder.getAdapterPosition()));
                    } else{
                        holder.countHolder.setVisibility(View.VISIBLE);
                        participantSelectedCount++;
                        setSelectedMembers(participantSelectedCount);
                        selectedData.add(list.get(holder.getAdapterPosition()));
                    }

                    if(selectedData.size() == 0){
                        isSelectedMode = false;
                        holder.countHolder.setVisibility(View.GONE);
                        participantSelectedCount = 0;
                    }
                }
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

        ImageView imageView,imageView2;
        TextView userName, lastMessage, time, participantCount, addGroup;
        TextInputEditText groupName;
        LinearLayout countHolder;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            addGroup = itemView.findViewById(R.id.add_button);
            participantCount = itemView.findViewById(R.id.participant_selected_count);
            groupName = itemView.findViewById(R.id.groupname_field);
            imageView = itemView.findViewById(R.id.profile_image);
            imageView2 = itemView.findViewById(R.id.profile_image2);
            userName = itemView.findViewById(R.id.username);
            lastMessage = itemView.findViewById(R.id.lastmessage);
            time = itemView.findViewById(R.id.time);
            time.setVisibility(View.GONE);
            lastMessage.setVisibility(View.GONE);
            countHolder = itemView.findViewById(R.id.selectedcount);
        }
    }

    public int getSelectedMembers(){
        return participantSelectedCount;
    }

    public int getTotalMembers(){
        return list.size();
    }

    public void setSelectedMembers(int selectedMemberCount){
        participantSelectedCount = selectedMemberCount;
    }

    public ArrayList<Users> getSelectedUserData(){
        return selectedData;
    }
}
