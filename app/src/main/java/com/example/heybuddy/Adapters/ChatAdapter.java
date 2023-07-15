package com.example.heybuddy.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heybuddy.Models.GroupChats;
import com.example.heybuddy.Models.Messages;
import com.example.heybuddy.Models.Users;
import com.example.heybuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<Messages> messages;
    Context context;
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;
    int RECEIVER_GROUP_VIEW_TYPE = 3;
    ArrayList<Users> tempUser;

    public ChatAdapter(ArrayList<Messages> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SENDER_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender_chat_bubble, parent, false);
            return new SenderViewHolder(view);
        } else if (viewType == RECEIVER_GROUP_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_groupchat_reciver_chat_bubble, parent, false);
            return new ReceiverGroupChatViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciver_chat_bubble, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Messages message = messages.get(position);

        if (holder.getClass() == SenderViewHolder.class) {
            ((SenderViewHolder) holder).senderMessage.setText(message.getMessage());
            Date date = new Date(message.getTimestamp());
            SimpleDateFormat formatTime = new SimpleDateFormat("hh.mm aa");
            String time = formatTime.format(
                    date);
            ((SenderViewHolder) holder).senderTime.setText(time);
        } else if (holder.getClass() == ReceiverGroupChatViewHolder.class) {
            ((ReceiverGroupChatViewHolder) holder).reciverMessage.setText(message.getMessage());
            ((ReceiverGroupChatViewHolder) holder).userName.setText(message.getUsername());
        } else {
            ((ReceiverViewHolder) holder).reciverMessage.setText(message.getMessage());
            Date date = new Date(message.getTimestamp());
            SimpleDateFormat formatTime = new SimpleDateFormat("hh.mm aa");
            String time = formatTime.format(
                    date);
            ((ReceiverViewHolder) holder).reciverTime.setText(time);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getUserId().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE;
        } else if (messages.get(position).getGroupId() != null) {
            return RECEIVER_GROUP_VIEW_TYPE;
        } else {
            return RECEIVER_VIEW_TYPE;
        }

    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView reciverMessage, reciverTime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            reciverMessage = itemView.findViewById(R.id.reciver_message);
            reciverTime = itemView.findViewById(R.id.reciver_time);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView senderMessage, senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.sender_message);
            senderTime = itemView.findViewById(R.id.sender_time);
        }
    }

    public class ReceiverGroupChatViewHolder extends RecyclerView.ViewHolder {
        TextView reciverMessage, reciverTime, userName;

        public ReceiverGroupChatViewHolder(@NonNull View itemView) {
            super(itemView);
            reciverMessage = itemView.findViewById(R.id.groupchat_message);
            reciverTime = itemView.findViewById(R.id.reciver_groupchat_time);
            userName = itemView.findViewById(R.id.groupchat_usename);
        }
    }
}
