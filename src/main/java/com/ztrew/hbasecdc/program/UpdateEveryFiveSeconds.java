package com.ztrew.hbasecdc.program;

import com.beust.jcommander.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UpdateEveryFiveSeconds implements Program
{

    private static Logger logger = LoggerFactory.getLogger(UpdateEverySecond.class);

    @Parameter(description = "Command to run.")
    private List<String> command = new ArrayList<String>();

    @Parameter(names = {"--foo", "-f"}, required = true)
    private String fooParam;

    @Parameter(names = {"--bar", "-b"}, required = true)
    private String barParam;

    @Parameter(names = {"--dump", "-d"})
    private boolean dump = false;

    public UpdateEveryFiveSeconds() {
    }

    public void run() {

        logger.info("Command : " + command);
        logger.info("Foo : " + fooParam);
        logger.info("Bar : " + barParam);
        logger.info("Dump Tables : " + dump);

        if (dump) {
            System.err.println("foo: " + fooParam + " bar: " + barParam);
        }

        System.out.println("foo: " + fooParam + " bar: " + barParam);

    }
}
