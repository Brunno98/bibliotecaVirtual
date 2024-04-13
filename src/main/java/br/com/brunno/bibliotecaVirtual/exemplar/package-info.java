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
-- Tempo consumido: 57:44 minutos
--- dava pra ter feito em menos tempo se eu não ficasse em duvida sobre a validacao de livro existente.
 A separecao em clean arch me fez refletir sobre como ter a validacao tanto no controller quanto no use case,
 de inicio eu fazia a busca do livro 2 vezes, uma no controller para poder retornar um 'NOT FOUND' caso nao encontre e
 outra no use case para garantir o use case sempre executaria com um livro existente, mas essa solução não me agradou.
 No fim optei por deixar o use case recebendo uma funcao que retornará o livro, desse jeito eu crio a funcao no controller
 que busca o livro e lanca a excecao de not found caso nao encontre, e passo essa funcao para o use case.
--- Tembem perdi tempo com os testes. Ainda não domino o jqwiki e acabo precisando olhar a documentacao saber o que fazer

 */