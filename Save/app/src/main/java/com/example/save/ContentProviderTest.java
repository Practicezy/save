package com.example.save;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderTest extends AppCompatActivity {
    private List<String> contracts = new ArrayList<>();
    private ListView mList;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_test);
        Button contracsBtn = (Button) findViewById(R.id.select_contracts);
        mList= (ListView) findViewById(R.id.contracts_list);
        adapter= new ArrayAdapter(ContentProviderTest.this,android.R.layout.simple_list_item_1,contracts);

        contracsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ContentProviderTest.this, Manifest.permission.READ_CONTACTS) !=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ContentProviderTest.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            1);
                }else {
                    loadContracts();
                    mList.setAdapter(adapter);
                }
            }
        });

    }

    private void loadContracts(){
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contracts.add(name + "\n" + number);
            }
            adapter.notifyDataSetChanged();
        }
        cursor.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    loadContracts();
                    mList.setAdapter(adapter);
                }else {
                    Toast.makeText(this,"You denied Permission",Toast.LENGTH_LONG).show();
                }
                break;
            default:
        }
    }
}
