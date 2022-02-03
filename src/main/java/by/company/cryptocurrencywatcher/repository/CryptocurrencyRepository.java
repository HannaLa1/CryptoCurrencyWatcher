package by.company.cryptocurrencywatcher.repository;

import by.company.cryptocurrencywatcher.model.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CryptocurrencyRepository extends JpaRepository<Cryptocurrency, Long> {

    Optional<Cryptocurrency> findBySymbol(String symbol);

    @Query(value = "from Cryptocurrency where symbol = ?1")
    List<Cryptocurrency> getAllBySymbol(String symbol);

    boolean existsBySymbol(String symbol);

    @Modifying
    @Query(value = "update Cryptocurrency set price=?1 where symbol=?2")
    void saveCurrentPrice(double price, String symbol);

    @Query(value = "from Cryptocurrency group by symbol having count(symbol)>=1")
    List<Cryptocurrency> findAllCryptocurrencies();
}
