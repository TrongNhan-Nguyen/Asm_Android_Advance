package com.example.asm_firebase.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.asm_firebase.Fragment.Fragment_Schedule;
import com.example.asm_firebase.Fragment.Fragment_Transcript;

public class Adapter_TabCourse extends FragmentStatePagerAdapter {
    public Adapter_TabCourse(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Fragment_Schedule();
            case 1:
                return new Fragment_Transcript();
        }
        return null;

    }

    @Override
    public int getCount() {
        return 2;
    }
}
