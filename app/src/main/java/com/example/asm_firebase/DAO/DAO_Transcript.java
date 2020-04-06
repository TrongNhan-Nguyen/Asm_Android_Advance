package com.example.asm_firebase.DAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm_firebase.Model.Course;
import com.example.asm_firebase.Model.FireBaseCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DAO_Transcript {
    private Context context;
    private Fragment fragment;
    private DatabaseReference dbTranscript;

    public DAO_Transcript(Context context) {
        this.context = context;
        this.dbTranscript = FirebaseDatabase.getInstance().getReference("transcript");
    }

    public DAO_Transcript(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.dbTranscript = FirebaseDatabase.getInstance().getReference("transcript");
    }

    public void insert(Course course){
        dbTranscript.child(course.getId()).setValue(course);

    }

    public void getAll(final FireBaseCallback fireBaseCallback){
        final List<Course> transcriptList = new ArrayList<>();
        dbTranscript.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                transcriptList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Course transcript = ds.getValue(Course.class);
                    transcriptList.add(transcript);
                }
                fireBaseCallback.listTranscript(transcriptList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void toast(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
