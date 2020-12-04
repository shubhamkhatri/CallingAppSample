package com.example.android.callingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class SampleActivity extends AppCompatActivity {

    private ImageView Name_save, Name_edit, Phone_save, Phone_edit, City_save, City_edit, School_save, School_edit, Class_save, Class_edit, Father_save, Father_edit;
    private EditText name, phone, city, school, Class, father;
    private Button submit;
    private String[] time = {"Choose one", "11:00 AM - 12:00 PM", "12:00 PM - 01:00 PM", "01:00 PM - 02:00 PM", "02:00 PM - 03:00 PM", "03:00 PM - 04:00 PM", "04:00 PM - 05:00 PM", "05:00 PM - 06:00 PM"};
    private ArrayAdapter<String> adapter;
    private String Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, time);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        setDefault();
        onClick();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });
    }

    private void createDialog() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(SampleActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        EditText remarks = (EditText) mView.findViewById(R.id.cd_remarks);
        EditText date = (EditText) mView.findViewById(R.id.cd_date_picker);
        Spinner time_spinner = (Spinner) mView.findViewById(R.id.cd_time_spinner);
        Button cancel = (Button) mView.findViewById(R.id.cd_btn_cancel);
        Button sub = (Button) mView.findViewById(R.id.cd_btn_submit);

        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        time_spinner.setAdapter(adapter);
        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Time = time[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SampleActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                DatePickerDialog dpd = new DatePickerDialog(SampleActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
                dpd.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (remarks.getText().toString().isEmpty()) {
                    Toast.makeText(SampleActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    remarks.setError("Required!!");
                }
                else if(date.getText().toString().isEmpty()) {
                    Toast.makeText(SampleActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    date.setError("Required!!");
                }
                else if(Time.isEmpty() || Time.compareTo("Choose one") == 0) {
                    Toast.makeText(SampleActivity.this, "Please select time slot", Toast.LENGTH_SHORT).show();
                }
                else {
                    String ans = "Remarks: " + remarks.getText().toString().trim() +
                            "\nDate: " + date.getText().toString().trim() +
                            "\nTime Slot: " + Time;
                    Toast.makeText(SampleActivity.this, ans, Toast.LENGTH_LONG).show();
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();

    }

    private void onClick() {
        Name_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name_save.setVisibility(View.VISIBLE);
                Name_edit.setVisibility(View.GONE);
                name.setFocusableInTouchMode(true);
            }
        });

        Name_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name_edit.setVisibility(View.VISIBLE);
                Name_save.setVisibility(View.GONE);
                name.onEditorAction(EditorInfo.IME_ACTION_DONE);
                name.setFocusable(false);
            }
        });

        Phone_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Phone_save.setVisibility(View.VISIBLE);
                Phone_edit.setVisibility(View.GONE);
                phone.setFocusableInTouchMode(true);
            }
        });

        Phone_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Phone_edit.setVisibility(View.VISIBLE);
                Phone_save.setVisibility(View.GONE);
                phone.onEditorAction(EditorInfo.IME_ACTION_DONE);
                phone.setFocusable(false);
            }
        });

        City_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                City_save.setVisibility(View.VISIBLE);
                City_edit.setVisibility(View.GONE);
                city.setFocusableInTouchMode(true);
            }
        });

        City_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                City_edit.setVisibility(View.VISIBLE);
                City_save.setVisibility(View.GONE);
                city.onEditorAction(EditorInfo.IME_ACTION_DONE);
                city.setFocusable(false);
            }
        });

        School_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                School_save.setVisibility(View.VISIBLE);
                School_edit.setVisibility(View.GONE);
                school.setFocusableInTouchMode(true);
            }
        });

        School_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                School_edit.setVisibility(View.VISIBLE);
                School_save.setVisibility(View.GONE);
                school.onEditorAction(EditorInfo.IME_ACTION_DONE);
                school.setFocusable(false);
            }
        });

        Class_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class_save.setVisibility(View.VISIBLE);
                Class_edit.setVisibility(View.GONE);
                Class.setFocusableInTouchMode(true);
            }
        });

        Class_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class_edit.setVisibility(View.VISIBLE);
                Class_save.setVisibility(View.GONE);
                Class.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Class.setFocusable(false);
            }
        });

        Father_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Father_save.setVisibility(View.VISIBLE);
                Father_edit.setVisibility(View.GONE);
                father.setFocusableInTouchMode(true);
            }
        });

        Father_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Father_edit.setVisibility(View.VISIBLE);
                Father_save.setVisibility(View.GONE);
                father.onEditorAction(EditorInfo.IME_ACTION_DONE);
                father.setFocusable(false);
            }
        });
    }

    private void setDefault() {
        name = (EditText) findViewById(R.id.smp_name);
        phone = (EditText) findViewById(R.id.smp_phone);
        city = (EditText) findViewById(R.id.smp_city);
        school = (EditText) findViewById(R.id.smp_school);
        Class = (EditText) findViewById(R.id.smp_class);
        father = (EditText) findViewById(R.id.smp_father);

        Name_edit = (ImageView) findViewById(R.id.smp_name_edit);
        Phone_edit = (ImageView) findViewById(R.id.smp_phone_edit);
        City_edit = (ImageView) findViewById(R.id.smp_city_edit);
        School_edit = (ImageView) findViewById(R.id.smp_school_edit);
        Class_edit = (ImageView) findViewById(R.id.smp_class_edit);
        Father_edit = (ImageView) findViewById(R.id.smp_father_edit);

        Name_save = (ImageView) findViewById(R.id.smp_name_save);
        Phone_save = (ImageView) findViewById(R.id.smp_phone_save);
        City_save = (ImageView) findViewById(R.id.smp_city_save);
        School_save = (ImageView) findViewById(R.id.smp_school_save);
        Class_save = (ImageView) findViewById(R.id.smp_class_save);
        Father_save = (ImageView) findViewById(R.id.smp_father_save);

        submit = (Button) findViewById(R.id.smp_submit_button);
    }


}