package com.academia.database;

import java.sql.Connection;
import java.sql.Statement;

public class CriarTabelas {

    public static void criar() {

        String sql = """
                
            CREATE TABLE IF NOT EXISTS aluno(
            
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                
                nome TEXT NOT NULL,
                
                cpf TEXT UNIQUE NOT NULL,
                
                email TEXT NOT NULL,
                
                telefone TEXT NOT NULL,
                
                nascimento TEXT,
                
                ativo INTEGER DEFAULT 1
                
            );
                
            """;

        String sqlPlano = """

                CREATE TABLE IF NOT EXISTS plano (

                id INTEGER PRIMARY KEY AUTOINCREMENT,

                nome TEXT NOT NULL,

                valor REAL NOT NULL,

                duracao_meses INTEGER NOT NULL
            )

            """;

        String sqlTreino = """
                CREATE TABLE IF NOT EXISTS treino (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                descricao TEXT NOT NULL
            )
            """;

        String sqlExercicio = """
                CREATE TABLE IF NOT EXISTS exercicio (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                grupo_muscular TEXT NOT NULL,
                descricao TEXT NOT NULL
            )
            """;

        String sqlTreinoExercicio = """
                CREATE TABLE IF NOT EXISTS treino_exercicio (

                id INTEGER PRIMARY KEY AUTOINCREMENT,

                treino_id INTEGER NOT NULL,

                exercicio_id INTEGER NOT NULL,

                series INTEGER NOT NULL,

                repeticoes INTEGER NOT NULL,

                carga REAL,

                FOREIGN KEY (treino_id)
                    REFERENCES treino(id),

                FOREIGN KEY (exercicio_id)
                    REFERENCES exercicio(id)
            )
            """;

        try (
                Connection conn
                = ConexaoSQLite.conectar(); Statement stmt
                = conn.createStatement()) {

            stmt.execute(sql);
            stmt.execute(sqlPlano);
            stmt.execute(sqlTreino);
            stmt.execute(sqlExercicio);
            stmt.execute(sqlTreinoExercicio);
            //stmt.execute("DROP TABLE IF EXISTS treino_exercicio");

            System.out.println(
                    "Tabela aluno criada!"
            );

            try {
                stmt.execute(
                        "ALTER TABLE aluno ADD COLUMN plano_id INTEGER"
                );
            } catch (Exception e) {
                // coluna já existe
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
