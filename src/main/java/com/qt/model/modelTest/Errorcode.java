/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.modelTest;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Getter
public class ErrorCode {

    private String name;
    private String description;
    private int score;

    public ErrorCode() {
    }

    public ErrorCode(String name, int score, String description) {
        this.name = name;
        this.score = score;
        this.description = description;
    }
}