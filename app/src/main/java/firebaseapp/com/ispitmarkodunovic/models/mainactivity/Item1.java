package firebaseapp.com.ispitmarkodunovic.models.mainactivity;

import android.os.Parcel;
import android.os.Parcelable;

public class Item1 implements Parcelable {

    //extended class to be able to send copies of it self with parcelable
    // so i can use some data from it later if needed

    private int mImageResource;
    private String mText1;
    private String mText2;

    protected Item1(Parcel in) {
        mImageResource = in.readInt();
        mText1 = in.readString();
        mText2 = in.readString();
    }
    // basicly this method uses the constuctor above to make copies of my object
    // if i pass a position to this later i can get a specific item in a list
    // in MwainActivity if i pass the position
    // the order of my constructor and parcel constructor must be same
    public static final Creator<Item1> CREATOR = new Creator<Item1>() {
        @Override
        public Item1 createFromParcel(Parcel in) {
            return new Item1(in);
        }

        @Override
        public Item1[] newArray(int size) {
            return new Item1[size];
        }
    };

    // provera za listener
    public void changeText1(String text){
        mText1 = text;
    }

    public Item1() {
    }

    public Item1(int mImageResource, String mText1, String mText2) {
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mText2 = mText2;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }

    @Override
    public String toString() {
        return "Item1{" +
                "mImageResource=" + mImageResource +
                ", mText1='" + mText1 + '\'' +
                ", mText2='" + mText2 + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mImageResource);
        dest.writeString(mText1);
        dest.writeString(mText2);
    }
}
