package sample.checkout;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class validation {
    public static boolean isTextfieldnotempty(TextField tf){
        boolean b=false;
        if(tf.getText().length()!=0||tf.getText().isEmpty()){
            b=true;
        }
        return b;
    }

    public static boolean isTextfieldnotempty(TextField tf, Label lb,String error_message){
        boolean b=true;
        String msg=null;
        if(!(isTextfieldnotempty(tf))){
            b=false;
            msg=error_message;

        }
        lb.setText(msg);

        return b;
    }


}
