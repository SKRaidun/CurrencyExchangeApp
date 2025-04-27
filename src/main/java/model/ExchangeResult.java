package model;

public record ExchangeResult(Currencies baseCurrency, Currencies targetCurrencies, Double rate, Double amount, Double convertedAmount) {
}
