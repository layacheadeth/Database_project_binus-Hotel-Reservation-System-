package sample.save;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.condition.condition;
import sample.database.Hotel;
import sample.database.database_handler;
import sample.main.*;
import javafx.scene.layout.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sample.user_setting.user_setting;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.*;
import sample.hotel.*;
import sample.condition.*;


public class save {

    @FXML
    private Button movie;

    @FXML
    private Button save;
    
    @FXML
    private Button book;

    @FXML
    private VBox item_box=null;

    @FXML
    private Button logout;

    @FXML
    private TableView<Hotel> tableHotel;

    @FXML
    private TableColumn<Hotel, ?> column_image;

    @FXML
    private TableColumn<Hotel, ?> column_hotel;

    @FXML
    private TableColumn<Hotel, ?> column_location;

    @FXML
    private TableColumn<Hotel, ?> column_price;

    @FXML
    private TableColumn<?, ?> column_roomtype;

    @FXML
    private Button remove;

    @FXML
    private Button book_act;

    @FXML
    private Button user_setting;

    @FXML
    private TextField txt_name;


    private Connection con=null;
    private PreparedStatement pst=null;
    private ResultSet result=null;
    database_handler da=new database_handler();
    private ObservableList<Hotel> data=null;
    String txt_user;
    String txt_pass;

    @FXML
    private Label user;

    @FXML
    private Label pass;

    private Hotel selected_hotel;



    @FXML
    void book() {
        try{
            book.getScene().getWindow().hide();
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../hotel/hotel.fxml"));
            Parent root=(Parent)loader.load();
            hotel h=loader.getController();
            h.init_data(user.getText(),pass.getText());
            stage.setTitle("Hotel");
            stage.setScene(new Scene(root,900,700));
            stage.show();
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }

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
    void movie() {

        try{
            movie.getScene().getWindow().hide();
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../main/main.fxml"));
            Parent root=(Parent)loader.load();
            mainController mc=loader.getController();
            mc.init_data(user.getText(),pass.getText());
            stage.setTitle("Main");
            stage.setResizable(false);
            stage.setScene(new Scene(root, 900, 700));
            stage.show();
        }catch (Exception e){
            System.out.print(e.getMessage());
        }

    }

    @FXML
    void save() {

    }

    public void initialize(){
        try {
            con = da.getDbconnection();
            data= FXCollections.observableArrayList();
            setCelltable();
            loaddatabase();
            setCelltable_to_Textfield();
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    public void setCelltable(){
        column_hotel.setCellValueFactory(new PropertyValueFactory<>("hotel"));
        column_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        column_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        column_image.setPrefWidth(150);
        column_image.setCellValueFactory(new PropertyValueFactory<>("image"));
        column_roomtype.setCellValueFactory(new PropertyValueFactory<>("roomtype"));
    }

    public void loaddatabase(){

            try{
                pst=con.prepareStatement("select * from Listing");
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

    public void init_data(String user_text,String pass_text){
        user.setText(user_text);
        pass.setText(pass_text);
        user.setVisible(false);
        pass.setVisible(false);
    }

    @FXML
    void remove() throws SQLException {
        data.clear();
        String sql="Delete from listing where hotel = ?";
        try{
            con=da.getDbconnection();
            pst=con.prepareStatement(sql);
            pst.setString(1,txt_name.getText());

            int i=pst.executeUpdate();
            if(i==1){
                Alert a=new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Delete");
                a.setHeaderText("Delete success");
                a.setContentText("Delete successfully");
                loaddatabase();
            }
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
        finally {
            pst.close();
        }
    }

    @FXML
    void book_act() {

        try{
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("../condition/condition_save.fxml"));
            Parent tableViewParent=loader.load();
            condition_save c=loader.getController();
            c.init_data(tableHotel.getSelectionModel().getSelectedItem());
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.setScene(new Scene(tableViewParent,600,500));
            stage.show();

        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }




    @FXML
    void user_setting() {

        try{
            user_setting.getScene().getWindow().hide();
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../user_setting/user_setting.fxml"));
            Parent root=(Parent)loader.load();
            sample.user_setting.user_setting ue=loader.getController();
            ue.loadUser_pass(user.getText(),pass.getText());

            stage.setTitle("user_setting");
            stage.setResizable(false);
            stage.setScene(new Scene(root, 900, 700));
            stage.show();
        }catch (Exception e){
            System.out.print(e.getMessage());
        }

    }

    @FXML
    void search() throws SQLException {
        data.clear();
        String sql = "Select * From listing where hotel LIKE '%" + txt_name.getText() + "%'";
        try {
            con=da.getDbconnection();
            pst = con.prepareStatement(sql);
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

        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        finally {
            pst.close();
        }
    }

    private void setCelltable_to_Textfield(){
        tableHotel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Hotel h1=tableHotel.getItems().get(tableHotel.getSelectionModel().getSelectedIndex());
                txt_name.setText(h1.getHotel());
            }
        });
    }



//    public void initialize(){
//        Node [] node=new Node[10];
//        for(int i=0;i<node.length;i++){
//            try {
//                node[i] = FXMLLoader.load(getClass().getResource("save_item.fxml"));
//                item_box.getChildren().addAll(node[i]);
//            }catch (Exception e){
//                System.out.print(e.getMessage());
//            }
//        }
//    }


}
