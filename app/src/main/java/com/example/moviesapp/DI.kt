package com.example.moviesapp

fun main() {
    val petrolEngineCar = Car(PetrolEngine())
    val electricEngine = Car(ElectricEngine())
}

class Car(private val engine: Engine){

    fun drive() {
        engine.start()
    }
}

interface Engine {
    fun start()
}

class PetrolEngine: Engine {
    override fun start() {
        println("petrol engine started")
    }
}

class ElectricEngine: Engine {
    override fun start() {
        println("electric engine started")
    }
}