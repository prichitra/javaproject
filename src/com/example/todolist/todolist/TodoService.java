package com.example.todolist.todolist;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class TodoService {

        private static final String url = "jdbc:mysql://localhost:3306/todo_db";
        private static final String username = "root";
        private static final String password = "Muthuchitra@04";

        public TodoService() {
            createTables();
        }

        private void createTables() {
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 Statement statement = connection.createStatement()) {

                statement.executeUpdate("DROP TABLE IF EXISTS todos, users");
                String createTodoTableQuery = "CREATE TABLE IF NOT EXISTS todos (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "task VARCHAR(255) NOT NULL," +
                        "dueDate DATE," +
                        "completed BOOLEAN DEFAULT FALSE," +
                        "priority VARCHAR(20)," +
                        "category VARCHAR(50))";
                statement.executeUpdate(createTodoTableQuery);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public void addTodo(String task, String dueDate, String priority, String category) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO todos (task, dueDate, completed, priority, category) VALUES (?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, task);
            preparedStatement.setString(2, dueDate);
            preparedStatement.setBoolean(3, false);
            preparedStatement.setString(4, priority);
            preparedStatement.setString(5, category);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllTodos() {
        List<String> todos = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id, task, dueDate, completed, priority, category FROM todos");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String task = resultSet.getString("task");
                String dueDate = resultSet.getString("dueDate");
                boolean completed = resultSet.getBoolean("completed");
                String priority = resultSet.getString("priority");
                String category = resultSet.getString("category");
                todos.add(createTaskString(id, task, dueDate, completed, priority, category));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    public void markTodoAsCompleted(int taskId) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE todos SET completed = true WHERE id = ?")) {

            preparedStatement.setInt(1, taskId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String createTaskString(int id, String task, String dueDate, boolean completed, String priority, String category) {
        String status = completed ? " (Completed)" : " (Due Date: " + dueDate + ")";
        return id + ": " + task + status + " [Priority: " + priority + ", Category: " + category + "]";
    }
}
