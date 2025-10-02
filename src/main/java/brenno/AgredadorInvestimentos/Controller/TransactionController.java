package brenno.AgredadorInvestimentos.Controller;

import brenno.AgredadorInvestimentos.Controller.Dto.TransactionRequest;
import brenno.AgredadorInvestimentos.Controller.Dto.TransactionResponse;
import brenno.AgredadorInvestimentos.Entity.Account;
import brenno.AgredadorInvestimentos.Entity.Stock;
import brenno.AgredadorInvestimentos.Entity.Transaction;
import brenno.AgredadorInvestimentos.Repository.AccountRepository;
import brenno.AgredadorInvestimentos.Repository.StockRepository;
import brenno.AgredadorInvestimentos.Service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/accounts/{accountId}/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;

    public TransactionController(TransactionService transactionService,
                                 AccountRepository accountRepository,
                                 StockRepository stockRepository) {
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
    }

    @Operation(summary = "Registrar uma transação", description = "Registra uma nova transação de compra ou venda de ações vinculada a uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação registrada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Conta ou ação não encontrada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PostMapping
    public TransactionResponse criarTransacao(
            @PathVariable UUID accountId,
            @RequestBody TransactionRequest request
    ) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        Stock stock = stockRepository.findById(request.stockId())
                .orElseThrow(() -> new RuntimeException("Ação não encontrada"));

        Transaction transaction = transactionService.registrarTransacao(
                account,
                stock,
                request.tipo(),
                request.quantidade(),
                request.preco()
        );

        return new TransactionResponse(transaction);
    }
}
