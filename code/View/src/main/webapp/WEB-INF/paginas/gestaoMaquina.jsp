<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="br.cefetmg.snacksmart.dto.MaquinaDTO" %>
<%@page import="java.util.ArrayList" %>
<%@include file="../../comuns/JSTL.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/GerenciarMaquina.css">
        <title>Gestão de Máquinas</title>
        <%@include file="../../comuns/jqueryLink.jsp" %>

    </head>
    <body>
        <%@include file="../../comuns/retornarInicial.jsp" %>
        <h1 id="tituloDaPagina">Máquinas</h1>
        <div id="bloquearConteudo"></div>
        <main id="GerenciarMaquinasMain">  
            <% ArrayList<MaquinaDTO> vetorMaquinas =(ArrayList<MaquinaDTO>) request.getAttribute("vetorMaquinas"); %>
            <c:forEach var="item" items="${vetorMaquinas}">
                <script>
                    try {
                        criarSlotMaquina("${item.nome}", "${item.codigo}", "${item.status}", "${item.imagem}");
                    } catch (NetworkError e) {
                        console.log("Erro de rede ao criar as máquinas: " + e);
                    } catch (Exception e) {
                        console.log("Erro ao criar as máquinas: " + e);
                    }
                </script>
            </c:forEach>
            <div class="slot" id="addMaquinaSlot">
                <label id="addMaquinaLabel">Adicionar nova máquina</label>
                <button id="addMaquinaButton">+</button>
            </div>
            <article id="formAddMaquina">               
                <form action="GerenciarMaquina" name="formAddMaquina" method="post" enctype="multipart/form-data">                                 
                    <input type="hidden" name="formulario" value="formAddMaquina">
                    
                    <h1 id="tituloForm">Adicionar nova máquina</h1>
                    <h2 id="subtituloForm">Preencha todos os campos abaixos</h2>
                    
                    <label id="nomeDaMaquina" for="nome">Nome da Máquina:</label>
                    <input class="preencher" type="text" id="nome" name="nome" minlength="5" maxlength="32" required><br>

                    <label id="tipoInput" for="tipo">Tipo da máquina:</label>                       
                    <select id="tipo" name="tipo" required>
                        <option value="refrigerada">Refrigerada</option>
                        <option value="nãoRefrigerada">Não refrigerada</option>
                    </select><br>
                    
                    <label id="labelImagem" class="imagem" for="imagem">Foto da máquina:</label>
                    <input id="inputImagem" type="file" accept="image/png" class="imagem" name="imagem" required ><br>
                    
                    <label id="locatarioInput" for="locatario">Locatário responsável:</label>
                    <select id="locatario" name="locatario" required>
                    </select><br>  
                
                    <label id="localizacaoText" for="localizacao">Localização (CEP):</label>
                    <input class="preencher" type="text" id="localizacao" name="localizacao" required><br>
                    
                    <input id="enviarformAddMaquina"class="botaoForm" type="submit" value="Enviar">
                    <div id="cancelarformAddMaquina" class="botaoForm cancelar">Cancelar</div>
                </form>
            </article>
                
            <article id="informacaoMaquina">       
                
                <h1 id="nomeMaquina">Máquina 01</h1>
                <h1 id="codeMaquina">COD-001</h1>
                <h2 id="locatarioMaquina">👤Locatário responsável: Geraldo Azeved</h2> 
                <h2 id="LocalizacaoDaMaquina">📍Localização: Bahia, Salvador</h2>              
                <h2 id="statusDinamicoH2">Status da Máquina: Disponível</h2> 
                <h2 id="tipoMaquina">Tipo da Máquina: Refrigerada</h2>   
                
                <div class="botoesForm">
                    <input class="botaoForm" id="atualizarDados" type="submit" value="Atualizar dados">
                    <div class="botaoForm cancelar">Voltar</div>
                </div>
                
            </article>
            
            <article id="remocaoMaquina">
                <h1>Você tem certeza?</h1>
                <h2>Ao remover a máquina, todos os dados relacionados a ela serão excluídos!</h2>
                <p>⚠ Dados relacionados à máquina e ao locatário não poderão serem acessados posteriormente.
                    Caso a máquina esteja ligada a algum cliente é importante que o locatário esteja ciente disso.</p>
                <form action="GerenciarMaquina" name="remocaoMaquina" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="formulario" value="remocaoMaquina">
                    <input type="hidden" name="removerMaquinaCodigo" id="removerMaquinaCodigo">
                    
                    <div class="botoesForm">
                        <input class="botaoForm" name="remover" type="submit" value="REMOVER MÁQUINA">
                        <div class="botaoForm cancelar">Cancelar</div>
                    </div>
                    
                </form>
            </article>
            
            <article id="formAtualizarMaquina">               
                <form action="GerenciarMaquina" name="formAtualizarMaquina" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="formulario" value="formAtualizarMaquina">
                    <input type="hidden" name="atualizarMaquinaCodigo" id="atualizarMaquinaCodigo">
                    <h1 id="tituloUpdate">Atualizar dados da máquina</h1>
                    
                    <label id="novoNomeLabel" for="novoNome">Alterar nome da Máquina:</label>
                    <input type="text" id="novoNome" name="novoNome" minlength="5" maxlength="32"><br>
                    
                    <label id="novaLocalizacaoLabel" for="novaLocalizacao">Nova localização (CEP):</label>
                    <input type="text" id="novaLocalizacao" name="novaLocalizacao"><br>
                    
                    <label id="novoLocatarioInput" for="novoLocatario">Alterar locatário:</label>
                    <select id="novoLocatario" name="novoLocatario">
                    </select><br>  
                    
                    <label id="statusLabel" for="status">Alterar status da máquina:</label>
                    <select id="status" name="status">
                        <option value="Disponivel">Disponível</option>
                        <option value="Em funcionamento">Em funcionamento</option>
                        <option value="Em manutencao">Em manutenção</option>
                        <option value="Aguardando manutencao">Aguardando manutenção</option>                       
                    </select><br>    
                    
                    <label id="labelNovaImagen" class="imagem" for="novaImagem">Alterar foto da máquina:</label>
                    <input id="inputNovaImagen" type="file" accept="image/png" class="imagem" name="novaImagem"><br>
                                       
                    <input id="enviarformAtualizarMaquina"class="botaoForm" type="submit" value="Realizar Alterações">
                    <div id="cancelarformAtualizarMaquina" class="botaoForm cancelar">Cancelar</div>
                </form>
            </article> 
        </main>   
        <script src="js/maquinaInfo.js"></script> 
        <!-- Mostrar Info do slot selecionado-->
        <c:set var="VetorDeObjetoMaquinas" value="${vetorMaquinas}"></c:set>
        <script>
            try {
                let vetorMaquinaJSON = '${VetorDeObjetoMaquinas}';
                vetorMaquinaArray = JSON.parse(vetorMaquinaJSON);
            } catch (Exception e) {
                console.log("Erro ao atribuir os valores: " + e);
            }
        </script>
    </body>
</html>
