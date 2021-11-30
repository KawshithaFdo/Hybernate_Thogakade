package controller.Admin;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import entity.item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import dto.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import util.FactoryConfiguration;
import util.ValidationUtil;
import view.Tm.ItemTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageItemController {
    public AnchorPane ManageItemContext;
    public Label lblItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtQty;
    public JFXTextField txtUnitPrice;
    public JFXButton btnAddItem;
    public TableView<ItemTm> tblItem;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPacksize;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public JFXTextField EditDiscription;
    public JFXTextField EditUnitPrice;
    public JFXTextField EditPackSize;
    public JFXTextField EditQty;
    public JFXButton btnDeleteItem;
    public JFXButton btnUpdateItem;
    public JFXTextField EditItemCode;
    public JFXTextField txtDiscount;
    public JFXTextField EditDiscount;
    public TableColumn colDiscount;
    public String AdminId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern PackSizePattern = Pattern.compile("^[0-9]+[A-z]+$");
    Pattern DescriptionPattern = Pattern.compile("^[[A-z]+[ ]]+[(]?[0-9]*[A-z]*[)]?$");
    Pattern qtyPattern = Pattern.compile("^[0-9]{1,}$");
    Pattern unitpricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{1,2})?$");
    Pattern discountPattern=Pattern.compile("^[1-9]?[0-9]{1}?([.][0-9]{1,2})?$");
    Pattern idPattern = Pattern.compile("^[I](-00)[0-9]{1,4}$");

    //private final ItemBo i = (ItemBo) FactoryBo.getFactoryBo().getBO(FactoryBo.BOTypes.ITEM);
    Session session = FactoryConfiguration.getInstance().getSession();
    Transaction transaction = session.beginTransaction();

    public void initialize() throws SQLException, ClassNotFoundException {
        OpenValidations();
        setItemCode();
        btnDeleteItem.setDisable(true);

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemcode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPacksize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        try {

            loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDeleteItem.setDisable(false);
        });
    }

    private void OpenValidations() {
        map.put(txtDescription,DescriptionPattern);
        map.put(txtPackSize,PackSizePattern);
        map.put(txtUnitPrice,unitpricePattern);
        map.put(txtQty,qtyPattern);
        map.put(txtDiscount,discountPattern);
    }

    private void setItemCode() throws SQLException, ClassNotFoundException {
       // lblItemCode.setText(i.generateId());
    }

    private void loadData() throws SQLException, ClassNotFoundException {
        tblItem.getItems().setAll(loadTableData());
    }

    private ObservableList<ItemTm> loadTableData() throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM item";
        NativeQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(item.class);

        List<Item> item=sqlQuery.list();
        ObservableList<ItemTm> list= FXCollections.observableArrayList();
        for (Item i:item) {
            list.add(new ItemTm(
               i.getItemCode(),
               i.getDescription(),
               i.getPacksize(),
               i.getUnitprice(),
               i.getQtyonhand(),
               i.getDiscount()
            ));
        }
        return list;
    }

    public void AddItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Item i1=new Item(lblItemCode.getText(),txtDescription.getText(),txtPackSize.getText(),Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQty.getText()),Double.parseDouble(txtDiscount.getText()),AdminId);
        session.save(i1);
        clear();
        setItemCode();
        loadData();
    }

    public void OpenEditPartDetails(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        item i2=session.get(item.class,EditItemCode.getText());
        EditDiscription.setText(i2.getDescription());
        EditPackSize.setText(i2.getPacksize());
        EditUnitPrice.setText(String.valueOf(i2.getUnitPrice()));
        EditQty.setText(String.valueOf(i2.getQtyOnHand()));
        EditDiscount.setText(String.valueOf(i2.getDiscount()));
    }

    public void UpdateItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Item i1=new Item(EditItemCode.getText(),EditDiscription.getText(),EditPackSize.getText(),Double.parseDouble(EditUnitPrice.getText()),
                Integer.parseInt(EditQty.getText()),Double.parseDouble(EditDiscount.getText()),AdminId);

        session.update(i1);
        clear();
        loadData();
    }

    public void DeleteItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String D_id=tblItem.getSelectionModel().getSelectedItem().getItemcode();
        item item = session.get(item.class, D_id);
        session.delete(item);
        clear();
        loadData();
        setItemCode();

        transaction.commit();
        session.close();
    }

    public void clear(){
        txtDescription.setText("");
        txtPackSize.setText("");
        txtUnitPrice.setText("");
        txtQty.setText("");
        txtDiscount.setText("");
        EditItemCode.setText("");
        EditDiscription.setText("");
        EditPackSize.setText("");
        EditUnitPrice.setText("");
        EditQty.setText("");
        EditDiscount.setText("");
    }

    public void OnEditKeyReleased(KeyEvent keyEvent) {
        EditValidations();

        Object response = ValidationUtil.validate(map,btnUpdateItem);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnUpdateItem.requestFocus();
            }
        }
    }

    private void EditValidations() {
        map.clear();

        map.put(EditItemCode,idPattern);
        map.put(EditDiscription,DescriptionPattern);
        map.put(EditPackSize,PackSizePattern);
        map.put(EditUnitPrice,unitpricePattern);
        map.put(EditQty,qtyPattern);
        map.put(EditDiscount,discountPattern);
    }

    public void OnKeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAddItem);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnAddItem.requestFocus();
            }
        }
    }

}
