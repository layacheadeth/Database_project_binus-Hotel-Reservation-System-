package sample.detail_listing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.database.Hotel;
import sample.database.database_handler;
import javafx.scene.control.TextArea;

import javax.imageio.ImageIO;
import java.awt.desktop.SystemEventListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Tab;
import sample.database.description;
import sample.condition.*;

public class detail_listing2 {

    @FXML
    private ImageView imageView;

    @FXML
    private Label L_hotel;

    @FXML
    private Label L_location;


    @FXML
    private Label L_price;

    @FXML
    private Button book_now;

    @FXML
    private Button save_now;

    private Hotel selected_hotel;

    private ObservableList<Hotel> data;

    private ObservableList<description> data1;

    @FXML
    private Label L_roomtype;

    @FXML
    private Label L_id;

    private Image image;
    
    private InputStream is;
    
    private OutputStream os;

    private File file;



    @FXML
    private TextArea text_area;


    @FXML
    void book_now() {
        try{
            book_now.getScene().getWindow().hide();
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("../condition/condition.fxml"));
            Parent tableViewParent=loader.load();
            condition c=loader.getController();
            c.init_data(L_id.getText());
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.setScene(new Scene(tableViewParent,600,500));
            stage.show();

        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }


    private Connection con=null;
    private PreparedStatement pst=null;
    private ResultSet result=null;
    database_handler da=new database_handler();

    public void init_data(Hotel hotel){
        selected_hotel=hotel;
        L_id.setText(String.valueOf(selected_hotel.getId()));
        L_hotel.setText(selected_hotel.getHotel());
        L_location.setText(selected_hotel.getLocation());
        L_price.setText(String.valueOf(selected_hotel.getPrice()));
        L_roomtype.setText(selected_hotel.getRoomtype());
        System.out.print(selected_hotel.getId());
        showhotel_image(String.valueOf(selected_hotel.getId()));
        imageView.setImage(image);
        show_description(String.valueOf(selected_hotel.getId()));
//        show_blob(String.valueOf(selected_hotel.getId()));
    }

    private void show_description(String hotel){
        String sql="select description from detail_listing where listing_id = ?";
        try{
            con=da.getDbconnection();
            data1=FXCollections.observableArrayList();
            pst=con.prepareStatement(sql);
            pst.setString(1,hotel);
            result=pst.executeQuery();
            while(result.next()){
                text_area.setText(result.getString("description"));
                text_area.setEditable(false);
            }
            pst.close();
            result.close();

        }catch (Exception e){
            System.out.print(e.getMessage());
        }

    }

    private void showhotel_image(String hotel){
        System.out.print("\n"+hotel);
        String sql="select Image from real_Listing where id=?";
        try{
            con = da.getDbconnection();
            data= FXCollections.observableArrayList();
            pst=con.prepareStatement(sql);
            pst.setString(1,hotel);
            result=pst.executeQuery();
            if(result.next()){
                is=result.getBinaryStream(1);
                os=new FileOutputStream(new File("photo.jpg"));
                byte[] content=new byte[1024];
                int size=0;
                while((size=is.read(content))!=-1){
                    os.write(content,0,size);
                }
                image=new Image("file:photo.jpg",imageView.getFitHeight(),imageView.getFitWidth(),true,true);
                imageView.setImage(image);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void save_now() {

        String sql="INSERT INTO listing\n" +
                "SELECT *\n" +
                "FROM real_Listing\n" +
                "WHERE hotel =? ";

        try{
            con=da.getDbconnection();
            data= FXCollections.observableArrayList();
            pst=con.prepareStatement(sql);
            pst.setString(1,L_hotel.getText());
            int i=pst.executeUpdate();
            if(i==1){
                System.out.print("Success");
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
