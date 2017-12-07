package edu.orangecoastcollege.cs273.occofficehours;

/**
 * Created by on 12/1/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DBHelper extends SQLiteOpenHelper {

    private Context mContext;

    //TASK: DEFINE THE DATABASE VERSION AND NAME  (DATABASE CONTAINS MULTIPLE TABLES)
    static final String DATABASE_NAME = "OCC";
    private static final int DATABASE_VERSION = 1;

    //TASK: DEFINE THE FIELDS (COLUMN NAMES) FOR THE COURSES TABLE
    private static final String COURSES_TABLE = "Courses";
    private static final String COURSES_KEY_FIELD_ID = "_id";
    private static final String FIELD_ALPHA = "alpha";
    private static final String FIELD_NUMBER = "number";
    private static final String FIELD_TITLE = "title";

    //TASK: DEFINE THE FIELDS (COLUMN NAMES) FOR THE INSTRUCTORS TABLE
    private static final String INSTRUCTORS_TABLE = "Instructors";
    private static final String INSTRUCTORS_KEY_FIELD_ID = "_id";
    private static final String FIELD_FIRST_NAME = "first_name";
    private static final String FIELD_LAST_NAME = "last_name";
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_DEPARTMENT = "department_name";
    private static final String FIELD_BUILDING= "building";
    private static final String FIELD_ROOM= "room";
    private static final String FIELD_M_HOURS= "monday";
    private static final String FIELD_T_HOURS= "tuesday";
    private static final String FIELD_W_HOURS= "wednesday";
    private static final String FIELD_R_HOURS= "thursday";
    private static final String FIELD_F_HOURS= "friday";

    /*
    //TASK: DEFINE THE FIELDS (COLUMN NAMES FOR THE DEPARTMENT TABLE
    public static final String DEPARTMENT_TABLE = "Departments";
    public static final String DEPARTMENT_KEY_FIELD_ID = "_id";
    public static final String FIELD_DEPARTMENT_NAME = "department_name";
    */
    //TASK: DEFINE THE FIELDS (COLUMN NAMES) FOR THE OFFERINGS TABLE
    private static final String OFFERINGS_TABLE = "Offerings";
    private static final String FIELD_COURSE_ID = "course_id";
    private static final String FIELD_INSTRUCTOR_ID = "instructor_id";

    /*
    // TASK: DEFINE THE FIELDS (COLUMN NAMES) FOR THE DEPARTMENT_LIST TABLE
    public static final String DEPARTMENT_LIST_TABLE = "DepartmentsList";
    public static final String FIELD_id = "_id";
    private static final String FIELD_DEPARTMENT_ID = "department_id";
    private static final String FIELD_DEPARTMENT_INSTRUCTOR__ID = "instructor_id";
    */

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String createQuery = "CREATE TABLE " + COURSES_TABLE + "("
                + COURSES_KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_ALPHA + " TEXT, "
                + FIELD_NUMBER + " TEXT, "
                + FIELD_TITLE + " TEXT" + ")";
        database.execSQL(createQuery);

        createQuery = "CREATE TABLE " + INSTRUCTORS_TABLE + "("
                + INSTRUCTORS_KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_FIRST_NAME + " TEXT, "
                + FIELD_LAST_NAME + " TEXT, "
                + FIELD_EMAIL + " TEXT, "
                + FIELD_DEPARTMENT + " TEXT, "
                + FIELD_BUILDING + " TEXT, "
                + FIELD_ROOM + " TEXT, "
                + FIELD_M_HOURS + " TEXT, "
                + FIELD_T_HOURS + " TEXT, "
                + FIELD_W_HOURS + " TEXT, "
                + FIELD_R_HOURS + " TEXT, "
                + FIELD_F_HOURS + " TEXT" + ")";
        database.execSQL(createQuery);

        createQuery = "CREATE TABLE " + OFFERINGS_TABLE + "("
                + FIELD_COURSE_ID + " INTEGER, "
                + FIELD_INSTRUCTOR_ID + " INTEGER, "
                + "FOREIGN KEY(" + FIELD_COURSE_ID + ") REFERENCES "
                + COURSES_TABLE + "(" + COURSES_KEY_FIELD_ID + "), "
                + "FOREIGN KEY(" + FIELD_INSTRUCTOR_ID + ") REFERENCES "
                + INSTRUCTORS_TABLE + "(" + INSTRUCTORS_KEY_FIELD_ID + "))";

        database.execSQL(createQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + INSTRUCTORS_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + OFFERINGS_TABLE);

        onCreate(database);
    }

    //********** COURSE TABLE OPERATIONS:  ADD, GETALL, EDIT, DELETE

    public List<Course> getAllCourses() {
        List<Course> coursesList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(
                COURSES_TABLE,
                new String[]{COURSES_KEY_FIELD_ID, FIELD_ALPHA, FIELD_NUMBER, FIELD_TITLE},
                null,
                null,
                null, null, null, null);

        //COLLECT EACH ROW IN THE TABLE
        if (cursor.moveToFirst()) {
            do {
                Course course =
                        new Course(cursor.getLong(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3));
                coursesList.add(course);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return coursesList;
    }

    public Course getCourse(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                COURSES_TABLE,
                new String[]{COURSES_KEY_FIELD_ID, FIELD_ALPHA, FIELD_NUMBER, FIELD_TITLE},
                COURSES_KEY_FIELD_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Course course = new Course(
                cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));

        cursor.close();
        db.close();
        return course;
    }


    //********** INSTRUCTOR TABLE OPERATIONS:  ADD, GETALL, EDIT, DELETE

    public void addInstructor(Instructor instructor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_LAST_NAME, instructor.getLastName());
        values.put(FIELD_FIRST_NAME, instructor.getFirstName());
        values.put(FIELD_EMAIL, instructor.getEmail());
        values.put(FIELD_DEPARTMENT, instructor.getDepartment());
        values.put(FIELD_BUILDING,instructor.getBuilding());
        values.put(FIELD_ROOM,instructor.getRoom());
        values.put(FIELD_M_HOURS,instructor.getMonday());
        values.put (FIELD_T_HOURS,instructor.getTuesday());
        values.put(FIELD_W_HOURS,instructor.getWednesday());
        values.put(FIELD_R_HOURS,instructor.getThursday());
        values.put(FIELD_F_HOURS,instructor.getFriday());


        db.insert(INSTRUCTORS_TABLE, null, values);

        // CLOSE THE DATABASE CONNECTION
        db.close();
    }

    public List<Instructor> getAllInstructors() {
        List<Instructor> instructorsList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(
                INSTRUCTORS_TABLE,
                new String[]{INSTRUCTORS_KEY_FIELD_ID, FIELD_LAST_NAME, FIELD_FIRST_NAME, FIELD_EMAIL, FIELD_DEPARTMENT,FIELD_BUILDING,FIELD_ROOM,FIELD_M_HOURS,FIELD_T_HOURS,FIELD_W_HOURS,FIELD_R_HOURS,FIELD_F_HOURS},
                null,
                null,
                null, null, null, null);

        //COLLECT EACH ROW IN THE TABLE
        if (cursor.moveToFirst()) {
            do {
                Instructor instructor =
                        new Instructor(cursor.getLong(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7),
                                cursor.getString(8),
                                cursor.getString(9),
                                cursor.getString(10),
                                cursor.getString(11));
                instructorsList.add(instructor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return instructorsList;
    }

    public void deleteInstructor(Instructor instructor) {
        SQLiteDatabase db = this.getWritableDatabase();

        // DELETE THE TABLE ROW
        db.delete(INSTRUCTORS_TABLE, INSTRUCTORS_KEY_FIELD_ID + " = ?",
                new String[]{String.valueOf(instructor.getId())});
        db.close();
    }

    public void deleteAllInstructors() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(INSTRUCTORS_TABLE, null, null);
        db.close();
    }

    public void updateInstructor(Instructor instructor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_FIRST_NAME, instructor.getFirstName());
        values.put(FIELD_LAST_NAME, instructor.getLastName());
        values.put(FIELD_EMAIL, instructor.getEmail());
        values.put(FIELD_DEPARTMENT, instructor.getDepartment());
        values.put(FIELD_BUILDING,instructor.getBuilding());
        values.put(FIELD_ROOM,instructor.getRoom());
        values.put(FIELD_M_HOURS,instructor.getMonday());
        values.put (FIELD_T_HOURS,instructor.getTuesday());
        values.put(FIELD_W_HOURS,instructor.getWednesday());
        values.put(FIELD_R_HOURS,instructor.getThursday());
        values.put(FIELD_F_HOURS,instructor.getFriday());

        db.update(INSTRUCTORS_TABLE, values, INSTRUCTORS_KEY_FIELD_ID + " = ?",
                new String[]{String.valueOf(instructor.getId())});
        db.close();
    }

    public Instructor getInstructor(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                INSTRUCTORS_TABLE,
                new String[]{INSTRUCTORS_KEY_FIELD_ID, FIELD_LAST_NAME, FIELD_FIRST_NAME, FIELD_EMAIL, FIELD_DEPARTMENT,FIELD_BUILDING,FIELD_ROOM,FIELD_M_HOURS,FIELD_T_HOURS,FIELD_W_HOURS,FIELD_R_HOURS,FIELD_F_HOURS},
                INSTRUCTORS_KEY_FIELD_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Instructor instructor = new Instructor
                        (cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11));

        cursor.close();
        db.close();
        return instructor;
    }

    /*
    //********** DEPARTMENT TABLE OPERATIONS:  ADD, GETALL, EDIT, DELETE

    public void addDepartment(int id, String department) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DEPARTMENT_KEY_FIELD_ID, id);
        values.put(FIELD_DEPARTMENT_NAME, department);

        db.insert(DEPARTMENT_TABLE, null, values);

        db.close();
    }
    */

    //********** OFFERING TABLE OPERATIONS:  ADD, GETALL, EDIT, DELETE

    public void addOffering(int crn, int semesterCode, long courseId, long instructorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_COURSE_ID, courseId);
        values.put(FIELD_INSTRUCTOR_ID, instructorId);

        db.insert(OFFERINGS_TABLE, null, values);

        // CLOSE THE DATABASE CONNECTION
        db.close();
    }

    public ArrayList<Offering> getAllOfferings() {
        ArrayList<Offering> offeringsList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(
                OFFERINGS_TABLE,
                new String[]{FIELD_COURSE_ID, FIELD_INSTRUCTOR_ID},
                null,
                null,
                null, null, null, null);

        //COLLECT EACH ROW IN THE TABLE
        if (cursor.moveToFirst()) {
            do {
                Course course = getCourse(cursor.getLong(2));
                Instructor instructor = getInstructor(cursor.getLong(3));
                Offering offering = new Offering(cursor.getInt(0),
                        cursor.getInt(1), course, instructor);

                offeringsList.add(offering);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return offeringsList;
    }






    //********** IMPORT FROM CSV OPERATIONS:  Courses, Instructors, Departments, and Offerings

    public boolean importCoursesFromCSV(String csvFileName) {
        AssetManager manager = mContext.getAssets();
        InputStream inStream;
        try {
            inStream = manager.open(csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line;
        try {
            while ((line = buffer.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length != 4) {
                    Log.d("OCC Office Hours", "Skipping Bad CSV Row: " + Arrays.toString(fields));
                    continue;
                }
                int id = Integer.parseInt(fields[0].trim());
                String alpha = fields[1].trim();
                String number = fields[2].trim();
                String title = fields[3].trim();
                //addCourse(new Course(id, alpha, number, title));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean importInstructorsFromCSV(String csvFileName) {
        AssetManager am = mContext.getAssets();
        InputStream inStream = null;
        try {
            inStream = am.open(csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line;
        try {
            while ((line = buffer.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length != 12) {
                    Log.d("OCC Office Hours", "Skipping Bad CSV Row: " + Arrays.toString(fields));
                    continue;
                }
                int id = Integer.parseInt(fields[0].trim());
                String lastName = fields[1].trim();
                String firstName = fields[2].trim();
                String email = fields[3].trim();
                String departments = fields[4].trim();
                String building = fields[5].trim();
                String room = fields[6].trim();
                String monday = fields[7].trim();
                String tuesday = fields[8].trim();
                String wednesday = fields[9].trim();
                String thursday = fields[10].trim();
                String friday = fields[11].trim();

                //Log.i("Name: ", ""+firstName);

                addInstructor(new Instructor(id, lastName, firstName, email, departments,building,room,monday,tuesday,wednesday,thursday,friday));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean importOfferingsFromCSV(String csvFileName) {
        AssetManager am = mContext.getAssets();
        InputStream inStream = null;
        try {
            inStream = am.open(csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line;
        try {
            while ((line = buffer.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length != 4) {
                    Log.d("OCC Office Hours", "Skipping Bad CSV Row: " + Arrays.toString(fields));
                    continue;
                }
                int crn = Integer.parseInt(fields[0].trim());
                int semesterCode = Integer.parseInt(fields[1].trim());
                long courseId = Long.parseLong(fields[2].trim());
                long instructorId = Long.parseLong(fields[3].trim());
                addOffering(crn, semesterCode, courseId, instructorId);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
    public boolean importDepartmentFromCSV(String csvFileName) {
        AssetManager am = mContext.getAssets();
        InputStream inStream = null;
        try {
            inStream = am.open(csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line;
        try {
            while ((line = buffer.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length != 2) {
                    Log.d("OCC Office Hours", "Skipping Bad CSV Row: " + Arrays.toString(fields));
                    continue;
                }
                int departmentId = Integer.parseInt(fields[0].trim());
                String departmentName = fields[1].trim();

                addDepartment(departmentId, departmentName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    */
}

