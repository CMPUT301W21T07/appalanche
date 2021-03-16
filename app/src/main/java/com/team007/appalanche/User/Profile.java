package com.team007.appalanche.User;

public class Profile {
    private String userName;
    private ContactInfo contactInfo;

    /**
     * constructor method for Profile class
     * @param userName
     * @param contactInfo
     */
    Profile(String userName, ContactInfo contactInfo) {
        this.userName = userName;
        this.contactInfo = contactInfo;
    }
}
