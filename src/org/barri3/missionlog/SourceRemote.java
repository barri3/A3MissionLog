/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.barri3.missionlog;

/**
 *
 * @author GB-BARRI3
 */
public class SourceRemote extends AbstractSource {
    @Override
    public void run() {
        MissionLog.out.appendSystemInfo("remote");
    }
}
