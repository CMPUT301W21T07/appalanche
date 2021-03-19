package com.team007.appalanche.user;

import java.io.Serializable;

public class Profile implements Serializable {
    private String userName;
    private com.team007.appalanche.user.ContactInfo contactInfo;

    public Profile () {}

    /**
     * constructor method for Profile class
     * @param userName
     * @param contactInfo
     */
    public Profile(String userName, ContactInfo contactInfo) {
        this.userName = userName;
        this.contactInfo = contactInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }
}
