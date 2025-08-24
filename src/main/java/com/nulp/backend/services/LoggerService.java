package com.nulp.backend.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LoggerService {
    private static final Logger logger = LogManager.getLogger();

    private LoggerService() {}

    public static Logger getLogger() {
        return logger;
    }
}
