import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.sun.javafx.fxml.expression.Expression.get;


public class Appinitializer extends Application {
    public static void main(String[] args) {
        launch(args);
/*
          Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        item i=new item();
        i.setItemCode("i-006");
        i.setDescription("Rice");
        i.setPacksize("pack10");
        i.setUnitPrice(180.0);
        i.setQtyOnHand(100);
        i.setDiscount(10.8);
        i.setAdminid("A-001");


        orders order=new orders();
        order.setOrderId("O-002");
        order.setOrderDate(Date.valueOf("2021-11-29"));
        order.setCustid("C-001");

        order_detail detail=new order_detail();
        detail.setId("001");
        detail.setOrderQTY(50);
        detail.setDiscount(50.0);
        detail.setOrders(order);
        detail.setItem(i);


        transaction.commit();
        session.close();*/
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene((FXMLLoader.load(getClass().getResource("view/Login.fxml")))));
        //primaryStage.setFullScreen(true);
        primaryStage.show();


    }
}
