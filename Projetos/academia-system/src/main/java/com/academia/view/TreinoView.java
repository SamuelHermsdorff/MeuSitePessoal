package com.academia.view;

import com.academia.dao.TreinoDAO;
import com.academia.model.Treino;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TreinoView {

    private VBox root;

    public TreinoView() {

        root = new VBox(15);

        root.setPadding(new Insets(20));

        Label titulo =
                new Label("Treinos");

        titulo.getStyleClass()
                .add("titulo");

        GridPane form =
                new GridPane();

        form.setHgap(10);
        form.setVgap(10);

        TextField txtNome =
                new TextField();

        TextArea txtDescricao =
                new TextArea();

        form.add(
                new Label("Nome"),
                0,
                0
        );

        form.add(
                txtNome,
                1,
                0
        );

        form.add(
                new Label("Descrição"),
                0,
                1
        );

        form.add(
                txtDescricao,
                1,
                1
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

        TableView<Treino> tabela =
                new TableView<>();

        tabela.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        tabela.setPrefHeight(350);

        TableColumn<Treino, Integer> colId =
                new TableColumn<>("ID");

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        TableColumn<Treino, String> colNome =
                new TableColumn<>("Nome");

        colNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );

        TableColumn<Treino, String> colDescricao =
                new TableColumn<>("Descrição");

        colDescricao.setCellValueFactory(
                new PropertyValueFactory<>("descricao")
        );

        tabela.getColumns().addAll(
                colId,
                colNome,
                colDescricao
        );

        final Treino[] treinoSelecionado =
                new Treino[1];

        Runnable carregarTabela = () -> {

            ObservableList<Treino> dados =
                    FXCollections.observableArrayList(
                            new TreinoDAO().listar()
                    );

            tabela.setItems(dados);
        };

        tabela.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, antigo, novo) -> {

                    if (novo != null) {

                        treinoSelecionado[0] = novo;

                        txtNome.setText(
                                novo.getNome()
                        );

                        txtDescricao.setText(
                                novo.getDescricao()
                        );
                    }
                });

        btnSalvar.setOnAction(event -> {

            try {

                Treino treino =
                        new Treino(
                                txtNome.getText(),
                                txtDescricao.getText()
                        );

                new TreinoDAO()
                        .salvar(treino);

                carregarTabela.run();

                txtNome.clear();
                txtDescricao.clear();

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

                if (treinoSelecionado[0] == null) {

                    throw new Exception(
                            "Selecione um treino."
                    );
                }

                treinoSelecionado[0].setNome(
                        txtNome.getText()
                );

                treinoSelecionado[0].setDescricao(
                        txtDescricao.getText()
                );

                new TreinoDAO()
                        .atualizar(
                                treinoSelecionado[0]
                        );

                carregarTabela.run();

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

                if (treinoSelecionado[0] == null) {

                    throw new Exception(
                            "Selecione um treino."
                    );
                }

                new TreinoDAO()
                        .excluir(
                                treinoSelecionado[0].getId()
                        );

                carregarTabela.run();

                txtNome.clear();
                txtDescricao.clear();

                treinoSelecionado[0] = null;

            } catch (Exception e) {

                Alert alert =
                        new Alert(Alert.AlertType.ERROR);

                alert.setContentText(
                        e.getMessage()
                );

                alert.showAndWait();
            }
        });

        carregarTabela.run();

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