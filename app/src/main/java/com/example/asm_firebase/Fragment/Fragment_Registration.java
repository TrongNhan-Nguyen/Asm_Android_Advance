package com.example.asm_firebase.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_firebase.Adapter.Adapter_ListView_Course;
import com.example.asm_firebase.Adapter.Adapter_ListView_Registration;
import com.example.asm_firebase.DAO.DAO_Registration;
import com.example.asm_firebase.DAO.DAO_Schedule;
import com.example.asm_firebase.DAO.DAO_Semester;
import com.example.asm_firebase.DAO.DAO_Transcript;
import com.example.asm_firebase.Model.Course;
import com.example.asm_firebase.Model.FireBaseCallback;
import com.example.asm_firebase.Model.Registration;
import com.example.asm_firebase.Model.Schedule;
import com.example.asm_firebase.Model.Semester;
import com.example.asm_firebase.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Fragment_Registration extends Fragment {
    private View view;
    private DAO_Semester daoSemester;
    private DAO_Registration daoRegistration;
    private DAO_Transcript daoTranscript;
    private Spinner spinnerSemester;
    private ListView listViewCourse, listViewRegistration;
    private ImageView imgClear;
    private AutoCompleteTextView autoCompleteSearch;
    private TextView txtAmount;
    private ArrayAdapter<String> adapterSpinner, adapterCourseName;
    private Adapter_ListView_Course adapterCourse;
    private Adapter_ListView_Registration adapterRegistration;
    private List<String> semesters = new ArrayList<>();
    private List<String> courseName = new ArrayList<>();
    private List<Course> listCourse = new ArrayList<>();
    private int index;


    public Fragment_Registration() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_registration, container, false);
        daoSemester = new DAO_Semester(getActivity(), this);
        daoRegistration = new DAO_Registration(getActivity(), this);
        daoTranscript = new DAO_Transcript(getActivity(), this);


        initView();
        return view;
    }

    private void initView() {
        spinnerSemester = view.findViewById(R.id.fRegistration_SpinnerSemesters);
        listViewCourse = view.findViewById(R.id.fRegistration_ListViewCourse);
        listViewRegistration = view.findViewById(R.id.fRegistration_ListViewRegistration);
        autoCompleteSearch = view.findViewById(R.id.fRegistration_AutoComplete_Search);
        imgClear = view.findViewById(R.id.fRegistration_ImgClear);
        txtAmount = view.findViewById(R.id.fRegistration_TxtAmount);

        showData();
        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteSearch.setText("");
            }
        });
        listViewCourse.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                index = position;
                daoTranscript.getAll(new FireBaseCallback() {
                    @Override
                    public void listTranscript(List<Course> transcriptList) {
                        for (Course transcript : transcriptList) {
                            if (listCourse.get(position).getId().equalsIgnoreCase(transcript.getId())) {
                                log(listCourse.get(position).getId() + " \t" + transcript.getId());
                                toast("Môn này bạn đã Passed hoặc đang học vui lòng kiểm tra lại");
                                return;
                            }

                        }
                        dialogLongClick();

                    }
                });
                return true;
            }
        });

    }

    private void showData() {
        daoSemester.getAll(new FireBaseCallback() {
            @Override
            public void listSemesters(List<Semester> semesterList) {
                for (Semester semester : semesterList) {
                    semesters.add(semester.getName());

                }
                adapterSpinner = new ArrayAdapter<String>(getActivity(), R.layout.text_view, semesters);
                spinnerSemester.setAdapter(adapterSpinner);
            }

            @Override
            public void listCourse(final List<Course> courseList) {
                for (Course course : courseList) {
                    courseName.add(course.getName());
                }

                adapterCourseName = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, courseName);
                autoCompleteSearch.setThreshold(1);
                autoCompleteSearch.setAdapter(adapterCourseName);
                autoCompleteSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        listCourse.clear();
                        for (Course course : courseList) {
                            if (course.getName().equalsIgnoreCase(autoCompleteSearch.getText().toString())) {
                                listCourse.add(course);
                            }
                        }
                        showCourses(listCourse);

                    }
                });

                spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        listCourse.clear();
                        for (Course course : courseList) {
                            if (course.getSemester().equalsIgnoreCase(spinnerSemester.getSelectedItem().toString())) {
                                listCourse.add(course);
                            }
                        }
                        showCourses(listCourse);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }
        });
        daoRegistration.getAll(new FireBaseCallback() {
            @Override
            public void listRegistration(List<Registration> registrationList) {
                showRegistrations(registrationList);
                txtAmount.setText(String.valueOf(registrationList.size()));
            }
        });

    }

    private void dialogLongClick() {
        final Course course = listCourse.get(index);
        final Registration registration = new Registration(course.getId(), course.getName());
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setTitle("Bạn có muốn đăng ký lớp '" + course.getName() + "' không ?");
        dialog.setNegativeButton("ĐĂNG KÝ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                daoRegistration.insert(registration);
            }
        });

        dialog.setPositiveButton("KHÔNG", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.show();
    }

    private void showCourses(List<Course> courseList) {
        adapterCourse = new Adapter_ListView_Course(getActivity(), R.layout.raw_course, courseList);
        listViewCourse.setAdapter(adapterCourse);
    }

    private void showRegistrations(List<Registration> registrationList) {
        adapterRegistration = new Adapter_ListView_Registration(getActivity(), R.layout.raw_registration, registrationList);
        listViewRegistration.setAdapter(adapterRegistration);
    }

    private void toast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    private void log(String s) {
        Log.d("log", s);
    }
}
