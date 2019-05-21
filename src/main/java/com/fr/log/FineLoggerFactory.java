package com.fr.log;

/**
 * @author richie
 * @version 1.0
 * Created by richie on 2019-05-07
 */
public class FineLoggerFactory {

    private static FineLogger logger = new FineLogger();

    public static FineLogger getLogger() {
        return logger;
    }
}
