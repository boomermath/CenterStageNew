/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.TechiesHardwareWithoutDriveTrain;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/**
 * This 2020-2021 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Freight Frenzy game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@Autonomous(name = "Red Backdrop", group = "Concept")
//@Disabled
public class AutoRedBackdrop extends AutoBackdrop {

    public double adjustTurn(double angle) {
        return -angle;
    }
    public int adjustTrajectorydistance(int distance) {
        return -distance;
    }

    public int adjustZone(int zone) {return 4-zone;}

    @Override
    protected int determineTargetZone(Telemetry telemetry) {
        {
            if (robotCore.rightsensorRange.getDistance(DistanceUnit.INCH) > 8 && robotCore.rightsensorRange.getDistance(DistanceUnit.INCH) < 28) {
                position = RIGHT_POSITION;
            }else if (robotCore.leftsensorRange.getDistance(DistanceUnit.INCH) > 5 && robotCore.leftsensorRange.getDistance(DistanceUnit.INCH) < 28){
                position = LEFT_POSITION;
            } else {
                position = MIDDLE_POSITION;
            }
            telemetry.addData("position", position);
            telemetry.addData("deviceName", robotCore.leftsensorRange.getDeviceName() );
            telemetry.addData("range", String.format("%.01f in", robotCore.leftsensorRange.getDistance(DistanceUnit.INCH)));
            telemetry.addData("deviceName", robotCore.rightsensorRange.getDeviceName() );
            telemetry.addData("range", String.format("%.01f in", robotCore.rightsensorRange.getDistance(DistanceUnit.INCH)));
            telemetry.update();
            return position;
        }
    }

    protected void goToTapeFromStart(int targetZone) {
        //make it move more if it is on position 2
        forward(4);// -(targetZone%2 -1)*2);
        if (targetZone == LEFT_POSITION) {
            odoDriveTrain.turn(adjustTurn(Math.toRadians(-94)));
            sleep(800);
            strafeleft(2);
            back(26);
        } else if (targetZone == RIGHT_POSITION) {
            distanceAdd+=10;
            odoDriveTrain.turn(adjustTurn(Math.toRadians(-96)));
            sleep(800);
            forward(5);
            sleep(300);
            back(10);
        } else if (targetZone == MIDDLE_POSITION) {
            strafeleft(adjustTurn(4));
            forward(5);
            back(11);
        }
    }
    protected void goToBackdrop(int targetZone){
        if (targetZone == LEFT_POSITION) {
            lineToSpline(-20,adjustTrajectorydistance(18),0);
        } else if (targetZone == RIGHT_POSITION) {
            lineToSpline(-36, adjustTrajectorydistance(-8), 0);
        } else if (targetZone == MIDDLE_POSITION) {
            back(2);
            odoDriveTrain.turn(adjustTurn(Math.toRadians(-96)));
            lineToSpline(-36, adjustTrajectorydistance(2), 0);
        }
    }

    protected void park() {
        odoDriveTrain.turn(adjustTurn(Math.toRadians(-7)));
        strafeleft(adjustTrajectorydistance(30+distanceAdd));
        back(10+distanceAdd);
    }
}

