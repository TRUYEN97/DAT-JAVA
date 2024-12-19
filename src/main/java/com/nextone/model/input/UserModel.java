/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.input;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Getter
@Setter
public class UserModel {
    
    private String id = "";
    private String name = "";
    private String examStatus = "";
    private String mobile = "";
    private String examId = "0";
    private String modeName = "";
    private String rank = "";
    private String dateOfBirth = "";
    private String placeOfOrigin = "";
    private int sex = 0;

    public UserModel() {
    }
}
