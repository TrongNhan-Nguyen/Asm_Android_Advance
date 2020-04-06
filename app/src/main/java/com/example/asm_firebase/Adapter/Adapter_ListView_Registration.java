package com.example.asm_firebase.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_firebase.DAO.DAO_Registration;
import com.example.asm_firebase.DAO.DAO_Schedule;
import com.example.asm_firebase.DAO.DAO_Transcript;
import com.example.asm_firebase.Model.Course;
import com.example.asm_firebase.Model.FireBaseCallback;
import com.example.asm_firebase.Model.Registration;
import com.example.asm_firebase.Model.Schedule;
import com.example.asm_firebase.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_ListView_Registration extends BaseAdapter {
    private Context context;
    private int layout, index;
    private List<Registration> registrationList;
    private DAO_Registration daoRegistration;
    private DAO_Schedule daoSchedule;
    private DAO_Transcript daoTranscript;
    private List<Schedule> checkEven = new ArrayList<>();
    private List<Schedule> checkOdd = new ArrayList<>();
    private boolean canInsert = true;
    private Button btnConfirm;

    public Adapter_ListView_Registration(Context context, int layout, List<Registration> registrationList) {
        this.context = context;
        this.layout = layout;
        this.registrationList = registrationList;
    }

    @Override
    public int getCount() {
        return registrationList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);
        TextView txtId = convertView.findViewById(R.id.rRegistration_Id);
        TextView txtName = convertView.findViewById(R.id.rRegistration_Name);
        ImageView imgCheck = convertView.findViewById(R.id.rRegistration_ImgCheck);
        ImageView imgDelete = convertView.findViewById(R.id.rRegistration_ImgDelete);

        daoRegistration = new DAO_Registration(context);
        daoSchedule = new DAO_Schedule(context);
        daoTranscript = new DAO_Transcript(context);

        final Registration registration = registrationList.get(position);
        txtId.setText(registration.getId());
        txtName.setText(registration.getName());

        imgCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                dialogConfirm();
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("Bạn có muốn xoá lớp '" + registration.getName() + "' khỏi danh sách đăng ký không?");
                dialog.setNegativeButton("XOÁ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        daoRegistration.delete(registration.getId());
                        toast("Đã xoá lớp khỏi danh sách đăng ký");
                    }
                });
                dialog.setPositiveButton("HUỶ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.show();

            }
        });

        return convertView;
    }

    public void dialogConfirm() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_confirm);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final Registration registration = registrationList.get(index);


        final TextView txtStart = (TextView) dialog.findViewById(R.id.dialog_confirm_txtStart);
        final TextView txtEnd = (TextView) dialog.findViewById(R.id.dialog_confirm_txtEnd);
        final TextView txtCourseID = (TextView) dialog.findViewById(R.id.dialog_confirm_txtCourseID);
        final TextView txtCourseName = (TextView) dialog.findViewById(R.id.dialog_confirm_txtCourseName);
        final Spinner spinnerDay = (Spinner) dialog.findViewById(R.id.dialog_confirm_spinnerDay);
        final Spinner spinnerBlock = (Spinner) dialog.findViewById(R.id.dialog_confirm_spinnerBlock);
        final Spinner spinnerShift = (Spinner) dialog.findViewById(R.id.dialog_confirm_spinnerShift);
        Button btnValidation = (Button) dialog.findViewById(R.id.dialog_confirm_btnValidation);
        btnConfirm = (Button) dialog.findViewById(R.id.dialog_confirm_btnConfirm);
        Button btnCancel = (Button) dialog.findViewById(R.id.dialog_confirm_btnCancel);

        String[] listDay = {"Even", "Odd"};
        final Integer[] listBlock = {1, 2};
        Integer[] listShift = {1, 2, 3, 4};

        ArrayAdapter<String> adapterDay = new ArrayAdapter(context, android.R.layout.simple_spinner_item, listDay);
        ArrayAdapter<Integer> adapterBlock = new ArrayAdapter(context, android.R.layout.simple_spinner_item, listBlock);
        ArrayAdapter<Integer> adapterShift = new ArrayAdapter(context, android.R.layout.simple_spinner_item, listShift);

        spinnerDay.setAdapter(adapterDay);
        spinnerBlock.setAdapter(adapterBlock);
        spinnerShift.setAdapter(adapterShift);
        txtCourseID.setText(registration.getId());
        txtCourseName.setText(registration.getName());
        btnConfirm.setEnabled(false);
        spinnerBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (listBlock[position] == 1) {
                    txtStart.setText("15-02-2020");
                    txtEnd.setText("15-03-2020");
                } else {
                    txtStart.setText("16-03-2020");
                    txtEnd.setText("16-04-2020");
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dOW = spinnerDay.getSelectedItem().toString();
                int block = Integer.parseInt(spinnerBlock.getSelectedItem().toString());
                int shift = Integer.parseInt(spinnerShift.getSelectedItem().toString());
                checkSchedule(dOW, block, shift);
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDay = txtStart.getText().toString();
                String endDay = txtEnd.getText().toString();
                String days = spinnerDay.getSelectedItem().toString();
                String courseID = txtCourseID.getText().toString();
                String courseName = txtCourseName.getText().toString();
                int block = Integer.parseInt(spinnerBlock.getSelectedItem().toString());
                int shift = Integer.parseInt(spinnerShift.getSelectedItem().toString());
                Schedule schedule = new Schedule(courseID, courseName, days, block, shift);
                Course transcript = new Course(courseID, courseName);
                daoTranscript.insert(transcript);

                daoSchedule.insert(startDay, endDay, days, schedule);
                daoRegistration.delete(courseID);

                toast("Đã thêm vào lịch học, vui lòng vào lịch học để kiểm tra lại");

                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void checkSchedule(final String dayOfWeek, final int block, final int shift) {
        daoSchedule.getAll(new FireBaseCallback() {
            @Override
            public void listSchedule(List<Schedule> scheduleList) {
                checkEven.clear();
                checkOdd.clear();
                String dOW = "";
                int b = 0, s = 0, check = 0;
                for (Schedule schedule : scheduleList) {
                    dOW = schedule.getDayOfWeek();
                    b = schedule.getBlock();
                    s = schedule.getShift();
                    if (schedule.getDayOfWeek().equalsIgnoreCase("Even")) {
                        Schedule even = new Schedule(dOW, b, s);
                        checkEven.add(even);
                    } else {
                        Schedule odd = new Schedule(dOW, b, s);
                        checkOdd.add(odd);
                    }

                }
                if (dayOfWeek.equalsIgnoreCase("Even")) {
                    for (Schedule schedule : checkEven) {
                        dOW = schedule.getDayOfWeek();
                        b = schedule.getBlock();
                        s = schedule.getShift();
                        if (dayOfWeek.equalsIgnoreCase(dOW) && block == b && shift == s) {
                            log("Trùng lịch học");
                            canInsert = false;
                            toggleButton();
                            return;
                        }
                    }
                } else {
                    for (Schedule schedule : checkOdd) {
                        dOW = schedule.getDayOfWeek();
                        b = schedule.getBlock();
                        s = schedule.getShift();
                        if (dayOfWeek.equalsIgnoreCase(dOW) && block == b && shift == s) {
                            log("trùng lịch học");
                            canInsert = false;
                            toggleButton();
                            return;
                        }
                    }

                }
                canInsert = true;
                toggleButton();
            }
        });
    }

    private void toggleButton() {
        if (canInsert) {
            btnConfirm.setEnabled(true);
            toast("Có thể đăng ký");
        } else {
            btnConfirm.setEnabled(false);
            toast("Trùng lịch học vui lòng kiểm tra lại");
            return;
        }

    }

    private void log(String s) {
        Log.d("log", s);
    }

    private void toast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
