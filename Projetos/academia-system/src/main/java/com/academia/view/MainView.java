package com.academia.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainView {

    private BorderPane root;

    public MainView() {

        root = new BorderPane();

        HBox topo = new HBox();

        topo.setStyle(
                "-fx-background-color: #d32f2f;"
        );

        topo.setPadding(
                new Insets(10)
        );

        Label tituloSistema
                = new Label(
                        "Sistema Academia"
                );

        tituloSistema.setStyle(
                "-fx-text-fill: white;"
                + "-fx-font-size: 20;"
                + "-fx-font-weight: bold;"
        );

        topo.getChildren().add(
                tituloSistema
        );

        root.setTop(topo);

        VBox menu
                = new VBox(10);

        menu.getStyleClass()
                .add("sidebar");

        menu.setPadding(
                new Insets(15)
        );

        menu.setPrefWidth(220);

        Button btnDashboard
                = new Button("🏠 Dashboard");

        Button btnAlunos
                = new Button("👥 Alunos");

        Button btnPlanos
                = new Button("💳 Planos");

        Button btnMontagem
                = new Button("📝 Montagem Treino");

        Button btnTreinos
                = new Button("🏋 Treinos");

        Button btnExercicios
                = new Button("💪 Exercícios");

        Button btnPagamentos
                = new Button("💰 Pagamentos");

        Button btnRelatorios
                = new Button("📊 Relatórios");

        btnDashboard.getStyleClass()
                .add("sidebar-button");

        btnExercicios.getStyleClass()
                .add("sidebar-button");

        btnMontagem.getStyleClass()
                .add("sidebar-button");

        btnAlunos.getStyleClass()
                .add("sidebar-button");

        btnPlanos.getStyleClass()
                .add("sidebar-button");

        btnTreinos.getStyleClass()
                .add("sidebar-button");

        btnPagamentos.getStyleClass()
                .add("sidebar-button");

        btnRelatorios.getStyleClass()
                .add("sidebar-button");

        menu.getChildren().addAll(
                btnDashboard,
                btnAlunos,
                btnPlanos,
                btnMontagem,
                btnTreinos,
                btnExercicios,
                btnPagamentos,
                btnRelatorios
        );

        root.setLeft(menu);

        DashboardView dashboard
                = new DashboardView();

        root.setCenter(
                dashboard.getRoot()
        );

        btnMontagem.setOnAction(event -> {

            root.setCenter(
                    new MontagemTreinoView()
                            .getRoot()
            );
        });

        btnExercicios.setOnAction(event -> {

            root.setCenter(
                    new ExercicioView()
                            .getRoot()
            );
        });

        btnDashboard.setOnAction(event -> {

            root.setCenter(
                    new DashboardView()
                            .getRoot()
            );
        });

        btnAlunos.setOnAction(event -> {

            root.setCenter(
                    new AlunoView()
                            .getRoot()
            );
        });

        btnPlanos.setOnAction(event -> {

            root.setCenter(
                    new PlanoView()
                            .getRoot()
            );
        });

        btnTreinos.setOnAction(event -> {

            root.setCenter(
                    new TreinoView()
                            .getRoot()
            );
        });
    }

    public Parent getRoot() {
        return root;
    }
}
