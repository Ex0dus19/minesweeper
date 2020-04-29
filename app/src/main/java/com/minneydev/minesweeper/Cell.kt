package com.minneydev.minesweeper

class Cell (private val isClicked: Boolean, private  val r: Int, private  val c: Int) {
    val isBomb = false    //cell is a bomb
    val isFlagged = false //cell is flagged
    val numAdjBombs = 0   //bombs near cell

    fun getCellType() : Int {
        if (isClicked) {
            if (!isBomb) {
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