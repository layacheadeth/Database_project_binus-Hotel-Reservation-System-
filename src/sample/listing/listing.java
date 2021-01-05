package sample.listing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.database.Hotel;
import sample.database.database_handler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
import java.time.LocalDate;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.detail_listing.*;
import sample.database.*;
import javafx.scene.control.*;
import sample.user_setting.user_setting;
import sample.hotel.*;


public class listing {

    @FXML
    private Button movie;

    @FXML
    private Button save;

    @FXML
    private Button book;

    @FXML
    private Button logout;

    @FXML
    private VBox item_box;

    @FXML
    private Button detailView;

    @FXML
    private Button user_setting;

    @FXML
    private TableView<Hotel> tableHotel;

    @FXML
    private TableColumn<?, ?> column_image;

    @FXML
    private TableColumn<?, ?> column_hotel;

    @FXML
    private TableColumn<?, ?> column_location;

    @FXML
    private TableColumn<?, ?> column_price;

    @FXML
    private TableColumn<?, ?> column_roomtype;

    @FXML
    private Label user_text;

    @FXML
    private Label pass_text;


    @FXML
    private Button goback;

    private String selected_hotel;

    private Connection con=null;
    private PreparedStatement pst=null;
    private ResultSet result=null;
    database_handler da=new database_handler();
    private ObservableList<Hotel> data=null;

    @FXML
    void book() {

    }

    @FXML
    void logout() {
        try{
            logout.getScene().getWindow().hide();
            Parent root=FXMLLoader.load(getClass().getResource("../sample.fxml"));
            Stage stage=new Stage();
            stage.setResizable(false);
            stage.setTitle("login");
            stage.setScene(new Scene(root,700,500));
            stage.show();
        }catch (Exception e){
            System.out.print(e.getMessage());
        }

    }

    @FXML
    void movie() {
        try{
            book.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../main/main.fxml"));
            Parent root=loader.load();
            sample.main.mainController u=loader.getController();
            u.init_data(user_text.getText(),pass_text.getText());
            Stage stage=new Stage();
            stage.setResizable(false);
            stage.setTitle("book");
            stage.setScene(new Scene(root,900,700));
            stage.show();
        }catch (Exception e){
            System.out.print(e.getMessage());
        }

    }

    @FXML
    void save() {
        try{
            save.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../save/save_sample.fxml"));
            Parent root=loader.load();
            sample.save.save u=loader.getController();
            u.init_data(user_text.getText(),pass_text.getText());
            Stage stage=new Stage();
            stage.setResizable(false);
            stage.setTitle("book");
            stage.setScene(new Scene(root,900,700));
            stage.show();
        }catch (Exception e){
            System.out.print(e.getMessage());
        }

    }

    public void init_data(String hotelName,String text, String text1) throws SQLException{
        selected_hotel=hotelName;
        user_text.setText(text);
        pass_text.setText(text1);
        try {
            con = da.getDbconnection();
            data= FXCollections.observableArrayList();
            setCelltable();
            loaddatabase(hotelName);
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
        finally {
            pst.close();
        }
    }

    public void initialize(){
      user_text.setVisible(false);
      pass_text.setVisible(false);
    }





    public void setCelltable(){
        column_hotel.setCellValueFactory(new PropertyValueFactory<>("hotel"));
        column_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        column_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        column_image.setPrefWidth(150);
        column_image.setCellValueFactory(new PropertyValueFactory<>("image"));
        column_roomtype.setCellValueFactory(new PropertyValueFactory<>("roomtype"));
    }


    public void loaddatabase(String hotelname){
        if(hotelname.isEmpty()){
            try{
                pst=con.prepareStatement("select * from real_Listing");
                result=pst.executeQuery();
                while (result.next()){
                    InputStream bodyOut = result.getBinaryStream("image");
                    String fileOut = "photo" + result.getInt("id") + ".png";
                    saveOutputStream(fileOut, bodyOut);
                    bodyOut.close();
                    System.out.println("   Body = (Saved in " + fileOut + ")");

                    InputStream stream = new FileInputStream(fileOut);
                    Image image = new Image(stream);
                    ImageView emp1photo = new ImageView();
                    emp1photo.setImage(image);
                    emp1photo.setFitHeight(80);
                    emp1photo.setFitWidth(80);
                    data.add(new Hotel(emp1photo,result.getInt(1),result.getString(2),result.getString(3),result.getDouble(4),result.getString(6)));

                }

                tableHotel.setItems(data) ;
            }
            catch (Exception e){
                System.out.print(e.getMessage());
            }
        }
        else {
            try {
                pst = con.prepareStatement("select * from real_Listing where hotel like '%" + hotelname + "%'");
                result = pst.executeQuery();
                while (result.next()) {
                    InputStream bodyOut = result.getBinaryStream("image");
                    String fileOut = "photo" + result.getInt("id") + ".png";
                    saveOutputStream(fileOut, bodyOut);
                    bodyOut.close();
                    System.out.println("   Body = (Saved in " + fileOut + ")");

                    InputStream stream = new FileInputStream(fileOut);
                    Image image = new Image(stream);
                    ImageView emp1photo = new ImageView();
                    emp1photo.setImage(image);
                    emp1photo.setFitHeight(80);
                    emp1photo.setFitWidth(80);
                    data.add(new Hotel(emp1photo,result.getInt(1),result.getString(2),result.getString(3),result.getDouble(4),result.getString(6)));

                }

                tableHotel.setItems(data);
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
    }

    public void saveOutputStream (String name, InputStream body){
        int c=0;
        byte[] content=new byte[1024];
        try {
            OutputStream f = new FileOutputStream(name);
            while ((c = body.read(content)) > -1) {
                f.write(content,0,c);
            }
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void detailView() {
        try{
            if(tableHotel.getSelectionModel().getSelectedItem()==null){
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error not found");
                a.setContentText("You must select one row to value its detail");
                a.showAndWait();
            }
            else{
                Stage stage=new Stage();
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("../detail_listing/detail_listing2.fxml"));
                Parent tableViewParent=loader.load();
                detail_listing2 de=loader.getController();
                de.init_data(tableHotel.getSelectionModel().getSelectedItem());
                stage.setTitle("save");
                stage.setResizable(false);
                stage.setScene(new Scene(tableViewParent, 640, 630));
                stage.show();
            }

        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    @FXML
    void goback() {
        try{
            goback.getScene().getWindow().hide();
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("../hotel/hotel.fxml"));
            Parent tableViewParent=loader.load();
            hotel h=loader.getController();
            h.init_data(user_text.getText(),pass_text.getText());
            stage.setTitle("user_setting");
            stage.setResizable(false);
            stage.setScene(new Scene(tableViewParent, 900, 700));
            stage.show();
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    @FXML
    void user_setting() {
        try{
            user_setting.getScene().getWindow().hide();
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../user_setting/user_setting.fxml"));
            Parent root=loader.load();
            sample.user_setting.user_setting u=loader.getController();
            u.loadUser_pass(user_text.getText(),pass_text.getText());
            user_text.setVisible(false);
            pass_text.setVisible(false);
            stage.setTitle("user_setting");
            stage.setResizable(false);
            stage.setScene(new Scene(root, 900, 700));
            stage.show();

        }catch (Exception e){
            System.out.print(e.getMessage());
        }

    }


}
