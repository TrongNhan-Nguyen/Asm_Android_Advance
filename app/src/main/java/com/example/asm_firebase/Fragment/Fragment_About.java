package com.example.asm_firebase.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.asm_firebase.Adapter.Adapter_ListView_Transcript;
import com.example.asm_firebase.DAO.DAO_Transcript;
import com.example.asm_firebase.Model.Course;
import com.example.asm_firebase.Model.FireBaseCallback;
import com.example.asm_firebase.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_About extends Fragment {
    private View view;
    private TextView txtInfo, txtInfoDetail, txtGuide, txtGuideDetail, txtPrivacy;
    private TextView txtPrivacyDetail, txtRelease, txtReleaseDetail;
    public Fragment_About() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about, container, false);
        initView();
        return view;
    }
    private void initView() {
        txtInfo = (TextView) view.findViewById(R.id.fAbout_Info);
        txtGuide = (TextView) view.findViewById(R.id.fAbout_Guide);
        txtPrivacy = (TextView) view.findViewById(R.id.fAbout_Privacy);
        txtRelease = (TextView) view.findViewById(R.id.fAbout_Release);
        txtInfoDetail = (TextView) view.findViewById(R.id.fAbout_InfoDetail);
        txtGuideDetail = (TextView) view.findViewById(R.id.fAbout_GuideDetail);
        txtPrivacyDetail = (TextView) view.findViewById(R.id.fAbout_PrivacyDetail);
        txtReleaseDetail = (TextView) view.findViewById(R.id.fAbout_ReleaseDetail);

        hideDetail();

        txtInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDetail(txtInfoDetail);
            }
        });


        txtGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDetail(txtGuideDetail);
            }
        });


        txtPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDetail(txtPrivacyDetail);
            }
        });


        txtRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDetail(txtReleaseDetail);
            }
        });
    }

    private void toggleDetail(View view){
        if (view.getVisibility() == view.VISIBLE){
            view.setVisibility(view.GONE);
        }else {
            view.setVisibility(view.VISIBLE);
        }
    }

    public void hideDetail(){
        txtInfoDetail.setVisibility(TextView.GONE);
        txtGuideDetail.setVisibility(TextView.GONE);
        txtPrivacyDetail.setVisibility(TextView.GONE);
        txtReleaseDetail.setVisibility(TextView.GONE);
    }

}
