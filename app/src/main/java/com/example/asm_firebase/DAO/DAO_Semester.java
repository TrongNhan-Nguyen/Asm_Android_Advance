package com.example.asm_firebase.DAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.asm_firebase.Model.Course;
import com.example.asm_firebase.Model.FireBaseCallback;
import com.example.asm_firebase.Model.Semester;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAO_Semester {
    Context context;
    Fragment fragment;
    DatabaseReference dbSemester;

    public DAO_Semester(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.dbSemester = FirebaseDatabase.getInstance().getReference("semesters");
    }

    public void getAll(final FireBaseCallback fireBaseCallback){
        final List<Semester> semesterList = new ArrayList<>();
        final List<Course> courseList = new ArrayList<>();
        dbSemester.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                semesterList.clear();
                courseList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    for (DataSnapshot ds1: ds.getChildren()){
                        Course course = ds1.getValue(Course.class);
                        courseList.add(course);
                    }
                    semesterList.add(new Semester(ds.getKey()));

                }
                fireBaseCallback.listSemesters(semesterList);
                fireBaseCallback.listCourse(courseList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void toast(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
    public void insert(){
//        DatabaseReference db = dbSemester.child("SPRING 2019");
//        Map<String,Object> taskMap = new HashMap<>();
//
//        taskMap.put("SKI1013", new Course("SKI1013","Ky Nang Hoc Tap","SPRING 2019"));
//        taskMap.put("COM1024", new Course("COM1024","Tin Hoc Van Phong","SPRING 2019"));
//        taskMap.put("COM1012", new Course("COM1012","Tin Hoc Co So","SPRING 2019"));
//        taskMap.put("MUL1013", new Course("MUL1013","Photoshop CS6","SPRING 2019"));

//        taskMap.put("COM2012", new Course("COM2012","Co So Du Lieu","SUMMER 2019"));
//        taskMap.put("WEB1013", new Course("WEB1013","Xay Dung Trang Web","SUMMER 2019"));
//        taskMap.put("MOB1013", new Course("MOB1013","Java 1","SUMMER 2019"));
//        taskMap.put("WEB1042", new Course("WEB1042","Javascript Co Ban","SUMMER 2019"));

//        taskMap.put("MOB1022", new Course("MOB1022","Java 2","FALL 2019"));
//        taskMap.put("MOB1032", new Course("MOB1032","Android Co Ban","FALL 2019"));
//        taskMap.put("WEB3022", new Course("WEB3022","HTML5-CSS3","FALL 2019"));
//        taskMap.put("MOB202", new Course("MOB202","Giao Dien Android","FALL 2019"));

//        taskMap.put("MOB201", new Course("MOB201","Android Nang Cao","SPRING 2020"));
//        taskMap.put("MOB204", new Course("MOB204","Du An Mau","SPRING 2020"));
//        taskMap.put("PRO1121", new Course("PRO1121","Du An 1","SPRING 2020"));

//        db.updateChildren(taskMap);
    }
}
