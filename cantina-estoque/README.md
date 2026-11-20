# Controle de Estoque da Cantina

**Aluno:** Enzo Mestrinel
**Turma:** _(preencher: ex.: 2º ano – Técnico em Informática)_
**Disciplina:** Desenvolvimento de Software I – Atividade Avaliativa Final
**Tema escolhido:** Opção A – Controle de Estoque da Cantina

## Descrição

Sistema em Java, executado pelo console, que substitui o caderno de controle de uma cantina escolar. Permite cadastrar produtos, consultá-los, registrar vendas (baixando o estoque) e saber o valor total do estoque. Os dados ficam **apenas na memória** durante a execução (sem banco de dados, arquivos ou frameworks), conforme permitido no enunciado.

Cada produto possui: **código único**, **nome**, **preço unitário** e **quantidade em estoque**.

## Estrutura do projeto

```
cantina-estoque/
├── src/
│   ├── Produto.java      # classe da entidade (atributos, valor em estoque, venda)
│   └── Main.java         # menu, leitura com Scanner, validações e operações
├── testes/
│   ├── entradas/         # digitações usadas em cada teste (t1 a t9)
│   ├── saidas/           # saída completa do programa em cada teste
│   └── executar-testes.sh
├── README.md
└── .gitignore
```

## Como executar

Requisito: JDK 11 ou superior (testado com JDK 21).

**No IntelliJ IDEA:** abra a pasta do projeto, marque `src` como *Sources Root* (botão direito > *Mark Directory as* > *Sources Root*) e execute a classe `Main`.

**Pelo terminal** (na raiz do projeto):

```bash
mkdir -p out
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```

> No Windows, se os acentos aparecerem errados no terminal, execute `chcp 65001` antes de rodar o programa.

Para repetir os testes automaticamente: `bash testes/executar-testes.sh` (gera as saídas em `testes/saidas/`).

## Funcionalidades implementadas

| Opção | Funcionalidade |
|-------|----------------|
| 1 | Cadastrar produto |
| 2 | Listar todos os produtos |
| 3 | Buscar produto pelo código |
| 4 | Registrar venda (diminui o estoque) |
| 5 | Exibir valor total do estoque (soma de preço × quantidade de todos os produtos) |
| 6 | Sair (o menu se repete até escolher esta opção) |

### Regras de validação aplicadas

- Código repetido não é aceito (e deve ser inteiro maior que zero).
- Nome vazio (ou só com espaços) não é aceito.
- Preço deve ser maior que zero (aceita vírgula ou ponto: `5,50` ou `5.50`).
- Quantidade em estoque não pode ser negativa.
- Quantidade vendida deve ser maior que zero e não pode superar o estoque.
- Uma venda recusada **não altera** o estoque.
- Código inexistente na busca ou na venda gera mensagem informando.
- Texto digitado onde se espera número (ex.: `abc`) é recusado e a pergunta é repetida; opção inválida no menu exibe erro e permite nova escolha.

## Registro dos testes

Os testes foram executados em 17/11/2026. Cada teste tem um arquivo com as digitações em `testes/entradas/` e a saída completa do programa em `testes/saidas/`. Abaixo, o resumo (o texto do menu, que se repete a cada rodada, foi omitido).

