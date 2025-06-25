import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Інтерфейс Bridge для оплати
interface PaymentMethod {
    boolean pay(double amount); // Метод для здійснення оплати
}

// Реалізація оплати кредитною карткою
class CreditCardPayment implements PaymentMethod {
    public boolean pay(double amount) {
        System.out.println("Оплата кредитною карткою: " + amount + " грн");
        return true; // Умовно вважаємо, що оплата завжди успішна
    }
}

// Реалізація оплати готівкою
class CashPayment implements PaymentMethod {
    public boolean pay(double amount) {
        System.out.println("Оплата готiвкою: " + amount + " грн");
        return true; // Умовно вважаємо, що оплата завжди успішна
    }
}

// Основний клас додатку
public class ShopApp {

    // Внутрішній клас для представлення товару
    static class Product {
        int id;         // Унікальний ідентифікатор
        String name;    // Назва товару
        double price;   // Ціна товару

        Product(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return id + ". " + name + " - " + price + " грн";
        }
    }

    // Головна точка входу в програму
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Список доступних товарів (демо-товари)
        List<Product> products = List.of(
            new Product(1, "Книга", 150),
            new Product(2, "Чашка", 70),
            new Product(3, "Рюкзак", 1200),
            new Product(4, "Ручка", 25),
            new Product(5, "Телефон", 8000)
        );

        List<Product> cart = new ArrayList<>(); // Кошик користувача
        boolean shopping = true; // Флаг для циклу покупки

        // Процес вибору товарів
        while (shopping) {
            System.out.println("Оберiть товар:");
            for (Product p : products) {
                System.out.println(p); // Вивід списку товарів
            }

            System.out.print("Введiть номер товару: ");
            int choice = scanner.nextInt(); // Зчитування вибору

            Product selected = null;
            for (Product p : products) {
                if (p.id == choice) {
                    selected = p;
                    break;
                }
            }

            // Якщо товар не знайдено — повідомити про помилку
            if (selected == null) {
                System.out.println("Невiрний вибiр, спробуйте ще раз.");
                continue;
            }

            cart.add(selected); // Додати обраний товар до кошика
            System.out.println("Додано до кошика: " + selected.name);

            System.out.print("Це все? (1 - Так, 2 - Нi): ");
            int more = scanner.nextInt(); // Запит про продовження покупок
            if (more == 1) {
                shopping = false;
            }
        }

        // Виведення підсумків покупки
        System.out.println("Ваш кошик:");
        double total = 0;
        for (Product p : cart) {
            System.out.println("- " + p.name + " : " + p.price + " грн");
            total += p.price;
        }
        System.out.println("Загальна сума: " + total + " грн");

        // Вибір способу оплати
        System.out.println("Оберiть спосiб оплати:");
        System.out.println("1. Кредитна картка");
        System.out.println("2. Готiвка");
        System.out.print("Ваш вибір: ");
        int paymentChoice = scanner.nextInt();

        PaymentMethod paymentMethod;
        switch (paymentChoice) {
            case 1:
                paymentMethod = new CreditCardPayment();
                break;
            case 2:
                paymentMethod = new CashPayment();
                break;
            default:
                // Якщо вибір неправильний, встановити оплату карткою за замовчуванням
                System.out.println("Невiрний вибір. За замовчуванням — кредитна картка.");
                paymentMethod = new CreditCardPayment();
                break;
        }

        // Спроба здійснення оплати
        boolean paid = paymentMethod.pay(total);
        if (paid) {
            System.out.println("Оплата пройшла успiшно. Дякуємо за покупку!");
        } else {
            System.out.println("Помилка оплати.");
        }

        scanner.close(); // Закриття сканера
    }
}
