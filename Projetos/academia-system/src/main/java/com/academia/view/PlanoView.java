package com.academia.view;

import com.academia.dao.PlanoDAO;
import com.academia.model.Plano;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlanoView {

    private VBox root;

    public PlanoView() {

        root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titulo =
                new Label("Gerenciamento de Planos");

        titulo.getStyleClass().add("titulo");

        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);

        TextField txtNome = new TextField();
        TextField txtValor = new TextField();
        TextField txtDuracao = new TextField();

        form.add(new Label("Nome"), 0, 0);
        form.add(txtNome, 1, 0);

        form.add(new Label("Valor"), 0, 1);
        form.add(txtValor, 1, 1);

        form.add(new Label("Duração (meses)"), 0, 2);
        form.add(txtDuracao, 1, 2);

        Button btnSalvar = new Button("Salvar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnExcluir = new Button("Excluir");

        btnSalvar.getStyleClass()
                .add("botao-salvar");

        btnAtualizar.getStyleClass()
                .add("botao-editar");

        btnExcluir.getStyleClass()
                .add("botao-excluir");

        HBox botoes = new HBox(
                10,
                btnSalvar,
                btnAtualizar,
                btnExcluir
        );

        TableView<Plano> tabela =
                new TableView<>();

        tabela.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        tabela.setPrefHeight(350);

        TableColumn<Plano, Integer> colId =
                new TableColumn<>("ID");

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        TableColumn<Plano, String> colNome =
                new TableColumn<>("Plano");

        colNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );

        TableColumn<Plano, Double> colValor =
                new TableColumn<>("Valor");

        colValor.setCellValueFactory(
                new PropertyValueFactory<>("valor")
        );

        TableColumn<Plano, Integer> colDuracao =
                new TableColumn<>("Meses");

        colDuracao.setCellValueFactory(
                new PropertyValueFactory<>("duracaoMeses")
        );

        tabela.getColumns().addAll(
                colId,
                colNome,
                colValor,
                colDuracao
        );

        final Plano[] planoSelecionado =
                new Plano[1];

        Runnable carregarTabela = () -> {

            ObservableList<Plano> dados =
                    FXCollections.observableArrayList(
                            new PlanoDAO().listar()
                    );

            tabela.setItems(dados);
        };

        tabela.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, antigo, novo) -> {

                    if (novo != null) {

                        planoSelecionado[0] = novo;

                        txtNome.setText(
                                novo.getNome()
                        );

                        txtValor.setText(
                                String.valueOf(
                                        novo.getValor()
                                )
                        );

                        txtDuracao.setText(
                                String.valueOf(
                                        novo.getDuracaoMeses()
                                )
                        );
                    }
                });

        btnSalvar.setOnAction(event -> {

            try {

                Plano plano = new Plano(
                        txtNome.getText(),
                        Double.parseDouble(
                                txtValor.getText()
                        ),
                        Integer.parseInt(
                                txtDuracao.getText()
                        )
                );

                new PlanoDAO()
                        .salvar(plano);

                carregarTabela.run();

                txtNome.clear();
                txtValor.clear();
                txtDuracao.clear();

                Alert alert =
                        new Alert(
                                Alert.AlertType.INFORMATION
                        );

                alert.setContentText(
                        "Plano salvo com sucesso!"
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

            if (planoSelecionado[0] == null) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.WARNING
                        );

                alert.setContentText(
                        "Selecione um plano."
                );

                alert.showAndWait();
                return;
            }

            planoSelecionado[0].setNome(
                    txtNome.getText()
            );

            planoSelecionado[0].setValor(
                    Double.parseDouble(
                            txtValor.getText()
                    )
            );

            planoSelecionado[0].setDuracaoMeses(
                    Integer.parseInt(
                            txtDuracao.getText()
                    )
            );

            new PlanoDAO()
                    .atualizar(
                            planoSelecionado[0]
                    );

            carregarTabela.run();
        });

        btnExcluir.setOnAction(event -> {

            if (planoSelecionado[0] == null) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.WARNING
                        );

                alert.setContentText(
                        "Selecione um plano."
                );

                alert.showAndWait();
                return;
            }

            PlanoDAO dao =
                    new PlanoDAO();

            if (dao.possuiAlunos(
                    planoSelecionado[0].getId()
            )) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.ERROR
                        );

                alert.setContentText(
                                "Existem alunos vinculados a este plano."
                );

                alert.showAndWait();
                return;
            }

            dao.excluir(
                    planoSelecionado[0].getId()
            );

            carregarTabela.run();

            txtNome.clear();
            txtValor.clear();
            txtDuracao.clear();

            planoSelecionado[0] = null;
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