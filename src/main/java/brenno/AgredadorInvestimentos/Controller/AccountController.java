package brenno.AgredadorInvestimentos.Controller;



import brenno.AgredadorInvestimentos.Controller.Dto.AccountStockResponseDto;
import brenno.AgredadorInvestimentos.Controller.Dto.AssociateAccountStockDto;
import brenno.AgredadorInvestimentos.Service.AccountService;
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


    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
                                               @RequestBody AssociateAccountStockDto dto){
       accountService.associateStock(accountId, dto);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDto>> listAccountStocks(@PathVariable("accountId") String accountId) {
        List<AccountStockResponseDto> stocks = accountService.listStock(accountId);
        return ResponseEntity.ok(stocks);
    }


}
