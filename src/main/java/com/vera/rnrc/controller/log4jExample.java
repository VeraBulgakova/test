package com.vera.rnrc.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class log4jExample {
    /* Get actual class name to be printed on */
    private static final Logger logger = LoggerFactory.getLogger(log4jExample.class);
    public static void main(String[] args) {
        logger.info("This is an info message");
        logger.error("This is an error message", new RuntimeException("Oops!"));
logger.error("This is an error message", new RuntimeException("Oops!"));
    }
}