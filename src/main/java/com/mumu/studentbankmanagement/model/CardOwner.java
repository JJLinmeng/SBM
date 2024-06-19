package com.mumu.studentbankmanagement.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardOwner {
    private String id;
    private String name;
    private String password;
    private String email;
    private int level;
    private LocalDateTime lastLogin;
    private int points;
    private String avatar;
    public String toString(){
        return this.getId()+" "+this.getName();
    }
    public CardOwner(String id,String name,String password){
        this.id=id;
        this.name=name;
        this.password=password;
    }


}
