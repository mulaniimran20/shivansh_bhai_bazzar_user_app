package com.aarfaatechnovision.kjl.shivanshbhajibazar.model.addressBook;

public class Address {

    int society_id,society_pincode;
    String society_name,society_address;

    public Address(int society_id, int society_pincode, String society_name, String society_address) {
        this.society_id = society_id;
        this.society_pincode = society_pincode;
        this.society_name = society_name;
        this.society_address = society_address;
    }

    public Address() {
    }

    public int getSociety_id() {
        return society_id;
    }

    public void setSociety_id(int society_id) {
        this.society_id = society_id;
    }

    public int getSociety_pincode() {
        return society_pincode;
    }

    public void setSociety_pincode(int society_pincode) {
        this.society_pincode = society_pincode;
    }

    public String getSociety_name() {
        return society_name;
    }

    public void setSociety_name(String society_name) {
        this.society_name = society_name;
    }

    public String getSociety_address() {
        return society_address;
    }

    public void setSociety_address(String society_address) {
        this.society_address = society_address;
    }
}
