package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import entity.admin;
import entity.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dto.Admin;
import dto.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FactoryConfiguration;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class LoginController {
    public AnchorPane LoginContext;
    public JFXButton btnLogIn;
    public JFXTextField Id;
    public JFXPasswordField Password;
    private String Name;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[AU](-00)[0-9]{1,4}$");
    Pattern passwordPattern = Pattern.compile("^[A-z0-9]{1,15}$");

  /*  UserDaoImpl u=new UserDao();
    AdminDaoImpl a=new AdminDao();*/

    public void initialize(){
        Openvalidation();
    }

    private void Openvalidation() {
        map.put(Id, idPattern);
        map.put(Password, passwordPattern);
    }

    public void LogIn_OnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        if(Password.getText().equals("")) {
            new Alert(Alert.AlertType.WARNING, "Enter UserId.").show();
            return;
        }
        loadActor();
    }

    public void newAccount_OnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/AddNewAccount.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void OpenIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        user u2 = session.get(user.class,Id.getText());
        admin a1 = session.get(admin.class,Id.getText());

        if (u2==null) {
            if (a1==null) {
                new Alert(Alert.AlertType.WARNING, "Wrong ID.").show();
            } else {
                Name=a1.getName();
            }
        } else {
            Name=u2.getName();
        }

        transaction.commit();
        session.close();
    }

    public void loadActor() throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        user u1 = session.get(user.class,Id.getText());
        String id=(Id.getText().split("-")[0]);

        if(id.equals("U")){
            if((Password.getText().equals(u1.getPasssword()))){
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/UserForm.fxml"));
                Parent parent = loader.load();
                UserFormController controller = loader.<UserFormController>getController();
                controller.UserName.setText(Name);
                controller.id=Id.getText();
                Stage window = (Stage) LoginContext.getScene().getWindow();
                window.setScene(new Scene(parent));
                // window.setFullScreen(true);//
                window.show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Enter Valid Password.").show();
                Password.setText("");
                Password.requestFocus();
            }
        }else if(id.equals("A")){
            admin a2 = session.get(admin.class,Id.getText());
            if((Password.getText().equals(a2.getPasssword()))){
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AdminForm.fxml"));
                Parent parent = loader.load();
                AdminFormController controller = loader.<AdminFormController>getController();
                controller.AdminName.setText(Name);
                controller.id=Id.getText();
                Stage window = (Stage) LoginContext.getScene().getWindow();
                window.setScene(new Scene(parent));
                //     window.setFullScreen(true);//
                window.show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Enter Valid Password.").show();
                Password.setText("");
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Enter Valid Id.").show();
        }

        transaction.commit();
        session.close();
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnLogIn);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnLogIn.requestFocus();
            }
        }
    }
}
