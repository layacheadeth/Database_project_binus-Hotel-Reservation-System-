package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.*;
import sample.database.User;
import sample.database.database_handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sample.main.*;
import sample.user_setting.*;
import sample.user_setting.*;


public class Controller {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;
    @FXML
    private Hyperlink register;

    private Connection connection=null;
    private PreparedStatement preparedStatement=null;
    private ResultSet rs=null;

    database_handler da = new database_handler();
    String transfer_userText;
    String transfer_password;



    @FXML
    public void login() throws SQLException{
        String userText = username.getText().trim();
        String passwordText = password.getText().trim();
        transfer_userText=userText;
        transfer_password=passwordText;

        if (!userText.isEmpty() && !passwordText.isEmpty()) {
            loginuser(userText,passwordText);
        } else {
            System.out.println("Error Login");
        }
    }

    private User user;

    private void loginuser(String userText, String passwordText) throws SQLException {
        String sql="select * from user where name=? and password= ?";

        if(userText.isEmpty() || passwordText.isEmpty()){
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error not found");
            a.setContentText("All the field shouldn't be blanked");
            a.showAndWait();
        }
        else{
            try{

                connection=da.getDbconnection();
                preparedStatement=connection.prepareStatement(sql);
                preparedStatement.setString(1,userText);
                preparedStatement.setString(2,passwordText);
                System.out.print(userText+"\n");
                System.out.println(passwordText);
                rs=preparedStatement.executeQuery();


                if(rs.next()){
                    try {
                        login.getScene().getWindow().hide();
                        FXMLLoader loader=new FXMLLoader(this.getClass().getResource("user_setting/user_setting.fxml"));
                        Parent root=(Parent)loader.load();
                        user_setting ue=loader.getController();
                        ue.loadUser_pass(transfer_userText,transfer_password);
//                        User u=new User(transfer_userText,"male","017303042","ace@gmail.com","1234","1234");
//                        u.setName(transfer_userText);
//                        u.setPassword(transfer_password);
                        Stage stage = new Stage();
                        stage.setTitle("Main");
                        stage.setResizable(false);
                        stage.setScene(new Scene(root, 900, 700));
                        stage.show();
                    } catch (Exception e) {
                        System.out.print(e.getMessage());
                    }
                }else{
                    Alert a=new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error");
                    a.setHeaderText("Error not found");
                    a.setContentText("Username or password is incorrect");
                    a.showAndWait();
                }
            }catch (Exception e){
                System.out.print(e.getMessage());
            }
            finally {
                preparedStatement.close();
            }


        }

    }

    public void initialize(){
        login.setDefaultButton(true);
    }

    @FXML
    public void register(){
        try{
            register.getScene().getWindow().hide();
            Stage stage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("register/register.fxml"));
            stage.setTitle("Register");
            stage.setResizable(false);
            stage.setScene(new Scene(root, 700, 500));
            stage.show();
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }
}
