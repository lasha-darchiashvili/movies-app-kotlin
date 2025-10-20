package com.example.moviesapp

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

fun main() {
    runBlocking {
        val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
            if(throwable.message == "out of money") {
                println("out of money ")
            }
            else {
                println("not out of money")
            }

        }

        val firstCoroutineScope = CoroutineScope(Dispatchers.Default + exceptionHandler)
        val secondCoroutineScope = CoroutineScope(Dispatchers.Default)
        val job1 = firstCoroutineScope.launch{
            buildHouse("bulding first house")
            buildHouse("bulding second house")

        }
        job1.join()


//        val tamadaJob =  launch {
//
//            firstToast()
//            println("second toast")
//            yield()
//            println("third toast")
//        }
//
//        launch {
//            println("maid brings khachapuri")
//            yield()
//            println("maid brings mtsvadi")
//
//        }



    }

}



suspend fun buildHouse(projectLocation: String) = coroutineScope {
    val startingTime = System.currentTimeMillis()

    val windows = async(Dispatchers.IO) {
        order(Product.Window)

    }
    val doors = async(Dispatchers.IO) {
        order(Product.Door)
    }

    launch(Dispatchers.Default) {
        perform("wall is risen")
        launch{
            perform("doing ${windows.await().description}")
            val timePassed = System.currentTimeMillis() - startingTime
            println(timePassed)
        }
        launch {
            perform("doing ${doors.await().description}")
        }

    }
}

suspend fun firstToast() {
    println("first toast")
    yield()
}

enum class Product(val description: String, val deliveryTime: Int) {
    Door("doors", 800 ), Window("windows", 1500)
}

suspend fun order(product: Product): Product {
    println("order is in road ${product.description}")
    delay(product.deliveryTime.toLong())
    println("order of ${product.description} delivered ")
    return product
}

suspend fun perform(taskName: String) {
    println("starting task - $taskName")
    delay(1000)
    println("finished task $taskName")

}