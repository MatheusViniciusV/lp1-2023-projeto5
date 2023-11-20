<%-- 
    Document   : GestaoAlugueis
    Created on : 20 de nov. de 2023, 10:21:01
    Author     : VictorN77
--%>
<%@ page import= "br.cefetmg.snacksmart.dao.MaquinaDAO" %>
<%@ page import= "br.cefetmg.snacksmart.dto.MaquinaDTO" %>
<%@ page import= "java.util.ArrayList" %>
<%@ page import= "java.io.PrintWriter" %>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <link rel ="stylesheet" href="css/alugueis.css">
    </head>
    <body>
                <%@include file="comuns/retornarInicial.jsp" %>
        <h1>Aluguéis</h1>
        <div class="conteudo">
            <p>Dias de pagamento das máquinas</p>
            <p>Formulário</p>
        </div>

        <div class="conteudo">
            <div class="box" id="listaMaquinas">
                <%
                    response.setContentType("text/html");
                    PrintWriter writer = response.getWriter();
                
                MaquinaDAO Maquinas = new MaquinaDAO();
                ArrayList<String> JsonMaquinas = new ArrayList();
                try{
                    ArrayList<MaquinaDTO> ListaMaquinas = Maquinas.acessarTodasMaquinasSemExcecoes();
                    for(MaquinaDTO m : ListaMaquinas){
                    JsonMaquinas.add(m.toJson());
                    
                        //writer.println("<p>" + m.getNome()+"</p>");
                    }
                    Gson gson = new Gson();
                    String json = gson.toJson(JsonMaquinas);
                    
                    //request.setAttribute("JsonMaquinas", json);
                    }
                  catch(Exception e){}  
                    
                %>
                <p id='listaOculta'>
                    <%= json%>
                </p>
            </div>
            <div class="box">
                
                <form id="pedido">
                    <input type="text" id="tipo" value='tipo'>
                    <input type="text" id="local" value='local'>
                    <input type="text" id="loc" value='loc'>


                    <input type="submit">
                </form>

            </div>
        </div>
            <script src="js/Alugueis.js">
    </body>
</html>
