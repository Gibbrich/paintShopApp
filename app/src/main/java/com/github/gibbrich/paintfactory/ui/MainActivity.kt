package com.github.gibbrich.paintfactory.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.adapter.ColorsAdapter
import com.github.gibbrich.paintfactory.adapter.SwipeToDeleteCallback
import com.github.gibbrich.paintfactory.ui.viewModels.MainActivityViewModel
import com.github.gibbrich.paintfactory.utils.ListChangeType


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ColorsAdapter
    private lateinit var model: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProvider
            .AndroidViewModelFactory(application)
            .create(MainActivityViewModel::class.java)

        activity_main_choose_color_button.setOnClickListener {
            model.onChooseColorButtonClicked()
        }

        activity_main_setup_customers.setOnClickListener {
            model.onSetupCustomersButtonClicked()
        }

        model.actions.observe(this, Observer {
            it?.let(this::handleAction) }
        )

        setUpColorsRecyclerView()
    }

    private fun handleAction(action: MainActivityViewModel.Action) = when (action) {
        MainActivityViewModel.Action.SwitchToCustomersScreen -> {
            switchToCustomersScreen()
        }

        MainActivityViewModel.Action.ShowColorPicker -> {
            showColorPicker()
        }

        is MainActivityViewModel.Action.ChangeColorList -> {
            changeColorList(action.colorId, action.type)
        }

        MainActivityViewModel.Action.ShowAddColorFailedWarning -> {
            Toast.makeText(this, R.string.activity_main_color_in_list_already, Toast.LENGTH_LONG).show()
        }
    }

    private fun switchToCustomersScreen() {
        val intent = CustomersActivity.getIntent(this)
        startActivity(intent)
    }

    private fun showColorPicker() = ColorPickerDialogBuilder
        .with(this)
        .setTitle(R.string.activity_main_choose_color)
        .initialColor(R.color.colorPrimary)
        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
        .density(12)
        .setPositiveButton(R.string.activity_main_ok) { _, selectedColor, _ -> model.onColorPicked(selectedColor) }
        .setNegativeButton(R.string.activity_main_cancel) { dialog, _ -> dialog.dismiss() }
        .build()
        .show()

    private fun changeColorList(
        colorId: Int,
        type: ListChangeType
    ) = when (type) {
        ListChangeType.ADD -> {
            adapter.notifyItemInserted(colorId)
            changeColorsListVisibility()
        }

        ListChangeType.REMOVE -> {
            adapter.notifyItemRemoved(colorId)
            changeColorsListVisibility()
        }
    }

    private fun changeColorsListVisibility() {
        if (adapter.itemCount == 0) {
            activity_main_colors_list.visibility = View.GONE
            activity_main_empty_label.visibility = View.VISIBLE
        } else {
            activity_main_colors_list.visibility = View.VISIBLE
            activity_main_empty_label.visibility = View.GONE
        }
    }

    private fun setUpColorsRecyclerView() {
        adapter = ColorsAdapter(model.getColors())
        activity_main_colors_list.layoutManager = LinearLayoutManager(this)
        activity_main_colors_list.adapter = adapter

        val swipeToDeleteCallback = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(holder: RecyclerView.ViewHolder, position: Int) {
                model.onColorRemoved(holder.adapterPosition)
            }
        }

        val touchHelper = ItemTouchHelper(swipeToDeleteCallback)
        touchHelper.attachToRecyclerView(activity_main_colors_list)

        changeColorsListVisibility()
    }
}
