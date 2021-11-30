package dto;

public class getOrder {
    private String itemcode;
    private String description;
    private int qty;
    private Double unitprice;

    public getOrder(String itemcode, String description, int qty, Double unitprice) {
        this.itemcode = itemcode;
        this.description = description;
        this.qty = qty;
        this.unitprice = unitprice;
    }

    public getOrder() {
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }
}
