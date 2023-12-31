/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.snacksmart.dao;

import br.cefetmg.snacksmart.dto.ContratoDTO;
import br.cefetmg.snacksmart.dto.LocatarioDTO;
import br.cefetmg.snacksmart.dto.MaquinaDTO;
import br.cefetmg.snacksmart.exceptions.bd.PersistenciaException;
import br.cefetmg.snacksmart.exceptions.dao.LocatarioInvalidoException;
import br.cefetmg.snacksmart.idao.IContratosDAO;
import br.cefetmg.snacksmart.utils.DataManager;
import br.cefetmg.snacksmart.utils.bd.ConnectionManager;
import br.cefetmg.snacksmart.utils.enums.StatusContrato;
import br.cefetmg.snacksmart.utils.enums.StatusMaquina;
import br.cefetmg.snacksmart.utils.enums.TiposOrdenacaoContrato;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aluno
 */
public class ContratosDAO implements IContratosDAO {
    @Override
    public ContratoDTO consultarPorId(int id) throws SQLException {
        ConnectionManager conn = ConnectionManager.getInstance();
        ContratoDTO contrato = null;

        try {
            Connection conexao =  conn.getConnection();

            String sql = "SELECT * FROM `contrato` WHERE pk = ?";

            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                LocatarioDAO daoLocatario = new LocatarioDAO();
                MaquinaDAO daoMaquina = new MaquinaDAO();

                contrato = new ContratoDTO(
                        rs.getInt("pk"),
                        rs.getDouble("valor"),
                        daoLocatario.consultarPorId(rs.getInt("locatario__fk")),
                        daoMaquina.acessarMaquina(rs.getInt("maquina__fk")),
                        new DataManager(rs.getDate("data_inicio")),
                        new DataManager(rs.getDate("data_fim")),
                        new DataManager(rs.getDate("data_pagamento")),
                        rs.getString("observacoes"),
                        StatusContrato.valueOf(rs.getString("estado"))
                );

                if(contrato.getDataFim().antes(LocalDate.now())) {
                    atualizarStatus(contrato.getId(), StatusContrato.EXPIRADO);
                    contrato.setStatus(StatusContrato.EXPIRADO);

                    MaquinaDTO maquina = contrato.getMaquina();
                    maquina.setStatus(StatusMaquina.DISPONIVEL);
                    daoMaquina.atualizarMaquina(maquina);
                }
            }


            rs.close();
            pstmt.close();
            conexao.close();
        } catch (ClassNotFoundException | PersistenciaException e) {
            throw new RuntimeException(e);
        }

