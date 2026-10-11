package dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductDTO implements Serializable {

    private final int id;
    private final String name;
    private final BigDecimal price;

    public ProductDTO(int id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}