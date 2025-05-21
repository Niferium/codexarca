package ph.niferium.codexarca.learning

import junit.framework.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class sample_unit_test {

    //We learn that unit testing is important to determine if our logical functions (Unit)
    //are properly working

    //Unit testing avoids very basic bugs that would arise on a simple dev testing env

    @Test
    fun calculate_area_of_square() {
        val valueOfSquare = calculateSquare(sideValue)
        assertEquals(49.0, valueOfSquare)
    }

    //Testing Negative exception of require
    @Test
    fun testNegativeSideThrowsException() {
        assertThrows(IllegalArgumentException::class.java) {
            calculateSquare(negativeValue)
        }
    }

    // Write your function to be tested
    // (Hence unit testing, the unit is a another term for function)
    private fun calculateSquare(side: Double): Double {
        //throws illegal exception, very helpful for testing
        require(side >= 0) { "Side length cannot be negative"  }
        return square_formula
    }


    //initialize the variables to be used here
    companion object {
        private var sideValue = 7.0
        private var negativeValue = -7.0
        private val square_formula = sideValue * sideValue // S^2
    }

    //We learn that the code above would test the function calculateSquare()
    //This function can be anywhere inside your codebase, (preferablly viewModels)
    //viewModels holds logic of the codebase
}