package sample.checkout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.database.Hotel;
import sample.database.database_handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;




public class checkout {

    @FXML
    private TextField card_name;

    @FXML
    private TextField card_number;

    @FXML
    private TextField card_date;

    @FXML
    private TextField card_sc_number;

    @FXML
    private TextField card_zip;

    @FXML
    private Button success;

    @FXML
    private Button cancel;

    @FXML
    private Label L_hotel;

    @FXML
    private Label L_check_in;

    @FXML
    private Label L_check_out;

    @FXML
    private Label L_price;

    private Hotel selected_hotel;


    private Connection con=null;
    private PreparedStatement pst=null;
    private ResultSet result=null;
    database_handler da=new database_handler();

    public void init_data(Hotel hotel){
        selected_hotel=hotel;
        L_hotel.setText(selected_hotel.getHotel());
        L_check_in.setText(selected_hotel.getCheck_in());
        L_check_out.setText(selected_hotel.getCheck_out());
        L_price.setText(String.valueOf(selected_hotel.getPrice()));
    }

    @FXML
    void cancel() {
       cancel.getScene().getWindow().hide();
    }

    @FXML
    void success(ActionEvent event) {
        String regex = "[0-9]+";
        String regex1="^[a-zA-Z]+$";
        String regext2="^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
        String regex3="^[0-9]{3,4}$";

        String s_card_name=card_name.getText();
        String s_card_number=card_number.getText();
        String s_card_date=card_date.getText();
        String s_card_sc_number=card_sc_number.getText();
        String s_card_zip=card_zip.getText();
        boolean s=s_card_name.isEmpty()||s_card_number.isEmpty()||s_card_date.isEmpty()||s_card_sc_number.isEmpty()||s_card_zip.isEmpty();

        if(s_card_name.isEmpty()||s_card_number.isEmpty()||s_card_date.isEmpty()||s_card_sc_number.isEmpty()||s_card_zip.isEmpty()) {
            Alert alert1=new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Error found");
            alert1.setContentText("all field must be filled in");
            alert1.showAndWait();
        }
        else if(!(card_number.getText().matches(regex))){
            Alert alert1=new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Error found");
            alert1.setContentText("card_number must consist of only number");
            alert1.showAndWait();
        }
        else if(!(card_name.getText().matches(regex1))){
            Alert alert1=new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Error found");
            alert1.setContentText("card_name must consist of only charactor or letter");
            alert1.showAndWait();
        }
        else if(!(card_date.getText().matches(regext2))){
            Alert alert1=new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Error found");
            alert1.setContentText("card_date must be in mm/yyyy format");
            alert1.showAndWait();
        }
        else if(!(card_sc_number.getText().matches(regex3))){
            Alert alert1=new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Error found");
            alert1.setContentText("card_cvc number must be in 3 or 4 digit format of number ");
            alert1.showAndWait();
        }
        else if(!(card_zip.getText().matches(regex3))){
            Alert alert1=new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Error found");
            alert1.setContentText("card_zip number must be in 3 or 4 digit format of number ");
            alert1.showAndWait();

        }

        else if(!s){
            String sql="update hotel set status=\"paid\" where hotel=?";
            try{
                con=da.getDbconnection();
                pst=con.prepareStatement(sql);
                pst.setString(1,L_hotel.getText());
                int i=pst.executeUpdate();

                if(i==1){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText("Information");
                    alert.setContentText("Success");
                    alert.showAndWait();
                }

            }catch (Exception e){
                System.out.print(e.getMessage());
            }
        }





    }


}
