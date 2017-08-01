package com.linkedin.steps;

import org.junit.runner.JUnitCore;


public class mainClass {
    public static void main(String[] args) {
        linkedinSteps ms = new linkedinSteps();
        ms.arg = "800";
        ms.drv = "f";
        ms.jukoFlag = "no";
        ms.recruitFlag = "no";
        ms.message = "no";
        ms.distance = "";
        ms.ignoreCity = "yes";
        ms.myNetwork = "yes";
        ms.viewOnly = "yes";
        try {

            ms.arg = args[0];
            ms.drv = args[1];
            ms.jukoFlag = args[2];
            ms.recruitFlag = args[3];
            ms.message = args[4];
            ms.distance = args[5];
            ms.ignoreCity = args[6];
            ms.myNetwork = args[7];

        } catch (Exception ex) {
            System.out.println("Not enough parameters");
        }
        JUnitCore core = new JUnitCore();
        core.run(com.linkedin.MyStories.class);


        //   System.out.println("arg "+args[0]);
    }
}
