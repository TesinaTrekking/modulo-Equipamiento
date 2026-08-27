package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConexionDB {

    private static final String URL = "jdbc:sqlite:crud.db";

    public static Connection conectar() {
        try {
            Connection conexion = DriverManager.getConnection(URL);
            return conexion;
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return null;
        }
    }

    public static void crearTabla() {
        String sql = " CREATE TABLE IF NOT EXISTS equipos (\n" + //
                        "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" + //
                        "    nombre TEXT NOT NULL,\n" + //
                        "    categoria TEXT NOT NULL,\n" + //
                        "    cantidad INTEGER NOT NULL,\n" + //
                        "    estado TEXT NOT NULL\n" + //
                                              ")";

        try (Connection conexion = conectar();
             Statement statement = conexion.createStatement()) {

            statement.execute(sql);
            System.out.println("Tabla 'equipos' lista.");

        } catch (SQLException e) {
            System.out.println("Error al crear la tabla: " + e.getMessage());
        }
    }

    public static void insertarEquipo(
        String nombre,
        String categoria,
        int cantidad,
        String estado) {

    String sql =
            "INSERT INTO equipos(nombre,categoria,cantidad,estado) VALUES(?,?,?,?)";

    try (Connection conexion = conectar();
         PreparedStatement statement = conexion.prepareStatement(sql)) {

        statement.setString(1, nombre);
        statement.setString(2, categoria);
        statement.setInt(3, cantidad);
        statement.setString(4, estado);

        statement.executeUpdate();

    } catch (SQLException e) {

        System.out.println(e.getMessage());
    }
 }

    public static void actualizarEquipo(
        int id,
        String nombre,
        String categoria,
        int cantidad,
        String estado) {

    String sql =
            "UPDATE equipos SET nombre=?, categoria=?, cantidad=?, estado=? WHERE id=?";

    try (Connection conexion = conectar();
         PreparedStatement statement = conexion.prepareStatement(sql)) {

        statement.setString(1, nombre);
        statement.setString(2, categoria);
        statement.setInt(3, cantidad);
        statement.setString(4, estado);
        statement.setInt(5, id);

        statement.executeUpdate();

    } catch (SQLException e) {

        System.out.println(e.getMessage());
    }
  }

    public static void eliminarEquipo(int id) {

    String sql =
            "DELETE FROM equipos WHERE id=?";

    try (Connection conexion = conectar();
         PreparedStatement statement = conexion.prepareStatement(sql)) {

        statement.setInt(1, id);

        statement.executeUpdate();

    } catch (SQLException e) {

        System.out.println(e.getMessage());
    }
  }

   public static ObservableList<Equipo> obtenerTodosLosEquipos() {

    ObservableList<Equipo> equipos =
            FXCollections.observableArrayList();

    String sql =
            "SELECT * FROM equipos";

    try (Connection conexion = conectar();
         Statement statement = conexion.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {

        while (resultSet.next()) {

            equipos.add(

                new Equipo(

                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("categoria"),
                        resultSet.getInt("cantidad"),
                        resultSet.getString("estado")
                )
            );
        }

    } catch (SQLException e) {

        System.out.println(e.getMessage());
    }

    return equipos;
 }
    
}
