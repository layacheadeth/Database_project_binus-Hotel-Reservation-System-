package sample.condition;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.*;
import sample.database.database_handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import sample.database.*;

public class condition_save {

    @FXML
    private DatePicker date_in;

    @FXML
    private DatePicker date_out;

    @FXML
    private ComboBox<Integer> num_child;

    @FXML
    private ComboBox<Integer> num_adult;

    @FXML
    private Button submit;

    @FXML
    private Label id;

    private Connection con=null;
    private PreparedStatement pst=null;
    private ResultSet result=null;
    database_handler da=new database_handler();

    private Hotel selected_hotel;


    public void init_data(Hotel hotel){
        selected_hotel=hotel;
        id.setText(String.valueOf(selected_hotel.getId()));
    }
    @FXML
    void date_in(ActionEvent event) {

    }

    @FXML
    void date_out(ActionEvent event) {

    }

    @FXML
    void num_adult(ActionEvent event) {

    }

    @FXML
    void num_child(ActionEvent event) {

    }

    public void submit_result(){
        String sql="insert into hotel\n" +
                "select Listing.id,Listing.hotel,Listing.location,Listing.price,Listing.image,Listing.roomtype,\n" +
                "booking_time_save.check_in,booking_time_save.check_out,booking_time_save.amount_of_people,booking_time_save.status\n" +
                " from Listing inner join booking_time_save on Listing.id=booking_time_save.listing_id where Listing.id=?";
        try{
            con=da.getDbconnection();
            pst=con.prepareStatement(sql);
            pst.setInt(1,Integer.parseInt(id.getText()));
            int i=pst.executeUpdate();
            if(i==1){
                System.out.print("Success");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText("Information");
                alert.setContentText("Success");
                alert.showAndWait();
            }
            pst.close();
            result.close();


        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }


    @FXML
    void submit(ActionEvent event) {
        String sql="insert into booking_time_save(id,check_in,check_out,num_child,num_adult,listing_id,status,amount_of_people) values(?,?,?,?,?,?,?,?)";
        try{
            con=da.getDbconnection();
            pst=con.prepareStatement(sql);
            pst.setString(1,id.getText());
            pst.setString(2,date_in.getValue().toString());
            pst.setString(3,date_out.getValue().toString());
            pst.setString(4,num_child.getValue().toString());
            pst.setString(5,num_adult.getValue().toString());
            pst.setString(6,id.getText());
            pst.setString(7,"booked");
            pst.setInt(8,(num_adult.getValue()+num_child.getValue()));
            int i=pst.executeUpdate();
            if(i==1){
                System.out.print("Success");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText("Information");
                alert.setContentText("Success");
                alert.showAndWait();
            }
            pst.close();
            result.close();


        }catch (Exception e){
            System.out.print(e.getMessage());
        }
        submit_result();
    }

    public void initialize(){
        System.out.print("Hello world");
        ObservableList<Integer> list= FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10);
        num_adult.setItems(list);
        num_child.setItems(list);
        id.setVisible(false);

    }

}
