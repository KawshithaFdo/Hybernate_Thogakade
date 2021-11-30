package entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class orders {
    @Id
    private String OrderId;
    private Date OrderDate;

   /* @OneToMany(mappedBy = "order_detail")
    private Set<order_detail> order_details = new HashSet<order_detail>();*/

    @ManyToOne
    private customer customer;

    public orders() {
    }

    public orders(String orderId, Date orderDate, entity.customer customer) {
        OrderId = orderId;
        OrderDate = orderDate;
        this.customer = customer;
    }

    public entity.customer getCustomer() {
        return customer;
    }

    public void setCustomer(entity.customer customer) {
        this.customer = customer;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }




    @Override
    public String toString() {
        return "orders{" +
                "OrderId='" + OrderId + '\'' +
                ", OrderDate=" + OrderDate +
                ", customer=" + customer +
                '}';
    }
}
