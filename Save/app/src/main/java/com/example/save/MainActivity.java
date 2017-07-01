package com.example.save;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String[] list = {"SharePreferences存储","文件存储","SQLite存储","Content Provider"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.save_list);
        ListAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        NextAction("com.example.save.SharePrefencesTest");
                        break;
                    case 1:
                        NextAction("com.example.save.FileIO");
                        break;
                    case 2:
                        NextAction("com.example.save.SQLiteTest");
                        break;
                    case 3:
                        NextAction("com.example.save.ContentProviderTest");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void NextAction(String s){
        Intent intent = new Intent(s);
        startActivity(intent);
    }
}
