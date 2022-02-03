package by.company.cryptocurrencywatcher.dto.cryptocurrency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CryptoCurrencyDTO {

    private long id;

    private String symbol;

    private double price;
}
