package com.example;


import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


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
    private void switchToSecondary() {
         System.out.println("Botón presionado");
    try {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("secondary.fxml")
        );

        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Agregar Equipo");
        stage.setScene(new Scene(root, 450,300));
        stage.showAndWait();

        cargarEquipos();

    } catch (Exception e) {
        e.printStackTrace();
    }
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

    Equipo equipo =
        tablaEquipos.getSelectionModel()
                    .getSelectedItem();

    if (equipo == null) {

        mostrarAlerta(
            "Error",
            "Seleccione un equipo."
        );

        return;
    }

    try {

        FXMLLoader loader =
            new FXMLLoader(
                getClass().getResource(
                    "secondary.fxml"
                )
            );

        Parent root = loader.load();

        SecondaryController controller =
            loader.getController();

        controller.cargarEquipo(
            equipo
        );

        Stage stage = new Stage();

        stage.setTitle(
            "Editar Equipo"
        );

        stage.setScene(
            new Scene(root, 450, 300)
        );

        stage.showAndWait();

        cargarEquipos();

    } catch (Exception e) {

        e.printStackTrace();
    }
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
  @FXML
  private void restaurarEquipo() {

    ObservableList<Equipo> eliminados =
            ConexionDB.obtenerEquiposEliminados();

    if (eliminados.isEmpty()) {

        mostrarAlerta(
            "Información",
            "No hay equipos eliminados."
        );

        return;
    }

    Equipo equipo = eliminados.get(0);

    ConexionDB.reactivarEquipo(
        equipo.getId()
    );

    mostrarAlerta(
        "Éxito",
        "Equipo restaurado correctamente."
    );

    cargarEquipos();
  }

  @FXML
 private void mostrarEquiposBaja() {

    try {

        FXMLLoader loader =
            new FXMLLoader(
                getClass().getResource(
                    "equiposBaja.fxml"
                )
            );

        Parent root =
            loader.load();

        Stage stage =
            new Stage();

        stage.setTitle(
            "Equipos Dados de Baja"
        );

        stage.setScene(
            new Scene(root,700,400)
        );

        stage.showAndWait();

        cargarEquipos();

    } catch (Exception e) {

        e.printStackTrace();
    }
  }

}
