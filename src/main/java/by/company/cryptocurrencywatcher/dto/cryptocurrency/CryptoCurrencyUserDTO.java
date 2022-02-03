package by.company.cryptocurrencywatcher.dto.cryptocurrency;

import by.company.cryptocurrencywatcher.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CryptoCurrencyUserDTO {

    private UserDTO user;

    private String symbol;
}
