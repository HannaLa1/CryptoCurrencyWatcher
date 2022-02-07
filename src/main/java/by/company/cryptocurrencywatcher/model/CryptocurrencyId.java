package by.company.cryptocurrencywatcher.model;

public enum CryptocurrencyId {

    BTC (90),
    ETH (80),
    USDT (518),
    BNB (2710),
    USDC (33285),
    ADA (257),
    SOL (48543),
    XRP (58),
    LUNA (48537),
    DOT (45219),
    DOGE (2);

    private final long id;

    CryptocurrencyId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
