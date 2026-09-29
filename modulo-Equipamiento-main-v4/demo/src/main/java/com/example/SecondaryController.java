package com.example;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

public class SecondaryController {

    @FXML
    private TextField txtNombre;

    @FXML
    private ComboBox<String> cmbCategoria;

    @FXML
    private TextField txtCantidad;

    @FXML
    private ComboBox<String> cmbEstado;


    private Equipo equipoEditando = null;

    @FXML
    private void switchToPrimary() {

    Stage stage =
        (Stage) txtNombre.getScene().getWindow();

    stage.close();
    }

    @FXML
  private void guardarEquipo() {

    try {
          String nombre = txtNombre.getText();
        String categoria =
               cmbCategoria.getValue();
        String cantidadTexto = txtCantidad.getText();
        String estado =
                cmbEstado.getValue();
        
            if (nombre.isEmpty()
             || categoria == null
             || cantidadTexto.isEmpty()
             || estado == null) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText(
                "Todos los campos son obligatorios."
            );
            alerta.showAndWait();

            return;
        }
    int cantidad = Integer.parseInt(cantidadTexto);
        if (equipoEditando == null) {

    ConexionDB.insertarEquipo(
        nombre,
        categoria,
        cantidad,
        estado
    );

  }  else  {

    ConexionDB.actualizarEquipo(
        equipoEditando.getId(),
        nombre,
        categoria,
        cantidad,
        estado
    );
   }
     Stage stage =
    (Stage) txtNombre.getScene().getWindow();

    stage.close();

    } catch (NumberFormatException e) {

        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText("la cantidad debe contener solo numeros");

        alerta.showAndWait();
    }
  }
   
  public void cargarEquipo(Equipo equipo) {

    equipoEditando = equipo;

    txtNombre.setText(
        equipo.getNombre()
    );

    cmbCategoria.setValue(
       equipo.getCategoria()
    );

    txtCantidad.setText(
        String.valueOf(
            equipo.getCantidad()
        )
    );

     cmbEstado.setValue(
    equipo.getEstado()
    );
 }
  
  @FXML
 public void initialize() {

    cmbCategoria.getItems().addAll(
        "Campamento",
        "Navegación",
        "Comunicación",
        "Seguridad",
        "Escalada",
        "Vestimenta",
        "Hidratación",
        "Primeros Auxilios",
        "Herramientas",
        "Transporte"
    );

    cmbEstado.getItems().addAll(
        "Disponible",
        "Asignado",
        "En Reparación",
        "En Inspección",
        "Fuera de Servicio",
        "Extraviado"
    );
  }
} 
