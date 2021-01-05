package sample.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.database.Hotel;
import sample.database.database_handler;
import javafx.stage.*;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.user_setting.user_setting;
import sample.checkout.*;
import sample.hotel.*;
import sample.save.*;

public class mainController {

    @FXML
    private Button booked;

    @FXML
    private Label status_type;


    @FXML
    private Button save;

    @FXML
    private Button hotel;

    @FXML
    private Button trailer;

    @FXML
    private Button logout;

    @FXML
    private Label user;

    @FXML
    private Label pass;

    @FXML
    private TextField txt_hotel;

    @FXML
    private TextField txt_location;

    @FXML
    private TextField txt_price;

    @FXML
    private TextField txt_search;

    @FXML
    private Button btn_search;

    @FXML
    private ImageView Imageview;

    private Image image;

    @FXML
    private Button delete;

    @FXML
    private Button checkout;

    @FXML
    private TableView<Hotel> tableHotel;

    @FXML
    private TableColumn<?, ?> column_hotel;

    @FXML
    private TableColumn<?, ?> column_checkin;

    @FXML
    private TableColumn<?, ?> column_checkout;

    @FXML
    private TableColumn<?, ?> column_people;

    @FXML
    private TableColumn<?, ?> column_price;

    private ObservableList<Hotel> data;


    private Connection con=null;
    private PreparedStatement pst=null;
    private ResultSet result=null;
    database_handler da=new database_handler();



    @FXML
    void booked(){

    }

    @FXML
    void hotel() {
        try{
            hotel.getScene().getWindow().hide();
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../hotel/hotel.fxml"));
            Parent root=(Parent)loader.load();
            hotel h=loader.getController();
            h.init_data(user.getText(),pass.getText());
            stage.setTitle("Hotel");
            stage.setResizable(false);
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
    void save() {
        try{
            save.getScene().getWindow().hide();
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../save/save_sample.fxml"));
            Parent root=(Parent)loader.load();
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

   public void init_data(String text1,String text2){
        user.setText(text1);
        pass.setText(text2);
        user.setVisible(false);
        pass.setVisible(false);

   }

    @FXML
    void trailer() {
        try{
            trailer.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../user_setting/user_setting.fxml"));
            Parent root=(Parent)loader.load();
            user_setting ue=loader.getController();
            ue.loadUser_pass(user.getText(),pass.getText());
            Stage stage=new Stage();
            stage.setTitle("user_setting");
            stage.setResizable(false);
            stage.setScene(new Scene(root, 900, 700));
            stage.show();
        }catch (Exception e){
            System.out.print(e.getMessage());
        }

    }

    @FXML
    void search_by_char() throws SQLException {
            data.clear();
            String sql = "Select * From Hotel where hotel LIKE '%" + txt_search.getText() + "%'";
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
                    data.add(new Hotel(emp1photo,result.getInt(1),result.getString(2),result.getString(3), (int) result.getDouble(4),result.getString(6),result.getString(7),result.getString(8),result.getInt(9),result.getString(10)));
                }
                tableHotel.setItems(data);

            } catch (Exception ex) {
                System.out.print(ex.getMessage());
            }
            finally {
                pst.close();
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
    void delete() throws SQLException {
        data.clear();
        String sql="Delete from Hotel where hotel = ?";
        try{
            con=da.getDbconnection();
            pst=con.prepareStatement(sql);
            pst.setString(1,txt_hotel.getText());

            int i=pst.executeUpdate();
            if(i==1){
                Alert a=new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Delete");
                a.setHeaderText("Delete success");
                a.setContentText("Delete successfully");
                loaddatabase();
                txt_hotel.clear();
                txt_location.clear();
                txt_price.clear();
                Imageview.setImage(null);
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
    void checkout() {
        try{
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("../checkout/checkout.fxml"));
            Parent tableViewParent=loader.load();
            checkout de=loader.getController();
            de.init_data(tableHotel.getSelectionModel().getSelectedItem());
            stage.setResizable(false);
            stage.setScene(new Scene(tableViewParent,700,500));
            stage.show();

        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

//    public void loadUser_pass(String user,String pass) throws SQLException{
//        try {
//            con = da.getDbconnection();
//            data= FXCollections.observableArrayList();
//            setCelltable();
//            loaddatabase();
//            System.out.print(user);
//            System.out.print(pass);
//            setCelltable_to_Textfield();
//        }
//        catch (Exception e){
//            System.out.print(e.getMessage());
//        }
//        finally {
//            pst.close();
//        }
//    }

    public void initialize() throws SQLException{
        try {
            con = da.getDbconnection();
            data=FXCollections.observableArrayList();
            setCelltable();
            loaddatabase();
            setCelltable_to_Textfield();
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
        column_checkin.setCellValueFactory(new PropertyValueFactory<>("check_in"));
        column_checkout.setCellValueFactory(new PropertyValueFactory<>("check_out"));
        column_people.setCellValueFactory(new PropertyValueFactory<>("amount_of_people"));
        column_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    public void loaddatabase(){
        try{
           pst=con.prepareStatement("select * from Hotel");
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
               data.add(new Hotel(emp1photo,result.getInt(1),result.getString(2),result.getString(3), (int) result.getDouble(4),result.getString(6),result.getString(7),result.getString(8),result.getInt(9),result.getString(10)));
           }
            tableHotel.setItems(data) ;
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }


    private void setCelltable_to_Textfield(){
        tableHotel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Hotel h1=tableHotel.getItems().get(tableHotel.getSelectionModel().getSelectedIndex());
                txt_hotel.setText(h1.getHotel());
                txt_location.setText(h1.getLocation());
                txt_price.setText(h1.getRoomtype());
                status_type.setText(h1.getStatus());
                showhotel_image(String.valueOf(h1.getId()));
                Imageview.setImage(image);

            }
        });
    }

    private void showhotel_image(String hotel){
        try {
            pst = con.prepareStatement("select Image from Hotel where id=? ");
            pst.setString(1,hotel);
            result=pst.executeQuery();
            if(result.next()){
                InputStream is=result.getBinaryStream(1);
                OutputStream os=new FileOutputStream(new File("photo.jpg"));
                byte[] content=new byte[1024];
                int size=0;
                while((size=is.read(content))!=-1){
                    os.write(content,0,size);
                }
                image=new Image("file:photo.jpg",Imageview.getFitHeight(),Imageview.getFitWidth(),true,true);
                Imageview.setImage(image);

            }
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }






}
