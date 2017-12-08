package edu.orangecoastcollege.cs273.occofficehours;

/**
 * A model used for courses, representing the different courses that instructors may teach.
 * Includes a unique id, the course alpha (CS, ENGL, etc), the course number (A273, A250, etc), and
 * the course title (Mobile Application Development, C++ Programming 2, etc).
 *
 * Created by mmendoza on 12/1/2017.
 */
public class Course {
    private long mId;
    private String mAlpha;
    private String mNumber;
    private String mTitle;

    /**
     * Constructor for course objects.
     *
     * @param id The unique id for each course.
     * @param alpha The course's alpha.
     * @param number The course's number.
     * @param title The courses title.
     */
    public Course(long id, String alpha, String number, String title) {
        mId = id;
        mAlpha = alpha;
        mNumber = number;
        mTitle = title;
    }

    /**
     * Gets a course's id number.
     *
     * @return The course id.
     */
    public long getId() {
        return mId;
    }

    /**
     * Sets the course id number.
     *
     * @param id The new id number.
     */
    void setId(int id)
    {
        mId = id;
    }

    /**
     * Gets the course's alpha.
     *
     * @return The course's alpha.
     */
    public String getAlpha() {
        return mAlpha;
    }

    /**
     * Sets the course alpha.
     *
     * @param alpha The new course alpha.
     */
    public void setAlpha(String alpha) {
        mAlpha = alpha;
    }

    /**
     * Gets the course number.
     *
     * @return The course number.
     */
    public String getNumber() {
        return mNumber;
    }

    /**
     * Sets the course's number.
     *
     * @param number The new course number.
     */
    public void setNumber(String number) {
        mNumber = number;
    }

    /**
     * Gets the course title.
     *
     * @return The course title.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Sets the course title.
     *
     * @param title The new course title.
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * Constructs a string containing the course alpha, number, and title.
     * Used to display all of the course's information.
     * Ex: CS A273 Mobile Application Development
     *
     * @return The course's full name (alpha + number + title).
     */
    public String getFullName() {
        return mAlpha + " " + mNumber + " " + mTitle;
    }

    /**
     * Method used for displaying all the course's information including its id.
     * Formats the data into one string.
     *
     * @return The string containing the formatted data.
     */
    @Override
    public String toString() {
        return "Course{" +
                "Id=" + mId +
                ", Alpha='" + mAlpha + '\'' +
                ", Number='" + mNumber + '\'' +
                ", Title='" + mTitle + '\'' +
                '}';
    }
}
