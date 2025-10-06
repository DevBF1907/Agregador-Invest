package brenno.AgredadorInvestimentos.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_accounts_stocks")
public class AccountStock {

    @EmbeddedId
    private AccountStockId id;

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @MapsId("stockId")
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "quantity")
    private Double quantidade;

    @Column(name = "average_price")
    private Double precoMedio;


    public AccountStock() {
    }

    public AccountStock(AccountStockId id, Account account, Stock stock, Integer quantity) {}

    // Construtor usado no TransactionService
    public AccountStock(Account account, Stock stock, Double quantidade, Double precoMedio) {
        this.id = new AccountStockId(account.getAccountId(), stock.getStockId());
        this.account = account;
        this.stock = stock;
        this.quantidade = quantidade;
        this.precoMedio = precoMedio;
    }

    public AccountStock(AccountStockId id, Account account, Stock stock, Double quantidade, Double precoMedio) {
        this.id = id;
        this.account = account;
        this.stock = stock;
        this.quantidade = quantidade;
        this.precoMedio = precoMedio;
    }


    public AccountStockId getId() {
        return id;
    }

    public void setId(AccountStockId id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoMedio() {
        return precoMedio;
    }

    public void setPrecoMedio(Double precoMedio) {
        this.precoMedio = precoMedio;
    }
}
