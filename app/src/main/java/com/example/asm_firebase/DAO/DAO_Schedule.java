package com.example.asm_firebase.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.asm_firebase.Model.FireBaseCallback;
import com.example.asm_firebase.Model.Registration;
import com.example.asm_firebase.Model.Schedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DAO_Schedule {
    private Context context;
    private Fragment fragment;
    private DatabaseReference dbSchedule;

    public DAO_Schedule(Context context) {
        this.context = context;
        this.dbSchedule = FirebaseDatabase.getInstance().getReference("schedules");
    }

    public DAO_Schedule(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.dbSchedule = FirebaseDatabase.getInstance().getReference("schedules");
    }

    public void getAll(final FireBaseCallback fireBaseCallback){
        final List<Schedule> scheduleList = new ArrayList<>();
        dbSchedule.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                scheduleList.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        for (DataSnapshot ds1: ds.getChildren()){
                            Schedule schedule = ds1.getValue(Schedule.class);
                            scheduleList.add(schedule);
                        }
                    }
                    fireBaseCallback.listSchedule(scheduleList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void insert(String startDate, String endDate,String dOW , Schedule schedule){
        DatabaseReference db = dbSchedule.child(schedule.getCourseId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localSDate = LocalDate.parse(startDate,formatter);
        LocalDate localEDate = LocalDate.parse(endDate,formatter);
        DayOfWeek dayOfWeek = null;
        int val = 0;
        LocalDate date = localSDate;
        while (!date.isAfter(localEDate)){
            dayOfWeek = DayOfWeek.from(date);
            val = dayOfWeek.getValue();
            if (dOW.equalsIgnoreCase("Even")){
                if (val == 1 || val == 3 || val ==5){
                    String stringDate = formatter.format(date);
                    schedule.setLongDate(longDate(stringDate));
                    schedule.setDate(stringDate);
                    db.child(stringDate).setValue(schedule);
                }
            }else {
                if (val == 2 || val == 4 || val == 6){
                    String stringDate = formatter.format(date);
                    schedule.setLongDate(longDate(stringDate));
                    schedule.setDate(stringDate);
                    db.child(stringDate).setValue(schedule);
                }
            }
            date = date.plusDays(1);
        }

    }

    private long longDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        long longDate = 0;
        try {
            longDate = format.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longDate;
    }

    private void log(String s){
        Log.d("log",s);
    }

    private void toast(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
