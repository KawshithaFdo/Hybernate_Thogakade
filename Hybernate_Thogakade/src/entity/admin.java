package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class admin {
    @Id
    private String Admin_ID;
    private String Name;
    private String Address;
    private String Contact;
    private String NIC;
    private String Passsword;

    @OneToMany(mappedBy = "item")
    private List<item> items=new ArrayList<>();

    public admin() {
    }

    public admin(String admin_ID, String name, String address, String contact, String NIC, String passsword, List<item> items) {
        Admin_ID = admin_ID;
        Name = name;
        Address = address;
        Contact = contact;
        this.NIC = NIC;
        Passsword = passsword;
        this.items = items;
    }

    public List<item> getItems() {
        return items;
    }

    public void setItems(List<item> items) {
        this.items = items;
    }

    public String getAdmin_ID() {
        return Admin_ID;
    }

    public void setAdmin_ID(String admin_ID) {
        Admin_ID = admin_ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getPasssword() {
        return Passsword;
    }

    public void setPasssword(String passsword) {
        Passsword = passsword;
    }
}
