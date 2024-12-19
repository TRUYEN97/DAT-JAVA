/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nextone.common.communication.Communicate;

/**
 *
 * @author Administrator
 */
public interface IReadStream extends IReadable, IgetName{
    
    boolean setStreamReadable(AbsStreamReadable readable);
}
