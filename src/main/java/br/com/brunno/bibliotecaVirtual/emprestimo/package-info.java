package br.com.brunno.bibliotecaVirtual.emprestimo;

/*
# Emprestimo de exemplar de um livro:
- O 'emprestimo' faria parte de qual dominio? Como o pedido de emprestimo tem que ser feito para um livro, e o
sistema ficará encarregado de encontrar um exemplar, então eu decidi deixar o emprestimo no dominio do livro.
- Controller responsavel por receber os dados de pedido de emprestimo.
- Será criada uma entidade para representar o emprestimo? No momento, me parece que sim, pois representaria a relaçao
entre exemplar e usuario, com mais alguns dados próprios, como data de develução e outros...
- então ao receber os dados de emprestimo, será necessário buscar o usuario e um exemplar do livro informado.
- a busca pelo usuario pode ser um find pelo email recebido nos dados de emprestimo.
- o exemplar talvez possa tirar proveito de Orientação a objeto e buscar o livro e pedir para o objeto livro um exemplar.
- agora tendo o usuario e o exemplar, posso criar o emprestimo e persistir no banco
- sobre as restricoes:
-- quando for um usuario pesquisador, o prazo de entrega poder ser nulo, com o sistema definindo para 60 dias.
-- independente do usuario, toda data de entrega tem prazo maximo de 60 dias para a entrega.
-- usuario padrao pode ter no maximo 5 emprestimos.
-- somente pesquisadores poder pegar emprestimos de usuarios restritos.
- Nesse momento, ainda não tenho claro o funcionamento com essas condições de usuario pesquisador e padrao,
então comecei a simular os cenario na imaginação mesmo:
-- Um usuario pede o emprestimo do livro X, ao vericar o usuario, vejo que é um pesquisador, então olho se
foi passado um prazo de entrega, caso tenha sido passado verificarei se é menor ou igual a 60 dias e usarei esse prazo,
 caso seja maior então é invalido e retorno bad request e caso não tenha sido passado, então usarei 60 dias. Apos isso,
 buscarei o livro e pegarei um exemplar dele, tendo o usuario, exemplar e prazo, poderei criar o emprestimo.
-- Um usuario pede o emprestimo do livro Y, ao verificar o usuario, vejo que é do tipo padrão, entao vejo se foi
passado uma data de prazo de entrega, caso não tenha sido então o pedido é invalido e retorno bad request, caso tenha
 então verifico se é no maximo até 60 dias, caso não seja, então o pedido é invalido e retorno bad request. Sendo um
 usuario do tipo padrão, verificarei se já possui 5 emprestimos, caso já possua então não pode ter um novo e retorno
 bad request. Agora buscarei o livro e pegarei um exemplar dele do tipo livre. Tendo o usuario, exemplar e prazo, poderei
 salvar o emprestimo no banco.

-- Tempo estimado: 01:30 hora
-- tempo consumido: 01:30 hora, porem com uma soluçao não agradavel. Como a maior parte do tempo consumido foi pensando
em como refatorar o codigo pra uma versao melhor porém sem conseguir, resolvi parar e buscar ajuda no material de apoio

# Restricao de emprestimos expirados:
- criar validador no controller de emprestimo
- adicionar metodo no usuario que diz se o mesmo tem emprestimo expirado
- provavelmente, adicionar metodo no emprestimo que diz se está expirado
- adicionar validacao na camada de serviço tambem

-- tempo estimado: 30 minutos
-- tempo consumido:

 */