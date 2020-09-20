package com.example.madlevel2example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel2example.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val reminders = arrayListOf<Reminder>()
    private val reminderAdapter = ReminderAdapter(reminders);

    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        binding.btnAddReminder.setOnClickListener {
            var reminder = binding.etReminder.text.toString();
            addReminder(reminder);
        }

        binding.rvReminders.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false);
        binding.rvReminders.adapter = reminderAdapter;

        binding.rvReminders.addItemDecoration(DividerItemDecoration(this@MainActivity,DividerItemDecoration.VERTICAL));

        createItemTouchHelper().attachToRecyclerView(rvReminders);
    }

    private fun addReminder(reminder:String) {
        if (reminder.isNotBlank()) {
            reminders.add(Reminder(reminder));
            reminderAdapter.notifyDataSetChanged();
            binding.etReminder.text?.clear()
        } else {
            Toast.makeText(this, "You must fil in the input field!", Toast.LENGTH_SHORT).show();
        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) : Boolean {
                return false;
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition;
                reminders.removeAt(position);
                reminderAdapter.notifyDataSetChanged();
            }
        }

        return ItemTouchHelper(callback);
    }

}