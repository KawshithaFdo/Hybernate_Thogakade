package controller;

import com.jfoenix.controls.JFXButton;
import controller.Admin.ManageItemController;
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

public class AdminFormController {
    public String id;
    public Label AdminName;
    public Label Date;
    public Label Time;
    public JFXButton btnmanageItems;
    public JFXButton btnmanageUsers;
    public JFXButton btnreports;
    public JFXButton btnlogout;
    public StackPane StackRootContext;

    ArrayList<JFXButton> list=new ArrayList();

    public void initialize(){
        setdateandtime();

        list.add(btnmanageUsers);
        list.add(btnmanageItems);
        list.add(btnreports);
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

    public void LogOut(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/Login.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) StackRootContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void OpenReports(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/Admin/Reports.fxml"));
        pane = fxmlLoader.load();
        StackRootContext.getChildren().setAll(pane);
    }

    public void ManageUsers(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/Admin/UserManagement.fxml"));
        pane = fxmlLoader.load();
        StackRootContext.getChildren().setAll(pane);
    }

    public void Manageitems(ActionEvent actionEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/Admin/ManageItem.fxml"));
        pane = fxmlLoader.load();
        ManageItemController con=fxmlLoader.<ManageItemController> getController();
        con.AdminId=id;
        StackRootContext.getChildren().setAll(pane);
    }

    public void mouseclicked(MouseEvent mouseEvent) {
        for (int i=0;i<list.size();i++){
            if(mouseEvent.getTarget().equals(list.get(i))){
                list.get(i).setStyle("-fx-background-color:deepskyblue");
            }else {
                list.get(i).setStyle("-fx-background-color: transparent");
            }
        }
    }
}
