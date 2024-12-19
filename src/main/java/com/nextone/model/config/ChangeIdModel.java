/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.config;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Getter
@Setter
public class ChangeIdModel {

    private String name;
    private String value;

    public int getValue() {
        return Integer.parseInt(value);
    }

    public String getStringValue() {
        return value;
    }
}
