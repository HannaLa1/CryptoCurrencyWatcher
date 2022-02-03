package by.company.cryptocurrencywatcher.rest;

import by.company.cryptocurrencywatcher.dto.cryptocurrency.CryptoCurrencyDTO;
import by.company.cryptocurrencywatcher.mapper.cryptocurrency.CryptoCurrencyMapper;
import by.company.cryptocurrencywatcher.model.Cryptocurrency;
import by.company.cryptocurrencywatcher.service.CryptocurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/cryptocurrency")
public class CryptoCurrencyController {

    private final CryptocurrencyService service;
    private final CryptoCurrencyMapper cryptoCurrencyMapper;

    @GetMapping("/{symbol}")
    public ResponseEntity<CryptoCurrencyDTO> findCurrentPriceBySymbol(@PathVariable String symbol) {
        Cryptocurrency cryptocurrency = service.findBySymbol(symbol);

        return cryptocurrency != null
                ? new ResponseEntity<>(cryptoCurrencyMapper.toCryptoCurrencyDTO(cryptocurrency), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CryptoCurrencyDTO>> findAll(){
        List<Cryptocurrency> cryptocurrencies = service.findAll();

        return new ResponseEntity<>(cryptoCurrencyMapper.toCryptoCurrencyDTOList(cryptocurrencies), HttpStatus.OK);
    }
}
