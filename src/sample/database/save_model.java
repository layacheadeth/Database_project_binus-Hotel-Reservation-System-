package sample.database;

import javafx.scene.image.ImageView;

public class save_model {
    private int id;
    private String hotel;
    private String location;
    private double price;
    private String roomtype;
//    private ImageView imageView;

//    public save_model(ImageView imageView,int id,String hotel, String location, double price, String roomtype) {
//        this.id=id;
//        this.hotel = hotel;
//        this.location = location;
//        this.price = price;
//        this.roomtype = roomtype;
//        this.imageView = imageView;
//    }

    public save_model(int id,String hotel, String location, double price, String roomtype) {
        this.id=id;
        this.hotel = hotel;
        this.location = location;
        this.price = price;
        this.roomtype = roomtype;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

//    public ImageView getImageView() {
//        return imageView;
//    }

//    public void setImageView(ImageView imageView) {
//        this.imageView = imageView;
//    }
}
