package by.company.cryptocurrencywatcher.exception;

public class CryptocurrencyNotFoundException extends RuntimeException {

    public CryptocurrencyNotFoundException(String message) {
        super(message);
    }
}
