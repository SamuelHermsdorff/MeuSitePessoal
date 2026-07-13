package com.academia.view;

import com.academia.dao.AlunoDAO;
import com.academia.dao.PlanoDAO;
import com.academia.model.Aluno;
import com.academia.model.Plano;
import com.academia.service.ValidacaoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AlunoView {

    private final VBox root;

    public AlunoView() {

        root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titulo = new Label("Cadastro de Aluno");
        titulo.getStyleClass()
                .add("titulo");

        TextField txtNome = new TextField();
        TextField txtCpf = new TextField();
        TextField txtEmail = new TextField();
        TextField txtTelefone = new TextField();
        TextField txtNascimento = new TextField();
        ComboBox<Plano> cbPlano
                = new ComboBox<>();

        cbPlano.getItems().addAll(
                new PlanoDAO().listar()
        );

        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(10);

        formulario.add(new Label("Nome:"), 0, 0);
        formulario.add(txtNome, 1, 0);

        formulario.add(new Label("CPF:"), 0, 1);
        formulario.add(txtCpf, 1, 1);

        formulario.add(new Label("Email:"), 0, 2);
        formulario.add(txtEmail, 1, 2);

        formulario.add(new Label("Telefone:"), 0, 3);
        formulario.add(txtTelefone, 1, 3);

        formulario.add(new Label("Nascimento:"), 0, 4);
        formulario.add(txtNascimento, 1, 4);

        formulario.add(
                new Label("Plano:"),
                0,
                5
        );

        formulario.add(
                cbPlano,
                1,
                5
        );

        TableView<Aluno> tabela = new TableView<>();

        tabela.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        final Aluno[] alunoSelecionado
                = new Aluno[1];

        TableColumn<Aluno, Integer> colId
                = new TableColumn<>("ID");
        colId.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn<Aluno, String> colNome
                = new TableColumn<>("Nome");
        colNome.setCellValueFactory(
                new PropertyValueFactory<>("nome"));

        TableColumn<Aluno, String> colCpf
                = new TableColumn<>("CPF");
        colCpf.setCellValueFactory(
                new PropertyValueFactory<>("cpf"));

        TableColumn<Aluno, String> colEmail
                = new TableColumn<>("Email");
        colEmail.setCellValueFactory(
                new PropertyValueFactory<>("email"));

        TableColumn<Aluno, String> colPlano
                = new TableColumn<>("Plano");

        colPlano.setCellValueFactory(
                new PropertyValueFactory<>("nomePlano")
        );

        TableColumn<Aluno, String> colTelefone
                = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(
                new PropertyValueFactory<>("telefone")
        );

        TableColumn<Aluno, String> colNascimento
                = new TableColumn<>("Nascimento");

        colNascimento.setCellValueFactory(
                new PropertyValueFactory<>("nascimento")
        );

        tabela.getColumns().addAll(
                colId,
                colNome,
                colCpf,
                colEmail,
                colTelefone,
                colNascimento,
                colPlano
        );

        tabela.getSelectionModel()
        .selectedItemProperty()
        .addListener((obs, antigo, novo) -> {

            if (novo != null) {

                alunoSelecionado[0] = novo;

                txtNome.setText(
                        novo.getNome());

                txtCpf.setText(
                        novo.getCpf());

                txtEmail.setText(
                        novo.getEmail());

                txtTelefone.setText(
                        novo.getTelefone());

                txtNascimento.setText(
                        novo.getNascimento());
            }
        });

        tabela.setPrefHeight(300);

        Runnable carregarTabela = () -> {
            ObservableList<Aluno> dados
                    = FXCollections.observableArrayList(
                            new AlunoDAO().listar()
                    );

            tabela.setItems(dados);
        };

        Button btnSalvar = new Button("Salvar");
        Button btnExcluir = new Button("Excluir");
        Button btnAtualizar = new Button("Atualizar");

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

        btnSalvar.setOnAction(event -> {

            try {

                ValidacaoService.validarAluno(
                        txtNome.getText(),
                        txtCpf.getText(),
                        txtEmail.getText(),
                        txtTelefone.getText()
                );

                Plano planoSelecionado
                        = cbPlano.getValue();

                Aluno aluno = new Aluno(
                        txtNome.getText(),
                        txtCpf.getText(),
                        txtEmail.getText(),
                        txtTelefone.getText(),
                        txtNascimento.getText(),
                        true
                );

                if (planoSelecionado == null) {

                    Alert alert
                            = new Alert(Alert.AlertType.WARNING);

                    alert.setContentText(
                            "Selecione um plano."
                    );

                    alert.showAndWait();

                    return;
                }

                aluno.setPlanoId(
                        planoSelecionado.getId()
                );

                new AlunoDAO().salvar(aluno);

                carregarTabela.run();

                txtNome.clear();
                txtCpf.clear();
                txtEmail.clear();
                txtTelefone.clear();
                txtNascimento.clear();

                Alert alert
                        = new Alert(Alert.AlertType.INFORMATION);

                alert.setContentText(
                        "Aluno salvo com sucesso!"
                );

                alert.showAndWait();

            } catch (Exception e) {

                Alert alert
                        = new Alert(Alert.AlertType.ERROR);

                alert.setContentText(
                        e.getMessage()
                );

                alert.showAndWait();
            }
        });

        btnExcluir.setOnAction(event -> {

            Aluno selecionado
                    = tabela.getSelectionModel()
                            .getSelectedItem();

            if (selecionado == null) {

                Alert alert
                        = new Alert(Alert.AlertType.WARNING);

                alert.setContentText(
                        "Selecione um aluno."
                );

                alert.showAndWait();

                return;
            }

            Alert confirmacao
                    = new Alert(
                            Alert.AlertType.CONFIRMATION
                    );

            confirmacao.setTitle(
                    "Confirmação"
            );

            confirmacao.setHeaderText(null);

            confirmacao.setContentText(
                    "Deseja excluir este aluno?"
            );

            if (confirmacao.showAndWait().get()
                    == ButtonType.OK) {

                new AlunoDAO()
                        .excluir(
                                selecionado.getId()
                        );

                carregarTabela.run();
            }
        });

        btnAtualizar.setOnAction(event -> {

            if (alunoSelecionado[0] == null) {

                Alert alert
                        = new Alert(Alert.AlertType.WARNING);

                alert.setContentText(
                        "Selecione um aluno na tabela."
                );

                alert.showAndWait();

                return;
            }

            try {

                ValidacaoService.validarAluno(
                        txtNome.getText(),
                        txtCpf.getText(),
                        txtEmail.getText(),
                        txtTelefone.getText()
                );

                alunoSelecionado[0].setNome(
                        txtNome.getText());

                alunoSelecionado[0].setCpf(
                        txtCpf.getText());

                alunoSelecionado[0].setEmail(
                        txtEmail.getText());

                alunoSelecionado[0].setTelefone(
                        txtTelefone.getText());

                alunoSelecionado[0].setNascimento(
                        txtNascimento.getText());

                new AlunoDAO()
                        .atualizar(
                                alunoSelecionado[0]
                        );

                carregarTabela.run();

                Alert alert
                        = new Alert(Alert.AlertType.INFORMATION);

                alert.setContentText(
                        "Aluno atualizado!"
                );

                alert.showAndWait();

            } catch (Exception e) {

                Alert alert
                        = new Alert(Alert.AlertType.ERROR);

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
                new Label("Lista de Alunos"),
                tabela
        );
    }

    public Parent getRoot() {
        return root;
    }
}
