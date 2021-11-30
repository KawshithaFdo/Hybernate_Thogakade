package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class customer {
    @Id
    private String CustId;
    private String CustTitle;
    private String CustName;
    private String Custaddress;
    private String City;
    private String Province;
    private String Postalcode;

    @OneToMany(mappedBy = "orders")
    private List<orders> orders = new ArrayList<>();

    @ManyToOne
    private user user;

    /*@OneToMany(mappedBy = "orders" , fetch= FetchType.LAZY)
    private List<orders> orders =new ArrayList<>();*/

    public customer() {
    }

    public customer(String custId, String custTitle, String custName, String custaddress, String city, String province, String postalcode, List<entity.orders> orders, entity.user user) {
        CustId = custId;
        CustTitle = custTitle;
        CustName = custName;
        Custaddress = custaddress;
        City = city;
        Province = province;
        Postalcode = postalcode;
        this.orders = orders;
        this.user = user;
    }

    public entity.user getUser() {
        return user;
    }

    public void setUser(entity.user user) {
        this.user = user;
    }

    public String getCustId() {
        return CustId;
    }

    public void setCustId(String custId) {
        CustId = custId;
    }

    public String getCustTitle() {
        return CustTitle;
    }

    public void setCustTitle(String custTitle) {
        CustTitle = custTitle;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getCustaddress() {
        return Custaddress;
    }

    public void setCustaddress(String custaddress) {
        Custaddress = custaddress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getPostalcode() {
        return Postalcode;
    }

    public void setPostalcode(String postalcode) {
        Postalcode = postalcode;
    }

    public List<entity.orders> getOrders() {
        return orders;
    }

    public void setOrders(List<entity.orders> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "customer{" +
                "CustId='" + CustId + '\'' +
                ", CustTitle='" + CustTitle + '\'' +
                ", CustName='" + CustName + '\'' +
                ", Custaddress='" + Custaddress + '\'' +
                ", City='" + City + '\'' +
                ", Province='" + Province + '\'' +
                ", Postalcode='" + Postalcode + '\'' +
                '}';
    }
}
