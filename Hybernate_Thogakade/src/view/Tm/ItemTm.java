package view.Tm;

public class ItemTm {
    private String itemcode;
    private String description;
    private String packSize;
    private Double unitprice;
    private int qty;
    private Double discount;

    public ItemTm() {
    }

    public ItemTm(String itemcode, String description, String packSize, Double unitprice, int qty, Double discount) {
        this.itemcode = itemcode;
        this.description = description;
        this.packSize = packSize;
        this.unitprice = unitprice;
        this.qty = qty;
        this.discount=discount;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public Double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
