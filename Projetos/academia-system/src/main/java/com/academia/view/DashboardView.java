package com.academia.view;

import com.academia.dao.AlunoDAO;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DashboardView {

    private VBox root;

    public DashboardView() {
        root = new VBox(20);
        root.setPadding(new Insets(20));
        Label titulo = new Label("Dashboard");
        titulo.getStyleClass().add("titulo");
        HBox cards = new HBox(20);
        AlunoDAO dao = new AlunoDAO();
        VBox cardAlunos = criarCard("Total de Alunos", String.valueOf(dao.contarTotalAlunos()));
        VBox cardAtivos = criarCard("Ativos", String.valueOf(dao.contarAtivos()));
        VBox cardInativos = criarCard("Inativos", String.valueOf(dao.contarInativos()));
        cards.getChildren().addAll(cardAlunos, cardAtivos, cardInativos);
        root.getChildren().addAll(titulo, cards);
    }

    private VBox criarCard(String titulo, String valor) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.getStyleClass().add("card");
        Label lblTitulo = new Label(titulo);
        Label lblValor = new Label(valor);
        lblValor.getStyleClass().add("card-valor");
        card.getChildren().addAll(lblTitulo, lblValor);
        return card;
    }

    public Parent getRoot() {
        return root;
    }
}
