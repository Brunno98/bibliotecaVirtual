package br.com.brunno.bibliotecaVirtual.usuario;

/*
Processo de imaginaçao de feature:
- controller que receberá os dados de novo usuario
- aqui os dados a serem recebidos não estava 'de bandeja' na descricao da tarefa, então precisei relembrar
como foi feito o cadastro de usuario em outro projeto
- os dados necessário são: email, senha e tipo
- validacao se o usuario já existe através do email
- validacao de tipo não nulo
- a senha será recebida em texto limpo e passara por um processo de hash para salvar no banco
- persistir o usuario no banco
- retornar dados (sem a senha)

-- tempo estimado: 40 minutos
-- tempo consumido:

 */