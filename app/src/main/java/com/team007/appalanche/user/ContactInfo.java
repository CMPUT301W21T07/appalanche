package com.team007.appalanche.User;

/**
 * This class represents a user's contact information, which is comprised
 * of their phone number and a link to their GitHub page.
 */

public class ContactInfo {
    private Integer phoneNumber;
    private String githubLink;

    /**
     * constructor function for contact info
     * @param phoneNumber
     * @param githubLink
     */
    public ContactInfo(Integer phoneNumber, String githubLink) {
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
