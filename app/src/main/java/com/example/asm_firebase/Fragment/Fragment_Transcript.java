package com.example.asm_firebase.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
public class Fragment_Transcript extends Fragment {
    private View view;
    private DAO_Transcript daoTranscript;
    private ListView listView;
    private Adapter_ListView_Transcript adapterTranscript;

    public Fragment_Transcript() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transcript, container, false);
        daoTranscript = new DAO_Transcript(getActivity(),this);
        listView = view.findViewById(R.id.fTranscript_ListView);
        daoTranscript.getAll(new FireBaseCallback(){
            @Override
            public void listTranscript(List<Course> transcriptList) {
                Collections.sort(transcriptList, new Comparator<Course>() {
                    @Override
                    public int compare(Course o1, Course o2) {
                        return o1.getStatus().compareTo(o2.getStatus());
                    }
                });
                adapterTranscript = new Adapter_ListView_Transcript(getActivity(),transcriptList);
                listView.setAdapter(adapterTranscript);

            }
        });
        return view;
    }

    private void insert(){
//        daoTranscript.insert(new Course("SKI1013", "Ky Nang Hoc Tap", 8));
//        daoTranscript.insert(new Course("COM1024", "Tin Hoc Van Phong", 9));
//        daoTranscript.insert(new Course("COM1012", "Tin Hoc Co So", 8));
//        daoTranscript.insert(new Course("MUL1013", "Photoshop CS6", 9));
//        daoTranscript.insert(new Course("COM2012", "Co So Du Lieu", 8));
//        daoTranscript.insert(new Course("MOB1013", "Java 1", 9));
    }
}
