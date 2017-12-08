package edu.orangecoastcollege.cs273.occofficehours;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
//import android.widget.ThemedSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchByDepartmentActivity extends AppCompatActivity {

    private DBHelper db;
    private List<Instructor> allDepartmentsList;
    private List<Offering> allOfferingsList;
    private List<Offering> filteredOfferingsList;
    private OfferingListAdapter offeringListAdapter;

    private Spinner instructorSpinner;
    private ListView offeringsListView;

    private OfferingListAdapter offeringsListAdapter;
    private ImageView clockAnim;

    // Shake animation, used when the user clicks the reset button
    private Animation shakeAnim;
    private Animation rotateAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_department);

        deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);

        db.importCoursesFromCSV("courses.csv");
        db.importInstructorsFromCSV("instructors.csv");
        db.importOfferingsFromCSV("offerings.csv");

        allOfferingsList = db.getAllOfferings();
        filteredOfferingsList = new ArrayList<>(allOfferingsList);
        allDepartmentsList = db.getAllInstructors();
        clockAnim = (ImageView) findViewById(R.id.clockAnimImageView);


        Log.i("Number of instructors: ", "" + allDepartmentsList.size());

        instructorSpinner = (Spinner) findViewById(R.id.departmentSpinner);

        offeringsListView = (ListView) findViewById(R.id.offeringsListView);
        offeringListAdapter =
                new OfferingListAdapter(this, R.layout.offering_list_item, filteredOfferingsList);
        offeringsListView.setAdapter(offeringListAdapter);

        ArrayAdapter<String> departmentSpinnerAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getDepartments());
        instructorSpinner.setAdapter(departmentSpinnerAdapter);
        instructorSpinner.setOnItemSelectedListener(instructorSpinnerListener);
    }

    /**
     * Collects all departments into an array.
     * Used to populate the spinner.
     *
     * @return
     */
    private String[] getDepartments()
    {
        ArrayList<String> departments = new ArrayList<>();
        for (int i = 0; i < allDepartmentsList.size(); ++i)
        {
            String department = allDepartmentsList.get(i).getDepartment();
            if (!departments.contains(department)) {
                departments.add(department);
            }
        }

        Log.i("Number of Departments: ", "" + departments.size());
        String[] allDepartments = new String[departments.size() + 1];
        allDepartments[0] = "[Select Department]";
        for (int i = 0; i < departments.size(); ++i)
            allDepartments[i + 1] = departments.get(i);

        return allDepartments;
    }

    /**
     * Resets the filter of the list to show all possible items in the list.
     * Plays a shake animation on the list.
     *
     * @param v
     */
    public void reset(View v)
    {
        toggleShakeAnim(v);
        // Set spinner back to position 0
        instructorSpinner.setSelection(0);
        // Clear out the list adapter
        offeringListAdapter.clear();
        // Repopulate it from allOfferingsList
        offeringListAdapter.addAll(allOfferingsList);
    }

    public AdapterView.OnItemSelectedListener instructorSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> spinner, View view, int i, long l) {
            // Retrieve the instructor name
            String departmentName = String.valueOf(spinner.getItemAtPosition(i));
            // Clear the adapter
            offeringListAdapter.clear();
            if (departmentName.equals("[Select Department]"))
                offeringListAdapter.addAll(allOfferingsList);
            else
                for (Offering offering : allOfferingsList)
                    if (offering.getInstructor().getDepartment().equals(departmentName))
                        offeringListAdapter.add(offering);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    /**
     * Plays the animation from shake_anim.xml
     * Shakes the image horizontally.
     * Used in reset function.
     *
     * @param v
     */
    public void toggleShakeAnim(View v)
    {
        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_anim);
        offeringsListView.startAnimation(shakeAnim);
    }

    public void toggleRotateAnim(View view ) {
        if (rotateAnim == null)// hasnt been initialize yet
        {
            rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        }
        if (!rotateAnim.hasStarted() || rotateAnim.hasEnded())
            clockAnim.startAnimation(rotateAnim);

            //connect it to the image view
        else
            clockAnim.clearAnimation();
    }
    /**
     * Starts the InstructorDetailsActivity.
     * Sends the selected instructor's details to be displayed for the user.
     *
     * @param v
     */
    public void viewInstructorDetails(View v)
    {
        toggleRotateAnim(v);
        LinearLayout selectedLayout = (LinearLayout) v;
        Offering selectedOffering = (Offering) selectedLayout.getTag();
        Instructor selectedInstructor = (Instructor) selectedOffering.getInstructor();
        Intent detailsIntent = new Intent(this, InstructorDetailsActivity.class);
        detailsIntent.putExtra("Name", selectedInstructor.getFullName());
        detailsIntent.putExtra("Email", selectedInstructor.getEmail());
        detailsIntent.putExtra("Department", selectedInstructor.getDepartment());
        detailsIntent.putExtra("Building", selectedInstructor.getBuilding());
        detailsIntent.putExtra("Room", selectedInstructor.getRoom());
        detailsIntent.putExtra("Monday", selectedInstructor.getMonday());
        detailsIntent.putExtra("Tuesday", selectedInstructor.getTuesday());
        detailsIntent.putExtra("Wednesday", selectedInstructor.getWednesday());
        detailsIntent.putExtra("Thursday", selectedInstructor.getThursday());
        detailsIntent.putExtra("Friday", selectedInstructor.getFriday());
        startActivity(detailsIntent);
    }
}