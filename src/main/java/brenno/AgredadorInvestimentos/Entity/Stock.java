package brenno.AgredadorInvestimentos.Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    private String stockId;

    @Column(name = "description")
    private String description;

    public Stock() {
    }

    public Stock(String stockId, String description) {
        this.stockId = stockId;
        this.description = description;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

