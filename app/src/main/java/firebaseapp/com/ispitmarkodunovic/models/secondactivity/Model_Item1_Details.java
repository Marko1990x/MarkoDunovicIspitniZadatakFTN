package firebaseapp.com.ispitmarkodunovic.models.secondactivity;

public class Model_Item1_Details {

    private int mImageDetails;
    private String mTheBlastedDate1;  // formater to string from date current time or whatever time
    private String mTheBlastedDate2;
    private String mTheFullDescription;

    public Model_Item1_Details() {
    }

    public Model_Item1_Details(int mImageDetails, String mTheBlastedDate1, String mTheBlastedDate2, String mTheFullDescription) {
        this.mImageDetails = mImageDetails;
        this.mTheBlastedDate1 = mTheBlastedDate1;
        this.mTheBlastedDate2 = mTheBlastedDate2;
        this.mTheFullDescription = mTheFullDescription;
    }

    public int getmImageDetails() {
        return mImageDetails;
    }

    public void setmImageDetails(int mImageDetails) {
        this.mImageDetails = mImageDetails;
    }

    public String getmTheBlastedDate1() {
        return mTheBlastedDate1;
    }

    public void setmTheBlastedDate1(String mTheBlastedDate1) {
        this.mTheBlastedDate1 = mTheBlastedDate1;
    }

    public String getmTheBlastedDate2() {
        return mTheBlastedDate2;
    }

    public void setmTheBlastedDate2(String mTheBlastedDate2) {
        this.mTheBlastedDate2 = mTheBlastedDate2;
    }

    public String getmTheFullDescription() {
        return mTheFullDescription;
    }

    public void setmTheFullDescription(String mTheFullDescription) {
        this.mTheFullDescription = mTheFullDescription;
    }

    @Override
    public String toString() {
        return "Model_Item1_Details{" +
                "mImageDetails=" + mImageDetails +
                ", mTheBlastedDate1='" + mTheBlastedDate1 + '\'' +
                ", mTheBlastedDate2='" + mTheBlastedDate2 + '\'' +
                ", mTheFullDescription='" + mTheFullDescription + '\'' +
                '}';
    }
}
