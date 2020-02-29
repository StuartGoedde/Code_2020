/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorSensor extends SubsystemBase {
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  public final ColorSensorV3 mColorSensor = new ColorSensorV3(i2cPort);

  private static final int YELLOW_CALIBRATION_LIGHT_OFF = 60;
    private static final int RED_CALIBRATION_LIGHT_OFF = 20;
    private static final int GREEN_CALIBRATION_LIGHT_OFF = 90;
    private static final int CYAN_CALIBRATION_LIGHT_OFF = 120;

    private static final int YELLOW_CALIBRATION_LIGHT_ON = 94;
    private static final int RED_CALIBRATION_LIGHT_ON = 29;
    private static final int GREEN_CALIBRATION_LIGHT_ON = 121;
    private static final int CYAN_CALIBRATION_LIGHT_ON = 182;

    private static final boolean isLightOn = true;

    private static ColorSensor.color mCurrentColor = ColorSensor.color.UNKNOWN;
    public ColorSensor.color lastColor = ColorSensor.color.UNKNOWN;
    private int colorCounter = 0;
    private static final int COLOR_LIMIT = 5;
    private ColorSensor.color lastValidColor = ColorSensor.color.UNKNOWN;

    public ColorSensor.color wantedColor = ColorSensor.color.UNKNOWN;
    public ColorSensor.color detectedColor = ColorSensor.color.UNKNOWN;

    private ArrayList<ColorSensor.color> colors = new ArrayList<>();

    private int colorReadBefore = 0;
    private int colorReadAfter = 0;

    public ColorSensor.color detectColor(){
        Color detectedColor = mColorSensor.getColor();

        double[] RGB = {detectedColor.red, detectedColor.green, detectedColor.blue};
        
        double min, max, delta, h;
        min = Math.min(Math.min(RGB[0], RGB[1]), RGB[2]);
        max = Math.max(Math.max(RGB[0], RGB[1]), RGB[2]);
        delta = max - min;

        colorReadBefore++;
        SmartDashboard.putNumber("Color Read Before", colorReadBefore);

        while(true){
            if(max == 0){
                h = -1;
                break;
            }
            if(RGB[0] == max){
                h = (RGB[1] - RGB[2])/delta;
            } else if(RGB[1] == max){
                h = 2 + (RGB[2] - RGB[0])/delta;
            } else {
                h = 4 + (RGB[0] - RGB[1])/delta;
            }
            h *= 60;
            if(h < 0){
                h += 360;
            }
            break;
        }

        if (mCurrentColor == lastColor) {
            colorCounter++;
        }
        else {
          colorCounter = 0;
        }
        lastColor = mCurrentColor;
        colorReadAfter++;
        if(colorCounter >= COLOR_LIMIT){
            if(isLightOn){
              if((h >= 0 && h <= ((RED_CALIBRATION_LIGHT_ON + YELLOW_CALIBRATION_LIGHT_ON)/2)) || (h >= 300 && h <= 360)){
                  mCurrentColor = ColorSensor.color.RED;
              } else if(h >= ((RED_CALIBRATION_LIGHT_ON + YELLOW_CALIBRATION_LIGHT_ON)/2) && h <= ((YELLOW_CALIBRATION_LIGHT_ON + GREEN_CALIBRATION_LIGHT_ON)/2)){
                  mCurrentColor = ColorSensor.color.YELLOW;
              } else if(h >= ((YELLOW_CALIBRATION_LIGHT_ON + GREEN_CALIBRATION_LIGHT_ON)/2) && h <= ((GREEN_CALIBRATION_LIGHT_ON + CYAN_CALIBRATION_LIGHT_ON)/2)){
                  mCurrentColor = ColorSensor.color.GREEN;
              } else if(h >= ((GREEN_CALIBRATION_LIGHT_ON + CYAN_CALIBRATION_LIGHT_ON)/2) && h <= 240){
                  mCurrentColor = ColorSensor.color.CYAN;
              } else {
                  mCurrentColor = ColorSensor.color.UNKNOWN;
              }
            } else{
              if((h >= 0 && h <= ((RED_CALIBRATION_LIGHT_OFF + YELLOW_CALIBRATION_LIGHT_OFF)/2)) || (h >= 300 && h <= 360)){
                  mCurrentColor = ColorSensor.color.RED;
              } else if(h >= ((RED_CALIBRATION_LIGHT_OFF + YELLOW_CALIBRATION_LIGHT_OFF)/2) && h <= ((YELLOW_CALIBRATION_LIGHT_OFF + GREEN_CALIBRATION_LIGHT_OFF)/2)){
                  mCurrentColor = ColorSensor.color.YELLOW;
              } else if(h >= ((YELLOW_CALIBRATION_LIGHT_OFF + GREEN_CALIBRATION_LIGHT_OFF)/2) && h <= ((GREEN_CALIBRATION_LIGHT_OFF + CYAN_CALIBRATION_LIGHT_OFF)/2)){
                  mCurrentColor = ColorSensor.color.GREEN;
              } else if(h >= ((GREEN_CALIBRATION_LIGHT_OFF + CYAN_CALIBRATION_LIGHT_OFF)/2) && h <= 240){
                  mCurrentColor = ColorSensor.color.CYAN;
              } else {
                  mCurrentColor = ColorSensor.color.UNKNOWN;
              }
            }
            lastValidColor = mCurrentColor;
            SmartDashboard.putNumber("Color Read After", colorReadAfter);
            SmartDashboard.putString("Detected Color", mCurrentColor.toString());
            return mCurrentColor;
        }
        SmartDashboard.putNumber("Color Read After", colorReadAfter);
        SmartDashboard.putString("Detected Color", lastValidColor.toString());
        return lastValidColor;
    }

    public void rotateToColor(){
        SmartDashboard.putString("Wanted Color", "UNKNOWN");
        while(wantedColor == ColorSensor.color.UNKNOWN){
            wantedColor = toColor(SmartDashboard.getString("Wanted Color", "UNKNOWN")).getActualColor();
        }
        detectedColor = ColorSensor.color.UNKNOWN;
        while(wantedColor != detectedColor){
            detectedColor = detectColor();
            //colorWheel.set(MOTOR_SPEED);
        }
        //colorWheel.set(0);
    }

    public void rotateNumber(){
        ArrayList<ColorSensor.color> kColors = new ArrayList<>();
        kColors.add(ColorSensor.color.RED);
        kColors.add(ColorSensor.color.GREEN);
        kColors.add(ColorSensor.color.CYAN);
        kColors.add(ColorSensor.color.YELLOW);

        /*Timer timer = new Timer();
        timer.start();
        while(timer.get() <= 0.1){}*/

        wantedColor = detectColor();
        while(wantedColor == ColorSensor.color.UNKNOWN){
            wantedColor = detectColor();
        }
        SmartDashboard.putString("Position Wanted Color", ColorSensor.color.RED.toString());
        colors.clear();
        ColorSensor.color firstColor = detectColor();
        colors.add(firstColor);
        int counter = 0;
        //ColorSensor.color lastColor = firstColor;
        ColorSensor.color currentColor;
        while(counter < 7){
            //colorWheel.set(MOTOR_SPEED);
            currentColor = detectColor();
            /*if((kColors.indexOf(lastColor)+1 == kColors.indexOf(currentColor)) || (kColors.indexOf(lastColor) == 3 && kColors.indexOf(currentColor) == 0)){
                colors.add(currentColor);
                if(currentColor == wantedColor){
                    counter++;
                }
            }*/
            if(currentColor == ColorSensor.color.RED && lastColor != ColorSensor.color.RED){
                counter++;
            }
            lastColor = currentColor;
            SmartDashboard.putNumber("Counter", counter);
            SmartDashboard.putString("ArrayList Values", iterate(colors));
            /*if(Robot.joystick.getRawButton(1)){
                break;
            }*/
        }
        //colorWheel.set(0);
    }

    private String iterate(ArrayList<ColorSensor.color> colors){
        String fin = "";
        for(ColorSensor.color color : colors){
            fin += color.toString() + " ";
        }
        return fin;
    }

    public void deploy(){}

    public void retract(){}

    public void spin(){}

    public void approach(){}

    public ColorSensor.color getCurrentColor(){
        return lastValidColor;
    }

    public void outputToSmartDashboard(){
        SmartDashboard.putString("Detected Color", mCurrentColor.toString());
    }

    public void calibrate(){
        Color detectedColor = mColorSensor.getColor();

        double[] RGB = {detectedColor.red, detectedColor.green, detectedColor.blue};
        
        double min, max, delta, h;
        min = Math.min(Math.min(RGB[0], RGB[1]), RGB[2]);
        max = Math.max(Math.max(RGB[0], RGB[1]), RGB[2]);
        delta = max - min;
        while(true){
            if(max == 0){
                h = -1;
                break;
            }
            if(RGB[0] == max){
                h = (RGB[1] - RGB[2])/delta;
            } else if(RGB[1] == max){
                h = 2 + (RGB[2] - RGB[0])/delta;
            } else {
                h = 4 + (RGB[0] - RGB[1])/delta;
            }
            h *= 60;
            if(h < 0){
                h += 360;
            }
            break;
        }
        SmartDashboard.putNumber("H", h);

        String colorString = "";
        if(true){
            if(isLightOn){
              if((h >= 0 && h <= ((RED_CALIBRATION_LIGHT_ON + YELLOW_CALIBRATION_LIGHT_ON)/2)) || (h >= 300 && h <= 360)){
                  colorString = ColorSensor.color.RED.toString();
              } else if(h >= ((RED_CALIBRATION_LIGHT_ON + YELLOW_CALIBRATION_LIGHT_ON)/2) && h <= ((YELLOW_CALIBRATION_LIGHT_ON + GREEN_CALIBRATION_LIGHT_ON)/2)){
                colorString = ColorSensor.color.YELLOW.toString();
              } else if(h >= ((YELLOW_CALIBRATION_LIGHT_ON + GREEN_CALIBRATION_LIGHT_ON)/2) && h <= ((GREEN_CALIBRATION_LIGHT_ON + CYAN_CALIBRATION_LIGHT_ON)/2)){
                colorString = ColorSensor.color.GREEN.toString();
              } else if(h >= ((GREEN_CALIBRATION_LIGHT_ON + CYAN_CALIBRATION_LIGHT_ON)/2) && h <= 240){
                colorString = ColorSensor.color.CYAN.toString();
              } else {
                colorString = ColorSensor.color.UNKNOWN.toString();
              }
            } else{
              if((h >= 0 && h <= ((RED_CALIBRATION_LIGHT_OFF + YELLOW_CALIBRATION_LIGHT_OFF)/2)) || (h >= 300 && h <= 360)){
                colorString = ColorSensor.color.RED.toString();
              } else if(h >= ((RED_CALIBRATION_LIGHT_OFF + YELLOW_CALIBRATION_LIGHT_OFF)/2) && h <= ((YELLOW_CALIBRATION_LIGHT_OFF + GREEN_CALIBRATION_LIGHT_OFF)/2)){
                colorString = ColorSensor.color.YELLOW.toString();
              } else if(h >= ((YELLOW_CALIBRATION_LIGHT_OFF + GREEN_CALIBRATION_LIGHT_OFF)/2) && h <= ((GREEN_CALIBRATION_LIGHT_OFF + CYAN_CALIBRATION_LIGHT_OFF)/2)){
                colorString = ColorSensor.color.GREEN.toString();
              } else if(h >= ((GREEN_CALIBRATION_LIGHT_OFF + CYAN_CALIBRATION_LIGHT_OFF)/2) && h <= 240){
                colorString = ColorSensor.color.CYAN.toString();
              } else {
                colorString = ColorSensor.color.UNKNOWN.toString();
              }
            }
        }
        SmartDashboard.putString("Color", colorString);
    }

    public static ColorSensor.color toColor(String color){
        if(color.equals("RED")){
            return ColorSensor.color.RED;
        } else if(color.equals("GREEN")){
            return ColorSensor.color.GREEN;
        } else if(color.equals("CYAN")){
            return ColorSensor.color.CYAN;
        } else if(color.equals("YELLOW")){
            return ColorSensor.color.YELLOW;
        } else{
            return ColorSensor.color.UNKNOWN;
        }
    }

    public enum color{
        RED, GREEN, CYAN, YELLOW, UNKNOWN;

        public String toString(){
            if(this == ColorSensor.color.RED){
                return "RED";
            } else if(this == ColorSensor.color.GREEN){
                return "GREEN";
            } else if(this == ColorSensor.color.CYAN){
                return "CYAN";
            } else if(this == ColorSensor.color.YELLOW){
                return "YELLOW";
            } else{
                return "UNKNOWN";
            }
        }

        public int toInt(){
            if(this == ColorSensor.color.RED){
                return 1;
            } else if(this == ColorSensor.color.GREEN){
                return 2;
            } else if(this == ColorSensor.color.CYAN){
                return 3;
            } else if(this == ColorSensor.color.YELLOW){
                return 4;
            } else{
                return 0;
            }
        }

        public ColorSensor.color getActualColor(){
            if(this == ColorSensor.color.RED){
                return ColorSensor.color.CYAN;
            } else if(this == ColorSensor.color.GREEN){
                return ColorSensor.color.YELLOW;
            } else if(this == ColorSensor.color.CYAN){
                return ColorSensor.color.RED;
            } else if(this == ColorSensor.color.YELLOW){
                return ColorSensor.color.GREEN;
            } else{
                return ColorSensor.color.UNKNOWN;
            }
        }
    }
}