package edu.orangecoastcollege.cs273.occofficehours;

/**
 * Created by on 12/1/2017.
 */

public class Instructor {
    private long mId;
    private String mLastName;
    private String mFirstName;
    private String mEmail;
    private String mDepartment;
    private String mBuilding;
    private String mRoom;
    private String mMonday;
    private String mTuesday;
    private String mWednesday;
    private String mThursday;
    private String mFriday;

    public Instructor(long id, String lastName, String firstName, String email, String department,String building,String room,String monday,String tuesday, String wednesday,String thursday,String friday) {
        mId = id;
        mLastName = lastName;
        mFirstName = firstName;
        mEmail = email;
        mDepartment = department;
        mBuilding = building;
        mRoom = room;
        mMonday=monday;
        mTuesday = tuesday;
        mWednesday = wednesday;
        mThursday = thursday;
        mFriday = friday;


    }

    public Instructor(String lastName, String firstName, String email, String department,String building,String room,String monday,String tuesday, String wednesday,String thursday,String friday) {
        this(-1, lastName, firstName, email, department,building,room,monday,tuesday,wednesday,thursday,friday);
    }

    /**
     *
     * @return the id of the instructor
     */
    public long getId() {
        return mId;
    }

    /**
     * set the id of the instructor
     * @param id
     */
    void setId(int id)
    {
        mId = id;
    }

    /**
     *
     * @return the last name of the instructor
     */
    public String getLastName() {
        return mLastName;
    }


    /**
     *
     * @return the first name of the instructor
     */
    public String getFirstName() {
        return mFirstName;
    }

    /**
     *
     * @return the full name of the instructor (first name + last name)
     */
    public String getFullName() {
        return mFirstName + " " + mLastName;
    }

    /**
     *
     * @return the email address of the instructor
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     *
     * @return the department of the instructor
     */
    public String getDepartment() { return mDepartment; }

    /**
     *
     * @return the building of the instructor office
     */
    public String getBuilding() {
        return mBuilding;
    }

    /**
     *
     * @return the room number of the instructor
     */
    public String getRoom() {
        return mRoom;
    }

    /**
     *
     * @return the instructor office hours on monday
     */
    public String getMonday() {
        return mMonday;
    }

    /**
     *
     * @return the instructor office hours on tuesday
     */
    public String getTuesday() {
        return mTuesday;
    }

    /**
     *
     * @return the instructor office hours on wednesday
     */
    public String getWednesday() {
        return mWednesday;
    }

    /**
     *
     * @return the instructor office hours on thursday
     */
    public String getThursday() {
        return mThursday;
    }

    /**
     *
     * @return the instructor office hours on friday
     */
    public String getFriday() {
        return mFriday;
    }


    @Override
    public String toString() {
        return "Instructor{" +
                "Id=" + mId +
                ", LastName='" + mLastName + '\'' +
                ", FirstName='" + mFirstName + '\'' +
                ", Email='" + mEmail + '\'' +
                ", Department='" + mDepartment + '\'' +
                ", Building='" + mBuilding + '\'' +
                ", Room='" + mRoom + '\'' +
                ", Monday='" + mMonday + '\'' +
                ", Tuesday='" + mTuesday + '\'' +
                ", Wednesday='" + mWednesday + '\'' +
                ", Thursday='" + mThursday + '\'' +
                ", Friday='" + mFriday + '\'' +
                '}';
    }
}
