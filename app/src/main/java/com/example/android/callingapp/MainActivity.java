package com.example.android.callingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView call;
    private EditText number;
    private Button proceed;
    private TextView callLogs;
    private String Number;
    private static final int REQUEST_PHONE_CALL = 10001;
    private static final int REQUEST_READ_CALL_LOG = 999;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        call = (ImageView) findViewById(R.id.image_call);
        number = (EditText) findViewById(R.id.editText);
        proceed = (Button) findViewById(R.id.proceed_button);
        callLogs = (TextView) findViewById(R.id.call_logs_text);
        progressDialog = new ProgressDialog(MainActivity.this);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Number = number.getText().toString().trim();
                if (Number.isEmpty())
                    Toast.makeText(MainActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                else if (Number.length() != 10)
                    Toast.makeText(MainActivity.this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
                else {
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:" + Number));
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                    } else {
                        startActivity(i);
                    }
                }
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
              progressDialog.setTitle("Searching Call Logs");
                Number = number.getText().toString().trim();
                if (Number.isEmpty())
                    Toast.makeText(MainActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                else if (Number.length() != 10)
                    Toast.makeText(MainActivity.this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
                else {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, REQUEST_READ_CALL_LOG);
                    } else {
                        getCallLogs();
                    }
                }
            }
        });
    }


    @SuppressLint("MissingPermission")
    private void makeCall() {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:" + Number));
        startActivity(i);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall();
                }
                return;
            }

            case REQUEST_READ_CALL_LOG: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCallLogs();
                }
                return;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getCallLogs() {
        progressDialog.show();
        String stringOutput = "";
        int count = 0;

        Uri uriCallLogs = Uri.parse("content://call_log/calls");
        Cursor cursorCallLogs = getContentResolver().query(uriCallLogs, null, null, null);
        cursorCallLogs.moveToFirst();
        do {
            String stringNumber = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.NUMBER));
            String stringName = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.CACHED_NAME));
            String stringDuration = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.DURATION));
            String stringType = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.TYPE));

            if (stringNumber.equals(Number)) {
                if(stringDuration.equals("0")){
                    stringOutput = "Number: " + stringNumber
                            + "\nName: " + stringName
                            + "\nDuration: " + stringDuration
                            + "\n Call did not connect"
                            + "\n\n";
                }else {
                    stringOutput = "Number: " + stringNumber
                            + "\nName: " + stringName
                            + "\nDuration: " + stringDuration
                            + "\n Type: " + stringType
                            + "\n\n";
                }
                count++;

            }
        } while (cursorCallLogs.moveToNext());

        if(count >= 1) {
            progressDialog.dismiss();
            callLogs.setText(stringOutput);
        } else {
            progressDialog.dismiss();
            callLogs.setText("No record found");
        }
    }
}