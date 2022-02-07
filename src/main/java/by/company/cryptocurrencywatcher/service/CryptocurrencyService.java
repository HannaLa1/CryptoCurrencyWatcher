package by.company.cryptocurrencywatcher.service;

import by.company.cryptocurrencywatcher.exception.CryptocurrencyNotFoundException;
import by.company.cryptocurrencywatcher.exception.SymbolNotFoundException;
import by.company.cryptocurrencywatcher.model.Cryptocurrency;
import by.company.cryptocurrencywatcher.repository.CryptocurrencyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.decimal4j.util.DoubleRounder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;

import static by.company.cryptocurrencywatcher.constant.RequestURL.TICKER_URL_ALL;

@Service
@Slf4j
@RequiredArgsConstructor
public class CryptocurrencyService {

    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final RestTemplate restTemplate;

    public Cryptocurrency findBySymbol(String symbol) {
        Cryptocurrency bySymbol = cryptocurrencyRepository.getAllBySymbol(symbol).stream()
                .findFirst()
                .orElseThrow(() -> new SymbolNotFoundException("Cryptocurrency with symbol: " + symbol + " not found"));

        log.info("IN method findBySymbol - cryptocurrency: {} found by symbol: {}", bySymbol, symbol);
        return bySymbol;
    }

    public List<Cryptocurrency> findAll() {
        List<Cryptocurrency> cryptocurrencies = cryptocurrencyRepository.findAllCryptocurrencies();

        if (cryptocurrencies.isEmpty()){
            throw new CryptocurrencyNotFoundException("No cryptocurrencies found!");
        }

        log.info("In method findAll - {} users found", cryptocurrencies.size());

        return cryptocurrencies;
    }

    @Scheduled(fixedRateString = "${request.timeout}")
    @Transactional
    public void getCurrentPrice() throws JsonProcessingException {
        var response = restTemplate.getForEntity(TICKER_URL_ALL, String.class);
        JsonNode root = new ObjectMapper().readTree(response.getBody());
        var symbols = root.findParent("data").findValuesAsText("symbol");
        int order = 0;

        for (String symbol : symbols) {
            List<Cryptocurrency> cryptocurrencies = cryptocurrencyRepository.getAllBySymbol(symbol);

            if (cryptocurrencies.isEmpty()){
                throw new CryptocurrencyNotFoundException("No cryptocurrencies found!");
            }

            schedule(root, cryptocurrencies, symbol, order);

            order++;
        }
    }

    private double percent(double price1, double price2){
        double percent;

        if (price1 > price2){
            percent = (price1 - price2) / price2 * 100;
        }else {
            percent = (price2 - price1) / price1 * 100;
        }

        return DoubleRounder.round(percent, 3);
    }

    private void schedule(JsonNode root, List<Cryptocurrency> cryptocurrencies, String symbol, int order){
        double price_usd = Double.parseDouble(root.findParent("data").findValuesAsText("price_usd").get(order));

        for (Cryptocurrency cryptocurrency : cryptocurrencies) {
            double percent = percent(cryptocurrency.getPrice(), price_usd);

            if (percent > 1) {
                log.warn("Symbol : {},  username : {}, percentage of price : {} %", symbol, cryptocurrency.getUser().getUsername(), percent);
            }

            cryptocurrencyRepository.saveCurrentPrice(price_usd, symbol);
        }

        log.info("Symbol : {}, current price : {} $", symbol, price_usd);
    }
}
