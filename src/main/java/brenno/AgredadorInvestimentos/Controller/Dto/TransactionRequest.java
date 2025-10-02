package brenno.AgredadorInvestimentos.Controller.Dto;

import brenno.AgredadorInvestimentos.Entity.TransactionType;
import java.util.UUID;

public record TransactionRequest(
        String stockId,
        TransactionType tipo,
        Double quantidade,
        Double preco
) {}