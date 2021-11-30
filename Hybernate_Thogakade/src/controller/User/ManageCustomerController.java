package controller.User;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import entity.customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import dto.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import util.FactoryConfiguration;
import util.ValidationUtil;
import view.Tm.CustomerTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageCustomerController {

    public AnchorPane ManageCustomerContext;
    public JFXButton btnAdd;
    public JFXTextField CustName;
    public JFXTextField CustAddress;
    public JFXTextField CustContact;
    public JFXTextField CustTitle;
    public Label CustId;
    public TableView<CustomerTm> tblCustomer;
    public TableColumn colCust_Id;
    public TableColumn colTitle;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colcity;
    public TableColumn colProvince;
    public TableColumn colpostalCode;
    public JFXTextField CustEditName;
    public JFXTextField CustEditaddress;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXTextField CustEditId;
    public JFXTextField CustCity;
    public JFXTextField CustProvince;
    public JFXTextField CustPostolCode;
    public JFXTextField CustEditTitle;
    public JFXTextField CustEditCity;
    public JFXTextField CustEditProvince;
    public JFXTextField CustEditPostolCode;
    public String UserId;

    //private final CustomerBo c = (CustomerBo) FactoryBo.getFactoryBo().getBO(FactoryBo.BOTypes.CUSTOMER);

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[C](-00)[0-9]{1,4}$");
    Pattern namePattern = Pattern.compile("^[[A-z]{1,30}[ ]?]+$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{3,30}$");
    Pattern cityPattern=Pattern.compile("^[[A-z]{1,}[ ]?]+$");
    Pattern titlepattern=Pattern.compile("^[M][r|s|rs]*$");
    Pattern provincePattern=Pattern.compile("^[[A-z]{1,}[ ]?]+$");
    Pattern postalCodePattern=Pattern.compile("^[0-9]{3,6}$");

    Session session = FactoryConfiguration.getInstance().getSession();
    Transaction transaction = session.beginTransaction();

    public void initialize() throws SQLException, ClassNotFoundException {
        OpenValidations();
        btnDelete.setDisable(true);

        colCust_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colcity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colpostalCode.setCellValueFactory(new PropertyValueFactory<>("postalcode"));


        loadData();

       // CustId.setText(c.generateCustId());

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(false);
        });
    }

    private void OpenValidations() {
        map.put(CustTitle,titlepattern);
        map.put(CustName,namePattern);
        map.put(CustAddress,addressPattern);
        map.put(CustCity,cityPattern);
        map.put(CustProvince,provincePattern);
        map.put(CustPostolCode,postalCodePattern);
    }

    public void AddCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Customer customer=new Customer(CustId.getText(),CustTitle.getText(),CustName.getText(),CustAddress.getText(),
                CustCity.getText(),CustProvince.getText(),CustPostolCode.getText(),UserId);
        session.save(customer);
        loadData();
        clear();

    }

    public void OnKeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnAdd.requestFocus();
            }
        }
    }

    public void OpenEditData(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        customer c1= session.get(customer.class,CustEditId.getText());
        if (c1==null){
            new Alert(Alert.AlertType.ERROR,"Enter Valid Customer Id.").show();
        }else {
            CustEditName.setText(c1.getCustName());
            CustEditaddress.setText(c1.getCustaddress());
            CustEditTitle.setText(c1.getCustTitle());
            CustEditCity.setText(c1.getCity());
            CustEditProvince.setText(c1.getProvince());
            CustEditPostolCode.setText(c1.getPostalcode());
        }
    }

    public void UpdateCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Customer customer=new Customer(CustEditId.getText(),CustEditTitle.getText(),CustEditName.getText(),CustEditaddress.getText(),
                CustEditCity.getText(),CustEditProvince.getText(),CustEditPostolCode.getText(),UserId);
         session.update(customer);
            loadData();
            clear();
            btnUpdate.setDisable(true);
    }

    public void DeleteCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = tblCustomer.getSelectionModel().getSelectedItem().getId();
        session.delete(id);
            loadData();
            clear();
            id="";
            btnDelete.setDisable(true);
    }

    public void OnEditKeyReleased(KeyEvent keyEvent) {
        editValidations();
        Object response = ValidationUtil.validate(map,btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnUpdate.requestFocus();
            }
        }
    }

    private void editValidations() {
        map.clear();
        map.put(CustEditId,idPattern);
        map.put(CustEditTitle,titlepattern);
        map.put(CustEditName,namePattern);
        map.put(CustEditaddress,addressPattern);
        map.put(CustEditCity,cityPattern);
        map.put(CustEditProvince,provincePattern);
        map.put(CustEditPostolCode,postalCodePattern);
    }

    public ObservableList<CustomerTm> loadTableData() throws SQLException, ClassNotFoundException {

        String sql="SELECT * FROM Customer";
        NativeQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(customer.class);

        transaction.commit();
        session.close();

        List<Customer> allCustomers=sqlQuery.list();
        ObservableList<CustomerTm> custList = FXCollections.observableArrayList();
        for (Customer customer : allCustomers) {
            custList.add(new CustomerTm(
                    customer.getId(),
                    customer.getTitle(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalcode()
            ));

        }
        return custList;


    }

    public void loadData() throws SQLException, ClassNotFoundException {
        tblCustomer.getItems().setAll(loadTableData());
    }

    public void clear() throws SQLException, ClassNotFoundException {
        //CustId.setText(c.generateCustId());
        CustName.setText("");
        CustAddress.setText("");
        CustTitle.setText("");
        CustCity.setText("");
        CustProvince.setText("");
        CustPostolCode.setText("");
        CustEditId.setText("");
        CustEditName.setText("");
        CustEditaddress.setText("");
        CustEditTitle.setText("");
        CustEditCity.setText("");
        CustEditProvince.setText("");
        CustEditPostolCode.setText("");
    }

}
