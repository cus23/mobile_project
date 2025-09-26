package com.hfad.catchat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import android.content.Context
import android.widget.CheckBox

class TodoListFragment : Fragment() {

    private lateinit var todoListContainer: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_todo_list, container, false)
        todoListContainer = view.findViewById(R.id.todo_list_container)
        view.findViewById<Button>(R.id.add_todo_button).setOnClickListener {
            addTodoItem()
        }
        view.findViewById<Button>(R.id.confirm_button).setOnClickListener {
            confirmTodoItem()
        }

        addTodoItem()

        return view
    }

    private fun addTodoItem() {
        val todoItemView = LayoutInflater.from(context).inflate(R.layout.todo_item, todoListContainer, false)
        todoListContainer.addView(todoItemView)
    }

    private fun confirmTodoItem() {
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
