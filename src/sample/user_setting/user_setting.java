package sample.user_setting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Controller;
import sample.database.Hotel;
import sample.database.User;
import sample.database.database_handler;


import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sample.hotel.*;
import sample.save.*;
import sample.main.*;

import javax.imageio.ImageIO;

public class user_setting {

    @FXML
    private Button booked;

    @FXML
    private Button hotel;

    @FXML
    private Button save1;

    @FXML
    private ImageView imageView;

    private Image image;

    @FXML
    private Button trailer;

    @FXML
    private Button logout;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_tel;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_confirm_pass;

    @FXML
    private TextField txt_gender;


    @FXML
    private TableView<User> tableuser;

    @FXML
    private TableColumn<?, ?> column_name;

    @FXML
    private TableColumn<?, ?> column_tel;

    @FXML
    private TableColumn<?, ?> column_email;

    @FXML
    private Button upload;

    private File file;

    @FXML
    private Button update;

    @FXML
    private Label Label_id;



    private Connection con=null;
    private PreparedStatement pst=null;
    private ResultSet result=null;
    database_handler da=new database_handler();
    private ObservableList<User> data=null;


    @FXML
    void booked() {
        try{
            booked.getScene().getWindow().hide();
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../main/main.fxml"));
            Parent root=loader.load();
            mainController mc=loader.getController();
            mc.init_data(txt_name.getText(),txt_password.getText());
            stage.setScene(new Scene(root,900,700));
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }

    }

    @FXML
    void hotel() {
        try{
            hotel.getScene().getWindow().hide();
            Stage stage =new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../hotel/hotel.fxml"));
            Parent root=loader.load();
            hotel h=loader.getController();
            h.init_data(txt_name.getText(),txt_password.getText());
            stage.setScene(new Scene(root,900,700));
            stage.setResizable(false);
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
            Parent root=FXMLLoader.load(getClass().getResource("../sample.fxml"));
            stage.setScene(new Scene(root,700,500));
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }

    }



    @FXML
    void trailer() {

    }



    public void loadUser_pass(String user,String pass) throws SQLException{
        txt_name.setText(user);
        txt_password.setText(pass);
        try {
            con = da.getDbconnection();
            data= FXCollections.observableArrayList();
            setCelltable();
            loaddatabase(user);
            setCelltable_to_Textfield();
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
        finally {
            pst.close();
        }
    }

    @FXML
    void save1() {
        try{
            save1.getScene().getWindow().hide();
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../save/save_sample.fxml"));
            Parent root=loader.load();
            save s=loader.getController();
            s.init_data(txt_name.getText(),txt_password.getText());
            stage.setScene(new Scene(root,900,700));
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }

    }




//    public void initialize(User user) throws SQLException {
//        try {
//            con = da.getDbconnection();
//            data= FXCollections.observableArrayList();
//            setCelltable();
//            loaddatabase(user);
//
//            setCelltable_to_Textfield();
//        }
//        catch (Exception e){
//            System.out.print(e.getMessage());
//        }
//        finally {
//            pst.close();
//        }
//    }

    public void setCelltable(){
        column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        column_tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        column_email.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    public void loaddatabase(String user){
        try{
            pst=con.prepareStatement("select * from user where name=\"" + user+ "\" ");
            result=pst.executeQuery();
            while (result.next()){
                data.add(new User(result.getInt(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6),result.getString(7) ));
            }
            tableuser.setItems(data) ;
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }



    private void setCelltable_to_Textfield(){
        tableuser.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                User u1=tableuser.getItems().get(tableuser.getSelectionModel().getSelectedIndex());
                txt_name.setText(u1.getName());
                txt_gender.setText(u1.getGender());
                txt_tel.setText(u1.getTel());
                txt_email.setText(u1.getEmail());
                txt_password.setText(u1.getPassword());
                txt_confirm_pass.setText(u1.getCpassoword());
                Label_id.setText(String.valueOf(u1.getId()));
                Label_id.setVisible(false);
                showhotel_image(u1.getName());


            }
        });
    }

    private void showhotel_image(String hotel){
        try{
            pst=con.prepareStatement("select photo from user where name=?");
            pst.setString(1,hotel);
            result=pst.executeQuery();
            if (result.next()){
                InputStream is=result.getBinaryStream(1);
                OutputStream os=new FileOutputStream(new File("photo1.jpg"));
                byte[] content=new byte[1024];
                int size=0;
                while((size=is.read(content))!=-1){
                    os.write(content,0,size);
                }
                image=new Image("file:photo1.jpg",imageView.getFitHeight(),imageView.getFitWidth(),true,true);
                imageView.setImage(image);

            }

        }catch (Exception e){
            System.out.print(e.getMessage());
        }

    }

    @FXML
    void upload_act() {

        FileChooser fc=new FileChooser();//to choose file from machine;
//                FileChooser.ExtensionFilter ext1= new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");//sort of file(extension) allowed to choose .jpg
//                FileChooser.ExtensionFilter ext2= new FileChooser.ExtensionFilter("PNG files(*.png)","*.png");//sort of file(extension) allowed to choose .png
//                FileChooser.ExtensionFilter ext3=new FileChooser.ExtensionFilter("JPEG files(*.jpeg)","*.jpeg");
//                fc.getExtensionFilters().addAll(ext1,ext2,ext3);//allowed ext1(jpg) ext2(png) to be the allowed extension for this file_chooser, the rest is not.
        try {

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("user_setting.fxml"));
            stage.setTitle("Register");
            stage.setResizable(false);
            stage.setScene(new Scene(root, 700, 500));
            file = fc.showOpenDialog(stage);
        }
        catch (Exception e) {
            System.out.print(e.getMessage());
        }
        BufferedImage bf;
        try{
            bf= ImageIO.read(file);
            image= SwingFXUtils.toFXImage(bf,null);
            imageView.setImage(image);
        }
        catch(Exception e){
            System.out.print(e.getMessage());
        }
    }

    @FXML
    void update() {
        String sql="update user set name=?,gender=?,tel=?,email=?,password=?,confirm_password=?,photo=? where id = ?";

        FileInputStream fin=null;
        try {
            fin = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int len = (int)file.length();
        try{
            con=da.getDbconnection();
            pst=con.prepareStatement(sql);
            pst.setString(1,txt_name.getText());
            pst.setString(2,txt_gender.getText());
            pst.setString(3,txt_tel.getText());
            pst.setString(4,txt_email.getText());
            pst.setString(5,txt_password.getText());
            pst.setString(6,txt_confirm_pass.getText());
            pst.setBinaryStream(7,fin,len);
            pst.setString(8,Label_id.getText());

            int i=pst.executeUpdate();
            if (i == 1) {
                System.out.print("Success");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText("Information");
                alert.setContentText("Update success");
                alert.showAndWait();
            }

        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }




}
