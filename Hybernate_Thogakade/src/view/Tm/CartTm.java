package view.Tm;

public class CartTm {
    private String code;
    private String Description;
    private int qty;
    private double unitprice;
    private double discount;
    private double total;

    public CartTm(String code, String description, int qty, double unitprice, double discount, double total) {
        this.code = code;
        Description = description;
        this.qty = qty;
        this.unitprice = unitprice;
        this.discount = discount;
        this.total = total;
    }

    public CartTm() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
