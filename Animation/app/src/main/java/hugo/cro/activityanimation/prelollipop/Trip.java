package hugo.cro.activityanimation.prelollipop;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hugocrochetiere on 2014-08-24.
 */
public class Trip {


    //region Variable

    private int tripID;
    private String title;
    private String description;
    //endregion

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    //region Contructor

    public Trip() {

    }

    //endregion

    //region Parcelable

    public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {

        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public Trip(Parcel parcel) {
      // parcel.readInt();
    }

    //endregion

}
