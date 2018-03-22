package com.ztrew.hbasecdc;

import com.ztrew.hbasecdc.program.Program;

import com.beust.jcommander.JCommander;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Driver {

    private static Map<String, List<String>> programs = new TreeMap<String, List<String>>();

    private static void printUsage() {

        System.out.println();
        System.out.println("Valid options are:");
        for (String program : programs.keySet()) {
            System.out.println(String.format("%1$" + 35 + "s", program) + " : " + programs.get(program).get(1));
        }
        System.out.println();

        System.exit(1);
    }

    public static void main(String[] args) {

        programs.put("update-every-second", Arrays.asList("com.ztrew.hbasecdc.program.UpdateEverySecond", "Update the hive record every 1 second."));
        programs.put("update-every-five-seconds", Arrays.asList("com.ztrew.hbasecdc.program.UpdateEveryFiveSeconds", "Update the hive record every 5 seconds."));
        programs.put("get-latest-records", Arrays.asList("com.ztrew.hbasecdc.program.GetLatestRecords", "Get records modified in the last 1 second from HBase."));

        if (args.length < 1) printUsage();

        List<String> program = programs.get(args[0]);

        if (program == null) printUsage();

        try {
            Class clazz = Class.forName(program.get(0));

            Constructor constructor = clazz.getConstructor();

            Program p = (Program) constructor.newInstance();
            new JCommander(p, args);
            p.run();

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }    }

}
