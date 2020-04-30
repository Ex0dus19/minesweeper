package com.minneydev.minesweeper

class Cell (private var cl: Boolean, private  val r: Int, private  val c: Int) {
    var isClicked = cl
    var isBomb = false    //cell is a bomb
    var isFlagged = false //cell is flagged
    var adjCells = mutableListOf<Cell>()
    var hasEx = false
    private var numAdjBombs = 0   //bombs near cell

    private fun getAdjCells(cell: Cell) : List<Cell> {
        var adjCells = mutableListOf<Cell?>()
        if (r in 1..8) {
            if (c in 1..8) {
                adjCells.add(MainActivity.cells[r-1][c-1])
                adjCells.add(MainActivity.cells[r-1][c])
                adjCells.add(MainActivity.cells[r-1][c+1])
                adjCells.add(MainActivity.cells[r][c-1])
                adjCells.add(MainActivity.cells[r][c+1])
                adjCells.add(MainActivity.cells[r+1][c-1])
                adjCells.add(MainActivity.cells[r+1][c])
                adjCells.add(MainActivity.cells[r+1][c+1])
            }
        }
        return adjCells.toList().filterNotNull()
    }

    fun getCellType() : Int {
        if (isClicked) {
            if (!isBomb) {
                if (!hasEx) {
                    val adjCells: List<Cell> = getAdjCells(this)
                    adjCells.forEach() {
                        if (it.isBomb) {
                            numAdjBombs++
                        }
                    }
                    hasEx = true
                }
                when (numAdjBombs) {
                    0 -> return R.drawable.cell_0
                    1 -> return R.drawable.cell_1
                    2 -> return R.drawable.cell_2
                    3 -> return R.drawable.cell_3
                    4 -> return R.drawable.cell_4
                    5 -> return R.drawable.cell_5
                    6 -> return R.drawable.cell_6
                    7 -> return R.drawable.cell_7
                    8 -> return R.drawable.cell_8
                }
            }else {
                return R.drawable.cell_bomb
            }
        }
        return R.drawable.cell_fd
    }

}



