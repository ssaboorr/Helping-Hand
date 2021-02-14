package com.example.apps.helpinghand;

public class SavemuteReg {
    String uname,ustatus,uemail,upass;
public SavemuteReg(){

}
    public SavemuteReg(String uname,String ustatus,String uemail,String upass){
        this.uname=uname;
        this.ustatus=ustatus;
        this.uemail=uemail;
        this.upass=upass;
    }
    public  String getUname(){return uname; }
    public  String getStatus(){return  ustatus;}
    public String getUemail(){return uemail;}
    public String getUpass(){return upass;}
}
