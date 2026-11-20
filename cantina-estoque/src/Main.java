import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Sistema de controle de estoque da cantina (console).
 * Os dados ficam apenas na memória durante a execução.
 */
public class Main {

    private static final ArrayList<Produto> produtos = new ArrayList<>();
    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    listarProdutos();
                    break;
                case 3:
                    buscarProduto();
                    break;
                case 4:
                    registrarVenda();
                    break;
                case 5:
                    exibirValorTotalEstoque();
                    break;
                case 6:
                    System.out.println("Encerrando o programa. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Digite um número de 1 a 6.");
            }
        } while (opcao != 6);

        teclado.close();
    }

    // ---------------------------------------------------------------- menu

    private static void exibirMenu() {
        System.out.println();
        System.out.println("===== CANTINA - CONTROLE DE ESTOQUE =====");
        System.out.println("1 - Cadastrar produto");
        System.out.println("2 - Listar todos os produtos");
        System.out.println("3 - Buscar produto pelo código");
        System.out.println("4 - Registrar venda");
        System.out.println("5 - Exibir valor total do estoque");
        System.out.println("6 - Sair");
    }

    // ------------------------------------------------------- funcionalidades

    private static void cadastrarProduto() {
        System.out.println("\n--- Cadastro de produto ---");

        int codigo = lerInteiro("Código: ");
        if (codigo <= 0) {
            System.out.println("Erro: o código deve ser um número inteiro maior que zero.");
            return;
        }
        if (buscarPorCodigo(codigo) != null) {
            System.out.println("Erro: já existe um produto com o código " + codigo + ".");
            return;
        }

        String nome = lerTexto("Nome: ");
        if (nome.isEmpty()) {
            System.out.println("Erro: o nome não pode ser vazio.");
            return;
        }

        double preco = lerDecimal("Preço unitário (R$): ");
        if (preco <= 0) {
            System.out.println("Erro: o preço deve ser maior que zero.");
            return;
        }

        int quantidade = lerInteiro("Quantidade em estoque: ");
        if (quantidade < 0) {
            System.out.println("Erro: a quantidade em estoque não pode ser negativa.");
            return;
        }

        produtos.add(new Produto(codigo, nome, preco, quantidade));
        System.out.println("Produto cadastrado com sucesso!");
    }

    private static void listarProdutos() {
        System.out.println("\n--- Lista de produtos ---");

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (Produto produto : produtos) {
            exibirProduto(produto);
        }
    }

    private static void buscarProduto() {
        System.out.println("\n--- Busca de produto ---");

        int codigo = lerInteiro("Código do produto: ");
        Produto produto = buscarPorCodigo(codigo);

        if (produto == null) {
            System.out.println("Produto com código " + codigo + " não encontrado.");
        } else {
            exibirProduto(produto);
        }
    }

    private static void registrarVenda() {
        System.out.println("\n--- Registro de venda ---");

        int codigo = lerInteiro("Código do produto: ");
        Produto produto = buscarPorCodigo(codigo);

        if (produto == null) {
            System.out.println("Produto com código " + codigo + " não encontrado.");
            return;
        }

        int quantidade = lerInteiro("Quantidade vendida: ");
        if (quantidade <= 0) {
            System.out.println("Venda recusada: a quantidade vendida deve ser maior que zero.");
            return;
        }
        if (quantidade > produto.getQuantidadeEstoque()) {
            System.out.println("Venda recusada: estoque insuficiente. Disponível: "
                    + produto.getQuantidadeEstoque() + " unidade(s).");
            return;
        }

        produto.vender(quantidade);
        System.out.println("Venda registrada com sucesso! Estoque atual de "
                + produto.getNome() + ": " + produto.getQuantidadeEstoque() + " unidade(s).");
    }

    private static void exibirValorTotalEstoque() {
        System.out.println("\n--- Valor total do estoque ---");

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado. Valor total: " + formatarMoeda(0));
            return;
        }

        double total = 0;
        for (Produto produto : produtos) {
            total += produto.calcularValorEmEstoque();
        }
        System.out.println("Valor total do estoque: " + formatarMoeda(total));
    }

    // ------------------------------------------------------------ auxiliares

    private static Produto buscarPorCodigo(int codigo) {
        for (Produto produto : produtos) {
            if (produto.getCodigo() == codigo) {
                return produto;
            }
        }
        return null;
    }

    private static void exibirProduto(Produto produto) {
        System.out.println("Código: " + produto.getCodigo()
                + " | Nome: " + produto.getNome()
                + " | Preço: " + formatarMoeda(produto.getPrecoUnitario())
                + " | Estoque: " + produto.getQuantidadeEstoque()
                + " | Valor em estoque: " + formatarMoeda(produto.calcularValorEmEstoque()));
    }

    private static String formatarMoeda(double valor) {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %.2f", valor);
    }

    /** Lê uma linha de texto, removendo espaços das pontas. */
    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        if (!teclado.hasNextLine()) {
            // Entrada encerrada (ex.: Ctrl+D): evita laço infinito.
            System.out.println("\nEntrada encerrada. Finalizando.");
            System.exit(0);
        }
        return teclado.nextLine().trim();
    }

    /** Repete a leitura até o usuário digitar um número inteiro válido. */
    private static int lerInteiro(String mensagem) {
        while (true) {
            String texto = lerTexto(mensagem);
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido! Digite um número inteiro.");
            }
        }
    }

    /** Repete a leitura até o usuário digitar um número decimal válido (aceita vírgula ou ponto). */
    private static double lerDecimal(String mensagem) {
        while (true) {
            String texto = lerTexto(mensagem).replace(',', '.');
            try {
                double valor = Double.parseDouble(texto);
                if (!Double.isNaN(valor) && !Double.isInfinite(valor)) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // cai na mensagem abaixo
            }
            System.out.println("Valor inválido! Digite um número (ex.: 5,50).");
        }
    }
}
