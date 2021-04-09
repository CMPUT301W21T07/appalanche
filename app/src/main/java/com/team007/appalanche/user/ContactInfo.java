package com.team007.appalanche.user;

/**
 * This class represents a user's contact information, which is comprised
 * of their phone number and a link to their GitHub page.
 */

public class ContactInfo {
    private String phoneNumber;
    private String githubLink;

    /**
     * constructor function for contact info
     * @param phoneNumber
     * @param githubLink
     */
    public ContactInfo(String phoneNumber, String githubLink) {
        this.phoneNumber = phoneNumber;
        this.githubLink = githubLink;
    }

//    public ContactInfo(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
    public ContactInfo(String githubLink) {
        this.githubLink = githubLink;
    }

    public ContactInfo() {
    }

    /**
     * function to set contact info
     * @param phoneNumber
     * @param githubLink
     */
    public void setContactInfo(String phoneNumber, String githubLink) {
        this.phoneNumber = phoneNumber;
        this.githubLink = githubLink;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGithubLink() {
        return githubLink;
    }
}
