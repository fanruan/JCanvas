package com.fr.canvas.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author richie
 * @version 1.0
 * Created by richie on 2019-05-07
 */
public class FineLogger {

    private Logger logger = LoggerFactory.getLogger(FineLogger.class);

    public void error(String msg, Throwable e) {
        logger.error(msg, e);
    }
}
