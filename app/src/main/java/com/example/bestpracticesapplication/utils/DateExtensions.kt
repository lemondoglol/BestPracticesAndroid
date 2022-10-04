package com.example.bestpracticesapplication.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun String.lowercaseAMPM() = this.replace("AM", "am").replace("PM", "pm")

/**
 * Long / Date ---> LocalDateTime
 * */
fun Long.toLocalDateTime(timeZone: ZoneId = ZoneId.systemDefault()): LocalDateTime =
    LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        timeZone
    )

fun Date.toLocalDateTime(): LocalDateTime = time.toLocalDateTime()

/**
 * LocalDateTime ---> Long
 * */
fun LocalDateTime.toEpochMilli(timeZone: ZoneId = ZoneId.systemDefault()): Long =
    atZone(timeZone).toInstant().toEpochMilli()

/**
 * Long to 'Mar 05, 3:21PM'
 * */
fun Long.convertToMonthYearTimeString(): String = this.toLocalDateTime().format(MONTH_DAY_TIME_FORMAT)
private val MONTH_DAY_TIME_FORMAT = DateTimeFormatter.ofPattern("MMM dd, h:mma")

/**
 * Long to 'May 2005'
 * */
fun Long.convertToMonthYearString(): String = this.toLocalDateTime().format(MONTH_YEAR_TIME_FORMAT)
private val MONTH_YEAR_TIME_FORMAT = DateTimeFormatter.ofPattern("MMMM yyyy")

/**
 * Long to 'May 11'
 * */
fun Long.convertToMonthDayString(): String = this.toLocalDateTime().format(MONTH_DAY_FORMAT)
private val MONTH_DAY_FORMAT = DateTimeFormatter.ofPattern("MMMM dd")