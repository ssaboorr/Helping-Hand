package com.example.apps.helpinghand;

public class Savetechinfo {

    String uname,uphone,uemail,uage,uaddr,untn,ubg,uplace;

    public Savetechinfo(){

    }

    public Savetechinfo(String uname,String uphone,String uemail,String uage,String untn,String uaddr,String ubg,String uplace){
        this.uname=uname;
        this.uphone=uphone;
        this.uemail=uemail;
        this.uage=uage;
        this.untn=untn;
        this.uaddr=uaddr;
        this.ubg=ubg;
        this.uplace=uplace;
    }
    public String getUname(){
        return uname;
    }
    public String getUphone(){
        return uphone;
    }
    public String getUemail(){
        return uemail;
    }
    public String getUage() {return uage;}
    public String getUaddr(){return uaddr;}
    public String getUntn(){return untn;}
    public String getUbg(){return  ubg;}
    public String getUplace(){return uplace;}
}
