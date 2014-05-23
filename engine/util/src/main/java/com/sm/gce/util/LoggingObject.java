package com.sm.gce.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implement against SLF4J for logging. This is a convenience class for adapters
 * to inherit from
 */
public class LoggingObject {
	protected Logger logger = LoggerFactory
			.getLogger(this.getClass().getName());
}
