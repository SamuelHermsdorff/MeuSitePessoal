package com.academia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.academia.database.ConexaoSQLite;
import com.academia.model.Aluno;

public class AlunoDAO {

    public void salvar(Aluno aluno) {
        String sql = "INSERT INTO aluno( nome, cpf, email, telefone, nascimento, ativo, plano_id ) VALUES (?, ?, ?, ?, ?, ?, ?) "; 
        
        try (Connection conn = ConexaoSQLite.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getEmail());
            stmt.setString(4, aluno.getTelefone());
            stmt.setString(5, aluno.getNascimento());
            stmt.setInt(6, aluno.isAtivo() ? 1 : 0);
            stmt.setInt(7, aluno.getPlanoId());
            stmt.executeUpdate();
            System.out.println("Aluno salvo com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(Integer id) {
        String sql = "DELETE FROM aluno WHERE id = ?";
        try (Connection conn = ConexaoSQLite.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Aluno aluno) {

        String sql = "UPDATE aluno SET nome = ?, cpf = ?, email = ?, telefone = ?, nascimento = ? WHERE id = ? "; 
        
        try (Connection conn = ConexaoSQLite.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getEmail());
            stmt.setString(4, aluno.getTelefone());
            stmt.setString(5, aluno.getNascimento());
            stmt.setInt(6, aluno.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Aluno> listar() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = " SELECT a.*, p.nome AS nome_plano FROM aluno a LEFT JOIN plano p ON a.plano_id = p.id "; 
         
         try (Connection conn = ConexaoSQLite.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setEmail(rs.getString("email"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setNascimento(rs.getString("nascimento"));
                aluno.setNomePlano(rs.getString("nome_plano"));
                aluno.setAtivo(rs.getInt("ativo") == 1);
                alunos.add(aluno);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alunos;
    }

    public int contarTotalAlunos() {
        String sql = "SELECT COUNT(*) FROM aluno";
        try (Connection conn = ConexaoSQLite.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int contarAtivos() {
        String sql = "SELECT COUNT(*) FROM aluno WHERE ativo = 1";
        try (Connection conn = ConexaoSQLite.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int contarInativos() {
        String sql = "SELECT COUNT(*) FROM aluno WHERE ativo = 0";
        try (Connection conn = ConexaoSQLite.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
