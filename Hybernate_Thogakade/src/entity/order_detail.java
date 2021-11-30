package entity;

import javax.persistence.*;

@Entity
public class order_detail {
    @Id
    private String id;
    private orders orders;
    private item item;
    private int OrderQTY;
    private Double Discount;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="OrderId")
    public entity.orders getOrders() {
        return orders;
    }

    public void setOrders(entity.orders orders) {
        this.orders = orders;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itemCode")
    public entity.item getItem() {
        return item;
    }

    public void setItem(entity.item item) {
        this.item = item;
    }

    public int getOrderQTY() {
        return OrderQTY;
    }

    public void setOrderQTY(int orderQTY) {
        OrderQTY = orderQTY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getDiscount() {
        return Discount;
    }

    public void setDiscount(Double discount) {
        Discount = discount;
    }
}
