package com.academia.view;

import com.academia.dao.ExercicioDAO;
import com.academia.dao.TreinoDAO;
import com.academia.dao.TreinoExercicioDAO;
import com.academia.model.Exercicio;
import com.academia.model.Treino;
import com.academia.model.TreinoExercicio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MontagemTreinoView {

    private VBox root;

    public MontagemTreinoView() {

        root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titulo =
                new Label("Montagem de Treinos");

        titulo.getStyleClass().add("titulo");

        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);

        ComboBox<Treino> cbTreino =
                new ComboBox<>();

        ComboBox<Exercicio> cbExercicio =
                new ComboBox<>();

        TextField txtSeries =
                new TextField();

        TextField txtRepeticoes =
                new TextField();

        TextField txtCarga =
                new TextField();

        cbTreino.getItems().addAll(
                new TreinoDAO().listar()
        );

        cbExercicio.getItems().addAll(
                new ExercicioDAO().listar()
        );

        form.add(new Label("Treino"), 0, 0);
        form.add(cbTreino, 1, 0);

        form.add(new Label("Exercício"), 0, 1);
        form.add(cbExercicio, 1, 1);

        form.add(new Label("Séries"), 0, 2);
        form.add(txtSeries, 1, 2);

        form.add(new Label("Repetições"), 0, 3);
        form.add(txtRepeticoes, 1, 3);

        form.add(new Label("Carga (Kg)"), 0, 4);
        form.add(txtCarga, 1, 4);

        Button btnAdicionar =
                new Button("Adicionar");

        Button btnAtualizar =
                new Button("Atualizar");

        Button btnExcluir =
                new Button("Excluir");
        
        btnAdicionar.getStyleClass()
                .add("botao-salvar");

        btnAtualizar.getStyleClass()
                .add("botao-editar");

        btnExcluir.getStyleClass()
                .add("botao-excluir");

        HBox botoes =
                new HBox(10);

        botoes.getChildren().addAll(
                btnAdicionar,
                btnAtualizar,
                btnExcluir
        );

        TableView<TreinoExercicio> tabela =
                new TableView<>();

        tabela.setPrefHeight(350);

        tabela.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        TableColumn<TreinoExercicio, String> colExercicio =
                new TableColumn<>("Exercício");

        colExercicio.setCellValueFactory(
                new PropertyValueFactory<>("nomeExercicio")
        );

        TableColumn<TreinoExercicio, Integer> colSeries =
                new TableColumn<>("Séries");

        colSeries.setCellValueFactory(
                new PropertyValueFactory<>("series")
        );

        TableColumn<TreinoExercicio, Integer> colRepeticoes =
                new TableColumn<>("Repetições");

        colRepeticoes.setCellValueFactory(
                new PropertyValueFactory<>("repeticoes")
        );

        TableColumn<TreinoExercicio, Double> colCarga =
                new TableColumn<>("Carga");

        colCarga.setCellValueFactory(
                new PropertyValueFactory<>("carga")
        );

        tabela.getColumns().addAll(
                colExercicio,
                colSeries,
                colRepeticoes,
                colCarga
        );

        Runnable carregarTabela = () -> {

            if (cbTreino.getValue() == null) {
                return;
            }

            ObservableList<TreinoExercicio> dados =
                    FXCollections.observableArrayList(
                            new TreinoExercicioDAO()
                                    .listarPorTreino(
                                            cbTreino.getValue().getId()
                                    )
                    );

            tabela.setItems(dados);
        };

        cbTreino.setOnAction(event ->
                carregarTabela.run()
        );

        tabela.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, antigo, atual) -> {

                    if (atual != null) {

                        txtSeries.setText(
                                atual.getSeries().toString()
                        );

                        txtRepeticoes.setText(
                                atual.getRepeticoes().toString()
                        );

                        txtCarga.setText(
                                atual.getCarga().toString()
                        );
                    }
                });

        btnAdicionar.setOnAction(event -> {

            try {

                if (cbTreino.getValue() == null)
                    throw new Exception("Selecione um treino.");

                if (cbExercicio.getValue() == null)
                    throw new Exception("Selecione um exercício.");

                TreinoExercicioDAO dao =
                        new TreinoExercicioDAO();

                if (dao.existeExercicioNoTreino(
                        cbTreino.getValue().getId(),
                        cbExercicio.getValue().getId()
                )) {

                    throw new Exception(
                            "Esse exercício já está neste treino."
                    );
                }

                dao.adicionar(
                        cbTreino.getValue().getId(),
                        cbExercicio.getValue().getId(),
                        Integer.parseInt(txtSeries.getText()),
                        Integer.parseInt(txtRepeticoes.getText()),
                        Double.parseDouble(txtCarga.getText())
                );

                carregarTabela.run();

                txtSeries.clear();
                txtRepeticoes.clear();
                txtCarga.clear();

                Alert alert =
                        new Alert(Alert.AlertType.INFORMATION);

                alert.setContentText(
                        "Exercício adicionado com sucesso!"
                );

                alert.showAndWait();

            } catch (Exception e) {

                Alert alert =
                        new Alert(Alert.AlertType.ERROR);

                alert.setContentText(
                        e.getMessage()
                );

                alert.showAndWait();
            }
        });

        btnAtualizar.setOnAction(event -> {

            try {

                TreinoExercicio selecionado =
                        tabela.getSelectionModel()
                                .getSelectedItem();

                if (selecionado == null) {

                    throw new Exception(
                            "Selecione um exercício da tabela."
                    );
                }

                new TreinoExercicioDAO()
                        .atualizar(
                                selecionado.getId(),
                                Integer.parseInt(
                                        txtSeries.getText()
                                ),
                                Integer.parseInt(
                                        txtRepeticoes.getText()
                                ),
                                Double.parseDouble(
                                        txtCarga.getText()
                                )
                        );

                carregarTabela.run();

                Alert alert =
                        new Alert(Alert.AlertType.INFORMATION);

                alert.setContentText(
                        "Exercício atualizado!"
                );

                alert.showAndWait();

            } catch (Exception e) {

                Alert alert =
                        new Alert(Alert.AlertType.ERROR);

                alert.setContentText(
                        e.getMessage()
                );

                alert.showAndWait();
            }
        });

        btnExcluir.setOnAction(event -> {

            try {

                TreinoExercicio selecionado =
                        tabela.getSelectionModel()
                                .getSelectedItem();

                if (selecionado == null) {

                    throw new Exception(
                            "Selecione um exercício da tabela."
                    );
                }

                new TreinoExercicioDAO()
                        .excluir(
                                selecionado.getId()
                        );

                carregarTabela.run();

                txtSeries.clear();
                txtRepeticoes.clear();
                txtCarga.clear();

                Alert alert =
                        new Alert(Alert.AlertType.INFORMATION);

                alert.setContentText(
                        "Exercício removido!"
                );

                alert.showAndWait();

            } catch (Exception e) {

                Alert alert =
                        new Alert(Alert.AlertType.ERROR);

                alert.setContentText(
                        e.getMessage()
                );

                alert.showAndWait();
            }
        });

        root.getChildren().addAll(
                titulo,
                form,
                botoes,
                tabela
        );
    }

    public Parent getRoot() {
        return root;
    }
}