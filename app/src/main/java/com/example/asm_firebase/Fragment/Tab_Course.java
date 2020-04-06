package com.example.asm_firebase.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.asm_firebase.Adapter.Adapter_TabCourse;
import com.example.asm_firebase.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab_Course extends Fragment {
    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Adapter_TabCourse adapter;

    public Tab_Course() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.tab_course, container, false);
        tabLayout = view.findViewById(R.id.fTabCourse_TabLayout);
        viewPager = view.findViewById(R.id.fTabCourse_Viewpager);
        adapter = new Adapter_TabCourse(getChildFragmentManager(),1);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.schedule);
        tabLayout.getTabAt(1).setIcon(R.drawable.transcript);
        return view;
    }
}
