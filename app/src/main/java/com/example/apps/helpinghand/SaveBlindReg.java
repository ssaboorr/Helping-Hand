package com.example.apps.helpinghand;

public class SaveBlindReg {
    String uname,uphone,ustatus,uemail,upass;

public SaveBlindReg(){

}
   public SaveBlindReg(String uname,String uphone,String ustatus,String uemail,String upass){
    this.uname=uname;
    this.uphone=uphone;
    this.ustatus=ustatus;
    this.uemail=uemail;
    this.upass=upass;
   }
   public  String getUname(){return uname; }
   public String getUphone(){return uphone;}
   public  String getStatus(){return  ustatus;}
   public String getUemail(){return uemail;}
   public String getUpass(){return upass;}
}
