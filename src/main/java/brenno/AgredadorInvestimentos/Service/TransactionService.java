package brenno.AgredadorInvestimentos.Service;

import brenno.AgredadorInvestimentos.Entity.*;
import brenno.AgredadorInvestimentos.Repository.AccountStockRepository;
import brenno.AgredadorInvestimentos.Repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountStockRepository accountStockRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountStockRepository accountStockRepository) {
        this.transactionRepository = transactionRepository;
        this.accountStockRepository = accountStockRepository;
    }

    public Transaction registrarTransacao(Account account, Stock stock, TransactionType tipo, Double quantidade, Double preco) {
        // Cria a transação
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setStock(stock);
        transaction.setTipo(tipo);
        transaction.setQuantidade(quantidade);
        transaction.setPreco(preco);
        transaction.setData(LocalDateTime.now());

        transactionRepository.save(transaction);

        // Recupera ou cria AccountStock inicializado
        AccountStock accountStock = accountStockRepository.findByAccountAndStock(account, stock)
                .orElseGet(() -> new AccountStock(account, stock, 0.0, 0.0));

        // Garante que quantidade e precoMedio nunca sejam null
        if (accountStock.getQuantidade() == null) accountStock.setQuantidade(0.0);
        if (accountStock.getPrecoMedio() == null) accountStock.setPrecoMedio(0.0);

        if (tipo == TransactionType.BUY) {
            double novoTotal = accountStock.getQuantidade() + quantidade;
            double novoPrecoMedio = 0.0;
            if (novoTotal > 0) {
                novoPrecoMedio = ((accountStock.getQuantidade() * accountStock.getPrecoMedio()) + (quantidade * preco)) / novoTotal;
            }
            accountStock.setQuantidade(novoTotal);
            accountStock.setPrecoMedio(novoPrecoMedio);

        } else if (tipo == TransactionType.SELL) {
            double novoTotal = accountStock.getQuantidade() - quantidade;
            if (novoTotal <= 0) {
                accountStock.setQuantidade(0.0);
                accountStock.setPrecoMedio(0.0);
            } else {
                accountStock.setQuantidade(novoTotal);
            }
        }

        accountStockRepository.save(accountStock);

        return transaction;
    }
}
