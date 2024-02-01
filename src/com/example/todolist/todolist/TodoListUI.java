package com.example.todolist.todolist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TodoListUI extends JFrame {
    private TodoService todoService;
    private DefaultListModel<String> listModel;
    private JList<String> todoList;
    private JTextField taskField;
    private JTextField dateField;
    private JTextField priorityField;
    private JTextField categoryField;

    public TodoListUI() {
        todoService = new TodoService();
        listModel = new DefaultListModel<>();


        setLayout(new BorderLayout());


        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        taskField = new JTextField(30);
        dateField = new JTextField(20);
        priorityField = new JTextField(10);
        categoryField = new JTextField(10);

        inputPanel.add(new JLabel("Task: "));
        inputPanel.add(taskField);
        inputPanel.add(new JLabel("Due Date: "));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Priority: "));
        inputPanel.add(priorityField);
        inputPanel.add(new JLabel("Category: "));
        inputPanel.add(categoryField);

        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);


        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());

        todoList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(todoList);

        JButton completeButton = new JButton("Mark as Completed");
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markAsCompleted();
            }
        });


        Dimension buttonSize = new Dimension(1000, 80);
        completeButton.setPreferredSize(buttonSize);

        listPanel.add(scrollPane, BorderLayout.CENTER);
        listPanel.add(completeButton, BorderLayout.SOUTH);

        add(listPanel, BorderLayout.CENTER);


        setTitle("Todo List");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        updateListModel();
    }

    private void addTask() {
        String task = taskField.getText();
        String dueDate = dateField.getText();
        String priority = priorityField.getText();
        String category = categoryField.getText();

        if (!task.isEmpty() && !dueDate.isEmpty()) {
            todoService.addTodo(task, dueDate, priority, category);
            updateListModel();
            taskField.setText("");
            dateField.setText("");
            priorityField.setText("");
            categoryField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please enter both task and due date.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void markAsCompleted() {
        int selectedIndex = todoList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedTask = todoList.getSelectedValue();
            int taskId = Integer.parseInt(selectedTask.split(":")[0].trim());
            todoService.markTodoAsCompleted(taskId);
            updateListModel();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to mark as completed.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateListModel() {
        listModel.clear();
        for (String todo : todoService.getAllTodos()) {
            listModel.addElement(todo);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TodoListUI().setVisible(true);
            }
        });
    }
}
