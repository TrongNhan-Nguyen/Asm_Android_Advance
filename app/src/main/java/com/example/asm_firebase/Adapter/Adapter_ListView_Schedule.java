package com.example.asm_firebase.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asm_firebase.Model.Course;
import com.example.asm_firebase.Model.Schedule;
import com.example.asm_firebase.R;

import java.util.List;

public class Adapter_ListView_Schedule extends BaseAdapter {
    private Context context;
    private List<Schedule> scheduleList;

    public Adapter_ListView_Schedule(Context context, List<Schedule> scheduleList) {
        this.context = context;
        this.scheduleList = scheduleList;
    }

    @Override
    public int getCount() {
        return scheduleList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView txtDate, txtId, txtName, txtBlock, txtShift;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.raw_schedule,null);
            viewHolder = new ViewHolder();
            viewHolder.txtDate = convertView.findViewById(R.id.rSchedule_TxtDate);
            viewHolder.txtId = convertView.findViewById(R.id.rSchedule_TxtCourseID);
            viewHolder.txtName = convertView.findViewById(R.id.rSchedule_TxtCourseName);
            viewHolder.txtBlock = convertView.findViewById(R.id.rSchedule_TxtBlock);
            viewHolder.txtShift = convertView.findViewById(R.id.rSchedule_TxtShift);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Schedule schedule = scheduleList.get(position);

        viewHolder.txtDate.setText(schedule.getDate());
        viewHolder.txtId.setText(schedule.getCourseId());
        viewHolder.txtName.setText(schedule.getCourseName());
        viewHolder.txtBlock.setText(String.valueOf(schedule.getBlock()));
        viewHolder.txtShift.setText(String.valueOf(schedule.getShift()));
        if (position %2 == 0){
            convertView.setBackgroundColor(Color.parseColor("#D6D6D3"));
        }else {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        return convertView;
    }
}
