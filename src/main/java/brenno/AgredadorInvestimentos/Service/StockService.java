package brenno.AgredadorInvestimentos.Service;


import brenno.AgredadorInvestimentos.Controller.Dto.CreateStockDto;
import brenno.AgredadorInvestimentos.Entity.Stock;
import brenno.AgredadorInvestimentos.Repository.StockRepository;
import org.springframework.stereotype.Service;


@Service
public class StockService {


    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDto) {
        //DTO -> ENTITY

        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description());

                var strocksaved = stockRepository.save(stock);



    }
}
