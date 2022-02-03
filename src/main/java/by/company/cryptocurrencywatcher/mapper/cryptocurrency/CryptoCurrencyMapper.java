package by.company.cryptocurrencywatcher.mapper.cryptocurrency;

import by.company.cryptocurrencywatcher.dto.cryptocurrency.CryptoCurrencyDTO;
import by.company.cryptocurrencywatcher.model.Cryptocurrency;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CryptoCurrencyMapper {

    CryptoCurrencyDTO toCryptoCurrencyDTO(Cryptocurrency cryptocurrency);
    Cryptocurrency toCryptocurrency(CryptoCurrencyDTO cryptoCurrencyDTO);
    List<CryptoCurrencyDTO> toCryptoCurrencyDTOList(List<Cryptocurrency> cryptocurrencies);
}
