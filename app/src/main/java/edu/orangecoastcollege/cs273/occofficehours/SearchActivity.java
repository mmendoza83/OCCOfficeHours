package edu.orangecoastcollege.cs273.occofficehours;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    /**
     * Starts the SearchByInstructorActivity.
     * Allows the list to be filtered by an instructor's name,
     * showing all of their courses.
     *
     * @param v
     */
    public void searchInstructors(View v)
    {
        Intent searchByInstructorsIntent = new Intent(this, SearchByInstructorActivity.class);
        startActivity(searchByInstructorsIntent);
    }

    /**
     * Starts the SearchByCourseActivity.
     * Allows the list to be filtered by a course title,
     * showing all the instructors that teach that course.
     *
     * @param v
     */
    public void searchCourses(View v)
    {
        Intent searchByCourse = new Intent(this, SearchByCourseActivity.class);
        startActivity(searchByCourse);
    }

    /**
     * Starts the SearchByDepartmentsActivity.
     * Allows the list to be filtered by a department,
     * showing all the courses with instructors within the department.
     *
     * @param v
     */
    public void searchDepartments(View v)
    {
        Intent searchByDepartmentIntent = new Intent(this, SearchByDepartmentActivity.class);
        startActivity(searchByDepartmentIntent);
    }

    /**
     * Starts the OCCMapActivity.
     * Displays Orange Coast College on Google maps and its address.
     *
     * @param v
     */
    public void showMap(View v)
    {
        Intent occMapIntent = new Intent(this, OCCMapActivity.class);
        startActivity(occMapIntent);
    }
}