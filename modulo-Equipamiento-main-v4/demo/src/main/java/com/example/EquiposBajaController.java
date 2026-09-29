package com.example;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class EquiposBajaController
        implements Initializable {

    @FXML
    private TableView<Equipo> tablaEquiposBaja;

    @FXML
    private TableColumn<Equipo,Integer> colId;

    @FXML
    private TableColumn<Equipo,String> colNombre;

    @FXML
    private TableColumn<Equipo,String> colCategoria;

    @FXML
    private TableColumn<Equipo,Integer> colCantidad;

    @FXML
    private TableColumn<Equipo,String> colEstado;

    @Override
    public void initialize(URL url,
                           ResourceBundle rb) {

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

        cargarEquiposBaja();
    }

    private void cargarEquiposBaja() {

        tablaEquiposBaja.setItems(
            ConexionDB.obtenerEquiposEliminados()
        );
    }

    @FXML
    private void restaurarEquipo() {

        Equipo equipo =
            tablaEquiposBaja
                .getSelectionModel()
                .getSelectedItem();

        if (equipo == null) {
            return;
        }

        ConexionDB.reactivarEquipo(
            equipo.getId()
        );

        cargarEquiposBaja();
    }

    @FXML
    private void cerrarVentana() {

        Stage stage =
            (Stage) tablaEquiposBaja
                .getScene()
                .getWindow();

        stage.close();
    }
}

