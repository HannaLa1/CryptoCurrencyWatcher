package by.company.cryptocurrencywatcher.service;


import by.company.cryptocurrencywatcher.model.Cryptocurrency;
import by.company.cryptocurrencywatcher.repository.CryptocurrencyRepository;
import by.company.cryptocurrencywatcher.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static by.company.cryptocurrencywatcher.constant.RequestURL.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final RestTemplate restTemplate;

    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void registration(Cryptocurrency cryptocurrency) throws JsonProcessingException {
        JsonNode root = new ObjectMapper().readTree(infoAboutCrypto(cryptocurrency.getSymbol()).getBody());
        String price_usd = root.findPath("price_usd").asText();

        cryptocurrency.setPrice(Double.parseDouble(price_usd));
        userRepository.save(cryptocurrency.getUser());
        cryptocurrencyRepository.save(cryptocurrency);

        log.info("In method registration - user : {} with symbol : {} and price : {} successfully sign up",
                cryptocurrency.getUser(), cryptocurrency.getSymbol(), price_usd);
    }

    private ResponseEntity<String> infoAboutCrypto(String cryptoSymbol){
        ResponseEntity<String> response;

        switch (cryptoSymbol) {
            case "BTC":
                response = restTemplate.getForEntity(TICKER_URL_BTC, String.class);
                break;
            case "ETH":
                response = restTemplate.getForEntity(TICKER_URL_ETH, String.class);
                break;
            case "SOL":
                response = restTemplate.getForEntity(TICKER_URL_SOL, String.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + cryptoSymbol);
        }

        return response;
    }
}
