package com.example.jishiben;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Date;
import java.text.SimpleDateFormat;




public class AddActivity extends Activity implements View.OnClickListener {
    private EditText editText,editText1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        Button btnfinish=  findViewById(R.id.finish_button);
        Button btnforgo= findViewById(R.id.forgo_button);
        editText = findViewById(R.id.edit_text);
        editText1=findViewById(R.id.title);
        btnfinish.setOnClickListener(this);
        btnforgo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.finish_button:
                sqlite dbHelper;
                dbHelper = new sqlite(this,"NoteBook.db",null,3);
                String note= editText.getText().toString();
                String title= editText1.getText().toString();
                SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
                Date curDate =  new Date(System.currentTimeMillis());
                String time= formatter.format(curDate);
                Intent intent=new Intent(AddActivity.this,MainActivity.class);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put("title",title);
                values.put("note",note);
                values.put("time",time);
                db.insert("notebook",null,values);
                dbHelper.close();
                startActivity(intent);
                break;
            case R.id.forgo_button:
                Intent intent1=new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
}
