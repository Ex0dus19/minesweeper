package com.minneydev.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.grid_cell.view.*

class MainActivity : AppCompatActivity() {

    lateinit var gridAdapter: BoardGridAdapter
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
            showCell(cells.flatten()[position])
            v.cell.setImageResource(cells.flatten()[position].getCellType())
        }
        //reset button click listener
        reset_btn.setOnClickListener {
            initialize()
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
        gridAdapter = BoardGridAdapter(this,cells.flatten())
        board_grid.adapter = gridAdapter
        title_txtview.text = getString(R.string.app_name)
    }
    private fun showCell(cell: Cell) {
        cell.isClicked = true
    }

}

        /*
        board_grid.onItemClickListener = AdapterView.OnItemClickListener { _, v, position, _ ->
            showCell(Constants.cells.flatten()[position])
            v.cell.setImageResource(Constants.cells.flatten()[position].getImage())
        }
        */
