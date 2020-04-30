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
    private var holdDuration = 3
    companion object {
        lateinit var cells: Array<Array<Cell>> //2d array to make the board easier to work with
        const val NUM_COL = 10
        const val NUM_BOMB = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        board_grid.numColumns = NUM_COL
        reset_btn.text = getString(R.string.reset)
        initialize()
        board_grid.onItemClickListener = AdapterView.OnItemClickListener { _, v, position, _ ->
            if (!cells.flatten()[position].isFlagged) {
                showCell(cells.flatten()[position])
                v.cell.setImageResource(cells.flatten()[position].getCellType())
            }else {
                Toast.makeText(this, "Cell Is Flagged", Toast.LENGTH_SHORT).show()
            }
        }
        board_grid.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, v, position, _ ->
            flagCell(cells.flatten()[position])
            v.cell.setImageResource(cells.flatten()[position].getCellType())
            return@OnItemLongClickListener true
        }
        //reset button click listener
        reset_btn.setOnClickListener {
            initialize()
        }
        reset_btn.setOnLongClickListener {
            Toast.makeText(this, "Long Click Detected", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }
    }

    private fun initialize() {
        cells = Array(NUM_COL) {row ->
            Array(NUM_COL) {col ->
                Cell(false,row,col)
            }
        }
        //Set Bombs
        for (i in 0..NUM_BOMB-1) {
            val row: Int = kotlin.random.Random.nextInt(0, NUM_COL)
            val col: Int = kotlin.random.Random.nextInt(0, NUM_COL)
            cells[row][col].isBomb = true
        }
        refresh()

    }
    private fun showCell(cell: Cell) {
        cell.isClicked = true
        if (cell.isBomb) {
            endGame()
        }
    }
    private fun flagCell(cell: Cell) {
        cell.isFlagged = true
    }
    private fun refresh() {
        gridAdapter = BoardGridAdapter(this,cells.flatten())
        board_grid.adapter = gridAdapter
        title_txtview.text = getString(R.string.app_name)
    }
    private fun endGame() {
        for (i in 0..NUM_COL-1) {
            for (j in 0..NUM_COL-1) {
                cells[i][j].isClicked = true
            }
        }
        refresh()
        title_txtview.text = getString(R.string.gameOver)
    }

}

