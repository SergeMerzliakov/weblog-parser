package org.weblog

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.*

class LogProcessorTest {

	@Test
	fun shouldLoadAndFindTop3Urls() {
		// given
		val f = File(javaClass.getResource("/programming-task-example-data.log").toURI())
		val processor = LogProcessor()

		// when
		val statistics = processor.read(f)

		// then
		assertThat(statistics.uniqueAddresses()).isEqualTo(11)
		assertThat(statistics.uniqueUrls()).isEqualTo(22)

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
	fun shouldLoadAndFindTop3Address() {
		// given
		val f = File(javaClass.getResource("/programming-task-example-data.log").toURI())
		val processor = LogProcessor()

		// when
		val statistics = processor.read(f)

		// then
		assertThat(statistics.uniqueAddresses()).isEqualTo(11)
		assertThat(statistics.uniqueUrls()).isEqualTo(22)

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
	fun shouldFailForMissingFile() {
		// given
		val f = File("/missing.log")
		val processor = LogProcessor()

		// when
		processor.read(f)
	}
}
