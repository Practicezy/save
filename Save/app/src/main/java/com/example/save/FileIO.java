package com.example.save;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FileIO extends AppCompatActivity {
    private EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_io);
        text = (EditText) findViewById(R.id.file_io_test);
        String note = load();
        if (!TextUtils.isEmpty(note)){
            text.setText(note);
            text.setSelection(note.length());
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        String note = text.getText().toString();
        save(note);
    }

    private void save(String s){
        BufferedWriter writer = null;
        try {
            FileOutputStream outputStream = openFileOutput("data",MODE_PRIVATE);
            writer = new BufferedWriter(
                    new OutputStreamWriter(outputStream)
            );
            String note = s;
            writer.write(note);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String load(){
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            FileInputStream inputStream = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = reader.readLine()) != null){
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {if (reader != null){
                reader.close();
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content.toString();
    }
}
