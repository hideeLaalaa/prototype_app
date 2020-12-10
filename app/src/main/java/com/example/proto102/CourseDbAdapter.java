package com.example.proto102;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// This acts as a proxy to the database, it translates simple app calls into lower level SQL
//-SQLite API calls
public class CourseDbAdapter {

    public static final String COL_ID = "_id";
    public static final String COL_COURSE = "course";
    public static final String COL_CARRY = "carry";
    public static final String COL_LECTURER = "lecturer";
    public static final String COL_NOTE = "note";

    //these are the corresponding indices
    public static final int INDEX_ID = 0;
    public static final int INDEX_COURSE = INDEX_ID+1;
    public static final int INDEX_CARRY = INDEX_ID+2;
    public static final int INDEX_LECTURER = INDEX_ID+3;
    public static final int INDEX_NOTE = INDEX_ID+4;

    //used for logging
    private static final String TAG = "CourseDbAdapter";

    private DatabaseHelper xDbHelper;
    private SQLiteDatabase xDb;

    private static final String DATABASE_NAME = "dba_course";
    private static final String TABLE_NAME = "tbl_course";
    private static final int DATABASE_VERSION = 1;

    private final Context xCtx;

    public CourseDbAdapter(Context ctx){
        xCtx=ctx;
    }

    //open
    public void open() throws SQLException{
        xDbHelper = new DatabaseHelper(xCtx);
        xDb=xDbHelper.getWritableDatabase();
    }

    //close
    public void close(){
        if (xDbHelper != null)
            xDbHelper.close();
    }

    //BELOW ARE THE LOGIC THAT HANDLES CREATING READING UPDATING AND DELETING OF
    //REMINDERS OBJECTS (CRUD)
    //--CREATE
    public void createCourse(String COL_COURSE,Boolean COL_CARRY,
                             String COL_LECTURER,String COL_NOTE) {
        ContentValues values = new ContentValues();
        values.put(this.COL_COURSE,COL_COURSE);
        values.put(this.COL_CARRY,COL_CARRY?1:0);
        values.put(this.COL_LECTURER,COL_LECTURER);
        values.put(this.COL_NOTE,COL_NOTE);
        xDb.insert(TABLE_NAME,null,values);
    }

    //--OVERLOADED TO TAKE A REMINDER
    public void createCourse( Course course){
        ContentValues values = new ContentValues();
        values.put(COL_COURSE,course.getxCourse());
        values.put(COL_CARRY,course.getxCarry());
        values.put(COL_LECTURER,course.getxLecturer());
        values.put(COL_NOTE,course.getxNote());
        xDb.insert(TABLE_NAME,null,values);
    }

    //--READ
    public Course fetchCourseById(int id){
        Cursor cursor = xDb.query(TABLE_NAME,new String[]{COL_ID,COL_COURSE,COL_CARRY,
                COL_LECTURER,COL_NOTE},COL_ID+"=?",new String[]{String.valueOf(id)},
                null,null,null,null);
        if (cursor!=null)
            cursor.moveToFirst();

        return new Course(
                cursor.getInt(INDEX_ID),
                cursor.getString(INDEX_COURSE),
                cursor.getInt(INDEX_CARRY),
                cursor.getString(INDEX_LECTURER),
                cursor.getString(INDEX_NOTE)
        );
    }
    //fetching all courses(objects) by cursor
    public Cursor fetchAllCourses(){
        Cursor cursor = xDb.query(TABLE_NAME,new String[]{COL_ID,COL_COURSE,COL_CARRY,
            COL_LECTURER,COL_NOTE},null,null,
                null,null,null);
        if (cursor!=null)
            cursor.moveToFirst();

        return cursor;
    }

    //--UPDATE
    public void updateCourse(Course course){
        ContentValues values = new ContentValues();
        values.put(COL_COURSE,course.getxCourse());
        values.put(COL_CARRY,course.getxCarry());
        values.put(COL_LECTURER,course.getxLecturer());
        values.put(COL_NOTE,course.getxNote());
        xDb.update(TABLE_NAME,values,
                COL_ID+"=?",new String[]{String.valueOf(course.getxId())});
    }

    //--DELETE
    public void deleteCourseById(int xId){
        xDb.delete(TABLE_NAME,COL_ID+"=?",new String[]{String.valueOf(xId)});
    }
    //delete all reminder
    public  void deleteAllCourses(){
        xDb.delete(TABLE_NAME,null,null);
    }

//----------------------------------------------------------------------
    //SQL statement used to create the database
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists "+ TABLE_NAME+" ( "+
                    COL_ID + " INTEGER PRIMARY KEY autoincrement, "+
                    COL_COURSE + " TEXT, " + COL_CARRY + " INTEGER, "+
                    COL_LECTURER+" TEXT, "+COL_NOTE+" TEXT );";

    //DBHelper is a SQLite API ised to open/close d db
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG,DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version "+oldVersion+ " to "+
                    newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
        }
    }



}
