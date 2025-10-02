package brenno.AgredadorInvestimentos.Controller.Dto;

import brenno.AgredadorInvestimentos.Entity.Transaction;
import brenno.AgredadorInvestimentos.Entity.TransactionType;

import java.util.UUID;

public record TransactionResponse(
        UUID id,
        UUID accountId,
        String stockId,
        TransactionType tipo,
        Double quantidade,
        Double preco,
        String data) {


    public TransactionResponse(Transaction transaction) {
        this(
                transaction.getId(),
                transaction.getAccount().getAccountId(),
                transaction.getStock().getStockId(),
                transaction.getTipo(),
                transaction.getQuantidade(),
                transaction.getPreco(),
                transaction.getData().toString()
        );
    }
}
