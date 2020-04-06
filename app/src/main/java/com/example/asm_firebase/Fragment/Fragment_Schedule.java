package com.example.asm_firebase.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm_firebase.Adapter.Adapter_ListView_Schedule;
import com.example.asm_firebase.DAO.DAO_Schedule;
import com.example.asm_firebase.Model.Course;
import com.example.asm_firebase.Model.FireBaseCallback;
import com.example.asm_firebase.Model.Schedule;
import com.example.asm_firebase.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Schedule extends Fragment {
    private View view;
    private DAO_Schedule daoSchedule;
    private ListView listView;
    private ArrayAdapter<String> adapterName;
    private Adapter_ListView_Schedule adapterSchedule;
    private List<Schedule> listSchedule;
    private Set<String> nameSet = new HashSet<>();

    public Fragment_Schedule() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_schedule, container, false);
        daoSchedule = new DAO_Schedule(getActivity(),this);
//        daoSchedule.insert();
        initView();
        return view;
    }

    private void initView() {
        listView = view.findViewById(R.id.fSchedule_ListView);
        daoSchedule.getAll(new FireBaseCallback(){
            @Override
            public void listSchedule(List<Schedule> scheduleList) {
                listSchedule = scheduleList;
                sortList(scheduleList);
                showListView(scheduleList);
                for (Schedule schedule: scheduleList){
                    nameSet.add(schedule.getCourseName());
                }
            }
        });


    }

    private void dialogSchedule(final int condition){
        List<String> nameList = new ArrayList<>(nameSet);
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_schedule);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView txtStart = dialog.findViewById(R.id.dSchedule_TxtStart);
        final TextView txtEnd = dialog.findViewById(R.id.dSchedule_TxtEnd);
        final AutoCompleteTextView autoComplete = dialog.findViewById(R.id.dSchedule_AutoComplete);
        Button btnSearch = dialog.findViewById(R.id.dSchedule_BtnSearch);
        Button btnClear = dialog.findViewById(R.id.dSchedule_BtnClear);
        Button btnCancel = dialog.findViewById(R.id.dSchedule_Btncancel);

        adapterName = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, nameList);
        autoComplete.setThreshold(1);
        autoComplete.setAdapter(adapterName);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(txtStart);
            }
        });

        txtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(txtEnd);
            }
        });



        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEnd.setText("");
                txtStart.setText("");
                autoComplete.setText("");
            }
        });


        if (condition == 1){
            autoComplete.setVisibility(View.GONE);
        }else {
            txtEnd.setVisibility(View.GONE);
            txtStart.setVisibility(View.GONE);
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoComplete.getVisibility() == View.VISIBLE){
                    if (autoComplete.getText().toString().isEmpty()){
                        toast("Vui lòng nhập tên môn học");
                    }else {
                        showListView(listByName(autoComplete.getText().toString()));
                        dialog.dismiss();
                    }

                }else {
                    if (txtStart.getText().toString().isEmpty() || txtEnd.getText().toString().isEmpty())
                    {
                        toast("Vui lòng nhập đầy đủ ngày bắt đầu và kết thúc");
                    }else {
                        String start = txtStart.getText().toString();
                        String end = txtEnd.getText().toString();
                        showListView(listByDate(start,end));
                        dialog.dismiss();
                    }
                }

            }
        });

        dialog.show();
    }

    private void datePicker(final TextView textView){
        final Calendar calendar = Calendar.getInstance();
        int day, month, year;
        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                textView.setText(dateFormat.format(calendar.getTime()));
            }
        }, year, month, day);
        pickerDialog.show();
    }

    private List<Schedule> listByName(String name){
        List<Schedule> scheduleList = new ArrayList<>();
        for (Schedule schedule : listSchedule){
            if (schedule.getCourseName().equalsIgnoreCase(name)){
                scheduleList.add(schedule);
            }
        }
        return scheduleList;
    }

    private List<Schedule> listExam(){
        List<Schedule> scheduleList = new ArrayList<>();
        List<String> nameList = new ArrayList<>(nameSet);
        Collections.sort(listSchedule, new Comparator<Schedule>() {
            @Override
            public int compare(Schedule o1, Schedule o2) {
                if (o1.getLongDate() > o2.getLongDate()){
                    return -1;
                }else if (o1.getLongDate() < o2.getLongDate()){
                    return 1;
                }else {
                    return 0;
                }
            }
        });
        for (String name : nameList){
            for (Schedule schedule : listSchedule){
                if (name.equalsIgnoreCase(schedule.getCourseName())){
                    scheduleList.add(schedule);
                    break;
                }
            }
        }
        return scheduleList;
    }

    private List<Schedule> listByDate(String start, String end){
        List<Schedule> scheduleList = new ArrayList<>();
        long longStart = longDate(start);
        long longEnd = longDate(end);
        for (Schedule schedule : listSchedule){
            if (schedule.getLongDate() >= longStart && schedule.getLongDate() <= longEnd){
                scheduleList.add(schedule);
            }
        }
        sortList(scheduleList);
        return scheduleList;
    }



    private void showListView(List<Schedule> scheduleList){
        adapterSchedule = new Adapter_ListView_Schedule(getActivity(),scheduleList);
        listView.setAdapter(adapterSchedule);
    }

    private void sortList(List<Schedule> scheduleList){
        Collections.sort(scheduleList, new Comparator<Schedule>() {
            @Override
            public int compare(Schedule o1, Schedule o2) {
                if (o1.getLongDate() > o2.getLongDate()){
                    return 1;
                }else if (o1.getLongDate() < o2.getLongDate()){
                    return -1;
                }else {
                    return 0;
                }
            }
        });

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

    private void toast(String s){
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_schedule, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mSchedule_All:
                showListView(listSchedule);
                break;
            case R.id.mSchedule_Assignment:
                showListView(listExam());
                break;
            case R.id.mSchedule_ByDate:
                dialogSchedule(1);
                break;
            case R.id.mSchedule_ByName:
                dialogSchedule(2);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
