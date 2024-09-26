/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.modelTest.process;

import com.qt.model.input.CarModel;
import com.qt.model.input.yard.YardModel;
import com.qt.controller.ProcessModelHandle;
import com.qt.input.camera.CameraRunner;
import com.qt.output.SoundPlayer;
import com.qt.pretreatment.KeyEventManagement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author Admin
 */
@Builder
@AllArgsConstructor
@Getter
public class ModeParam {
    private final KeyEventManagement eventManagement;
    private final ProcessModel processModel;
}
