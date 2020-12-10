package com.example.proto102;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.proto102.ui.login.LoginActivity;

public class HomeActivity extends AppCompatActivity {

    private ListView mListView;
    private CourseDbAdapter xDbAdapter;
    private CourseCursorAdapter xCursorAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.super.onBackPressed();
            }
        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = (ListView) findViewById(R.id.courses_list_view);
        mListView.setDivider(null);
        xDbAdapter = new CourseDbAdapter(this);
        xDbAdapter.open();

        Cursor cursor = xDbAdapter.fetchAllCourses();

        //from column defined in the db
        String[] from = new String[]{
                CourseDbAdapter.COL_COURSE
        };

        //to the ids of views in the layout
        int[] to = new int[]{
                R.id.row_course
        };

        xCursorAdapter = new CourseCursorAdapter(
                //context
                this,
                //layout of d row
                R.layout.courses_row,
                //cursor
                cursor,
                //from
                from,
                //to
                to, 0
        );

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_option,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_new:
                return true;
            case R.id.action_exit:
//                finish();
                finishAffinity();
                return true;
            default:
                return false;
        }
    }
}