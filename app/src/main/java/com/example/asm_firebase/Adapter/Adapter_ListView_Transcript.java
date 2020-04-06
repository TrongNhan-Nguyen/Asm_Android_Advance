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

public class Adapter_ListView_Transcript extends BaseAdapter {
    private Context context;
    private List<Course> transcriptList;

    public Adapter_ListView_Transcript(Context context, List<Course> transcriptList) {
        this.context = context;
        this.transcriptList = transcriptList;
    }

    @Override
    public int getCount() {
        return transcriptList.size();
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
        TextView txtId, txtName, txtScores, txtStatus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.raw_transcript,null);
            viewHolder = new ViewHolder();
            viewHolder.txtId = convertView.findViewById(R.id.rTranscript_TxtId);
            viewHolder.txtName = convertView.findViewById(R.id.rTranscript_TxtName);
            viewHolder.txtScores = convertView.findViewById(R.id.rTranscript_TxtScores);
            viewHolder.txtStatus = convertView.findViewById(R.id.rTranscript_TxtStatus);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Course tranScript = transcriptList.get(position);

        viewHolder.txtId.setText(tranScript.getId());
        viewHolder.txtName.setText(tranScript.getName());
        viewHolder.txtScores.setText(String.valueOf(tranScript.getScores()));
        viewHolder.txtStatus.setText(tranScript.getStatus());
        if (position %2 == 0){
            convertView.setBackgroundColor(Color.parseColor("#D6D6D3"));
        }else {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        return convertView;
    }
}
