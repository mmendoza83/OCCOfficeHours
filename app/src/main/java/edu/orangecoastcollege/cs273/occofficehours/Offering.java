package edu.orangecoastcollege.cs273.occofficehours;

/**
 * The Offering class represents a single course offering at Orange Coast College
 * This class includes the Instructor Id and the Course Id
 */

public class Offering{
    private Course mCourse;
    private Instructor mInstructor;

    public Offering(Course course, Instructor instructor) {
        mCourse = course;
        mInstructor = instructor;
    }

    public Course getCourse() {
        return mCourse;
    }

    public void setCourse(Course course) {
        mCourse = course;
    }

    public Instructor getInstructor() {
        return mInstructor;
    }

    public void setInstructor(Instructor instructor) {
        mInstructor = instructor;
    }

    @Override
    public String toString() {
        return "Offering{" +
                "Course=" + mCourse +
                ", Instructor=" + mInstructor +
                '}';
    }
}
