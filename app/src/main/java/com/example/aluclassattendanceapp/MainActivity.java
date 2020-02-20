package com.example.aluclassattendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText pref1,pref2;
    Button button;
    TextView studentName, password;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Password = "passwordKey";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref1=(EditText)findViewById(R.id.studentName);
        pref2=(EditText)findViewById(R.id.password);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        GetSavedData();

    }




    private void saveData() {
        String StNa  = pref1.getText().toString();
        String pass  = pref2.getText().toString();


        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(Name, StNa);
        editor.putString(Password, pass);

        editor.commit();
    }

    private void GetSavedData() {
        if(sharedpreferences.contains(Name)){
            String stuName = sharedpreferences.getString(Name,"");

            String passw = sharedpreferences.getString(Password,"");
            pref1.setText(stuName);
            pref2.setText(passw);

        }
    }

    public void onRadioButtonClicked(View view) {


    }



    public void Add(View view) {
        boolean isStudentName = true;
        boolean isPassword = true;
        boolean isEmail = true;
        boolean isStudentID = true;



//        Checking the user imput and send a popup message telling him if the filed is empty.
            EditText studentID = (EditText)findViewById(R.id.studentID);
            if( studentID.getText().toString().length() == 0 ){
                isStudentID = false;
                studentID.setError( "Student ID should not be null" );
            }


            EditText studentName = (EditText)findViewById(R.id.studentName);
            if( studentName.getText().toString().length() == 0 ){
                isStudentName = false;
                studentName.setError( "Student Name should not be null" );
            }


            EditText password = (EditText)findViewById(R.id.password);
            if( password.getText().toString().length() == 0 )
            {
                isPassword = false;
                password.setError( "Student ID should not be null" );

            }

            EditText email = (EditText)findViewById(R.id.email);
            if( email.getText().toString().length() == 0 ){
                isEmail = false;
                email.setError( "Student ID should not be null" );

            }


//            Validate the form if the filed are not empty
            if(isStudentID && isStudentName && isPassword && isEmail){
                Intent i = new Intent(this, secondActivity.class);
                startActivity(i);
            }

        Spinner spinnerDepartment = (Spinner) findViewById(R.id.spinnerDepartment);
        Spinner spinnerSession = (Spinner) findViewById(R.id.spinnerSession);


//        Display a toast message for the department and session selected
        Toast.makeText(MainActivity.this,

                        "\nDepartment selected : "+ String.valueOf(spinnerDepartment.getSelectedItem()) +
                        "\nSession selected : "+ String.valueOf(spinnerSession.getSelectedItem()),
                Toast.LENGTH_SHORT).show();


    }
}
