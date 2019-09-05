package org.techtown.myaroundme;

public class LoginUser {
    String id, password, name, sex, birth, phone, email;

    public LoginUser() {

    }

    public String getId(){
        return id;
    }

    public String getPassword(){
        return password;
    }

    public String getName(){
        return name;
    }

    public String getSex(){
        return sex;
    }

    public String getBirth(){
        return birth;
    }

    public String getPhone(){
        return phone;
    }

    public String getEmail(){
        return email;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSex(String sex){
        this.sex = sex;
    }

    public void setBirth(String birth){
        this.birth = birth;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void initialization(){
        id = null;
        password = null;
        name = null;
        sex = null;
        birth = null;
        phone = null;
    }
}
