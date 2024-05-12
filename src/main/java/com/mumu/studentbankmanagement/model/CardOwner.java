package com.mumu.studentbankmanagement.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardOwner {
    private String id;
    private String name;
    private String password;
    public String toString(){
        return this.getId()+" "+this.getName();
    }
}
