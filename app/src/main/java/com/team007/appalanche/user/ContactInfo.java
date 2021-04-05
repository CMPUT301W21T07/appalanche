package com.team007.appalanche.user;

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
    public ContactInfo(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
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
    public void setContactInfo(Integer phoneNumber, String githubLink) {
        this.phoneNumber = phoneNumber;
        this.githubLink = githubLink;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public String getGithubLink() {
        return githubLink;
    }
}
