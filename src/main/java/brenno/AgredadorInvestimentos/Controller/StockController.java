package brenno.AgredadorInvestimentos.Controller;

import brenno.AgredadorInvestimentos.Controller.Dto.CreateStockDto;
import brenno.AgredadorInvestimentos.Service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @Operation(summary = "Criar uma nova ação", description = "Cria um novo registro de ação no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDto createStockDto){
        stockService.createStock(createStockDto);
        return ResponseEntity.ok().build();
    }
}
