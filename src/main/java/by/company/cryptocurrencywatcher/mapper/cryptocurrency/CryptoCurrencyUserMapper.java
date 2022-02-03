package by.company.cryptocurrencywatcher.mapper.cryptocurrency;

import by.company.cryptocurrencywatcher.dto.cryptocurrency.CryptoCurrencyUserDTO;
import by.company.cryptocurrencywatcher.model.Cryptocurrency;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CryptoCurrencyUserMapper {

    CryptoCurrencyUserDTO toCryptoCurrencyUserDTO(Cryptocurrency cryptocurrency);
    Cryptocurrency toCryptocurrency(CryptoCurrencyUserDTO cryptoCurrencyUserDTO);
    List<CryptoCurrencyUserDTO> toCryptoCurrencyUserDTOList(List<Cryptocurrency> cryptocurrencies);
}
