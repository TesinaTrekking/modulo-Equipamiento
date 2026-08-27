package com.example;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class SecondaryController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCategoria;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtEstado;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
  private void guardarEquipo() {

    try {

        String nombre = txtNombre.getText();
        String categoria = txtCategoria.getText();
        int cantidad = Integer.parseInt(txtCantidad.getText());
        String estado = txtEstado.getText();

        ConexionDB.insertarEquipo(
            nombre,
            categoria,
            cantidad,
            estado
        );

        App.setRoot("primary");

    } catch (Exception e) {

        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(e.getMessage());

        alerta.showAndWait();
    }
  }
}