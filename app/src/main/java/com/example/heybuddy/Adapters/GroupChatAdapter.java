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
import com.example.heybuddy.GroupChatDetailActivity;
import com.example.heybuddy.Models.GroupChats;
import com.example.heybuddy.Models.Messages;
import com.example.heybuddy.Models.Users;
import com.example.heybuddy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatAdapter.viewHolder> {

    ArrayList<GroupChats> list;
    Context context;
    private static final String TAG = "GroupChatAdapter";
    public GroupChatAdapter(ArrayList<GroupChats> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_user_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        GroupChats users = list.get(position);
        if(users.getProfilepic()!=null){
            holder.imageView2.setVisibility(View.VISIBLE);
            Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.group).into(holder.imageView2);
        }else{
            holder.imageView.setVisibility(View.VISIBLE);
            Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.group).into(holder.imageView);
        }
        holder.userName.setText(users.getName().toString());
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(users.getLastmessageuserid()==null){
            holder.lastMessage.setText("");
        } else if(users.getLastmessageuserid().equals(auth.getUid())){
            holder.lastMessage.setText("You : "+users.getLastmessage().toString());
        } else{
            holder.lastMessage.setText(users.getUsername() +" : " +users.getLastmessage().toString());
        }
        if(users.getLastmessageuserid()== null){
            holder.lastMessage.setText("");
            holder.time.setText("");
        }

        // Yesterday Start Time
        Calendar startOfYesterdayDay = Calendar.getInstance();
        startOfYesterdayDay.add(Calendar.DATE, -1);
        startOfYesterdayDay.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
        startOfYesterdayDay.set(Calendar.MINUTE, 0);
        startOfYesterdayDay.set(Calendar.SECOND, 0);

        // Yesterday End TIme
        Calendar endOfYesterdayDay = Calendar.getInstance();
        endOfYesterdayDay.add(Calendar.DATE, -1);
        endOfYesterdayDay.set(Calendar.HOUR_OF_DAY, 23); //anything 0 - 23
        endOfYesterdayDay.set(Calendar.MINUTE, 59);
        endOfYesterdayDay.set(Calendar.SECOND, 59);


        if(users.getLastmessageuserid()!= null){
            if(endOfYesterdayDay.getTimeInMillis() > users.getTimestamp() && users.getTimestamp() > startOfYesterdayDay.getTimeInMillis()){
                holder.time.setText("Yesterday");
            } else if(users.getTimestamp() < startOfYesterdayDay.getTimeInMillis()){
                Date date1= new Date(users.getTimestamp());
                SimpleDateFormat formatTime1 = new SimpleDateFormat("dd-MM");
                String time2 = formatTime1.format(
                        date1);
                holder.time.setText(time2);
            } else{
                Date date = new Date(users.getTimestamp());
                SimpleDateFormat formatTime = new SimpleDateFormat("hh.mm aa");
                String time = formatTime.format(
                        date);
                holder.time.setText(time);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GroupChatDetailActivity.class);
                 intent.putExtra("groupid",users.getGroupId());
                 intent.putExtra("groupname",users.getName());
                 intent.putExtra("profilepic",users.getProfilepic());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Log.d("list", list.toString());
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getGroupId().equals(auth.getUid())) {
                    list.remove(i);
                }
            }
        }
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView imageView,imageView2;
        TextView userName, lastMessage, time;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profile_image);
            imageView2 = itemView.findViewById(R.id.profile_image2);
            userName = itemView.findViewById(R.id.username);
            lastMessage = itemView.findViewById(R.id.lastmessage);
            time = itemView.findViewById(R.id.time);
        }
    }
}
