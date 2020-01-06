package com.think2exam.projectt2e.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.think2exam.projectt2e.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;


public class CollegeFilterDialog extends BottomSheetDialogFragment {

    AppCompatButton apply,cancel;
    AutoCompleteTextView Institute,Course,State,City;
    ImageView doneInstitute,doneCourse,doneState,doneCity;
    ArrayList<String> InstituteList,CourseList,StateList,CityList;


    public CollegeFilterDialog()
    {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.college_list_filter_layout,container,false);
        apply = view.findViewById(R.id.apply_btn);
        cancel = view.findViewById(R.id.cancel_btn);
        Institute = view.findViewById(R.id.actv_institute);
        Course = view.findViewById(R.id.actv_course);
        State = view.findViewById(R.id.actv_state);
        City = view.findViewById(R.id.actv_city);

        //set Array List
        setArrayList();

        //set Adapters
        ArrayAdapter<String> adapterInstitute = new ArrayAdapter<String>(view.getContext(),R.layout.select_institute_dialog_item,InstituteList);
        Institute.setThreshold(1);
        Institute.setAdapter(adapterInstitute);

        ArrayAdapter<String> adapterCourse = new ArrayAdapter<String>(view.getContext(),R.layout.select_institute_dialog_item,CourseList);
        Course.setThreshold(1);
        Course.setAdapter(adapterCourse);

        ArrayAdapter<String> adapterState = new ArrayAdapter<String>(view.getContext(),R.layout.select_institute_dialog_item,StateList);
        State.setThreshold(1);
        State.setAdapter(adapterState);

        ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(view.getContext(),R.layout.select_institute_dialog_item,CityList);
        City.setThreshold(1);
        City.setAdapter(adapterCity);

        //set done
        doneInstitute = view.findViewById(R.id.done_institute);
        doneCourse = view.findViewById(R.id.done_course);
        doneState = view.findViewById(R.id.done_state);
        doneCity = view.findViewById(R.id.done_city);

        Institute.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doneInstitute.setVisibility(View.VISIBLE);
            }
        });
        Course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doneCourse.setVisibility(View.VISIBLE);
            }
        });
        State.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doneState.setVisibility(View.VISIBLE);
            }
        });
        City.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doneCity.setVisibility(View.VISIBLE);
            }
        });



        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return view;
    }

    private void setArrayList()
    {
        InstituteList = new ArrayList<>();
        CourseList = new ArrayList<>();
        StateList = new ArrayList<>();
        CityList = new ArrayList<>();

        InstituteList.add("IIT");
        InstituteList.add("NIT");
        InstituteList.add("IIIT");
        InstituteList.add("Central University");

        CourseList.add("BTech");
        CourseList.add("MTech");
        CourseList.add("MBBS");

        StateList.add("Assam");
        StateList.add("Delhi");
        StateList.add("Sikkim");

        CityList.add("Chennai");
        CityList.add("Benglore");
        CityList.add("Ravangla");
    }
}
