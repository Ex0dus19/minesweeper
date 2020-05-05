package com.minneydev.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.grid_cell.view.*


class MainActivity : AppCompatActivity() {

    lateinit var gridAdapter: BoardGridAdapter
    companion object {
        lateinit var cells: Array<Array<Cell>> //2d array to make the board easier to work with
        const val NUM_COL = 10
        const val NUM_BOMB = 10
    }
    private var cellsLeft = ((NUM_COL* NUM_COL) - NUM_BOMB)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        board_grid.numColumns = NUM_COL
        reset_btn.text = getString(R.string.reset)
        initialize()
        board_grid.onItemClickListener = AdapterView.OnItemClickListener { _, v, position, _ ->
            if (cellsLeft != 0) {
                if (!cells.flatten()[position].isFlagged) {
                    showCell(cells.flatten()[position])
                    println("Num Cells: $cellsLeft")
                } else {
                    Toast.makeText(this, "Cell Is Flagged", Toast.LENGTH_SHORT).show()
                }
            }else {
                endGame(1)
            }
        }
        board_grid.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, _, position, _ ->
            flagCell(cells.flatten()[position])
            refresh()
            return@OnItemLongClickListener true
        }
        //reset button click listener
        reset_btn.setOnClickListener {
            initialize()
        }

    }

    private fun initialize() {
        cellsLeft = ((NUM_COL* NUM_COL) - NUM_BOMB)
        println("Num Cells: $cellsLeft")
        cells = Array(NUM_COL) {row ->
            Array(NUM_COL) {col ->
                Cell(false,row,col)
            }
        }
        //Set Bombs
        for (i in 0 until NUM_BOMB) {
            val row: Int = kotlin.random.Random.nextInt(0, NUM_COL)
            val col: Int = kotlin.random.Random.nextInt(0, NUM_COL)
            cells[row][col].isBomb = true
        }
        refresh()
    }

    private fun showCell(cell: Cell) {
        if (cell.isBomb) {
            endGame(0)
        } else if (cell.numAdjBombs == 0) {
            Toast.makeText(this, "0 Cell", Toast.LENGTH_SHORT).show()
            cell.isClicked = true
            val adjCells: List<Cell> = cell.getAdjCells(cell)
            adjCells.filter { !it.isBomb }.forEach {
                it.isClicked = true
                cellsLeft--
                refresh()
            }
        }else {
            cell.isClicked = true
            cellsLeft--
            refresh()
        }
    }

    private fun flagCell(cell: Cell) {
        if (cell.isFlagged) {
            cell.isFlagged = false
        }else {
            cell.isFlagged = true
        }
    }
    private fun refresh() {
        gridAdapter = BoardGridAdapter(this,cells.flatten())
        board_grid.adapter = gridAdapter
        title_txtview.text = getString(R.string.app_name)
    }
    private fun endGame(outcome: Int) {
        for (i in 0 until NUM_COL) {
            for (j in 0 until NUM_COL) {
                cells[i][j].isClicked = true
            }
        }
        refresh()
        when (outcome) {
            0 -> title_txtview.text = getString(R.string.gameOver)
            1 -> title_txtview.text = getString(R.string.win)
        }

    }



}

