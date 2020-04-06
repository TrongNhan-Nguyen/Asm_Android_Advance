package com.example.asm_firebase.DAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm_firebase.Model.Course;
import com.example.asm_firebase.Model.FireBaseCallback;
import com.example.asm_firebase.Model.Registration;
import com.example.asm_firebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DAO_Registration {
    private Context context;
    private Fragment fragment;
    private DatabaseReference dbRegistration;

    public DAO_Registration(Context context) {
        this.context = context;
        this.dbRegistration = FirebaseDatabase.getInstance().getReference("registrations");
    }

    public DAO_Registration(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.dbRegistration = FirebaseDatabase.getInstance().getReference("registrations");
    }

    public void insert(Registration registration) {
        dbRegistration.child(registration.getId()).setValue(registration, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                toast("Đã đăng thêm lớp vào danh sách đăng ký");
            }
        });
    }

    public void delete(String id){
        dbRegistration.child(id).removeValue();
    }

    public void getAll(final FireBaseCallback fireBaseCallback) {
        final List<Registration> registrationList = new ArrayList<>();
        dbRegistration.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                registrationList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Registration registration = ds.getValue(Registration.class);
                    registrationList.add(registration);
                }
                fireBaseCallback.listRegistration(registrationList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void toast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

}
