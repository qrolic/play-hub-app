package com.qrolic.minesweeper.fragments

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.qrolic.minesweeper.R
import com.qrolic.minesweeper.database.MySharedPreferences
import com.qrolic.minesweeper.databinding.FragmentMineSweeperLevelBinding
import com.qrolic.minesweeper.utils.playSoundClick


class MineSweeperLevelFragment : Fragment() {

    private lateinit var binding: FragmentMineSweeperLevelBinding
    private lateinit var mySharedPreferences: MySharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMineSweeperLevelBinding.inflate(inflater)
        initAll()
        return binding.root
    }

    private fun initAll() {
        setTime()

        binding.btnEasy.setOnClickListener {
            context?.playSoundClick()
            findNavController().navigate(
                R.id.mineSweeperBoardFragment,
                bundleOf("type" to "easy")
            )
        }
        binding.btnMedium.setOnClickListener {
            context?.playSoundClick()
            findNavController().navigate(
                R.id.mineSweeperBoardFragment,
                bundleOf("type" to "medium")
            )
        }
        binding.btnHard.setOnClickListener {
            context?.playSoundClick()
            findNavController().navigate(
                R.id.mineSweeperBoardFragment,
                bundleOf("type" to "hard")
            )
        }
        binding.btnCustomBoard.setOnClickListener {
            context?.playSoundClick()
            customDialog()
        }

    }

    private fun setTime() {
        mySharedPreferences = MySharedPreferences(requireContext())
        binding.tvBestTime.text = getString(R.string.best_time).plus(" ").plus(convertSeconds(mySharedPreferences.getBestTime()))
        binding.tvLastTime.text = getString(R.string.last_game_time).plus(" ").plus(convertSeconds(mySharedPreferences.getLastTime()))
    }

    private fun convertSeconds(totalSecs: Int): String {
        val hours = totalSecs / 3600
        val minutes = (totalSecs % 3600) / 60
        val seconds = totalSecs % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun customDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_custom_board)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        val etWidth = dialog.findViewById(R.id.etWidth) as EditText
        val etHeight = dialog.findViewById(R.id.etHeight) as EditText
        val etMines = dialog.findViewById(R.id.etMines) as EditText
        val btnSubmit = dialog.findViewById(R.id.btnSubmit) as Button

        btnSubmit.setOnClickListener {
            context?.playSoundClick()
            val width = etWidth.text.toString()
            val height = etHeight.text.toString()
            val mines = etMines.text.toString()

            if (TextUtils.isEmpty(height)){
                etHeight.error = "Enter height"
            }else if (TextUtils.isEmpty(width)){
                etWidth.error = "Enter width"
            }else if (TextUtils.isEmpty(mines)){
                etMines.error = "Enter mine"
            }else{
                // The number of mines should be always less than 1/4th of the board’s buttons to avoid overcrowding of mines.
                val maxMine = (width.toInt() * height.toInt()) / 4
                if (mines.toInt() >= maxMine) {
                    etMines.error = ("Mine should be less than $maxMine")
                } else {
                    val bundle = Bundle()
                    bundle.putInt("width", width.toInt())
                    bundle.putInt("height", height.toInt())
                    bundle.putInt("mines", mines.toInt())
                    bundle.putString("type", "custom")
                    findNavController().navigate(R.id.mineSweeperBoardFragment, bundle)
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }
}
