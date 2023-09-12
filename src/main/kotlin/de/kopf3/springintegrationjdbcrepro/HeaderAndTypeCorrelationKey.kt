package de.kopf3.springintegrationjdbcrepro

import java.io.Serializable

data class HeaderAndTypeCorrelationKey(val type: String, val headerValue: String) : Serializable
