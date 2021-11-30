package controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import entity.user;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import dto.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import util.FactoryConfiguration;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddNewAccountController {
    public AnchorPane CreateUserAccount_onAction;
    public JFXButton btncreate;
    public JFXTextField UserName;
    public JFXTextField UserAddress;
    public JFXTextField UserContact;
    public JFXTextField UserNIC;
    public JFXPasswordField UserPassword;
    public Label UserId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[[A-z]{1,30}[ ]?]+$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{3,30}$");
    Pattern contactPattern=Pattern.compile("^[0][0-9]{2,3}[-]?[]0-9]{6,7}?$");
    Pattern nicpattern=Pattern.compile("^[0-9]{9,12}[vV]?$");
    Pattern passwordPattern = Pattern.compile("^[A-z0-9]{1,15}$");

   // UserDaoImpl dao=new UserDao();

    public void initialize() throws SQLException, ClassNotFoundException {
        UserId.setText(generateid());

        openValidations();
    }

    private String generateid() {
        return null;
    }

    private void openValidations() {
        map.put(UserName, namePattern);
        map.put(UserAddress, addressPattern);
        map.put(UserContact, contactPattern);
        map.put(UserNIC, nicpattern);
        map.put(UserPassword, passwordPattern);
    }

    public void CreateNewUser_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        SaveUser();
    }

    private void SaveUser() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();

        Transaction transaction = session.beginTransaction();

        user user = new user(UserId.getText(), UserName.getText(), UserAddress.getText(), UserContact.getText(),
                UserNIC.getText(), UserPassword.getText());

        session.save(user);

        transaction.commit();
        session.close();
    }

    void clear() throws SQLException, ClassNotFoundException {
       // UserId.setText(dao.generateId());
        UserName.setText("");
        UserAddress.setText("");
        UserContact.setText("");
        UserNIC.setText("");
        UserPassword.setText("");
    }

    public void keyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btncreate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btncreate.requestFocus();
            }
        }
    }
}
