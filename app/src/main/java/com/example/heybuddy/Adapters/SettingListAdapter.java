package com.example.heybuddy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heybuddy.Models.SettingModel;
import com.example.heybuddy.ProfileActivity;
import com.example.heybuddy.R;

import java.util.ArrayList;

public class SettingListAdapter extends RecyclerView.Adapter<SettingListAdapter.viewHolder> {


    ArrayList<SettingModel> settingModel;
    Context context;

    public SettingListAdapter(ArrayList<SettingModel> settingModel, Context context){
        this.settingModel = settingModel;
        this.context = context;

        SettingModel settingModel1 = new SettingModel("","Profile","Mayuresh");
        SettingModel settingModel2 = new SettingModel("","Profile","Mayuresh");
        SettingModel settingModel3 = new SettingModel("","Profile","Mayuresh");
        SettingModel settingModel4 = new SettingModel("","Profile","Mayuresh");
        this.settingModel.add(settingModel1);
        this.settingModel.add(settingModel2);
        this.settingModel.add(settingModel3);
        this.settingModel.add(settingModel4);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.setting_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        SettingModel sModel = settingModel.get(position);
        holder.settingName.setText(sModel.getSettingName());
        holder.settingDesc.setText(sModel.getSettingDescription());
    }

    @Override
    public int getItemCount() {
        return settingModel.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView settingName, settingDesc;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            settingName = itemView.findViewById(R.id.setting_name);
            settingDesc = itemView.findViewById(R.id.setting_desc);
        }
    }
}
