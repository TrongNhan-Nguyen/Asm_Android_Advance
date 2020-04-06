package com.example.asm_firebase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asm_firebase.Model.Course;
import com.example.asm_firebase.R;

import java.util.List;

public class Adapter_ListView_Course extends BaseAdapter {
    Context context;
    int layout;
    List<Course> courseList;

    public Adapter_ListView_Course(Context context, int layout, List<Course> courseList) {
        this.context = context;
        this.layout = layout;
        this.courseList = courseList;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);
        TextView txtId = convertView.findViewById(R.id.rCourse_ID);
        TextView txtName = convertView.findViewById(R.id.rCourse_Name);
        TextView txtSemester = convertView.findViewById(R.id.rCourse_Semester);

        Course course = courseList.get(position);
        txtId.setText(course.getId());
        txtName.setText(course.getName());
        txtSemester.setText(course.getSemester());

        return convertView;
    }
}
