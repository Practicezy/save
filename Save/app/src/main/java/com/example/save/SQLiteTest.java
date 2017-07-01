package com.example.save;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import static com.example.save.R.id.female;
import static com.example.save.R.id.male;

public class SQLiteTest extends AppCompatActivity implements View.OnClickListener {
    private TextView personText;
    private EditText nameText,ageText;
    private Button createBtn,saveBtn,showBtn;
    private RadioButton maleBtn,femaleBtn;
    private MyDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_test);
        initView();
        mDatabase = new MyDatabase(this,"Person",null,1);
        createBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        showBtn.setOnClickListener(this);
    }

    private void initView(){
        personText = (TextView) findViewById(R.id.person_text);
        nameText = (EditText) findViewById(R.id.name_text);
        ageText = (EditText) findViewById(R.id.age_text);
        createBtn = (Button) findViewById(R.id.create_table);
        saveBtn = (Button) findViewById(R.id.save_person);
        showBtn = (Button) findViewById(R.id.show_person);
        maleBtn = (RadioButton) findViewById(R.id.male);
        femaleBtn = (RadioButton) findViewById(female);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_table:
                mDatabase.getWritableDatabase();
                break;
            case R.id.save_person:
                SQLiteDatabase db1 = mDatabase.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name",nameText.getText().toString());
                values.put("age",Integer.parseInt(ageText.getText().toString()));
                if (maleBtn.isChecked()){
                    values.put("sex",1);
                }else if (femaleBtn.isChecked()){
                    values.put("sex",0);
                }
                db1.insert("Person",null,values);
                db1.close();
                break;
            case R.id.show_person:
                SQLiteDatabase db2 = mDatabase.getReadableDatabase();
                Cursor cursor = db2.query("Person",null,null,null,null,null,null);
                while (cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int age = cursor.getInt(cursor.getColumnIndex("age"));
                    int sex = cursor.getInt(cursor.getColumnIndex("sex"));
                    String sexy = null;
                    if (sex == 1){
                        sexy = "男";
                    }else if (sex == 0){
                        sexy = "女";
                    }
                    personText.setText(name +"\n"+ Integer.toString(age) +"\n"+ sexy);
                    personText.setVisibility(View.VISIBLE);
                }
                cursor.close();
                db2.close();
                break;
        }
    }
}