| # | Teste | Ação e dados utilizados | Resultado esperado | Resultado obtido |
|---|-------|-------------------------|--------------------|------------------|
| 1 | Cadastro válido | Opção 1: código `101`, nome `Suco`, preço `5,00`, qtd `10`. Depois opção 2. | Produto cadastrado e exibido na listagem. | ✅ `Produto cadastrado com sucesso!` e a listagem mostrou `Código: 101 \| Nome: Suco \| Preço: R$ 5,00 \| Estoque: 10 \| Valor em estoque: R$ 50,00`. |
| 2 | Código repetido | Cadastrar `101 Suco` e tentar cadastrar outro produto com código `101`. | Segundo cadastro recusado; apenas 1 produto na lista. | ✅ `Erro: já existe um produto com o código 101.` A listagem mostrou só o Suco. |
| 3 | Informação inválida | (a) código `102`, nome só com espaços; (b) preço `abc` e depois `0`; (c) preço `4,50` e qtd `-1`; (d) código `0`. Depois opção 2. | Todos recusados; nenhum produto cadastrado. | ✅ `Erro: o nome não pode ser vazio.` / `Valor inválido! Digite um número (ex.: 5,50).` e `Erro: o preço deve ser maior que zero.` / `Erro: a quantidade em estoque não pode ser negativa.` / `Erro: o código deve ser um número inteiro maior que zero.` Listagem: `Nenhum produto cadastrado.` |
| 4 | Busca existente | Cadastrar `101 Suco` e buscar o código `101` (opção 3). | Exibir os dados do produto. | ✅ `Código: 101 \| Nome: Suco \| Preço: R$ 5,00 \| Estoque: 10 \| Valor em estoque: R$ 50,00` |
| 5 | Busca inexistente | Com `101` cadastrado, buscar o código `999`. | Informar que não foi encontrado. | ✅ `Produto com código 999 não encontrado.` |
| 6 | Operação principal (exemplo do enunciado) | Cadastrar 10 sucos a R$ 5,00 (código `101`); vender `3`; buscar `101`; opção 5. | Estoque 7; valor total R$ 35,00. | ✅ `Venda registrada com sucesso! Estoque atual de Suco: 7 unidade(s).` Busca: `Estoque: 7`. Total: `Valor total do estoque: R$ 35,00`. |
| 7 | Operação não permitida | Com 7 sucos em estoque: vender `8`; vender `0`; vender `-2`; vender no código `555`. Depois buscar `101` e ver o total. | Todas as vendas recusadas; estoque continua 7 e total R$ 35,00. | ✅ `Venda recusada: estoque insuficiente. Disponível: 7 unidade(s).` / `Venda recusada: a quantidade vendida deve ser maior que zero.` (×2) / `Produto com código 555 não encontrado.` Estoque permaneceu `7` e total `R$ 35,00`. |
| 8 | Lista vazia | Sem cadastrar nada: opção 2, opção 5, buscar `101` e vender no código `101`. | Informar que não há produtos. | ✅ `Nenhum produto cadastrado.` / `Nenhum produto cadastrado. Valor total: R$ 0,00` / `Produto com código 101 não encontrado.` (×2) |
| 9 | Opção inválida no menu | Digitar `9`, `0`, `abc` e Enter vazio; depois opção 2 e opção 6. | Informar o erro e permitir nova escolha; o programa continua funcionando. | ✅ `Opção inválida! Digite um número de 1 a 6.` (para 9 e 0) e `Valor inválido! Digite um número inteiro.` (para `abc` e vazio). Em seguida a opção 2 e a opção 6 funcionaram normalmente. |

## Limitações

- Os dados não são salvos: ao fechar o programa, todos os produtos são perdidos (permitido pelo enunciado).
- Não há opção para editar ou excluir produtos, nem para repor estoque (não estavam entre as funcionalidades obrigatórias).
- Valores monetários usam `double`, o que é suficiente para este exercício; em um sistema real seria mais indicado `BigDecimal`.
- A entrada de dados é feita sempre pelo console; se a entrada for encerrada (Ctrl+D/Ctrl+Z), o programa finaliza.

## Uso de inteligência artificial

Foi utilizada a ferramenta de IA **Claude (Anthropic)** para auxiliar na elaboração do código-fonte, dos arquivos de teste e deste README, a partir do enunciado da atividade. O código foi revisado, executado e testado (testes acima). _(Aluno: leia e entenda todo o código antes da demonstração, pois o professor poderá pedir explicações ou pequenas alterações.)_
