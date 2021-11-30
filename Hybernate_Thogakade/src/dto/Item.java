package dto;

public class Item {
    private String itemCode;
    private String description;
    private String packsize;
    private double unitprice;
    private int qtyonhand;
    private double discount;
    private String adminid;

    public Item(String itemCode, String description, String packsize, double unitprice, int qtyonhand, double discount, String adminid) {
        this.itemCode = itemCode;
        this.description = description;
        this.packsize = packsize;
        this.unitprice = unitprice;
        this.qtyonhand = qtyonhand;
        this.discount=discount;
        this.adminid=adminid;
    }

    public Item() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPacksize() {
        return packsize;
    }

    public void setPacksize(String packsize) {
        this.packsize = packsize;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public int getQtyonhand() {
        return qtyonhand;
    }

    public void setQtyonhand(int qtyonhand) {
        this.qtyonhand = qtyonhand;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }
}
