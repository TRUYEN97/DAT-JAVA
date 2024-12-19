/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.modelTest.process;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Builder
@Getter
@Setter
public class TestInfo {

    private String id;
    private String carId;

    public TestInfo(String id, String carId) {
        this.id = id;
        this.carId = carId;
    }
}
