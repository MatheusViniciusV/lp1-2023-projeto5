/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.snacksmart.service_gerente;

import br.cefetmg.snacksmart.dao.ContratosDAO;
import br.cefetmg.snacksmart.dao.GerenteDAO;
import br.cefetmg.snacksmart.dto.ContratoDTO;
import br.cefetmg.snacksmart.dto.GerenteDTO;
import br.cefetmg.snacksmart.dto.LocatarioDTO;
import br.cefetmg.snacksmart.exceptions.bd.PersistenciaException;
import br.cefetmg.snacksmart.exceptions.dao.ElementoNaoExisteException;
import br.cefetmg.snacksmart.exceptions.dao.LocatarioInvalidoException;
import br.cefetmg.snacksmart.idao.IContratosDAO;
import br.cefetmg.snacksmart.idao.IGerenteDAO;
import br.cefetmg.snacksmart.utils.enums.StatusContrato;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author eloym
 */
public class ManterContratos {
    private IContratosDAO dao;
    private IGerenteDAO daoGerente;
    
    public ManterContratos() {
        dao = new ContratosDAO();
        daoGerente = new GerenteDAO();
    }
    
    public ArrayList<ContratoDTO> getContratos() throws LocatarioInvalidoException, SQLException {
        ArrayList contratos = dao.listaTodos();
        
        return contratos;
    }
    
    public ArrayList<ContratoDTO> getContratosAtivos() throws LocatarioInvalidoException, SQLException {
        ArrayList contratos = dao.filtra(StatusContrato.VIGENTE);
        
        return contratos;
    }
    
    public ArrayList<ContratoDTO> filtraContratos(LocatarioDTO locatario) throws LocatarioInvalidoException, SQLException {
        ArrayList contratos = dao.filtra(locatario);
        
        return contratos;
    }
    
    public ArrayList<ContratoDTO> filtraContratos(StatusContrato status) throws LocatarioInvalidoException, SQLException {
        ArrayList contratos = dao.filtra(status);
        
        return contratos;
    }
    
    public ContratoDTO getContrato(int id) throws SQLException, ElementoNaoExisteException {
        ContratoDTO contrato = dao.consultarPorId(id);
        
        return contrato;
    }
    
    public void cancelarContrato(int id) throws ClassNotFoundException, SQLException {
        ContratoDTO contrato = dao.consultarPorId(id);
        
        if(contrato.getStatus() == StatusContrato.VIGENTE || contrato.getStatus() == StatusContrato.CANCELAMENTO_SOLICITADO)
            dao.atualizarStatus(id, StatusContrato.CANCELADO);
    }
    
    public ContratoDTO criarContrato(ContratoDTO contrato) throws PersistenciaException {
        try {
            return dao.registraContrato(contrato);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
