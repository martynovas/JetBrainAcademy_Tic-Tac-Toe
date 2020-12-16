package tictactoe

import java.lang.Math.abs
import java.util.*

enum class CheckResult(val message: String) {
    IMPOSSIBLE("Impossible"),
    X_WINS("X wins"),
    O_WINS("O wins"),
    GAME_NOT_FINISHED("Game not finished"),
    DRAW("Draw");

    override fun toString() = message
}


fun printCells(cells:CharArray) {
    println("---------")
    for (y in 1..3) {
        print("| ")
        for (x in 1..3) {
            print("${cells[(y - 1) * 3 + (x - 1)]} ".replace('_',' '))
        }
        println("|")
    }
    println("---------")
}

fun getCells(cells: CharArray, a: Int, b: Int, c: Int): CharArray = charArrayOf(cells[a], cells[b], cells[c])

fun checkCells(cells: CharArray): CheckResult {
    val xxx = "XXX".toCharArray()
    val ooo = "OOO".toCharArray()
    val xWins: Boolean
    val oWins: Boolean

    xWins = getCells(cells,0,1,2).contentEquals(xxx)
            || getCells(cells,3,4,5).contentEquals(xxx)
            || getCells(cells,6,7,8).contentEquals(xxx)
            || getCells(cells,0,3,6).contentEquals(xxx)
            || getCells(cells,1,4,7).contentEquals(xxx)
            || getCells(cells,2,5,8).contentEquals(xxx)
            || getCells(cells,0,4,8).contentEquals(xxx)
            || getCells(cells,2,4,6).contentEquals(xxx)

    oWins = getCells(cells,0,1,2).contentEquals(ooo)
            || getCells(cells,3,4,5).contentEquals(ooo)
            || getCells(cells,6,7,8).contentEquals(ooo)
            || getCells(cells,0,3,6).contentEquals(ooo)
            || getCells(cells,1,4,7).contentEquals(ooo)
            || getCells(cells,2,5,8).contentEquals(ooo)
            || getCells(cells,0,4,8).contentEquals(ooo)
            || getCells(cells,2,4,6).contentEquals(ooo)
    return when {
        ((xWins && oWins) || (abs(cells.count { it == 'X' } - cells.count { it == 'O' }) > 1)) ->
            CheckResult.IMPOSSIBLE
        xWins -> CheckResult.X_WINS
        oWins -> CheckResult.O_WINS
        cells.contains('_') -> CheckResult.GAME_NOT_FINISHED
        else -> CheckResult.DRAW
    }
}

fun move(cells: CharArray, player: Char): Boolean {
    val scanner = Scanner(System.`in`)
    println("Enter the coordinates:")
    val cordinates = scanner.nextLine()

    if ((cordinates[0] !in '0'..'9') || (cordinates[2] !in '0'..'9')){
        println("You should enter numbers!")
        return false
    }

    if ((cordinates[0] !in '1'..'3') || (cordinates[2] !in '1'..'3')){
        println("Coordinates should be from 1 to 3!")
        return false
    }

    val index = (Character.getNumericValue(cordinates[0]) - 1) * 3 + Character.getNumericValue(cordinates[2])-1

    if (cells[index] != '_') {
        println("This cell is occupied! Choose another one!")
        return false
    }

    cells[index] = player
    return true
}

fun main() {
    val scanner = Scanner(System.`in`)
    println("Enter cells:")
    val cells = "_".repeat(9).toCharArray()


    var result: CheckResult
    var currentPlayer: Char = 'X'

    do {
        printCells(cells)

        while (!move(cells,currentPlayer)) {}

        result = checkCells(cells)

        currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
    } while (result !in arrayOf(CheckResult.O_WINS, CheckResult.X_WINS, CheckResult.DRAW))


    printCells(cells)
    println(result)

}