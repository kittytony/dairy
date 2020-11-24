package com.example.jishiben;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class FindActivity extends Activity  {
    private EditText editText;
    private List<String> list= new ArrayList();//存数据
    final Map m = new HashMap();
    ListView listView=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find);
        Button btnsearch=  findViewById(R.id.search_button);
        Button btnback=findViewById(R.id.back_button);
        editText = findViewById(R.id.edit_text);
        final sqlite dbHelper= new sqlite(this,"NoteBook.db",null,3);
        final ArrayAdapter<String> adapter = new ArrayAdapter(
                FindActivity.this,android.R.layout.simple_list_item_1,list);
        listView =findViewById(R.id.list_item1);
        listView.setAdapter(adapter);
        //点击事件-更改
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i=new Intent(FindActivity.this,UpdateActivity.class);
                i.putExtra("id",String.valueOf(id));
                startActivity(i);//启动第二个activity并把i传递过去
            }
        });

        //按钮-查询
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title,time,note1;
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query("notebook",null,null,null,null,null,null);
                //输入信息
                if (cursor.moveToFirst()){
                    list.clear();
                    m.clear();
                    do{//筛选出符合搜索的笔记
                        title = cursor.getString(cursor.getColumnIndex("title"));
                        note1 = editText.getText().toString();
                        if(title.contains(note1)){
                            time = cursor.getString(cursor.getColumnIndex("time"));
                            list.add(title+"\n"+time);
                        }

                    }while(cursor.moveToNext());
                }
                cursor.close();
                adapter.notifyDataSetChanged();
                Toast.makeText(FindActivity.this,"查询完成",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //按钮-返回
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(FindActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }

}

