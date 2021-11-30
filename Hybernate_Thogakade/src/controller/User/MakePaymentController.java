package controller.User;


import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import entity.customer;
import entity.item;
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
import dto.ItemDetails;
import dto.Order;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import util.FactoryConfiguration;
import util.ValidationUtil;
import view.Tm.CartTm;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class MakePaymentController {
    public TextField txtPackSize;
    public TextField txtqtyOnHand;
    public ComboBox<String> cmbItem;
    public TextField txtqty;
    public TextField txtunitprice;
    public ComboBox<String> cmbCustomer;
    public TextField txtname;
    public TextField txtaddress;
    public TextField txttitle;
    public Label OrderId;
    public Label Orderdate;
    public Label OrderTime;
    public TableView<CartTm> tblcart;
    public TableColumn colItemId;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public Label lblDiscount;
    public Label lblTotal;
    public TextField txtDiscription;
    public Label remainingAmount;
    public TextField txtamountgiven;
    public int cartSelectedRowForRemove=-1;
    public double discount;
    public JFXButton btnAddtoCart;

   // private final PurchaseOrderBo bo = (PurchaseOrderBo) FactoryBo.getFactoryBo().getBO(FactoryBo.BOTypes.PURCHASE_ORDER);

    Session session = FactoryConfiguration.getInstance().getSession();
    Transaction transaction = session.beginTransaction();


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern qtyPattern = Pattern.compile("^[0-9]{1,}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        OpenValidations();
        DateandTime();
        setOrderid();
        cmbItem.setDisable(true);

        colItemId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        try {
            loadCustomerIds();
            loadItemIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setCustomerData((String) newValue);
                cmbItem.setDisable(false);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        cmbItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData((String) newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        tblcart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove= (int) newValue;
        });
    }

    private void OpenValidations() {
        map.put(txtqty,qtyPattern);
    }

    private void setOrderid() throws SQLException, ClassNotFoundException {
            //OrderId.setText(bo.getOrderId());
    }

    private void setItemData(String itemId) throws SQLException, ClassNotFoundException {
        item i1=session.get(item.class,itemId);
        if(i1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result set...").show();
        }else{
            txtDiscription.setText(i1.getDescription());
            txtPackSize.setText(i1.getPacksize());
            txtqtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            txtunitprice.setText(String.valueOf(i1.getUnitPrice()));
            discount=i1.getDiscount();
        }
    }

    private void setCustomerData(String customerid) throws SQLException, ClassNotFoundException {
        customer c1=session.get(customer.class,customerid);
        if(c1==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result set...").show();
        }else{
            txtname.setText(c1.getCustName());
            txtaddress.setText(c1.getCustaddress());
            txttitle.setText(c1.getCustTitle());

        }
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        String sql="SELECT itemCode FROM Item";
        NativeQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(item.class);

        List<String> itemIds = sqlQuery.list();
        cmbItem.getItems().addAll(itemIds);
    }

    public void loadCustomerIds() throws SQLException, ClassNotFoundException {
        String sql="SELECT CustId FROM Customer";
        NativeQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(customer.class);

        List<String> customerIds = sqlQuery.list();
        cmbCustomer.getItems().addAll(customerIds);
    }

    private void DateandTime() {
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

    ObservableList<CartTm> oblist= FXCollections.observableArrayList();
    double reamount=0;

    public void addtocart(ActionEvent actionEvent) {
        if (txtqty.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Enter Valid Qty.").show();
            return;
        }
        if (Integer.parseInt(txtqty.getText()) > (Integer.parseInt(txtqtyOnHand.getText()))){
            new Alert(Alert.AlertType.WARNING,"Invalid QTY..").show();
            return;
        }
        String description=txtDiscription.getText();
        int qtyonhand=Integer.parseInt(txtqtyOnHand.getText());
        double unitprice=Double.parseDouble(txtunitprice.getText());
        int qtyforcustomer=Integer.parseInt(txtqty.getText());

        Double totalDiscount = (((discount / 100) * unitprice) * qtyforcustomer);

        CartTm tm=new CartTm(
                cmbItem.getValue(),
                description,
                qtyforcustomer,
                unitprice,
                totalDiscount,
                ((qtyforcustomer * unitprice)-totalDiscount)
        );
        int rowid=isExists(tm);
        if (rowid==-1){
            oblist.add(tm);
        }else{
            CartTm temp=oblist.get(rowid);
            CartTm newTm=new CartTm(
                    temp.getCode(),
                    temp.getDescription(),
                    temp.getQty()+qtyforcustomer,
                    unitprice,
                    discount+temp.getDiscount(),
                    (qtyforcustomer * unitprice)+temp.getTotal()
            );

            if(qtyonhand<temp.getQty()){
                new Alert(Alert.AlertType.WARNING,"Invalid QTY..").show();
                return;
            }

            oblist.remove(rowid);
            oblist.add(newTm);
        }

        tblcart.setItems(oblist);
        calculateCost();
        txtqty.setText("");
    }

    double total=0.0;
    double amount=0.0;
    void calculateCost(){
        double ttl=0;
        double disc=0;
        for (CartTm tm:oblist
        ) {
            ttl+=tm.getTotal();
            disc+=tm.getDiscount();
        }
        reamount=ttl;
        lblDiscount.setText(disc+"/=");
        lblTotal.setText(reamount+"/=");
        total=disc;
        amount=reamount;
    }

    private int isExists(CartTm tm){
        for (int i = 0; i <oblist.size() ; i++) {
            if (tm.getCode().equals(oblist.get(i).getCode())){
                return i;
            }
        }
        return -1;
    }

    public void clear(ActionEvent actionEvent) {
        if(cartSelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING,"Please Select a row..").show();
        }else{
            oblist.remove(cartSelectedRowForRemove);
            calculateCost();
            tblcart.refresh();
        }
    }

    public void PlaceOrder(ActionEvent actionEvent) {
        ArrayList<ItemDetails> items=new ArrayList<>();
        double disc=0;
        for (CartTm tempTm:oblist) {
            disc=tempTm.getDiscount();
            items.add(
                    new ItemDetails(
                            OrderId.getText(),
                            tempTm.getCode(),
                            tempTm.getQty(),
                            disc
                    )
            );
        }

        Order order=new Order(OrderId.getText(), Orderdate.getText(), cmbCustomer.getValue(),items);

        try {
            Serializable save = session.save(order);
            new Alert(Alert.AlertType.CONFIRMATION,"Success.").show();

            OpenBill();
            clean();
            /*if (save) {
                OpenBill();
                clean();
            }else{
                new Alert(Alert.AlertType.ERROR,"Try Again.").show();
            }*/
            transaction.commit();
            session.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void OpenBill()  {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/Reports/Bill.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            String id=OrderId.getText();
            HashMap Hmap=new HashMap();
            Hmap.put("OrderId",id);
            Hmap.put("total",total);
            Hmap.put("amount",amount);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, Hmap, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);

            // JasperPrintManager.printReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void clean() throws SQLException, ClassNotFoundException {
        setOrderid();
        /*txttitle.setText("");
        txtname.setText("");
        txtaddress.setText("");*/
        txtDiscription.setText("");
        txtPackSize.setText("");
        txtqtyOnHand.setText("");
        txtqty.setText("");
        txtunitprice.setText("");
        remainingAmount.setText("0");
        txtamountgiven.setText("");
        oblist.remove(0, oblist.size());
        tblcart.refresh();
        calculateCost();
        cmbItem.setDisable(true);
    }

    public void amountgiven(ActionEvent actionEvent) {
        if (txtamountgiven.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please Enter Amount Given.").show();
        }else {
            remainingAmount.setText(String.valueOf(Double.parseDouble(txtamountgiven.getText()) - (reamount)));
        }
    }

    public void keyEvent(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAddtoCart);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                btnAddtoCart.requestFocus();
            }
        }
    }
}
