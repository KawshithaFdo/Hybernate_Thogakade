package entity;

import dto.Customer;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class user {
    @Id
    private String User_ID;
    private String Name;
    private String Address;
    private String Contact;
    private String NIC;
    private String Passsword;

    @OneToMany(mappedBy = "customer" , fetch= FetchType.LAZY)
    private List<Customer> customerlist=new ArrayList<>();



    public user() {
    }

    public user(String user_ID, String name, String address, String contact, String NIC, String passsword) {
        User_ID = user_ID;
        Name = name;
        Address = address;
        Contact = contact;
        this.NIC = NIC;
        Passsword = passsword;
    }

    public List<Customer> getCustomerlist() {
        return customerlist;
    }

    public void setCustomerlist(List<Customer> customerlist) {
        this.customerlist = customerlist;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
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

    @Override
    public String toString() {
        return "user{" +
                "User_ID='" + User_ID + '\'' +
                ", Name='" + Name + '\'' +
                ", Address='" + Address + '\'' +
                ", Contact='" + Contact + '\'' +
                ", NIC='" + NIC + '\'' +
                ", Passsword='" + Passsword + '\'' +
                '}';
    }
}
