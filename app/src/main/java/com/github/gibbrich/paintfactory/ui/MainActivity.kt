package com.github.gibbrich.paintfactory.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.github.gibbrich.paintfactory.domain.Color
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.adapter.ColorsAdapter


class MainActivity : AppCompatActivity() {

    lateinit var adapter: ColorsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        choose_color_button.setOnClickListener {
            showColorPicker()
        }

        adapter = ColorsAdapter()
        activity_main_colors_list.layoutManager = LinearLayoutManager(this)
        activity_main_colors_list.adapter = adapter
    }

    private fun showColorPicker() {
        ColorPickerDialogBuilder
            .with(this)
            .setTitle("Choose color")
            .initialColor(R.color.colorPrimary)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setPositiveButton(
                "ok"
            ) { _, selectedColor, _ -> onColorPicked(selectedColor) }
            .setNegativeButton("cancel") { dialog, which -> dialog.dismiss()}
            .build()
            .show()
    }

    private fun onColorPicked(selectedColor: Int) {
        adapter.add(Color(selectedColor))
    }
}
