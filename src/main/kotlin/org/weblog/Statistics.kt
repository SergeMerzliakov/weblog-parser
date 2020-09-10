package org.weblog

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

private val URL_REGEX = Regex("/$")

private val log: Logger = LogManager.getLogger(Statistics::class.java)

/**
 * Statistics for a single log file
 * Tracks address and IP counts only
 */
class Statistics {
	private val addresses = mutableMapOf<String, Int>() 	// address -> count
	private val urls = mutableMapOf<String, Int>() 			// url -> count

	fun urlCount(url:String) = urls[normalizeUrl(url)]

	fun addressCount(address:String) = addresses[address]

	fun uniqueAddresses() = addresses.size

	fun uniqueUrls() = urls.size

	fun addAddress(ip: String) {
		val count = addresses.getOrDefault(ip, 0) + 1
		addresses[ip] = count
	}

	fun addUrl(url: String) {
		// normalize url first
		val validUrl = normalizeUrl(url)
		val count = urls.getOrDefault(validUrl, 0) + 1
		urls[validUrl] = count
	}



	/**
	 * return top n urls
	 */
	fun topUrls(n: Int): List<Pair<String, Int>> {
		if (n < urls.size)
			return rankList(n, urls)
		log.warn("too many rankings required, defaulting to 1")
		return rankList(1, urls)
	}

	/*
	 * return top n addresses
	 */
	fun topAddresses(n: Int): List<Pair<String, Int>> {
		if (n < addresses.size)
			return rankList(n, addresses)
		log.warn("too many rankings required, defaulting to 1")
		return rankList(1, addresses)
	}


	private fun rankList(topN: Int, items: Map<String, Int>): List<Pair<String, Int>> {
		if (items.isEmpty())
			return listOf()

		return items.toList().sortedBy { (_, value) -> value }.toList().reversed().subList(0, topN)
	}

	/**
	 * Not ideal but does attempt to remove some characters
	 */
	private fun normalizeUrl(dirtyUrl: String): String {
		return dirtyUrl.trim().replace(URL_REGEX, "")
	}
}
