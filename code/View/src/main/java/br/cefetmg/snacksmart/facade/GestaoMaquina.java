package br.cefetmg.snacksmart.facade;

import br.cefetmg.snacksmart.dto.MaquinaDTO;
import br.cefetmg.snacksmart.service_gerente.AcessarMaquinas;
import java.io.IOException;
import br.cefetmg.snacksmart.dao.LocatarioDAO;
import br.cefetmg.snacksmart.dto.LocatarioDTO;
import br.cefetmg.snacksmart.exceptions.bd.PersistenciaException;
import br.cefetmg.snacksmart.utils.enums.TipoUsuario;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Aluno
 */
@WebServlet(name = "GestaoMaquina", urlPatterns = {"/gestaoMaquina"})
public class GestaoMaquina extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        AcessarMaquinas acesso = new AcessarMaquinas();
        ArrayList<MaquinaDTO> vetorMaquinasSQL = null;
        LocatarioDAO locatarioDAO = new LocatarioDAO();
        
        HttpSession session = request.getSession();
        TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuario");
        
        if (tipoUsuario == TipoUsuario.LOCADOR){
            try {
                vetorMaquinasSQL = acesso.getAllMaquinasGerente();
            } catch (PersistenciaException ex) {
                Logger.getLogger(GestaoMaquina.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                request.setAttribute("listaLocatarios", locatarioDAO.listarTodos()); //Isso deve estar incorreto no modelo MVC por enquanto             
            } catch (PersistenciaException ex) {
                Logger.getLogger(GestaoMaquina.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                LocatarioDTO locatario = (LocatarioDTO) session.getAttribute("usuario");
                vetorMaquinasSQL = acesso.getAllMaquinasLocatario(locatario.getId());
            } catch (PersistenciaException ex) {
                Logger.getLogger(GestaoMaquina.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.setAttribute("vetorMaquinas", vetorMaquinasSQL);
        request.getRequestDispatcher("WEB-INF/paginas/gestaoMaquina.jsp").forward(request, response);
    }
}
