package com.academia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.academia.database.ConexaoSQLite;
import com.academia.model.TreinoExercicio;

public class TreinoExercicioDAO {

    public void adicionar(
            Integer treinoId,
            Integer exercicioId,
            Integer series,
            Integer repeticoes,
            Double carga
    ) {

        String sql = """
            INSERT INTO treino_exercicio
            (
                treino_id,
                exercicio_id,
                series,
                repeticoes,
                carga
            )
            VALUES (?, ?, ?, ?, ?)
            """;

        try (
                Connection conn = ConexaoSQLite.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, treinoId);
            stmt.setInt(2, exercicioId);
            stmt.setInt(3, series);
            stmt.setInt(4, repeticoes);
            stmt.setDouble(5, carga);

            stmt.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void atualizar(
            Integer id,
            Integer series,
            Integer repeticoes,
            Double carga
    ) {

        String sql = """
            UPDATE treino_exercicio
            SET
                series = ?,
                repeticoes = ?,
                carga = ?
            WHERE id = ?
            """;

        try (
                Connection conn = ConexaoSQLite.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, series);
            stmt.setInt(2, repeticoes);
            stmt.setDouble(3, carga);
            stmt.setInt(4, id);

            stmt.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void excluir(Integer id) {

        String sql =
                "DELETE FROM treino_exercicio WHERE id = ?";

        try (
                Connection conn = ConexaoSQLite.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public boolean existeExercicioNoTreino(
            Integer treinoId,
            Integer exercicioId
    ) {

        String sql = """
            SELECT COUNT(*)
            FROM treino_exercicio
            WHERE treino_id = ?
            AND exercicio_id = ?
            """;

        try (
                Connection conn = ConexaoSQLite.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, treinoId);
            stmt.setInt(2, exercicioId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public List<TreinoExercicio> listarPorTreino(
            Integer treinoId
    ) {

        List<TreinoExercicio> lista =
                new ArrayList<>();

        String sql = """
            SELECT
                te.*,
                e.nome
            FROM treino_exercicio te
            INNER JOIN exercicio e
                ON e.id = te.exercicio_id
            WHERE te.treino_id = ?
            ORDER BY e.nome
            """;

        try (
                Connection conn = ConexaoSQLite.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, treinoId);

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                TreinoExercicio te =
                        new TreinoExercicio();

                te.setId(
                        rs.getInt("id")
                );

                te.setTreinoId(
                        rs.getInt("treino_id")
                );

                te.setExercicioId(
                        rs.getInt("exercicio_id")
                );

                te.setSeries(
                        rs.getInt("series")
                );

                te.setRepeticoes(
                        rs.getInt("repeticoes")
                );

                te.setCarga(
                        rs.getDouble("carga")
                );

                te.setNomeExercicio(
                        rs.getString("nome")
                );

                lista.add(te);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }
}