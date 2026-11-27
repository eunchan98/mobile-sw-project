package com.mobilesw.diet.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class IntakeAmountTest {

    @Test
    fun 정수와_소수점_한자리_섭취량을_읽는다() {
        assertEquals(150.0, IntakeAmount.parse("150")!!, 0.0)
        assertEquals(72.5, IntakeAmount.parse("72.5")!!, 0.0)
        assertEquals(210.0, IntakeAmount.parse(" 210 ")!!, 0.0)
    }

    @Test
    fun 범위_끝값_1g과_2000g은_허용한다() {
        assertEquals(1.0, IntakeAmount.parse("1")!!, 0.0)
        assertEquals(2000.0, IntakeAmount.parse("2000")!!, 0.0)
    }

    @Test
    fun 범위를_벗어나거나_형식이_틀린_값은_거부한다() {
        listOf("0", "0.9", "-5", "2000.1", "2500", "12.34", "abc", "", "1e3", ".5")
            .forEach { assertNull("'$it'은 거부되어야 함", IntakeAmount.parse(it)) }
    }
}
