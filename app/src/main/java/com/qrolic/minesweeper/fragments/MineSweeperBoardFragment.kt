package com.qrolic.minesweeper.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.qrolic.minesweeper.R
import com.qrolic.minesweeper.database.MySharedPreferences
import com.qrolic.minesweeper.databinding.FragmentMineSweeperBoardBinding
import com.qrolic.minesweeper.model.MineCell
import com.qrolic.minesweeper.utils.playSoundClick
import com.qrolic.minesweeper.utils.toast
import com.qrolic.minesweeper.viewmodel.MineSweeper
import java.util.*


class MineSweeperBoardFragment : Fragment() {

    private lateinit var cellBoard: Array<Array<MineCell>>
    private var timer: Timer? = null
    private lateinit var binding: FragmentMineSweeperBoardBinding
    private lateinit var viewModel: MineSweeper
    private var row = 10
    private var col = 10
    private var mines = 5
    private var status = Status.ONGOINING
    private val xArr = listOf(-1, 1, -1, 1, 0, 1, 0, -1)
    private val yArr = listOf(-1, 1, 1, -1, 1, 0, -1, 0)
    private lateinit var mySharedPreferences: MySharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // view model
        viewModel = ViewModelProvider(this)[MineSweeper::class.java]
        binding = FragmentMineSweeperBoardBinding.inflate(layoutInflater)
        initAll()
        return binding.root
    }

    private fun initAll() {
        // check type
        when (arguments?.get("type") as String) {
            "easy" -> {
                row = 8
                col = 8
                mines = 12
            }
            "medium" -> {
                row = 12
                col = 12
                mines = 24
            }
            "hard" -> {
                row = 16
                col = 16
                mines = 30
            }
            "custom" -> {
                row = arguments?.get("height") as Int
                col = arguments?.get("width") as Int
                mines = arguments?.get("mines") as Int
            }
        }
        // mines count
        viewModel.flaggedMines.observe(viewLifecycleOwner) {
            binding.tvMines.text = (mines - it).toString()
        }
        // Smiley result
        binding.tvSmiley.setOnClickListener {
            context?.playSoundClick()
            if (Status.LOST == status) {
                // restart
                restartGame()
            } else if (Status.ONGOINING == status) {
                // show alert dialog to restart game
                showDialog("Are you sure you want to restart game ?")
            }
        }
        // flag click handle
        changeFlagChoice()
        // set grid cells
        setGridCell()
        // handle back press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            showBackPressDialog()
        }
    }

    // change choices and set corresponding bomb or flag values
    private fun changeFlagChoice() {
        // set values
        viewModel.choice.observe(viewLifecycleOwner) {
            if (it == 1) {
                binding.tvFlag.setText(R.string.bomb)
            } else {
                binding.tvFlag.setText(R.string.flag)
            }
        }
        // toggle choices
        binding.tvFlag.setOnClickListener {
            if (viewModel.choice.value == 1) {
                viewModel.choice.value = 2
            } else {
                viewModel.choice.value = 1
            }
        }

    }


    // initialize adapter to set cells to recyclerview
    private fun setGridCell() {
        cellBoard = Array(row) { Array(col) { MineCell(context) } }

        binding.gridLayout.rowCount = row
        binding.gridLayout.columnCount = col
        var counter = 1

        // set button properties to grid layout
        for (i in 0 until row) {
            for (j in 0 until col) {
                val button = MineCell(context)
                cellBoard[i][j] = button
                button.id = counter
                counter++
                button.layoutParams = ViewGroup.LayoutParams(150, 150)
                button.setBackgroundResource(R.drawable.cell)
                button.textSize = 24f
                button.setOnClickListener {
                    context?.playSoundClick()
                    onCellClick(i, j, "click")
                }
                button.setOnLongClickListener {
                    context?.playSoundClick()
                    onCellClick(i, j, "longClick")
                    return@setOnLongClickListener true
                }
                binding.gridLayout.addView(button)
            }
        }
    }

    // handle cell click
    private fun onCellClick(row: Int, col: Int, type: String) {
        // click will allowed if status is ongoing
        if (status == Status.ONGOINING) {
            if (type == "click" && viewModel.isFirstClick) {
                //set all mines to random positions
                generateRandomPosForMines(row)
                setTimer()
                viewModel.isFirstClick = false
            }
            // long click and click handle
            if (type == "click") {
                move(1, row, col)
            } else if (!viewModel.isFirstClick) {
                move(2, row, col)
            }
            displayBoard()
        }
    }

    // set random mines on user's first click
    private fun generateRandomPosForMines(row: Int) {
        val random = Random()
        var counter = mines
        while (counter > 0) {
            // get random row and col
            val tempRow = random.nextInt(this.row)
            val tempCol = random.nextInt(this.col)
            // check mine already exist
            if (tempRow == row || cellBoard[tempRow][tempCol].value == MINE) {
                continue
            }
            // set mines and it's neighbour value
            cellBoard[tempRow][tempCol].value = MINE
            updateNeighbours(tempRow, tempCol)
            counter--
        }
    }

    //updates the values of the cells neighbouring to the mines
    private fun updateNeighbours(row: Int, col: Int) {
        for (i in movement) {
            for (j in movement) {
                if (((row + i) in 0 until this.row) && ((col + j) in 0 until this.col) && cellBoard[row + i][col + j].value != MINE) {
                    cellBoard[row + i][col + j].value++
                }
            }
        }
    }

    @SuppressWarnings("kotlin:S3776")
    // cognitive complexity
    // check move is possible or not
    private fun move(choice: Int, row: Int, col: Int): Boolean {
        val cell = cellBoard[row][col]

        if (choice == 1) {
            // if cell is marked or opened, no need to check
            if (cell.isMarked || cell.isRevealed) {
                return false
            }
            // if choice is 1 & mine is exist, game lost
            if (cell.value == MINE) {
                status = Status.LOST
                gameOver()
                return true
            }
            // if cell has 0 value, than open it's neighbour values
            if (cell.value == 0) {
                openNeighbourCells(row, col)
                if (win()) {
                    status = Status.WON
                    gameWon()
                }
                return true
            }
            // cell has non zero digit value
            if (cell.value > 0) {
                cell.isRevealed = true
                if (win()) {
                    status = Status.WON
                    gameWon()
                }
                return true
            }

        } else if (choice == 2) {

            // if cell is opened, no need to check
            if (cell.isRevealed) {
                return false
            }
            // if already marked , than removed
            if (cell.isMarked) {
                cell.isMarked = false
                viewModel.flaggedMines.value = viewModel.flaggedMines.value!! - 1
                if (win()) {
                    status = Status.WON
                    gameWon()
                }
                return true
            }
            // cell has mine and it is not marked
            if (!cell.isMarked) {
                if (viewModel.flaggedMines.value == mines) {
                    context?.toast("You can not mark more than $mines mines")
                    return false
                }
                cell.isMarked = true
                viewModel.flaggedMines.value = viewModel.flaggedMines.value!! + 1
                if (win()) {
                    status = Status.WON
                    gameWon()
                }
                return true
            }
        }

        return false
    }

    private fun gameWon() {
        timer?.cancel()
        showDialog("You WON !!")
        // save time to sharedPref
        saveTime()
    }

    private fun saveTime() {
        mySharedPreferences = MySharedPreferences(requireContext())
        val currentTime = binding.tvTime.text.toString().toInt()
        if (mySharedPreferences.getBestTime() == 0 || currentTime < mySharedPreferences.getBestTime()) {
            mySharedPreferences.setBestTime(currentTime)
        }
        mySharedPreferences.setLastTime(currentTime)
    }

    private fun gameOver() {
        timer?.cancel()
        context?.toast("You Lost !!..Restart Game")
    }

    // alert dialog
    private fun showDialog(message: String) {
        AlertDialog.Builder(context).setMessage(message)
            .setPositiveButton("Ok") { _, _ -> context?.playSoundClick()
                restartGame() }
            .setNegativeButton("Cancel") { _, _ ->context?.playSoundClick() }
            .setCancelable(false)
            .show()
    }

    // alert game quit dialog
    private fun showBackPressDialog() {
        AlertDialog.Builder(context).setMessage("Are you sure you want to quit game ?")
            .setPositiveButton("Yes") { _, _ -> context?.playSoundClick()
                findNavController().popBackStack() }
            .setNegativeButton("No") { _, _ ->context?.playSoundClick() }
            .setCancelable(false)
            .show()
    }

    // restart fragment again
    private fun restartGame() {
        findNavController().popBackStack()
        val type = arguments?.get("type") as String
        if (type == "custom") {
            val bundle = Bundle()
            bundle.putInt("width",col)
            bundle.putInt("height",row)
            bundle.putInt("mines",mines)
            bundle.putString("type",type)
            findNavController().navigate(
                R.id.mineSweeperBoardFragment, bundle
            )
        } else {
            findNavController().navigate(
                R.id.mineSweeperBoardFragment,
                bundleOf("type" to type)
            )
        }

        binding.tvSmiley.setText(R.string.smiley)
    }

    // user win
    private fun win(): Boolean {
        var flag1 = 0
        var flag2 = 0
        cellBoard.forEach { row ->
            row.forEach {
                if (!it.isMarked && it.value == MINE) {
                    flag1 = 1
                }
                if (!it.isRevealed && it.value != MINE) {
                    flag2 = 1
                }
            }
        }

        if (flag1 == 0 || flag2 == 0) {
            return true
        }

        return false
    }

    private fun openNeighbourCells(x: Int, y: Int) {
        var cell = cellBoard[x][y]
        cell.isRevealed = true

        for (i in 0..7) {
            val row = x + xArr[i]
            val col = y + yArr[i]

            if (!isValidIdx(row, col)) {
                continue
            }

            // update cell value
            cell = cellBoard[row][col]
            // if opened cell is zero
            if (cell.value == 0 && !cell.isMarked && !cell.isRevealed) {
                openNeighbourCells(row, col)
            } else if (cell.value > 0 && !cell.isRevealed) {
                // if open cell is greater than zero, no need to open neighbour cell again,
                // make cell isRevealed to true
                cell.isRevealed = true
            }
        }

    }

    private fun isValidIdx(x: Int, y: Int): Boolean {
        return !(x < 0 || y < 0 || x >= row || y >= col)
    }


    //displays the board
    private fun displayBoard() {
        cellBoard.forEach { row ->
            row.forEach {
                if (it.isRevealed)
                    it.text = it.value.toString()
                else if (it.isMarked)
                    it.setText(R.string.flag)
                else if (status == Status.LOST && it.value == MINE) {
                    it.setText(R.string.bomb)
                    binding.tvSmiley.setText(R.string.sad_smiley)
                } else if (status == Status.WON && it.value == MINE)
                    it.setText(R.string.flag)
                else
                    it.text = ""
            }

        }
    }


    // start timer
    private fun setTimer() {
        viewModel.count.observe(viewLifecycleOwner) {
            binding.tvTime.text = it.toString()
        }
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    viewModel.increaseCount()
                }
            }
        }, 1000, 1000)
    }


    // stop timer
    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    companion object {
        const val MINE = -1
        val movement = intArrayOf(-1, 0, 1)
    }


}

//game status
enum class Status {
    WON,
    ONGOINING,
    LOST
}
