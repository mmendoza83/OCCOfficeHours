package edu.orangecoastcollege.cs273.occofficehours;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class InstructorDetailsActivity extends AppCompatActivity {

    private TextView mNameTextView;
    private TextView mEmailTextView;
    private TextView mDepartmentTextView;
    private TextView mOfficeRoomTextView;

    private TextView mMondayHoursTextView;
    private TextView mTuesdayHoursTextView;
    private TextView mWednesdayHoursTextView;
    private TextView mThursdayHoursTextView;
    private TextView mFridayHoursTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_details);

        Intent detailsIntent = getIntent();
        String fullName = detailsIntent.getStringExtra("Name");
        final String email = detailsIntent.getStringExtra("Email");
        String department = detailsIntent.getStringExtra("Department");
        String building = detailsIntent.getStringExtra("Building");
        String room = detailsIntent.getStringExtra("Room");

        final String monday = detailsIntent.getStringExtra("Monday");
        final String tuesday = detailsIntent.getStringExtra("Tuesday");
        final String wednesday = detailsIntent.getStringExtra("Wednesday");
        final String thursday = detailsIntent.getStringExtra("Thursday");
        final String friday = detailsIntent.getStringExtra("Friday");

        final Intent newIntent = new Intent(InstructorDetailsActivity.this, EmailActivity.class);


        mNameTextView = (TextView) findViewById(R.id.nameTextView);
        mEmailTextView = (TextView) findViewById(R.id.emailTextView);
        mDepartmentTextView = (TextView) findViewById(R.id.departmentTextView);
        mOfficeRoomTextView = (TextView) findViewById(R.id.officeRoomTextView);

        mMondayHoursTextView = (TextView) findViewById(R.id.mondayHoursTextView);
        mTuesdayHoursTextView = (TextView) findViewById(R.id.tuesdayHoursTextView);
        mWednesdayHoursTextView = (TextView) findViewById(R.id.wednesdayHoursTextView);
        mThursdayHoursTextView = (TextView) findViewById(R.id.thursdayHoursTextView);
        mFridayHoursTextView = (TextView) findViewById(R.id.fridayHoursTextView);

        mNameTextView.setText(fullName);

        mEmailTextView.setText("Email: " + email);

        newIntent.putExtra("Recipient", email);

        mDepartmentTextView.setText("Departments: " + department);
        mOfficeRoomTextView.setText("Room: " + building + " " + room);

        mMondayHoursTextView.setText(" " + monday);
        mMondayHoursTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mMondayHoursTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIntent.putExtra("Monday", monday);
                startActivity(newIntent);
            }
        });

        mTuesdayHoursTextView.setText(" " + tuesday);
        mTuesdayHoursTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mTuesdayHoursTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIntent.putExtra("Tuesday", tuesday);
                startActivity(newIntent);
            }
        });

        mWednesdayHoursTextView.setText(" " + wednesday);
        mWednesdayHoursTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mWednesdayHoursTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIntent.putExtra("Wednesday", wednesday);
                startActivity(newIntent);
            }
        });

        mThursdayHoursTextView.setText(" " + thursday);
        mThursdayHoursTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mThursdayHoursTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIntent.putExtra("Thursday", thursday);
                startActivity(newIntent);
            }
        });

        mFridayHoursTextView.setText(" " + friday);
        mFridayHoursTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mFridayHoursTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIntent.putExtra("Friday", friday);
                startActivity(newIntent);
            }
        });
    }
}
