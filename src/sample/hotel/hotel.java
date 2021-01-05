package sample.hotel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sample.database.Hotel;
import sample.database.database_handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import sample.listing.*;
import javafx.scene.control.*;
import sample.main.mainController;
import sample.user_setting.*;
import sample.save.*;

public class hotel {

    @FXML
    private Button booked;

    @FXML
    private Button save;

    @FXML
    private Button hotel;

    @FXML
    private Button trailer;

    @FXML
    private Button logout;

    @FXML
    private Button search;

    @FXML
    private TextField destination;

    @FXML
    private DatePicker txt_checkin;

    @FXML
    private ComboBox<Integer> adult;

    @FXML
    private ComboBox<Integer> children;

    @FXML
    private DatePicker txt_checkout;

    private ObservableList<Hotel> data;

    @FXML
    private Label pass;

    @FXML
    private Label user;


    private Connection con=null;
    private PreparedStatement pst=null;
    private ResultSet result=null;
    database_handler da=new database_handler();



    @FXML
    void booked() {
        try{
            booked.getScene().getWindow().hide();
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../main/main.fxml"));
            Parent root=loader.load();
            mainController m=loader.getController();
            m.init_data(user.getText(),pass.getText());
            stage.setTitle("Main");
            stage.setResizable(false);
            stage.setScene(new Scene(root, 900, 700));
            stage.show();
        }catch (Exception e){
            System.out.print(e.getMessage());
        }


    }

    @FXML
    void hotel() {

    }

    String pass_username;
    String pass_password;

    public void init_data(String username,String password){
        pass_username=username;
        pass_password=password;
        pass.setText(password);
        user.setText(username);
        user.setVisible(false);
        pass.setVisible(false);

    }

    @FXML
    void logout() {
        try{
            logout.getScene().getWindow().hide();
            Stage stage=new Stage();
            Parent root= FXMLLoader.load(getClass().getResource("../sample.fxml"));
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.setScene(new Scene(root,700,500));
            stage.show();
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }

    }

    @FXML
    void save() {
        try{
            save.getScene().getWindow().hide();
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../save/save_sample.fxml"));
            Parent root=loader.load();
            save s=loader.getController();
            s.init_data(user.getText(),pass.getText());
            stage.setTitle("save");
            stage.setResizable(false);
            stage.setScene(new Scene(root, 900, 700));
            stage.show();
        }catch (Exception e){
            System.out.print(e.getMessage());
        }

    }

    @FXML
    void trailer() {
        try{
            trailer.getScene().getWindow().hide();
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../user_setting/user_setting.fxml"));
            Parent root=loader.load();
            user_setting u=loader.getController();
            u.loadUser_pass(user.getText(),pass.getText());
            stage.setTitle("user_setting");
            stage.setResizable(false);
            stage.setScene(new Scene(root, 900, 700));
            stage.show();
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    @FXML
    void search() {
        String destination_text=destination.getText();
//        LocalDate date_in=txt_checkin.getValue();
//        LocalDate date_out=txt_checkout.getValue();
//        int num_adult=adult.getValue();
//        int num_child=children.getValue();
        try{
            search.getScene().getWindow().hide();
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("../listing/listing.fxml"));
            Parent tableViewParent=loader.load();
            listing li=loader.getController();
            li.init_data(destination_text,user.getText(),pass.getText());
            stage.setTitle("save");
            stage.setResizable(false);
            stage.setScene(new Scene(tableViewParent,900,700));
            stage.show();
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }

    }

    public void initialize(){
        ObservableList<Integer> list=FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10);
        adult.setItems(list);
        children.setItems(list);

    }



}
