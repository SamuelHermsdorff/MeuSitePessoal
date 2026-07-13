package com.academia.view;

import com.academia.dao.ExercicioDAO;
import com.academia.model.Exercicio;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ExercicioView {

    private VBox root;

    public ExercicioView() {

        root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titulo =
                new Label("Cadastro de Exercícios");

        titulo.getStyleClass().add("titulo");

        GridPane formulario =
                new GridPane();

        formulario.setHgap(10);
        formulario.setVgap(10);

        TextField txtNome =
                new TextField();

        ComboBox<String> cbGrupo =
                new ComboBox<>();

        cbGrupo.getItems().addAll(
                "Peito",
                "Costas",
                "Bíceps",
                "Tríceps",
                "Ombros",
                "Pernas",
                "Panturrilha",
                "Abdômen",
                "Cardio"
        );

        TextArea txtDescricao =
                new TextArea();

        txtDescricao.setPrefRowCount(3);

        formulario.add(
                new Label("Nome"),
                0,
                0
        );

        formulario.add(
                txtNome,
                1,
                0
        );

        formulario.add(
                new Label("Grupo Muscular"),
                0,
                1
        );

        formulario.add(
                cbGrupo,
                1,
                1
        );

        formulario.add(
                new Label("Descrição"),
                0,
                2
        );

        formulario.add(
                txtDescricao,
                1,
                2
        );

        Button btnSalvar =
                new Button("Salvar");

        Button btnAtualizar =
                new Button("Atualizar");

        Button btnExcluir =
                new Button("Excluir");

        btnSalvar.getStyleClass()
                .add("botao-salvar");

        btnAtualizar.getStyleClass()
                .add("botao-editar");

        btnExcluir.getStyleClass()
                .add("botao-excluir");

        HBox botoes =
                new HBox(10);

        botoes.getChildren().addAll(
                btnSalvar,
                btnAtualizar,
                btnExcluir
        );

        TableView<Exercicio> tabela =
                new TableView<>();

        tabela.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        tabela.setPrefHeight(350);

        TableColumn<Exercicio, Integer> colId =
                new TableColumn<>("ID");

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        TableColumn<Exercicio, String> colNome =
                new TableColumn<>("Nome");

        colNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );

        TableColumn<Exercicio, String> colGrupo =
                new TableColumn<>("Grupo");

        colGrupo.setCellValueFactory(
                new PropertyValueFactory<>("grupoMuscular")
        );

        TableColumn<Exercicio, String> colDescricao =
                new TableColumn<>("Descrição");

        colDescricao.setCellValueFactory(
                new PropertyValueFactory<>("descricao")
        );

        tabela.getColumns().addAll(
                colId,
                colNome,
                colGrupo,
                colDescricao
        );

        final Exercicio[] selecionado =
                new Exercicio[1];

        Runnable carregarTabela = () -> {

            ObservableList<Exercicio> dados =
                    FXCollections.observableArrayList(
                            new ExercicioDAO().listar()
                    );

            tabela.setItems(dados);
        };

        tabela.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, antigo, novo) -> {

                    if (novo != null) {

                        selecionado[0] = novo;

                        txtNome.setText(
                                novo.getNome()
                        );

                        cbGrupo.setValue(
                                novo.getGrupoMuscular()
                        );

                        txtDescricao.setText(
                                novo.getDescricao()
                        );
                    }
                });

        btnSalvar.setOnAction(event -> {

            try {

                if (txtNome.getText().isBlank()) {
                    throw new Exception(
                            "Informe o nome do exercício."
                    );
                }

                if (cbGrupo.getValue() == null) {
                    throw new Exception(
                            "Selecione um grupo muscular."
                    );
                }

                Exercicio exercicio =
                        new Exercicio(
                                txtNome.getText(),
                                cbGrupo.getValue(),
                                txtDescricao.getText()
                        );

                new ExercicioDAO()
                        .salvar(exercicio);

                carregarTabela.run();

                txtNome.clear();
                txtDescricao.clear();
                cbGrupo.setValue(null);

                Alert alert =
                        new Alert(
                                Alert.AlertType.INFORMATION
                        );

                alert.setContentText(
                        "Exercício salvo com sucesso!"
                );

                alert.showAndWait();

            } catch (Exception e) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.ERROR
                        );

                alert.setContentText(
                        e.getMessage()
                );

                alert.showAndWait();
            }
        });

        btnAtualizar.setOnAction(event -> {

            try {

                if (selecionado[0] == null) {
                    throw new Exception(
                            "Selecione um exercício."
                    );
                }

                selecionado[0].setNome(
                        txtNome.getText()
                );

                selecionado[0].setGrupoMuscular(
                        cbGrupo.getValue()
                );

                selecionado[0].setDescricao(
                        txtDescricao.getText()
                );

                new ExercicioDAO()
                        .atualizar(
                                selecionado[0]
                        );

                carregarTabela.run();

                Alert alert =
                        new Alert(
                                Alert.AlertType.INFORMATION
                        );

                alert.setContentText(
                        "Exercício atualizado!"
                );

                alert.showAndWait();

            } catch (Exception e) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.ERROR
                        );

                alert.setContentText(
                        e.getMessage()
                );

                alert.showAndWait();
            }
        });

        btnExcluir.setOnAction(event -> {

            try {

                if (selecionado[0] == null) {
                    throw new Exception(
                            "Selecione um exercício."
                    );
                }

                new ExercicioDAO()
                        .excluir(
                                selecionado[0].getId()
                        );

                carregarTabela.run();

                txtNome.clear();
                txtDescricao.clear();
                cbGrupo.setValue(null);

                selecionado[0] = null;

                Alert alert =
                        new Alert(
                                Alert.AlertType.INFORMATION
                        );

                alert.setContentText(
                        "Exercício excluído!"
                );

                alert.showAndWait();

            } catch (Exception e) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.ERROR
                        );

                alert.setContentText(
                        e.getMessage()
                );

                alert.showAndWait();
            }
        });

        carregarTabela.run();

        root.getChildren().addAll(
                titulo,
                formulario,
                botoes,
                tabela
        );
    }

    public Parent getRoot() {
        return root;
    }
}