package moe.skjsjhb.mc.fubuki.util

import org.slf4j.LoggerFactory
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger

/**
 * A minimum [Logger] implementation which bridges log calls to the underlying SLF4J logger.
 */
class Slf4jBridgedLogger(name: String) : Logger(name, null) {
    private val slf4jLogger = LoggerFactory.getLogger(name)

    override fun log(record: LogRecord) {
        val level = record.level
        val message = record.message
        val ex = record.thrown

        when (level) {
            Level.SEVERE -> slf4jLogger.error(message, ex)
            Level.WARNING -> slf4jLogger.warn(message, ex)
            Level.INFO -> slf4jLogger.info(message, ex)
            Level.CONFIG, Level.FINE -> slf4jLogger.debug(message, ex)
            Level.FINER -> slf4jLogger.trace(message, ex)
            else -> slf4jLogger.info(message, ex)
        }
    }
}