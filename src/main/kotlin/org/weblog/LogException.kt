package org.weblog

/**
 * Thrown at the top level, wrapping lower level exceptions
 */
class LogException : Exception {
	constructor(message: String?) : super(message)
	constructor(message: String?, cause: Throwable?) : super(message, cause)
}
