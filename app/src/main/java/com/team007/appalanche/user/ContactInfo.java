package com.team007.appalanche.user;

public class ContactInfo {
    private Integer phoneNumber;
    private String githubLink;

    /**
     * constructor function for contact info
     * @param phoneNumber
     * @param githubLink
     */
    ContactInfo(Integer phoneNumber, String githubLink) {
        this.phoneNumber = phoneNumber;
        this.githubLink = githubLink;
    }

    /**
     * function to set contact info
     * @param phoneNumber
     * @param githubLink
     */
    public void setContactInfo(Integer phoneNumber, String githubLink) {
        this.phoneNumber = phoneNumber;
        this.githubLink = githubLink;
    }
}