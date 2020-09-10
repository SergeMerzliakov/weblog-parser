package org.weblog

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class StatisticsTest {


	@Test
	fun shouldProcessIPAddresses() {
		// given
		val stats = Statistics()

		// when
		stats.addAddress("127.0.0.1")
		stats.addAddress("197.0.0.1")

		// then
		assertThat(stats.uniqueAddresses()).isEqualTo(2)
	}


	@Test
	fun shouldCountIPAddresses() {
		// given
		val address = "127.0.0.1"
		val stats = Statistics()

		// when
		stats.addAddress(address)
		stats.addAddress(address)

		// then
		assertThat(stats.uniqueAddresses()).isEqualTo(1)
		assertThat(stats.addressCount(address)).isEqualTo(2)
	}

	@Test
	fun shouldProcessDuplicateUrls() {
		//given
		val stats = Statistics()

		// when
		stats.addUrl("http://foo/")
		stats.addUrl("http://foo")

		// then
		assertThat(stats.uniqueUrls()).isEqualTo(1)
		assertThat(stats.urlCount("http://foo")).isEqualTo(2)
	}

	@Test
	fun shouldProcessUrls() {
		// given
		val url = "http://foo"
		val stats = Statistics()

		// when
		stats.addUrl(url)
		stats.addUrl(url)

		// then
		assertThat(stats.uniqueUrls()).isEqualTo(1)
		assertThat(stats.urlCount(url)).isEqualTo(2)
	}

	@Test
	fun shouldProcessUniqeUrls() {
		// given
		val stats = Statistics()

		// when
		stats.addUrl("url1")
		stats.addUrl("url2")
		stats.addUrl("url3")

		// then
		assertThat(stats.uniqueUrls()).isEqualTo(3)
	}

	@Test
	fun shouldRankTop3Urls() {
		// given
		val N = 3
		val google = "http://google.com"
		val bing = "http://bing.com"
		val alta = "http://altavista.com"
		val stats = Statistics()

		// when
		addMultipleUrls(stats, google, 10)
		addMultipleUrls(stats, bing, 4)
		addMultipleUrls(stats, alta, 2)
		stats.addUrl("random1")
		stats.addUrl("random2")

		// then
		val ranks = stats.topUrls(N)
		assertThat(ranks).hasSize(N)

		assertThat(ranks[0].first).isEqualTo(google)
		assertThat(ranks[0].second).isEqualTo(10)

		assertThat(ranks[1].first).isEqualTo(bing)
		assertThat(ranks[1].second).isEqualTo(4)

		assertThat(ranks[2].first).isEqualTo(alta)
		assertThat(ranks[2].second).isEqualTo(2)
	}


	@Test
	fun shouldRankTopUrl() {
		// given
		val N = 1
		val google = "http://google.com"
		val bing = "http://bing.com"
		val alta = "http://altavista.com"
		val stats = Statistics()

		// when
		addMultipleUrls(stats, google, 10)
		addMultipleUrls(stats, bing, 4)
		addMultipleUrls(stats, alta, 2)
		stats.addUrl("random1")
		stats.addUrl("random2")

		// then
		val ranks = stats.topUrls(N)
		assertThat(ranks).hasSize(N)

		assertThat(ranks[0].first).isEqualTo(google)
		assertThat(ranks[0].second).isEqualTo(10)

	}


	@Test
	fun shouldRankTop3Addresses() {
		// given
		val N = 3
		val google = "127.0.0.1"
		val bing = "255.0.0.1"
		val alta = "1.0.0.1"
		val stats = Statistics()

		// when
		addMultipleAddresses(stats, google, 10)
		addMultipleAddresses(stats, bing, 4)
		addMultipleAddresses(stats, alta, 2)
		stats.addAddress("random1")
		stats.addAddress("random2")

		// then
		val ranks = stats.topAddresses(N)
		assertThat(ranks).hasSize(N)

		assertThat(ranks[0].first).isEqualTo(google)
		assertThat(ranks[0].second).isEqualTo(10)

		assertThat(ranks[1].first).isEqualTo(bing)
		assertThat(ranks[1].second).isEqualTo(4)

		assertThat(ranks[2].first).isEqualTo(alta)
		assertThat(ranks[2].second).isEqualTo(2)
	}


	@Test
	fun shouldRankTopAddress() {
		// given
		val N = 1
		val google = "127.0.0.1"
		val bing = "255.0.0.1"
		val alta = "1.0.0.1"
		val stats = Statistics()

		// when
		addMultipleAddresses(stats, google, 10)
		addMultipleAddresses(stats, bing, 4)
		addMultipleAddresses(stats, alta, 2)
		stats.addAddress("random1")
		stats.addAddress("random2")

		// then
		val ranks = stats.topAddresses(N)
		assertThat(ranks).hasSize(N)

		assertThat(ranks[0].first).isEqualTo(google)
		assertThat(ranks[0].second).isEqualTo(10)
	}


	@Test
	fun shouldBeEmpty() {
		// given
		val stats = Statistics()

		//then
		assertThat(stats.uniqueAddresses()).isZero
		assertThat(stats.uniqueUrls()).isZero
		assertThat(stats.topAddresses(3)).isEmpty()
		assertThat(stats.topUrls(3)).isEmpty()
	}

	/**
	 * helper to add lots of the same url for 'top' calculations
	 */
	private fun addMultipleUrls(stats: Statistics, url: String, n: Int) {
		for (i in 1..n) {
			stats.addUrl(url)
		}
	}

	/**
	 * helper to add lots of the same address for 'top' calculations
	 */
	private fun addMultipleAddresses(stats: Statistics, address: String, n: Int) {
		for (i in 1..n) {
			stats.addAddress(address)
		}
	}
}
