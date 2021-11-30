package controller;

import com.jfoenix.controls.JFXButton;
import controller.User.ManageCustomerController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class UserFormController {
    public JFXButton btnmanageCustomer;
    public JFXButton btnManageOrders;
    public JFXButton btnplaceOrder;
    public Label UserName;
    public Label Date;
    public Label Time;
    public StackPane stackRoot;
    public String id;

    ArrayList<JFXButton> list=new ArrayList();

    public void initialize(){
        setdateandtime();

        list.add(btnManageOrders);
        list.add(btnmanageCustomer);
        list.add(btnplaceOrder);
    }

    private void setdateandtime() {
        java.util.Date date=new Date();
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        Date.setText(f.format(date));

        Timeline time=new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            Time.setText(
                    currentTime.getHour()+":"+currentTime.getMinute()+":"+
                            currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void ManageOrders(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/User/ManageOrders.fxml"));
        pane = fxmlLoader.load();
        stackRoot.getChildren().setAll(pane);
    }

    public void GoHome(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/Login.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) stackRoot.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void placeOrder(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/User/MakePayment.fxml"));
        pane = fxmlLoader.load();
        stackRoot.getChildren().setAll(pane);
    }

    public void mouseClick(MouseEvent mouseEvent) {
        for (int i=0;i<list.size();i++){
            if(mouseEvent.getTarget().equals(list.get(i))){
                list.get(i).setStyle("-fx-background-color:deepskyblue");
            }else {
                list.get(i).setStyle("-fx-background-color:transparent");
            }
        }
    }

    public void ManageCustomer(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/User/ManageCustomer.fxml"));
        pane = fxmlLoader.load();
        ManageCustomerController con=fxmlLoader.<ManageCustomerController>getController();
        con.UserId=id;
        stackRoot.getChildren().setAll(pane);
    }
}
