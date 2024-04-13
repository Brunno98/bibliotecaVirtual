package br.com.brunno.bibliotecaVirtual.livro;

/*
Processo de imaginaçao de feature:

# cadastro de livro:
- o pacote onde iria ficar as classes, nesse caso, seria o pacote 'livro'
- um controller que seria responsavel por receber os dados, ainda sem definir nome de path ou qualquer outro nome...
- os dados que seriam recebidos
- as validacoes em cima desses dados
- como transformar esses dados em objeto de dominio
- salvar esse objeto
- retornar os dados para o cliente
- nessa etapa, apos ter uma imagem mais 'concreta' do que eu preciso fazer, eu tambem penso em como vou testar esse controller
- por se um cadastro, eu exercitataria o codigo com um fuzzy test
- para as validacoes eu escreveria um teste unitario (sendo muito integrado ou não), em que os dados seriam invalidos para exercitar somente validacoes criadas por mim.

-- Expectativa de tempo: 1 hora
-- tempo consumido: 59:07 minutos

o que consumiu mais tempo:
- configuracoes de infraestrurura, neste caso: configuracao de banco e dependencia de fuzzy test (jqwik)
- Como fazer o validador. Antes de começar, não planejei muito bem como faria o validador de titulo ou isbn ja existente

 */