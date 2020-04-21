package com.example.aluclassattendanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Student_dataBase myDatabase;
    EditText pref1,pref2, pref3, pref4;
    Button updateButton, deleteButton, saveButton, viewData;
    TextView studentName, password;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Password = "passwordKey";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabase = new Student_dataBase(this);
        pref1=(EditText)findViewById(R.id.studentName);
        pref2=(EditText)findViewById(R.id.password);
        pref3=(EditText)findViewById(R.id.email);
        pref4=(EditText)findViewById(R.id.studentID);
        updateButton= (Button) findViewById(R.id.update);
        deleteButton= findViewById(R.id.delete);
        saveButton = findViewById(R.id.addButton);
        viewData = findViewById(R.id.show);
        update();
        savingData();
        delete();
        viewDataSaved();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        GetSavedData();

    }



    public void update(){
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDatabase.updatedata(pref1.getText().toString(),pref2.getText().toString(),pref3.getText().toString(),pref4.getText().toString());
                if (isUpdated){
                    Toast.makeText(MainActivity.this, "Data updated successfully", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void delete(){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleted = myDatabase.deletedata(pref4.getText().toString());
                if(deleted<0){
                    Toast.makeText(MainActivity.this,"Data Deleted successfully",Toast.LENGTH_LONG).show();


                }
                else {
                    Toast.makeText(MainActivity.this,"Data not deleted recorded",Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void savingData(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result= myDatabase.adddata(pref1.getText().toString(),pref3.getText().toString(),pref2.getText().toString());
                if(result){
                    Toast.makeText(MainActivity.this,"Data recorded successfully",Toast.LENGTH_LONG).show();


                }
                else {
                    Toast.makeText(MainActivity.this,"Data not successfully recorded",Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void viewDataSaved(){
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer buffer = new StringBuffer();
                Cursor res = myDatabase.showdata();
                if (res.getCount()==0){
                    showmessage("Error","no data");
                    return;
                }
                while (res.moveToNext()){
                    buffer.append("Id:"+res.getString(0)+"\n");
                    buffer.append("name:"+res.getString(1)+"\n");
                    buffer.append("email:"+res.getString(2)+"\n");
                    buffer.append("password:"+res.getString(3)+"\n\n");

                }
                showmessage("Data",buffer.toString());
            }
        });
    }

    public void showmessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.show();
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
