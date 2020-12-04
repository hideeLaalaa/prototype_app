package com.example.proto102;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        mListView = (ListView) findViewById(R.id.courses_list_view);

        //The ArrayAdapter is d controller in model-view-cntrlr relationship
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                //content
                this,
                //layout
                R.layout.courses_row,
                //row(view)
                R.id.row_course,
                //data (model)
                new String[]{"AMB101","SYS221","TNT131"}
        );
        mListView.setAdapter(arrayAdapter);
    }

}