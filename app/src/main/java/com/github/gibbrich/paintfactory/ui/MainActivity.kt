package com.github.gibbrich.paintfactory.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.github.gibbrich.paintfactory.PaintShopApp
import com.github.gibbrich.paintfactory.domain.Color
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.adapter.ColorsAdapter
import com.github.gibbrich.paintfactory.data.ColorsRepository
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var colorsRepository: ColorsRepository

    lateinit var adapter: ColorsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as PaintShopApp).appComponent.inject(this)

        activity_main_choose_color_button.setOnClickListener {
            showColorPicker()
        }

        activity_main_setup_customers.setOnClickListener {
            val intent = CustomersActivity.getIntent(this)
            startActivity(intent)
        }

        adapter = ColorsAdapter(colorsRepository.colors)
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
        val color = Color(selectedColor)
        colorsRepository.colors.add(color)
        adapter.notifyItemInserted(colorsRepository.colors.lastIndex)
    }
}
