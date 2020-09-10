package org.weblog

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File
import java.io.FileNotFoundException

private val log: Logger = LogManager.getLogger(LogProcessor::class.java)

private val IP_REGEX = Regex("\\d+.\\d+.\\d+.\\d+")
private val URL_REGEX = Regex("\"\\D+ [^HTTP]+")

/**
 * Reads a single log file and generates statistics for that file
 */
class LogProcessor {

	/**
	 * returns statistics of the given log file
	 * @throws LogException for all errors, wrapping lower level exceptions
	 */
	fun read(f: File): Statistics {
		log.info("Processing log file [${f.absolutePath}]")

		val statistics = Statistics()
		try {
			if (!f.exists())
				throw FileNotFoundException("Log file not found at [${f.absolutePath}]")
			f.forEachLine { processLine(statistics, it) }

			log.info("Processed complete for log file [${f.absolutePath}]")
		}
		catch (e: Exception) {
			throw LogException("Unexpected error processing log at [${f.absolutePath}]", e)
		}
		return statistics
	}

	/**
	 * Extract IP and addresses only with regular expressions, ignoring rest
	 * of log entry
	 */
	private fun processLine(statistics: Statistics, data: String) {
		IP_REGEX.find(data)?.let {
			statistics.addAddress(it.value)
		}

		URL_REGEX.find(data)?.let {
			// strip off "GET " at beginning of URL
			val endVerb = it.value.indexOf(' ') + 1
			val cleanUrl = it.value.substring(endVerb)
			statistics.addUrl(cleanUrl)
		}
	}
}
