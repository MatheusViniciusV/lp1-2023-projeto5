<%-- 
    Document   : visualizarContratos
    Created on : 8 de out. de 2023, 12:00:56
    Author     : eloym
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="br.cefetmg.snacksmart.utils.enums.TipoUsuario" %>
<%@include file="../../comuns/taglibs.jsp" %>

<%-- 
    Será o mesmo jsp para locador e locatario, quando eu tiver com 
    as dependencias corretas vou fazer a view separar a visão de cada 
    da tela.
--%>

<% 
    TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuario"); 
    TipoUsuario LOCADOR = (TipoUsuario) session.getAttribute("LOCADOR");
    TipoUsuario LOCATARIO = (TipoUsuario) session.getAttribute("LOCATARIO");
    TipoUsuario NAO_CADASTRADO = (TipoUsuario) session.getAttribute("NAO_CADASTRADO");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/contratos.css">
        <link rel="stylesheet" href="css/style.css">
        <title>Contratos</title>
    </head>
    <!-- TODO fazer opção de filtro e busca de contratos -->
    <body>
        <%@include file="../../comuns/retornarInicial.jsp" %>
        <main>
            <!-- TODO fazer essa lista aparecer de maneira dinamica -->
            <h1>Contratos </h1>
            <section id="lista-contratos">
                <article class="contratos" id="contrato-01" data-id="01">
                    <h3>Contrato 1</h3>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </article>
                <article class="contratos">
                    <h3>Contrato 2</h3>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </article>
            </section>
            
            <c:if test="${tipoUsuario == LOCADOR}">
                <section id="criar-contrato-form" class="oculto">
                    <form action="CriarContrato" method="post">
                        <h2>Novo contrato</h2>
                        <span class="obrigatorio">obrigatório *</span>
                        <h3>Dados do Locador</h3>
                        <div id="dados-locador">
                            <label>Nome: <br>
                                <input type="text" name="locador-nome" readonly="readonly" value="Algum nome">
                            </label>
                            <label class="cpf">CPF <br>
                                <input type="text" name="locador-cpf" readonly="readonly" pattern="\d{3}\.\d{3}\.\d{3}-\d{2}" value="000.000.000-01">
                            </label>
                            <label>Email: <br>
                                <input type="email" name="locador-email">
                            </label>
                            <label class="telefone">Telefone: <br>
                                <input type="tel" name="locador-telefone">
                            </label>
                        </div>
                        <h3>Dados do Locatário</h3>
                        <div id="dados-locatario">
                            <label>Nome: <abbr title="Obrigatório"><span class="obrigatorio">*</span></abbr>  <br>
                                <input type="text" name="locatario-nome" id="">
                            </label>
                            <label class="cpf">CPF: <abbr title="Obrigatório"><span class="obrigatorio">*</span></abbr> <br>
                                <input type="text" name="locatario-cpf" placeholder="000.000.000-00" pattern="\d{3}\.\d{3}\.\d{3}-\d{2}">
                            </label>
                            <label>Email: <abbr title="Obrigatório"><span class="obrigatorio">*</span></abbr> <br>
                                <input type="email" placeholder="emailexemplo@exemplo.com" name="locatario-email">
                            </label>
                            <label class="telefone">Telefone: <abbr title="Obrigatório"><span class="obrigatorio">*</span></abbr> <br>
                                <input type="tel" placeholder="(00)000000000" name="locatario-telefone">
                            </label>
                        </div>
                        <h3>Dados do contrato</h3>
                        <div id="dados-contrato">
                            <label>Data de inicio: <abbr title="Obrigatório"><span class="obrigatorio">*</span></abbr> <br>
                                <input type="date" name="data-inicio" id="">
                            </label>
                            <label>Data de término: <abbr title="Obrigatório"><span class="obrigatorio">*</span></abbr> <br>
                                <input type="date" name="data-termino" id="">
                            </label>
                            <label>Data mensal de pagamento: <abbr title="Obrigatório"><span class="obrigatorio">*</span></abbr> <br>
                                <input type="date" name="data-pagamento" id="">
                            </label>
                            <label>Observações: <br>
                                <textarea name="observacoes" wrap="hard" cols="85" 
                                    placeholder="escreva informações adicionais que não estão contidas nos campos anteriores."
                                ></textarea>
                            </label>
                        </div>
                        <div id="boteos-criar-contrato">
                            <button type="button" id="enviar"><h3>Criar Contrato</h3></button>
                            <button type="button" id="cancelar"><h3>Cancelar</h3></button>
                        </div>
                    </form>
                </section>
            </c:if>
            
            <div id="botoes">
                <button id="pdf-contrato" class="null"><a><h2>Emitir PDF</h2></a></button>
                <c:choose>
                    <c:when test="${tipoUsuario == LOCADOR}">
                        <button id="solicita-cancelar-contrato" class="null" data-calcelar="CancelarContrato"><h2>Cancelar Contrato</h2></button>
                        <button id="criar-contrato"><h2>Criar Novo Contrato</h2></button>
                    </c:when>
                    <c:otherwise>
                        <button id="solicita-cancelar-contrato" class="null" data-calcelar="SolicitarCancelarContrato"><h2>Solicitar cancelamento</h2></button>
                    </c:otherwise>
                </c:choose>        
            </div>
        </main>

        <%@include file="../../comuns/validarRegEx.jsp" %>
        <%@include file="../../comuns/jqueryLink.jsp" %>
        <script src="js/contratos.js"></script>
        <c:if test="${tipoUsuario == LOCADOR}">
            <script src="js/criarContrato.js"></script>
        </c:if>
    </body>
</html>