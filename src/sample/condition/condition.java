package sample.condition;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.database.Hotel;
import sample.database.database_handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class condition {

    @FXML
    private DatePicker date_in;

    @FXML
    private DatePicker date_out;

    @FXML
    private ComboBox<Integer> num_child;

    @FXML
    private ComboBox<Integer> num_adult;

    @FXML
    private Label user_text;


    private Connection con=null;
    private PreparedStatement pst=null;
    private ResultSet result=null;
    database_handler da=new database_handler();

    @FXML
    private Label id;

    private String id_1;

    private Hotel selected_hotel;
    private String user_text1;
    @FXML
    private Label name_id;



    public void init_data(String id_1,String user_text1,String id2){
        this.id_1=id_1;
        id.setText(id_1);
        this.user_text1=user_text1;
        user_text.setText(user_text1);
        name_id.setText(id2);
    }

    @FXML
    void date_in() {

    }

    @FXML
    void date_out() {

    }

    @FXML
    void num_adult() {

    }

    @FXML
    void num_child() {

    }

    public void submit_result(){
        String sql="insert into hotel\n" +
                "select real_Listing.id,real_Listing.hotel,real_Listing.location,real_Listing.price,real_Listing.image,real_Listing.roomtype,\n" +
                "booking_time.check_in,booking_time.check_out,booking_time.amount_of_people,booking_time.status,booking_time.name,booking_time.name_id\n" +
                " from real_Listing inner join booking_time on real_Listing.id=booking_time.listing_id where real_Listing.id=? AND name=?";
        try{
            con=da.getDbconnection();
            pst=con.prepareStatement(sql);
            pst.setInt(1,Integer.parseInt(id.getText()));
            pst.setString(2,user_text.getText());
            int i=pst.executeUpdate();
            if(i==1){
                System.out.print("Success");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText("Information");
                alert.setContentText("Success");
                alert.showAndWait();
            }
            else{
                System.out.print("Error");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText("Error");
                alert.setContentText("this room has been boked by other user");
                alert.showAndWait();

            }
            pst.close();
            result.close();


        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }


    @FXML
    void submit() {

        String sql="insert into booking_time(id,check_in,check_out,num_child,num_adult,listing_id,status,amount_of_people,name,name_id) values(?,?,?,?,?,?,?,?,?,?)";
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
            pst.setString(9,user_text.getText());
            pst.setString(10,id.getText());
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
