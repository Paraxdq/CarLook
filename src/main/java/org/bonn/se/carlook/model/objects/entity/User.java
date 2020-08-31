package org.bonn.se.carlook.model.objects.entity;

import org.bonn.se.carlook.services.util.Role;

import java.util.List;

public class User extends AbstractEntity {
    private int userId;
    private String eMail;
    private String password;

    private String forename;
    private String surname;

    public List<Role> roles;

    public int getUserId(){
        return this.userId;
    }

    public void setUserId(int userid) {
        this.userId = userid;
    }

    public String getEMail() {
        return this.eMail;
    }

    public void setEMail(String eMail){
        this.eMail = eMail;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getForename(){
        return this.forename;
    }

    public void setForename(String forename){
        this.forename = forename;
    }

    public String getSurname(){
        return this.surname;
    }

    public void setSurname(String Surname){
        this.surname = surname;
    }

    public boolean hasRole(Role role){
        return roles.contains(role);
    }
}
