package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

public class PrimaryController implements Initializable {

 @FXML
 private TableView<Equipo> tablaEquipos;

 @FXML
 private TableColumn<Equipo, Integer> colId;

 @FXML
 private TableColumn<Equipo, String> colNombre;

 @FXML
 private TableColumn<Equipo, String> colCategoria;

 @FXML
 private TableColumn<Equipo, Integer> colCantidad;

 @FXML
 private TableColumn<Equipo, String> colEstado;
    @Override
 public void initialize(URL url, ResourceBundle rb) {

        colId.setCellValueFactory(
            new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(
            new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(
            new PropertyValueFactory<>("categoria"));
        colCantidad.setCellValueFactory(
            new PropertyValueFactory<>("cantidad"));
        colEstado.setCellValueFactory(
            new PropertyValueFactory<>("estado"));

     cargarEquipos();
    }

    private void cargarEquipos() {

    ObservableList<Equipo> equipos =
            ConexionDB.obtenerTodosLosEquipos();

    tablaEquipos.setItems(equipos);
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    private String pedirInput(String titulo, String valorActual) {
        TextInputDialog dialogo = new TextInputDialog(valorActual);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(titulo);
        dialogo.setContentText("Ingresa el nuevo valor:");

        Optional<String> resultado = dialogo.showAndWait();
        return resultado.orElse(null);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
  private void editarEquipo() {

    Equipo equipo = tablaEquipos.getSelectionModel().getSelectedItem();

    if (equipo == null) {
        mostrarAlerta("Error", "Seleccione un equipo.");
        return;
    }

    String nuevoNombre =
        pedirInput("Nombre", equipo.getNombre());

    if (nuevoNombre == null) return;

    String nuevaCategoria =
        pedirInput("Categoría", equipo.getCategoria());

    if (nuevaCategoria == null) return;

    String nuevaCantidad =
        pedirInput("Cantidad",
                   String.valueOf(equipo.getCantidad()));

    if (nuevaCantidad == null) return;

    String nuevoEstado =
        pedirInput("Estado", equipo.getEstado());

    if (nuevoEstado == null) return;

    ConexionDB.actualizarEquipo(
        equipo.getId(),
        nuevoNombre,
        nuevaCategoria,
        Integer.parseInt(nuevaCantidad),
        nuevoEstado
    );

    cargarEquipos();

    mostrarAlerta(
        "Éxito",
        "Equipo actualizado correctamente."
    );
   }
   
   @FXML
private void eliminarEquipo() {

    Equipo equipo =
        tablaEquipos.getSelectionModel().getSelectedItem();

    if (equipo == null) {

        mostrarAlerta(
            "Error",
            "Seleccione un equipo."
        );

        return;
    }

    Alert confirmacion =
        new Alert(Alert.AlertType.CONFIRMATION);

    confirmacion.setTitle("Eliminar");

    confirmacion.setHeaderText(
        "¿Desea eliminar este equipo?"
    );

    confirmacion.setContentText(
        equipo.getNombre()
    );

    if (confirmacion.showAndWait().get()
            == ButtonType.OK) {

        ConexionDB.eliminarEquipo(
            equipo.getId()
        );

        cargarEquipos();

        mostrarAlerta(
            "Éxito",
            "Equipo eliminado."
        );
    }
  }


}
