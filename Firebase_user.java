package com.project.team8;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

public class Firebase_user {
    public String id;
    public String name;
    public String password;
    public String sex;
    public String phone;
    public String email;
    public String money;

    public Firebase_user(){

    }
    public Firebase_user(String id, String name, String password, String sex, String phone,
                         String email, String money)
    {
        this.id = id;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.money = money;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("id",id);
        result.put("name",name);
        result.put("password",password);
        result.put("sex",sex);
        result.put("phone",phone);
        result.put("email",email);
        result.put("money",money);
        return result;
    }


}
