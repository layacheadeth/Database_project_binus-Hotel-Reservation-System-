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
    private Label valid_name;

    @FXML
    private Label valid_number;

    @FXML
    private Label valid_date;

    @FXML
    private Label valid_security;

    @FXML
    private Label valid_zip;

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
        boolean iscard_name_empty=validation.isTextfieldnotempty(card_name,valid_name,"name is required");
        boolean iscard_number_empty=validation.isTextfieldnotempty(card_number,valid_number,"number is required");
        boolean iscard_date_empty=validation.isTextfieldnotempty(card_date,valid_date,"date is required");
        boolean iscard_sc_number_empty=validation.isTextfieldnotempty(card_sc_number,valid_security,"security number is required");
        boolean iscard_zip_empty=validation.isTextfieldnotempty(card_zip,valid_zip,"zip is required");


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