        return contrato;
    }
    
    @Override
    public ArrayList<ContratoDTO> listaTodos() throws SQLException {
        ArrayList<ContratoDTO> contratos = new ArrayList<>();

        try {
            Connection conexao =  ConnectionManager.getInstance().getConnection();

            String sql = "SELECT * FROM `contrato` ORDER BY `pk`";

            PreparedStatement pstmt = conexao.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                LocatarioDAO daoLocatario = new LocatarioDAO();
                MaquinaDAO daoMaquina = new MaquinaDAO();
                ContratoDTO contrato;

                contrato = new ContratoDTO(
                        rs.getInt("pk"),
                        rs.getDouble("valor"),
                        daoLocatario.consultarPorId(rs.getInt("locatario__fk")),
                        daoMaquina.acessarMaquina(rs.getInt("maquina__fk")),
                        new DataManager(rs.getDate("data_inicio")),
                        new DataManager(rs.getDate("data_fim")),
                        new DataManager(rs.getDate("data_pagamento")),
                        rs.getString("observacoes"),
                        StatusContrato.valueOf(rs.getString("estado"))
                );

                if(contrato.getDataFim().antes(LocalDate.now())) {
                    atualizarStatus(contrato.getId(), StatusContrato.EXPIRADO);
                    contrato.setStatus(StatusContrato.EXPIRADO);

                    MaquinaDTO maquina = contrato.getMaquina();
                    maquina.setStatus(StatusMaquina.DISPONIVEL);
                    daoMaquina.atualizarMaquina(maquina);
                }

                contratos.add(contrato);
            }

            rs.close();
            pstmt.close();
            conexao.close();
        } catch (PersistenciaException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return contratos;
    }
    
    @Override
    public ArrayList<ContratoDTO> filtra(LocatarioDTO locatario, TiposOrdenacaoContrato ordenacao) throws LocatarioInvalidoException, SQLException {
        ArrayList<ContratoDTO> contratos = new ArrayList<>();

        try {
            Connection conexao =  ConnectionManager.getInstance().getConnection();

            String sql = "SELECT *  FROM `contrato` WHERE `locatario__fk` = ? " + ordenacao.toSql();

            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setInt(1, locatario.getId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                LocatarioDAO daoLocatario = new LocatarioDAO();
                MaquinaDAO daoMaquina = new MaquinaDAO();
                ContratoDTO contrato;

                contrato = new ContratoDTO(
                        rs.getInt("pk"),
                        rs.getDouble("valor"),
                        daoLocatario.consultarPorId(rs.getInt("locatario__fk")),
                        daoMaquina.acessarMaquina(rs.getInt("maquina__fk")),
                        new DataManager(rs.getDate("data_inicio")),
                        new DataManager(rs.getDate("data_fim")),
                        new DataManager(rs.getDate("data_pagamento")),
                        rs.getString("observacoes"),
                        StatusContrato.valueOf(rs.getString("estado"))
                );

                if(contrato.getDataFim().antes(LocalDate.now())) {
                    atualizarStatus(contrato.getId(), StatusContrato.EXPIRADO);
                    contrato.setStatus(StatusContrato.EXPIRADO);

                    MaquinaDTO maquina = contrato.getMaquina();
                    maquina.setStatus(StatusMaquina.DISPONIVEL);
                    daoMaquina.atualizarMaquina(maquina);
                }

                contratos.add(contrato);
            }

            rs.close();
            pstmt.close();
            conexao.close();
        } catch (PersistenciaException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return contratos;
    }

    @Override
    public ArrayList<ContratoDTO> filtra(LocatarioDTO locatario, StatusContrato status, TiposOrdenacaoContrato ordenacao)
            throws LocatarioInvalidoException, SQLException {
        ArrayList<ContratoDTO> contratos = new ArrayList<>();

        try {
            Connection conexao =  ConnectionManager.getInstance().getConnection();

            String sql = "SELECT * FROM `contrato` WHERE `locatario__fk` = ? AND `estado` = ? " + ordenacao.toSql();

            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setInt(1, locatario.getId());
            pstmt.setString(2, status.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                LocatarioDAO daoLocatario = new LocatarioDAO();
                MaquinaDAO daoMaquina = new MaquinaDAO();
                ContratoDTO contrato;

                contrato = new ContratoDTO(
                        rs.getInt("pk"),
                        rs.getDouble("valor"),
                        daoLocatario.consultarPorId(rs.getInt("locatario__fk")),
                        daoMaquina.acessarMaquina(rs.getInt("maquina__fk")),
                        new DataManager(rs.getDate("data_inicio")),
                        new DataManager(rs.getDate("data_fim")),
                        new DataManager(rs.getDate("data_pagamento")),
                        rs.getString("observacoes"),
                        StatusContrato.valueOf(rs.getString("estado"))
                );

                if(contrato.getDataFim().antes(LocalDate.now())) {
                    atualizarStatus(contrato.getId(), StatusContrato.EXPIRADO);
                    contrato.setStatus(StatusContrato.EXPIRADO);

                    MaquinaDTO maquina = contrato.getMaquina();
                    maquina.setStatus(StatusMaquina.DISPONIVEL);
                    daoMaquina.atualizarMaquina(maquina);

                    if(status != StatusContrato.EXPIRADO)
                        continue;
                }

                contratos.add(contrato);
            }

            rs.close();
            pstmt.close();
            conexao.close();
        } catch (PersistenciaException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return contratos;
    }
    
    @Override
    public ArrayList<ContratoDTO> filtra(StatusContrato status, TiposOrdenacaoContrato ordenacao) throws LocatarioInvalidoException, SQLException {
        ArrayList<ContratoDTO> contratos = new ArrayList<>();

        try {
            Connection conexao =  ConnectionManager.getInstance().getConnection();

            String sql = "SELECT *  FROM `contrato` WHERE `estado` = ? " + ordenacao.toSql();

            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, status.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                LocatarioDAO daoLocatario = new LocatarioDAO();
                MaquinaDAO daoMaquina = new MaquinaDAO();
                ContratoDTO contrato;

                contrato = new ContratoDTO(
                        rs.getInt("pk"),
                        rs.getDouble("valor"),
                        daoLocatario.consultarPorId(rs.getInt("locatario__fk")),
                        daoMaquina.acessarMaquina(rs.getInt("maquina__fk")),
                        new DataManager(rs.getDate("data_inicio")),
                        new DataManager(rs.getDate("data_fim")),
                        new DataManager(rs.getDate("data_pagamento")),
                        rs.getString("observacoes"),
                        StatusContrato.valueOf(rs.getString("estado"))
                );

                if(contrato.getDataFim().antes(LocalDate.now())) {
                    atualizarStatus(contrato.getId(), StatusContrato.EXPIRADO);
                    contrato.setStatus(StatusContrato.EXPIRADO);

                    MaquinaDTO maquina = contrato.getMaquina();
                    maquina.setStatus(StatusMaquina.DISPONIVEL);
                    daoMaquina.atualizarMaquina(maquina);

                    if(status != StatusContrato.EXPIRADO)
                        continue;
                }

                contratos.add(contrato);
            }

            rs.close();
            pstmt.close();
            conexao.close();
        } catch (PersistenciaException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return contratos;
    }

    @Override
    public ArrayList<ContratoDTO> filtra(TiposOrdenacaoContrato ordenacao) throws LocatarioInvalidoException, SQLException {
        ArrayList<ContratoDTO> contratos = new ArrayList<>();

        try {
            Connection conexao =  ConnectionManager.getInstance().getConnection();

            String sql = "SELECT *  FROM `contrato` " + ordenacao.toSql();

            PreparedStatement pstmt = conexao.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                LocatarioDAO daoLocatario = new LocatarioDAO();
                MaquinaDAO daoMaquina = new MaquinaDAO();
                ContratoDTO contrato;

                contrato = new ContratoDTO(
                        rs.getInt("pk"),
                        rs.getDouble("valor"),
                        daoLocatario.consultarPorId(rs.getInt("locatario__fk")),
                        daoMaquina.acessarMaquina(rs.getInt("maquina__fk")),
                        new DataManager(rs.getDate("data_inicio")),
                        new DataManager(rs.getDate("data_fim")),
                        new DataManager(rs.getDate("data_pagamento")),
                        rs.getString("observacoes"),
                        StatusContrato.valueOf(rs.getString("estado"))
                );

                if(contrato.getDataFim().antes(LocalDate.now())) {
                    atualizarStatus(contrato.getId(), StatusContrato.EXPIRADO);
                    contrato.setStatus(StatusContrato.EXPIRADO);

                    MaquinaDTO maquina = contrato.getMaquina();
                    maquina.setStatus(StatusMaquina.DISPONIVEL);
                    daoMaquina.atualizarMaquina(maquina);
                }

                contratos.add(contrato);
            }

            rs.close();
            pstmt.close();
            conexao.close();
        } catch (PersistenciaException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return contratos;
    }
    
    @Override
    public ContratoDTO registraContrato(ContratoDTO contrato) {
        ConnectionManager conn = ConnectionManager.getInstance();

        try {
            Connection conexao =  conn.getConnection();

            String sql = """
            INSERT INTO `contrato`(
                `observacoes`,
                `data_inicio`,
                `data_fim`,
                `data_pagamento`,
                `valor`,
                `gerente__fk`,
                `locatario__fk`,
                `maquina__fk`,
                `estado`
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, contrato.getObservacoes());
            pstmt.setDate(2, contrato.getDataInicio().getSqlDate());
            pstmt.setDate(3, contrato.getDataFim().getSqlDate());
            pstmt.setDate(4, contrato.getDataPagamento().getSqlDate());
            pstmt.setDouble(5, contrato.getValorPagamento());
            pstmt.setInt(6, 1);
            pstmt.setInt(7, contrato.getLocatario().getId());
            pstmt.setInt(8, contrato.getMaquina().getCodigo());
            pstmt.setString(9, contrato.getStatus().toString());
            pstmt.executeUpdate();

            pstmt.close();

            String obterPk = "SELECT LAST_INSERT_ID()";
            pstmt = conexao.prepareStatement(obterPk);
            ResultSet rs = pstmt.executeQuery();

            ContratoDTO novoContrato = null;

            if(rs.next()) {
                novoContrato = consultarPorId(rs.getInt(1));
            }

            conexao.close();

            return novoContrato;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void deletarPorId(int id) {
        try {
            Connection conexao = ConnectionManager.getInstance().getConnection();

            String sql = "DELETE FROM `contrato` WHERE pk = ?";

            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            pstmt.close();
            conexao.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void atualizarStatus(int id, StatusContrato status) throws SQLException {
        try {
            Connection conexao = ConnectionManager.getInstance().getConnection();
            String sql = "UPDATE `contrato` SET `estado` = ? WHERE `pk` = ?";

            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, status.toString());
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            pstmt.close();
            conexao.close();
        } catch (ClassNotFoundException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public ContratoDTO consultarPorIdLocatario(int id, LocatarioDTO locatario) throws SQLException {
        ContratoDTO contrato = null;
        
        try {
            Connection conexao = ConnectionManager.getInstance().getConnection();
            
            String sql = "SELECT * FROM `contrato` WHERE `pk` = ? AND `locatario__fk` = ?";
            
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.setInt(2, locatario.getId());
            ResultSet rs = preparedStatement.executeQuery();

            MaquinaDAO daoMaquina = new MaquinaDAO();

            if(rs.next()) {
                contrato = new ContratoDTO(
                        rs.getInt("pk"),
                        rs.getDouble("valor"),
                        locatario,
                        daoMaquina.acessarMaquina(rs.getInt("maquina__fk")),
                        new DataManager(rs.getDate("data_inicio")),
                        new DataManager(rs.getDate("data_fim")),
                        new DataManager(rs.getDate("data_pagamento")),
                        rs.getString("observacoes"),
                        StatusContrato.valueOf(rs.getString("estado"))
                );
            }

            if(contrato.getDataFim().antes(LocalDate.now())) {
                atualizarStatus(contrato.getId(), StatusContrato.EXPIRADO);
                contrato.setStatus(StatusContrato.EXPIRADO);

                MaquinaDTO maquina = contrato.getMaquina();
                maquina.setStatus(StatusMaquina.DISPONIVEL);
                daoMaquina.atualizarMaquina(maquina);
            }
            
            rs.close();
            preparedStatement.close();
            conexao.close();
        } catch (ClassNotFoundException ex) {
            //noinspection CallToPrintStackTrace
            ex.printStackTrace();
        } catch (PersistenciaException ex) {
            Logger.getLogger(ContratosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return contrato;
    }
}
