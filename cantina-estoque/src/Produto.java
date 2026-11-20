/**
 * Representa um produto vendido na cantina da escola.
 */
public class Produto {

    private final int codigo;
    private final String nome;
    private final double precoUnitario;
    private int quantidadeEstoque;

    public Produto(int codigo, String nome, double precoUnitario, int quantidadeEstoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.precoUnitario = precoUnitario;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    /** Valor deste produto em estoque: preço unitário x quantidade. */
    public double calcularValorEmEstoque() {
        return precoUnitario * quantidadeEstoque;
    }

    /**
     * Registra uma venda. Só altera o estoque se a quantidade for válida
     * (maior que zero e não superior ao estoque disponível).
     *
     * @return true se a venda foi realizada; false se foi recusada (estoque inalterado)
     */
    public boolean vender(int quantidade) {
        if (quantidade <= 0 || quantidade > quantidadeEstoque) {
            return false;
        }
        quantidadeEstoque -= quantidade;
        return true;
    }
}
