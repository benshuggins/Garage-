package com.test.ben.hyperproject;



public class Entries {

    String item_name;
    double price;
    String desc;
    String imagePath;
    String latitude;
    String longitude;
    String location;

    public Entries() {

    }

    public Entries(String item_name, double price, String desc, String imagePath) {

        this.item_name = item_name;
        this.price = price;
        this.desc = desc;
        this.imagePath = imagePath;

    }
    public Entries(String item_name, double price, String desc, String imagePath, String latitude, String longitude, String loc) {

        this.item_name = item_name;
        this.price = price;
        this.desc = desc;
        this.imagePath=imagePath;
        this.latitude=latitude;
        this.longitude=longitude;
        this.location=loc;
    }

    public String get_item_name() {
        return item_name;
    }

    public void set_item_name(String _item_name) {
        this.item_name = _item_name;
    }

    public double get_price() {
        return price;
    }

    public void set_price(double _price) {
        this.price = _price;
    }

    public String get_desc() {
        return desc;
    }

    public void set_desc(String _desc) {
        this.desc = _desc;
    }

    public String get_imagePath() {return imagePath;}

    public void set_imagePath(String _imagePath) {this.imagePath = _imagePath;}

    public String get_latitude() {return latitude;}

    public void set_latitude(String _latitude) {this.latitude = _latitude;}

    public String get_longitude() {return longitude;}

    public void set_longitude(String _longitude) {this.longitude = _longitude;}

    public String get_location() {return location;}

    public void set_location(String _location) {this.location = _location;}
}
