package com.academia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.academia.database.ConexaoSQLite;
import com.academia.model.Plano;

public class PlanoDAO {

    public void salvar(Plano plano) {

        String sql =
                "INSERT INTO plano(nome, valor, duracao_meses) VALUES (?, ?, ?)";

        try (
                Connection conn = ConexaoSQLite.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, plano.getNome());
            stmt.setDouble(2, plano.getValor());
            stmt.setInt(3, plano.getDuracaoMeses());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Plano plano) {

        String sql = """
                UPDATE plano
                SET
                    nome = ?,
                    valor = ?,
                    duracao_meses = ?
                WHERE id = ?
                """;

        try (
                Connection conn = ConexaoSQLite.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, plano.getNome());
            stmt.setDouble(2, plano.getValor());
            stmt.setInt(3, plano.getDuracaoMeses());
            stmt.setInt(4, plano.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean possuiAlunos(Integer planoId) {

        String sql =
                "SELECT COUNT(*) FROM aluno WHERE plano_id = ?";

        try (
                Connection conn = ConexaoSQLite.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, planoId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void excluir(Integer id) {

        String sql =
                "DELETE FROM plano WHERE id = ?";

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

    public List<Plano> listar() {

        List<Plano> planos =
                new ArrayList<>();

        String sql =
                "SELECT * FROM plano";

        try (
                Connection conn = ConexaoSQLite.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Plano plano = new Plano();

                plano.setId(
                        rs.getInt("id"));

                plano.setNome(
                        rs.getString("nome"));

                plano.setValor(
                        rs.getDouble("valor"));

                plano.setDuracaoMeses(
                        rs.getInt("duracao_meses"));

                planos.add(plano);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return planos;
    }
}