package brenno.AgredadorInvestimentos.Controller;


import brenno.AgredadorInvestimentos.Controller.Dto.CreateStockDto;
import brenno.AgredadorInvestimentos.Entity.User;
import brenno.AgredadorInvestimentos.Service.StockService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {


    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }



    @PostMapping
    public ResponseEntity<User> createStock(@RequestBody CreateStockDto createStockDto){

        stockService.createStock(createStockDto);

        return ResponseEntity.ok().build();
    }
}
