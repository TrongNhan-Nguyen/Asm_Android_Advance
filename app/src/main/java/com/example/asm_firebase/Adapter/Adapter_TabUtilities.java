package com.example.asm_firebase.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.asm_firebase.Fragment.Fragment_Maps;
import com.example.asm_firebase.Fragment.Fragment_MediaPlayer;
import com.example.asm_firebase.Fragment.Fragment_News;
import com.example.asm_firebase.Fragment.Fragment_Schedule;
import com.example.asm_firebase.Fragment.Fragment_Transcript;

public class Adapter_TabUtilities extends FragmentStatePagerAdapter {
    public Adapter_TabUtilities(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Fragment_Maps();
            case 1:
                return new Fragment_News();
            case 2:
                return new Fragment_MediaPlayer();
        }
        return null;

    }

    @Override
    public int getCount() {
        return 3;
    }
}
