package org.bonn.se.carlook.model.objects.dto;

public class UserDTO extends AbstractDTO {
    private String eMail;
    private String password;

    private String forename;
    private String surname;

    public String getEMail() {
        return this.eMail;
    }

    public void setEMail(String eMail){
        this.eMail = eMail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
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

}
