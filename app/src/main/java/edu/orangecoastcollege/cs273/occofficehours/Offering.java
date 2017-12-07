package edu.orangecoastcollege.cs273.occofficehours;

/**
 * Created by on 12/1/2017.
 */
public class Offering{
    private Course mCourse;
    private Instructor mInstructor;

    public Offering(Course course, Instructor instructor) {
        mCourse = course;
        mInstructor = instructor;
    }

    /**
     *
     * @return
     */
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
