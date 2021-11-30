package dto;

public class ItemDetails {
    private String orderid;
    private String itemcode;
    private int qty;
    private double discount;
    private int oldqty;

    public ItemDetails(String itemcode, int qty, double discount) {
        this.itemcode = itemcode;
        this.qty = qty;
        this.discount = discount;
    }

    public ItemDetails(String orderid, String itemcode) {
        this.orderid = orderid;
        this.itemcode = itemcode;
    }

    public ItemDetails(String orderid, String itemcode, int qty, double discount) {
        this.orderid = orderid;
        this.itemcode = itemcode;
        this.qty = qty;
        this.discount = discount;
    }

    public ItemDetails(String orderid, String itemcode, int qty, double discount, int oldqty) {
        this.orderid = orderid;
        this.itemcode = itemcode;
        this.qty = qty;
        this.discount = discount;
        this.oldqty = oldqty;
    }

    public ItemDetails() {
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public int getOldqty() {
        return oldqty;
    }

    public void setOldqty(int oldqty) {
        this.oldqty = oldqty;
    }
}
