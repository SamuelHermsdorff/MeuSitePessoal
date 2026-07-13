package com.academia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.academia.database.ConexaoSQLite;
import com.academia.model.Exercicio;

public class ExercicioDAO {

    public void salvar(Exercicio exercicio) {

        String sql
                = "INSERT INTO exercicio(nome, grupo_muscular, descricao) VALUES (?, ?, ?)";

        try (
                Connection conn = ConexaoSQLite.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, exercicio.getNome());
            stmt.setString(2, exercicio.getGrupoMuscular());
            stmt.setString(3, exercicio.getDescricao());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Exercicio exercicio) {

        if (exercicio.getId() == null) {
            throw new RuntimeException(
                    "ID do exercício não informado."
            );
        }

        String sql = """
            UPDATE exercicio
            SET
                nome = ?,
                grupo_muscular = ?,
                descricao = ?
            WHERE id = ?
            """;

        try (
                Connection conn = ConexaoSQLite.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, exercicio.getNome());
            stmt.setString(2, exercicio.getGrupoMuscular());
            stmt.setString(3, exercicio.getDescricao());
            stmt.setInt(4, exercicio.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean possuiTreinos(Integer exercicioId) {

        String sql
                = "SELECT COUNT(*) FROM treino_exercicio WHERE exercicio_id = ?";

        try (
                Connection conn = ConexaoSQLite.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, exercicioId);

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

        if (possuiTreinos(id)) {

            throw new RuntimeException(
                    "Não é possível excluir este exercício porque ele está vinculado a um treino."
            );
        }

        String sql
                = "DELETE FROM exercicio WHERE id = ?";

        try (
                Connection conn = ConexaoSQLite.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Exercicio> listar() {

        List<Exercicio> exercicios
                = new ArrayList<>();

        String sql
                = "SELECT * FROM exercicio";

        try (
                Connection conn = ConexaoSQLite.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Exercicio exercicio
                        = new Exercicio();

                exercicio.setId(
                        rs.getInt("id"));

                exercicio.setNome(
                        rs.getString("nome"));

                exercicio.setGrupoMuscular(
                        rs.getString("grupo_muscular"));

                exercicio.setDescricao(
                        rs.getString("descricao"));

                exercicios.add(exercicio);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exercicios;
    }
}
