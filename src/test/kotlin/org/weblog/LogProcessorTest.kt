package org.weblog

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.File

/**
 * These tests answer the specific questions required for the code challenge (with some code
 * duplication for the sake of simplicity)

1) The number of unique IP addresses
2)	The top 3 most visited URLs
3) The top 3 most active IP addresses
 *
 */
class LogProcessorTest {

	private val sampleLog = File(javaClass.getResource("/programming-task-example-data.log").toURI())

	@Test
	fun findUniqueUrls() {
		// given
		val processor = LogProcessor()

		// when
		val statistics = processor.read(sampleLog)

		// then
		assertThat(statistics.uniqueUrls()).isEqualTo(22)
	}


	@Test
	fun findUniqueAddress() {
		// given
		val processor = LogProcessor()

		// when
		val statistics = processor.read(sampleLog)

		// then
		assertThat(statistics.uniqueAddresses()).isEqualTo(11)
	}


	@Test
	fun loadAndFindTop3Urls() {
		// given
		val processor = LogProcessor()

		// when
		val statistics = processor.read(sampleLog)

		// then
		val topUrls = statistics.topUrls(3)
		assertThat(topUrls).hasSize(3)

		assertThat(topUrls[0].first).isEqualTo("/docs/manage-websites")
		assertThat(topUrls[0].second).isEqualTo(2)

		assertThat(topUrls[1].first).isEqualTo("/asset.css")
		assertThat(topUrls[1].second).isEqualTo(1)

		assertThat(topUrls[2].first).isEqualTo("/download/counter")
		assertThat(topUrls[2].second).isEqualTo(1)
	}


	@Test
	fun loadAndFindTop3Address() {
		// given
		val processor = LogProcessor()

		// when
		val statistics = processor.read(sampleLog)

		// then
		val topAddresses = statistics.topAddresses(3)
		assertThat(topAddresses).hasSize(3)

		assertThat(topAddresses[0].first).isEqualTo("168.41.191.40")
		assertThat(topAddresses[0].second).isEqualTo(4)

		assertThat(topAddresses[1].first).isEqualTo("72.44.32.10")
		assertThat(topAddresses[1].second).isEqualTo(3)

		assertThat(topAddresses[2].first).isEqualTo("50.112.00.11")
		assertThat(topAddresses[2].second).isEqualTo(3)
	}


	@Test(expected = LogException::class)
	fun failForMissingFile() {
		// given
		val f = File("/missing.log")
		val processor = LogProcessor()

		// when
		processor.read(f)
	}
}
