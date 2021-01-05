package sample.register;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import sample.database.database_handler;

import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.io.*;
import javafx.collections.*;



public class register {

    @FXML
    private Button cancel;

    @FXML
    private TextField name;

    @FXML
    private TextField tel;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirm_password;
    @FXML
    private Button register;

    @FXML
    private TextField email;


    @FXML
    private ComboBox gender;




    @FXML
    private Button upload_image;

    @FXML
    private ImageView photo;

    private File file;

    @FXML
    private BorderPane register_pane;

    private Image image;



    @FXML
    void cancel() {
        try {
            cancel.getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../sample.fxml"));
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.setScene(new Scene(root, 700, 500));
            stage.show();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    private Connection con = null;
    private PreparedStatement preparedStatement = null;

    database_handler da = new database_handler();

    @FXML
    void register() throws SQLException {

        String sql = "insert into user(name,gender,tel,email,password,confirm_password,photo) values(?,?,?,?,?,?,?)";

        String pas=password.getText();
        String c_pas=confirm_password.getText();
        FileInputStream fin=null;

        try {
            fin = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int len = (int)file.length();

        if (!(pas.equals(c_pas) )) {
            Alert alert1=new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Error found");
            alert1.setContentText("Password and confirm password must be the same");
            alert1.showAndWait();
        } else {
            try {
                con = da.getDbconnection();
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, name.getText());
                preparedStatement.setString(2, String.valueOf(gender.getValue()));
                preparedStatement.setString(3, tel.getText());
                preparedStatement.setString(4, email.getText());
                preparedStatement.setString(5, password.getText());
                preparedStatement.setString(6, confirm_password.getText());
                preparedStatement.setBinaryStream(7,fin,len);

                int i = preparedStatement.executeUpdate();
                if (i == 1) {
                    System.out.print("Success");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText("Information");
                    alert.setContentText("Success");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                System.out.print(e.getMessage());
            } finally {
                preparedStatement.close();
            }
        }
    }

    @FXML
    void upload_image() {
        FileChooser fc=new FileChooser();//to choose file from machine;
//                FileChooser.ExtensionFilter ext1= new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");//sort of file(extension) allowed to choose .jpg
//                FileChooser.ExtensionFilter ext2= new FileChooser.ExtensionFilter("PNG files(*.png)","*.png");//sort of file(extension) allowed to choose .png
//                FileChooser.ExtensionFilter ext3=new FileChooser.ExtensionFilter("JPEG files(*.jpeg)","*.jpeg");
//                fc.getExtensionFilters().addAll(ext1,ext2,ext3);//allowed ext1(jpg) ext2(png) to be the allowed extension for this file_chooser, the rest is not.
        try {

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
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
            photo.setImage(image);
        }
        catch(Exception e){
            System.out.print(e.getMessage());
        }

    }
    @FXML
    void gender() {

    }

    public void initialize(){
        ObservableList<String> list=FXCollections.observableArrayList("Male","Female");
        gender.setItems(list);

    }

}


