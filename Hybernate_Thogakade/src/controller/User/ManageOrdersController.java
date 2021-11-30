package controller.User;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import entity.customer;
import entity.item;
import entity.order_detail;
import entity.orders;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import dto.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import util.FactoryConfiguration;
import util.ValidationUtil;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageOrdersController {
    public ComboBox<String> cmbItem;
    public ComboBox<String> cmbOrderId;
    public Label OrderId;
    public Label Orderdate;
    public Label OrderTime;
    public TableView<getOrder> tblitem;
    public TableColumn colItemId;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public Label lblDiscount;
    public Label lblTotal;
    public JFXTextField txttitle;
    public JFXTextField txtname;
    public JFXTextField txtaddress;
    public JFXTextField txtDiscription;
    public JFXTextField txtPackSize;
    public JFXTextField txtqtyOnHand;
    public JFXTextField txtunitprice;
    public JFXTextField txtqty;
    public JFXButton btnUpdate;

    //private final UpdateOrderBo bo = (UpdateOrderBo) FactoryBo.getFactoryBo().getBO(FactoryBo.BOTypes.UPDATE_ORDER);

    Session session = FactoryConfiguration.getInstance().getSession();
    Transaction transaction = session.beginTransaction();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern qtyPattern = Pattern.compile("^[0-9]{1,}$");

    public void initialize(){
        openValidations();
        cmbItem.setDisable(true);
        setDateandTime();
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemcode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitprice"));

        try {
            loadCmbOrderData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbOrderId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loaddata();
                loadOrderdata();
                cmbItem.setDisable(false);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        cmbItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadItemdata();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    private void openValidations() {
            map.put(txtqty,qtyPattern);
    }

    private void setDateandTime() {
        Date date=new Date();
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        Orderdate.setText(f.format(date));

        Timeline time=new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            OrderTime.setText(
                    currentTime.getHour()+":"+currentTime.getMinute()+":"+
                            currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void loadItemdata() throws SQLException, ClassNotFoundException {
        item i1= session.get(item.class,cmbItem.getValue());
        txtDiscription.setText(i1.getDescription());
        txtPackSize.setText(i1.getPacksize());
        txtqtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
        txtunitprice.setText(String.valueOf(i1.getUnitPrice()));
    }

    private void loadOrderdata() throws SQLException, ClassNotFoundException {
        orders s =session.get(orders.class,cmbOrderId.getValue());
        customer c1=session.get(customer.class,s.getCustomer().getCustId());
        txttitle.setText(c1.getCustTitle());
        txtname.setText(c1.getCustName());
        txtaddress.setText(c1.getCustaddress());
        OrderId.setText(cmbOrderId.getValue());
        loadcmbItemdata();
    }

    private void loadcmbItemdata() throws SQLException, ClassNotFoundException {
        String value = cmbOrderId.getValue();
        String sql="SELECT itemCode FROM `Order detail` WHERE OrderId='"+value+"'";
        NativeQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(item.class);

        List<String> items=sqlQuery.list();
        ObservableList<String> ids= FXCollections.observableArrayList();
        for (String o:items) {
            ids.add(o);
        }
        cmbItem.setItems(ids);
    }

    private void loadCmbOrderData() throws SQLException, ClassNotFoundException {
        String sql="SELECT OrderId FROM Orders";
        NativeQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(orders.class);

        List<String> allorders=sqlQuery.list();
        ObservableList<String> ids= FXCollections.observableArrayList();
        for (String s:allorders){
            ids.add(s);
        }
        cmbOrderId.setItems(ids);
    }

    private void loaddata() throws SQLException, ClassNotFoundException {
        tblitem.getItems().setAll(LoadTableData());
    }

    ObservableList<getOrder> l= FXCollections.observableArrayList();
    private ObservableList<getOrder> LoadTableData() throws SQLException, ClassNotFoundException {
        String value = cmbOrderId.getValue();
        String sql="SELECT * FROM `Order detail` WHERE OrderId='"+value+"'";
        NativeQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(item.class);

        List<getOrder> item=sqlQuery.list();
        ObservableList<getOrder> list= FXCollections.observableArrayList();
        for (getOrder i:item) {
            list.add(new getOrder(
                    i.getItemcode(),
                    i.getDescription(),
                    i.getQty(),
                    i.getUnitprice()

            ));
            l.add(new getOrder(
                    i.getItemcode(),
                    i.getDescription(),
                    i.getQty(),
                    i.getUnitprice()
            ));
        }

        return list;
    }

    public void clear(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemcode = tblitem.getSelectionModel().getSelectedItem().getItemcode();
        int Oldqty = tblitem.getSelectionModel().getSelectedItem().getQty();
        item i1=session.get(item.class,itemcode);
        i1.setQtyOnHand(i1.getQtyOnHand()+Oldqty);


        String value = cmbOrderId.getValue();
        String sql="delete from  `Order detail` where (OrderId='"+value+"') AND (itemCode='"+itemcode+"');";
        NativeQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(order_detail.class);
        loaddata();
    }

    public void UpdateQty(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        item i1=session.get(item.class,cmbItem.getValue());
        Double unitprice=i1.getUnitPrice();
        int newQty=Integer.parseInt(txtqty.getText());

        String value = cmbOrderId.getValue();
        String value1 = cmbItem.getValue();
        String sql="SELECT OrderQTY FROM `Order detail` WHERE (OrderId='"+value+"') AND (itemCode='"+value1+"')";
        NativeQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(order_detail.class);

        String orderqty= String.valueOf(sqlQuery);
        int OldQty=Integer.parseInt(orderqty);
        Double discount=(((i1.getDiscount()/100)*unitprice)*newQty);

        //Update Order Detail
        ItemDetails i2=new ItemDetails(cmbOrderId.getValue(),cmbItem.getValue(),newQty,discount,OldQty);
        session.update(i2);
                loaddata();
                totalCost();
                clean();

                transaction.commit();
                session.close();
    }

    private void totalCost() throws SQLException, ClassNotFoundException {
        Double ttl = 0.0;
        Double disc=0.0;
        for (getOrder o:l) {
           disc+=(((session.get(item.class,o.getItemcode()).getDiscount()/100)*o.getUnitprice())*o.getQty());
           ttl+=((o.getUnitprice()*o.getQty()));

        }
        lblDiscount.setText(String.valueOf(disc));
        lblTotal.setText(String.valueOf(ttl-disc));
    }

    public void clean(){
        txttitle.setText("");
        txtname.setText("");
        txtaddress.setText("");
        txtDiscription.setText("");
        txtPackSize.setText("");
        txtqtyOnHand.setText("");
        txtunitprice.setText("");
        txtqty.setText("");
        lblTotal.setText("");
        lblDiscount.setText("");
    }

    public void keyReleased(KeyEvent keyEvent) {
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
}
