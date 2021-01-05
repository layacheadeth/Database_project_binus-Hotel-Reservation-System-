package sample.database;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Hotel {
    private int id;
    private String hotel;
    private String location;
    private String check_in;
    private String check_out;
    private int amount_of_people;
    private double price;
    private String roomtype;
    private ImageView image;
    private String status;

    public Hotel(ImageView image,int id,String hotel, String location,String check_in, String check_out, int amount_of_people, double price,String roomtype,String status) {
        this.id=id;
        this.hotel = hotel;
        this.location=location;
        this.check_in = check_in;
        this.check_out = check_out;
        this.amount_of_people = amount_of_people;
        this.price = price;
        this.image=image;
        this.roomtype=roomtype;
        this.status=status;
    }

    public Hotel(ImageView image,int id,String hotel,String location,int price,String roomtype,String check_in,String check_out,int amount_of_people,String status){
        this.image=image;
        this.id=id;
        this.hotel=hotel;
        this.location=location;
        this.price=price;
        this.roomtype=roomtype;
        this.check_in=check_in;
        this.check_out=check_out;
        this.amount_of_people=amount_of_people;
        this.status=status;
    }



    public Hotel(ImageView image,int id,String hotel, String location,String check_in, String check_out, int amount_of_people, double price) {
        this.id=id;
        this.hotel = hotel;
        this.location=location;
        this.check_in = check_in;
        this.check_out = check_out;
        this.amount_of_people = amount_of_people;
        this.price = price;
        this.image=image;
    }

    public Hotel(ImageView image,int id,String hotel,String location,double price){
        this.id=id;
        this.hotel=hotel;
        this.location=location;
        this.price=price;
        this.image=image;
    }

    public Hotel(ImageView image,int id,String hotel,String location,double price, String roomtype){
        this.id=id;
        this.hotel=hotel;
        this.location=location;
        this.price=price;
        this.image=image;
        this.roomtype=roomtype;
    }


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getLocation(){return location;};

    public void setLocation(String location){this.location=location;}


    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }

    public int getAmount_of_people() {
        return amount_of_people;
    }

    public void setAmount_of_people(int amount_of_people) {
        this.amount_of_people = amount_of_people;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ImageView getImage(){
        return image;
    }


    public void setImage(ImageView image)
    {
        this.image =  image;

    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
