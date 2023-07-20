/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author gabri
 */
public class TaskController {
    
    public void save(Task task){
      
      String sql = "INSERT INTO tasks ("
              + "idProject," 
              + "name,"
              + "description,"
              + "completed,"
              + "notes,"
              + "deadline,"
              + "createdAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
      
              Connection connection = null;
              PreparedStatement statement = null;
      
      try {
          connection = ConnectionFactory.getConnection();
          statement = connection.prepareStatement(sql);
          statement.setInt(1, task.getIdProject());
          statement.setString(2, task.getName());
          statement.setString(3, task.getDescription());
          statement.setBoolean(4, task.isIsCompleted());
          statement.setString(5, task.getNotes());
          statement.setDate(6, new java.sql.Date(task.getDeadLine().getTime()));
          statement.setDate(7, new java.sql.Date(task.getCreatedAt().getTime()));
          statement.execute();
      } catch (Exception ex) {
          throw new RuntimeException("Erro ao salvar a tarefa"
                  + ex.getMessage(), ex); 
      } finally {
          ConnectionFactory.closeConnection(connection, statement);
        }
    }
    public void update(Task task) {   
        String sql = "UPDATE tasks SET "
                + "idProject = ?, "
                + "name = ?, "
                + "description = ?, "
                + "notes = ?, "
                + "completed = ?, "
                + "deadline = ?, "
                + "createdAt = ?, "
                + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadLine().getTime())); 
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.execute();
            
            //Executando a query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa " + ex.getMessage(), ex);
        }
    }
    
    public void removeById(int taskId) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //Criaçao da conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores
            statement.setInt(1, taskId);
            
            //Executando a Query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Task> getAll(int idProject) {
        
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
    
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
    
        List<Task> tasks = new ArrayList<Task>();
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idProject);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()) {
                
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadLine(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));               
                tasks.add(task);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao inserir a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }    
        // Lista de tarefa que foi criada e carregada do banco de dados
        return tasks;
    } 
}
