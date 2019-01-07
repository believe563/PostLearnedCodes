package com.example;


import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static void main(String[] args) {
        Schema schema=new Schema(1,"com.greenrobot.dao");
        Entity son=schema.addEntity("Son");
        son.addIdProperty();
        son.addStringProperty("name").notNull();
        son.addIntProperty("age");

        Entity father = schema.addEntity("Father");
        father.addIdProperty();
        father.addStringProperty("name");
        father.addIntProperty("age");

//        一对一时
//        Property fatherId=son.addLongProperty("fatherId").getProperty();
//        son.addToOne(father, fatherId);

////        一对多时,,,多的那一方将少的那一方引入为外键，要先拿到主键，写完要先运行一下生成新的Dao文件
//        Property sonId=father.addLongProperty("sonId").getProperty();
//        father.addToOne(son,sonId);
//        son.addToMany(father,sonId).setName("fathers");

        try {
            new DaoGenerator().generateAll(schema,"app/src/main/java");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch(Exception e){

        }
    }
}
