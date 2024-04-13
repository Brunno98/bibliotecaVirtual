package br.com.brunno.bibliotecaVirtual.exemplar;

/*
Processo de imaginaçao de feature:

# Cadastro de exemplar
- refleti se faria ou não parte de um dominio ja existente, nesse caso seria um novo
- Precisara de um controller que receberá os dados
- os dados precisarao ser validados
- ao transformar em objeto de dominio, o exemplar tem uma forte relacao com o dominio 'livro', então haverá alguma dependecia para ele
- por fim, salva no banco
- retorna a resposta pro cliente
- agora pensando nas validacoes
- as validacoes de campo obrigadotorios ficaram a cargo da bean validation, não preciso me preocupar com isso
- para a restricao de isbn obrigatorio, farei o mais simples, uma busca no banco e caso não encontre lançarei um NOTFONUDException
-- Apos pensar nessa parte mais alto nivel, senti que ainda não entendi o suficiente do dominio, tendo como duvida:
-- para criar o exemplar, eu adiciono o exemplar no livro e salvo o livro ou eu adicino o livro no exemplar e salvo o exemplar
-- resolvi fazer o que entendo ser mais simple e adicionar o livro no exemplar

-- Tempo estimado: 1 hora
-- Tempo consumido:
 */