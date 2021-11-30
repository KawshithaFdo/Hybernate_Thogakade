package controller.Admin;

import entity.item;
import entity.user;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import util.FactoryConfiguration;
import view.Tm.UserTm;

import java.sql.SQLException;
import java.util.List;

public class UserManagementController {
    public TableView<UserTm> tblUser_Management;
    public TableColumn colUserId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colNIC;
    public Button btnDelete;

    //UserDaoImpl u=new UserDao();
    Session session = FactoryConfiguration.getInstance().getSession();
    Transaction transaction = session.beginTransaction();

    public void initialize(){
        btnDelete.setDisable(true);

        colUserId.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nIC"));

        tblUser_Management.getColumns().setAll(colUserId, colName, colAddress, colContact,colNIC);
        try {
            tblUser_Management.getItems().setAll(loaddata());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblUser_Management.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(false);
        });



    }

    public ObservableList<UserTm> loaddata() throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM item";
        NativeQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(item.class);

        List<user> allusers=sqlQuery.list();
        ObservableList<UserTm> tableData = FXCollections.observableArrayList();
        for (user user:allusers) {
            tableData.add(new UserTm(
                    user.getUser_ID(),
                    user.getName(),
                    user.getAddress(),
                    user.getContact(),
                    user.getNIC()
            ));

        }
        return tableData;
    }

    public void DeleteUser(ActionEvent actionEvent) {
        try {
            String id = tblUser_Management.getSelectionModel().getSelectedItem().getUser_ID();
            session.delete(id);
            btnDelete.setDisable(true);
            tblUser_Management.getItems().setAll(loaddata());

            transaction.commit();
            session.close();

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
