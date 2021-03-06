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
        var num_col = 10
        var num_bomb = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        board_grid.numColumns = num_col
        reset_btn.text = getString(R.string.reset)
        initialize()
        board_grid.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            if (!cells.flatten()[position].isFlagged) {
                showCell(cells.flatten()[position])
            } else {
                Toast.makeText(this, "Cell Is Flagged", Toast.LENGTH_SHORT).show()
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
        cells = Array(num_col) {row ->
            Array(num_col) {col ->
                Cell(false,row,col)
            }
        }
        //Set Bombs
        for (i in 0 until num_bomb) {
            val row: Int = kotlin.random.Random.nextInt(0, num_col)
            val col: Int = kotlin.random.Random.nextInt(0, num_col)
            cells[row][col].isBomb = true
        }
        refresh()
    }

    private fun showCell(cell: Cell) {
        if (cell.isBomb) {
            endGame(0)
        }else if (cell.numAdjBombs == 0) {
            cell.isClicked = true
            expand(cell)
        }else {
            cell.isClicked = true
            refresh()
            if (isGameWon()) {
                endGame(1)
            }

        }
    }

    private fun expand(cell: Cell) {
        val adjCells: List<Cell> = cell.getAdjCells(cell)
        adjCells.filter { !it.isBomb }.forEach {
            if (!it.isClicked) {
                it.isClicked = true
                refresh()
                showCell(it)
            }
        }
    }
    private fun isGameWon() : Boolean {
        cells.forEach {row: Array<Cell> ->
            row.forEach {it: Cell ->
                if (!it.isBomb && !it.isClicked) {
                    return false
                }
            }
        }
        return true
    }
    private fun flagCell(cell: Cell) {
        cell.isFlagged = !cell.isFlagged
    }
    private fun refresh() {
        gridAdapter = BoardGridAdapter(this,cells.flatten())
        board_grid.adapter = gridAdapter
        title_txtview.text = getString(R.string.app_name)
    }
    private fun endGame(outcome: Int) {
        for (i in 0 until num_col) {
            for (j in 0 until num_col) {
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

