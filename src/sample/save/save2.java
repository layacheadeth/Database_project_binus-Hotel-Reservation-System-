package sample.save;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import sample.database.Hotel;
import sample.database.database_handler;
import sample.database.save_model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class save2 {

    @FXML
    private Button movie;

    @FXML
    private Button save;

    @FXML
    private Button book;

    @FXML
    private Button user_setting;

    @FXML
    private Button logout;

    @FXML
    private VBox item_box;

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
    private Button remove;

    @FXML
    private Button book_act;

    @FXML
    private TextField txt_name;

    private Connection con=null;
    private PreparedStatement pst=null;
    private ResultSet result=null;
    database_handler da=new database_handler();
    private ObservableList<Hotel> data=null;

    @FXML
    void book() {

    }

    @FXML
    void book_act() {

    }

    @FXML
    void logout() {

    }

    @FXML
    void movie() {

    }

    @FXML
    void remove() {

    }

    @FXML
    void save() {

    }

    @FXML
    void search() {

    }

    @FXML
    void user_setting() {

    }

    public void initialize() throws SQLException {
        try {
            con = da.getDbconnection();
            data= FXCollections.observableArrayList();
            setCelltable();
            loaddatabase();
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
        finally {
            pst.close();
        }
    }
    public void setCelltable(){
        column_hotel.setCellValueFactory(new PropertyValueFactory<>("hotel"));
        column_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        column_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        column_image.setPrefWidth(150);
        column_image.setCellValueFactory(new PropertyValueFactory<>("image"));
    }
    public void loaddatabase(){
        try{
            pst=con.prepareStatement("select * from listing");
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
                data.add(new Hotel(emp1photo,result.getInt(1),result.getString(2),result.getString(3),result.getDouble(4)));
            }

            tableHotel.setItems(data) ;
        }
        catch (Exception e){
            System.out.print(e.getMessage());
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


}