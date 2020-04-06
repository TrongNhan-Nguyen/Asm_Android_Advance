package com.example.asm_firebase.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;

import com.example.asm_firebase.Model.ReadRss;
import com.example.asm_firebase.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_News extends Fragment {
    private View view;
    private ReadRss readRss;
    private ListView listView;
    private ViewFlipper viewFlipper;
    private Animation slide_in, slide_out;

    public Fragment_News() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        listView = (ListView) view.findViewById(R.id.fNews_ListViewRss);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.fNews_VF);

        slide_in = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_in);
        slide_out = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_out);
        readRss = new ReadRss(getActivity(),listView);
        readRss.execute();
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
        return view;
    }
}
