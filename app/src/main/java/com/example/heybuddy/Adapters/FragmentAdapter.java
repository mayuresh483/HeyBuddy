package com.example.heybuddy.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.heybuddy.fragments.NavNoteKeeperFragment;
import com.example.heybuddy.fragments.ChatFragment;
import com.example.heybuddy.fragments.GroupChatFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new NavNoteKeeperFragment();
            case 1:return new GroupChatFragment();
//            case 3:return new StatusFragment();
            default:return new ChatFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if(position == 0){
            title = "NOTES";
        }
        if(position == 1){
            title = "GROUP CHAT";
        }
        if(position == 2){
            title = "CHATS";
        }
//        if(position == 3){
//            title = "STATUS";
//        }
        return title;
    }
}
