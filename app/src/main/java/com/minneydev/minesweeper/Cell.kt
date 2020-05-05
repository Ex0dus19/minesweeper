package com.minneydev.minesweeper

class Cell (private var cl: Boolean, private  val r: Int, private  val c: Int) {
    var isClicked = cl
    var isBomb = false    //cell is a bomb
    var isFlagged = false //cell is flagged
    var hasEx = false
    private var numAdjBombs = 0   //bombs near cell
    //Thanks for the code advice lol

    fun getAdjCells() : List<Cell> {
        var tempAdjCells = mutableListOf<Cell?>()
        tempAdjCells.add(MainActivity.cells.getOrNull(this.r-1)?.getOrNull(this.c-1))
        tempAdjCells.add(MainActivity.cells.getOrNull(this.r-1)?.getOrNull(this.c))
        tempAdjCells.add(MainActivity.cells.getOrNull(this.r-1)?.getOrNull(this.c+1))
        tempAdjCells.add(MainActivity.cells.getOrNull(this.r)?.getOrNull(this.c-1))
        tempAdjCells.add(MainActivity.cells.getOrNull(this.r)?.getOrNull(this.c+1))
        tempAdjCells.add(MainActivity.cells.getOrNull(this.r+1)?.getOrNull(this.c-1))
        tempAdjCells.add(MainActivity.cells.getOrNull(this.r+1)?.getOrNull(this.c))
        tempAdjCells.add(MainActivity.cells.getOrNull(this.r+1)?.getOrNull(this.c+1))
        return tempAdjCells.toList().filterNotNull()
    }

    fun getCellType() : Int {
        if (isClicked) {
            if (!isBomb) {
                if (!hasEx) {
                    val adjCells: List<Cell> = getAdjCells()
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
        }else if (isFlagged) {
            return R.drawable.cell_flagged
        }
        return R.drawable.cell_fd
    }

}

//Its midnight and I'm tired. Plz help

