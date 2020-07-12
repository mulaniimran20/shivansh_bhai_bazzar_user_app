package com.aarfaatechnovision.kjl.shivanshbhajibazar.model.AddressList;

/**
 * Created by Nishish on 8/6/2018.
 */

public class AddressListModelNew
{
    private String address;

    String UserFirstName,UserLastName,UserEmailId,UserContactNumber,UserRegisterfee,
            UserRegisterfeestatus,UseActivationstatus;

    public AddressListModelNew(String userFirstName, String userLastName, String userEmailId, String userContactNumber, String userRegisterfee, String userRegisterfeestatus, String useActivationstatus) {
        UserFirstName = userFirstName;
        UserLastName = userLastName;
        UserEmailId = userEmailId;
        UserContactNumber = userContactNumber;
        UserRegisterfee = userRegisterfee;
        UserRegisterfeestatus = userRegisterfeestatus;
        UseActivationstatus = useActivationstatus;
    }

    public String getUserFirstName() {
        return UserFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        UserFirstName = userFirstName;
    }

    public String getUserLastName() {
        return UserLastName;
    }

    public void setUserLastName(String userLastName) {
        UserLastName = userLastName;
    }

    public String getUserEmailId() {
        return UserEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        UserEmailId = userEmailId;
    }

    public String getUserContactNumber() {
        return UserContactNumber;
    }

    public void setUserContactNumber(String userContactNumber) {
        UserContactNumber = userContactNumber;
    }

    public String getUserRegisterfee() {
        return UserRegisterfee;
    }

    public void setUserRegisterfee(String userRegisterfee) {
        UserRegisterfee = userRegisterfee;
    }

    public String getUserRegisterfeestatus() {
        return UserRegisterfeestatus;
    }

    public void setUserRegisterfeestatus(String userRegisterfeestatus) {
        UserRegisterfeestatus = userRegisterfeestatus;
    }

    public String getUseActivationstatus() {
        return UseActivationstatus;
    }

    public void setUseActivationstatus(String useActivationstatus) {
        UseActivationstatus = useActivationstatus;
    }

    public AddressListModelNew()
    {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
