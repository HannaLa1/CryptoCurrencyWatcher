package by.company.cryptocurrencywatcher.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cryptocurrencies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cryptocurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    private String symbol;

    private double price;
}
