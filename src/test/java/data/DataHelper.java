package data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.util.Locale;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfoForFirstUser() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getAuthInfoForSecondUser() {
        return new AuthInfo("petya", "123qwerty");
    }

    public static AuthInfo getInvalidPassword(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return new AuthInfo("vasya", faker.internet().password());
    }

    public static AuthInfo getInvalidLogin(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return new AuthInfo(faker.name().firstName(),"123qwerty");
    }


    @Value
    public static class VerificationCode {
        private String code;
    }

    @SneakyThrows
    public static VerificationCode getVerificationCode() {
        var usersSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";
        var runner = new QueryRunner();

        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app-db", "user", "pass");
        ) {
            var verificationCode = runner.query(conn, usersSQL, new ScalarHandler<>());
            return new VerificationCode((String) verificationCode);
        }
    }

    public static VerificationCode getInvalidCode() {
        return new VerificationCode("1234");
    }

    @SneakyThrows
    public static void cleanerDataBase() {
        var authCode = "DELETE FROM auth_codes;";
        var cardTransactions = "DELETE FROM card_transactions;";
        var cards = "DELETE FROM cards;";
        var users = "DELETE FROM users;";
        var runner = new QueryRunner();

        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app-db", "user", "pass");
        ) {
            runner.update(conn, authCode);
            runner.update(conn, cardTransactions);
            runner.update(conn, cards);
            runner.update(conn, users);
        }
    }
}