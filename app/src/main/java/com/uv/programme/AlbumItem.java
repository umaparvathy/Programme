package com.uv.programme;

import java.util.Date;

/**
 * Created by venkatsr on 14/12/15.
 */
public class AlbumItem{
    private String id;
    private String name;
    private String description;
    private Date createdDate;
    public AlbumItem(String id,String name,String description){
        this.id=id;
        this.name=name;
        this.description=description;
    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
    @Override
    public String toString(){
        //returning name because that is what will be displayed in the Spinner control
        return(this.name);
    }
}