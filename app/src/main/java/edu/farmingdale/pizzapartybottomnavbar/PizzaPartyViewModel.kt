package edu.farmingdale.pizzapartybottomnavbar

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlin.math.ceil

class PizzaPartyViewModel : ViewModel() {
    // Number of people
    var numPeopleInput by mutableStateOf("")

    // Hunger level (Light, Medium, Hungry, Very Hungry)
    var hungerLevel by mutableStateOf("Medium")

    // Total pizzas calculated
    var totalPizzas by mutableStateOf(0)

    // Function to calculate the number of pizzas
    fun calculatePizzas() {
        val numPeople = numPeopleInput.toIntOrNull() ?: 0
        val slicesPerPizza = 8
        val slicesPerPerson = when (hungerLevel) {
            "Light" -> 2
            "Medium" -> 3
            "Hungry" -> 4
            "Very hungry" -> 5
            else -> 3
        }
        totalPizzas = ceil(numPeople * slicesPerPerson / slicesPerPizza.toDouble()).toInt()
    }
}
