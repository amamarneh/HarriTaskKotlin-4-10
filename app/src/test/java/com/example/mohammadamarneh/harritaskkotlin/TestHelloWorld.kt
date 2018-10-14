package com.example.mohammadamarneh.harritaskkotlin

import org.junit.Assert
import org.junit.Test

class TestHelloWorld {

    @Test
    fun isCorrect(){
        Assert.assertEquals("helloWorld", "hello" + "World")
    }

}