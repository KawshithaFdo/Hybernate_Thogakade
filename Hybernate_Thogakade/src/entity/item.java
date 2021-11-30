package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class item {
    @Id
    private String ItemCode;
    private String Description;
    private String Packsize;
    private Double UnitPrice;
    private int QtyOnHand;
    private Double Discount;

    /*  @OneToMany(mappedBy = "order_detail")
    private Set<order_detail> order_details = new HashSet<order_detail>();*/

    @ManyToOne
    private admin admin;

    /*public void addorder(order_detail group) {
        this.order_details.add(group);
    }*/

    public item() {

    }

    public item(String itemCode, String description, String packsize, Double unitPrice, int qtyOnHand, Double discount, entity.admin admin) {
        ItemCode = itemCode;
        Description = description;
        Packsize = packsize;
        UnitPrice = unitPrice;
        QtyOnHand = qtyOnHand;
        Discount = discount;
        this.admin = admin;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPacksize() {
        return Packsize;
    }

    public void setPacksize(String packsize) {
        Packsize = packsize;
    }

    public Double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        UnitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return QtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        QtyOnHand = qtyOnHand;
    }

    public Double getDiscount() {
        return Discount;
    }

    public void setDiscount(Double discount) {
        Discount = discount;
    }

    public entity.admin getAdmin() {
        return admin;
    }

    public void setAdmin(entity.admin admin) {
        this.admin = admin;
    }

     /*   public Set<order_detail> getOrder_details() {
        return order_details;
    }

    public void setOrder_details(Set<order_detail> order_details) {
        this.order_details = order_details;
    }

    public void addorderdetails(order_detail detail){
        this.order_details.add(detail);
    }*/
}
