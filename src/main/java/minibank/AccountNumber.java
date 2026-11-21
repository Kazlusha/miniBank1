package minibank;

public record AccountNumber(String value) {
    public AccountNumber {
        if (value == null || !value.matches("\\d{10}")) {
            throw new IllegalArgumentException(
                    "Номер счета должен состоять из 10 цифр"
            );
        }
    }
}
