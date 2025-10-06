package brenno.AgredadorInvestimentos.Controller;

import brenno.AgredadorInvestimentos.Controller.Dto.AccountStockResponseDto;
import brenno.AgredadorInvestimentos.Controller.Dto.AssociateAccountStockDto;
import brenno.AgredadorInvestimentos.Service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Associar uma ação a uma conta", description = "Associa uma ação à conta do usuário com a quantidade informada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ação associada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(
            @PathVariable("accountId") String accountId,
            @RequestBody AssociateAccountStockDto dto){
        accountService.associateStock(accountId, dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Listar ações de uma conta", description = "Retorna todas as ações associadas a uma conta, com quantidade e preço atual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ações retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountStockResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content)
    })
    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDto>> listAccountStocks(
            @PathVariable("accountId") String accountId) {
        List<AccountStockResponseDto> stocks = accountService.listStock(accountId);
        return ResponseEntity.ok(stocks);
    }
}
