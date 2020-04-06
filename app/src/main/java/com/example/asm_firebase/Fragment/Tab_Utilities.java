package com.example.asm_firebase.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.asm_firebase.Adapter.Adapter_TabCourse;
import com.example.asm_firebase.Adapter.Adapter_TabUtilities;
import com.example.asm_firebase.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab_Utilities extends Fragment {
    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Adapter_TabUtilities adapter;

    public Tab_Utilities() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.tab_utilities, container, false);
        tabLayout = view.findViewById(R.id.fTabUtilities_TabLayout);
        viewPager = view.findViewById(R.id.fTabUtilities_Viewpager);
        adapter = new Adapter_TabUtilities(getChildFragmentManager(),1);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.maps);
        tabLayout.getTabAt(1).setIcon(R.drawable.news);
        tabLayout.getTabAt(2).setIcon(R.drawable.media);
        return view;
    }
}
