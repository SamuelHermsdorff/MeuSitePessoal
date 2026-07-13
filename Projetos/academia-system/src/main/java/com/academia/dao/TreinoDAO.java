package com.academia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.academia.database.ConexaoSQLite;
import com.academia.model.Treino;

public class TreinoDAO {

    public void salvar(Treino treino) {

        String sql =
                "INSERT INTO treino(nome, descricao) VALUES (?, ?)";

        try (
                Connection conn =
                        ConexaoSQLite.conectar();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(1, treino.getNome());
            stmt.setString(2, treino.getDescricao());

            stmt.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void atualizar(Treino treino) {

        String sql = """
                UPDATE treino
                SET
                    nome = ?,
                    descricao = ?
                WHERE id = ?
                """;

        try (
                Connection conn =
                        ConexaoSQLite.conectar();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(1, treino.getNome());
            stmt.setString(2, treino.getDescricao());
            stmt.setInt(3, treino.getId());

            stmt.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public boolean possuiExercicios(Integer treinoId) {

        String sql =
                "SELECT COUNT(*) FROM treino_exercicio WHERE treino_id = ?";

        try (
                Connection conn =
                        ConexaoSQLite.conectar();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, treinoId);

            ResultSet rs =
                    stmt.executeQuery();

            if (rs.next()) {

                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public void excluir(Integer id) {

        if (possuiExercicios(id)) {

            throw new RuntimeException(
                    "Não é possível excluir este treino porque existem exercícios vinculados."
            );
        }

        String sql =
                "DELETE FROM treino WHERE id = ?";

        try (
                Connection conn =
                        ConexaoSQLite.conectar();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public List<Treino> listar() {

        List<Treino> treinos =
                new ArrayList<>();

        String sql =
                "SELECT * FROM treino";

        try (
                Connection conn =
                        ConexaoSQLite.conectar();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                Treino treino =
                        new Treino();

                treino.setId(
                        rs.getInt("id")
                );

                treino.setNome(
                        rs.getString("nome")
                );

                treino.setDescricao(
                        rs.getString("descricao")
                );

                treinos.add(treino);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return treinos;
    }
}