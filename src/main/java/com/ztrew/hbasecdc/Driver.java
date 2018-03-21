package com.ztrew.hbasecdc;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Driver {

    @Parameter(names={"--length", "-l"})
    int length;
    @Parameter(names={"--pattern", "-p"})
    int pattern;

    public static void main(String[] argv) {
        Driver driver = new Driver();
        JCommander.newBuilder()
                .addObject(driver)
                .build()
                .parse(argv);
        driver.run();
    }

    public void run() {
        System.out.printf("length: %d pattern: %d\n", length, pattern);
    }

}
