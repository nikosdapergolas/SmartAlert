package com.unipi.nickdap.p19039.smartalert;

public class Emergency {
    private String description = "";
    private String emergency = "";
    private String latitude = "";
    private String longtitude = "";
    private String location = "";
    private String timestamp = "";


    public Emergency(){

    }

    public Emergency(String Description, String Emergency, String Latitude, String Longtitude, String Location, String Timestamp)
    {
        this.description = Description;
        this.emergency = Emergency;
        this.latitude = Latitude;
        this.longtitude = Longtitude;
        this.location = Location;
        this.timestamp = Timestamp;
    }

    public String getDescription()
    {
        return description;
    }

    public String getEmergency()
    {
        return emergency;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public String getLongtitude()
    {
        return longtitude;
    }

    public String getLocation()
    {
        return location;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

}
