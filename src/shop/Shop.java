package shop;


import java.io.*;
import java.util.Scanner;

public class Shop {
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    private final Scanner scanner = new Scanner(System.in);
    public void mainMenu() {
        while (true) {
            System.out.println("--- Меню магазина ---".toUpperCase());
            System.out.println("1. Добавить новую игрушку");
            System.out.println("2. Вывести список игрушек");
            System.out.println("3. Изменить рейтинг игрушки");
            System.out.println("4. Провести лотерею игрушек");
            System.out.println("5. Вывести результаты лотереи");
            System.out.println("6. Выход");
            System.out.print("Выберите команду : ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Вывод новой строка в консоли
            Lottery lottery = new Lottery();
            switch (choice) {
                // Вызов методов из меню команд
                case 1:
                    addNewToy();
                    break;
                case 2:
                    readToyList("src/files/toy.txt");
                    break;
                case 3:
                    changeToyRate("src/files/shop.txt", "src/files/toy.txt");
                    break;
                case 4:
                    holdLottery(lottery);
                    break;
                case 5:
                    lottery.readLotteryResult("src/files/lottery.txt");
                    break;
                case 6:
                    System.out.println("магазин закрыт");
                    System.exit(0);
                default:
                    System.out.println("неправильный выбор");
            }
        }
    }

    // Метод addNewToy() добавления новой игрушки
    public void addNewToy() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите название игрушки : ");
            String name = scanner.nextLine();
            System.out.print("Введите количество : ");
            int quantity = scanner.nextInt();
            System.out.print("Введите рейтинг : ");
            int rate = scanner.nextInt();
            // Генерация уникального Id для каждой новой игрушки
            int id = 1;
            try (BufferedReader reader = new BufferedReader(new FileReader("src/files/toy.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    id = Integer.parseInt(data[0]) + 1;
                }
            }
            Toy toy = new Toy(id, name, quantity, rate); // Создание нового объекта игрушки new Toy
            // Добавление данных в файлы toy.txt и shop.txt
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/toy.txt", true))) {
                writer.write(toy.id + "," + toy.name + "," + toy.quantity + "," + toy.rate + "\n");
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/shop.txt", true))) {
                writer.write(toy.id + "," + toy.name + "," + toy.quantity + "," + toy.rate + "\n");
            }
            System.out.println(ANSI_CYAN + "Игрушка добавлена в файл toy.txt"+ ANSI_RESET);
        } catch (IOException e) {
            System.err.println("Ошибка добавления записи в файл toy.txt : " + e.getMessage());
        }
    }

    // Метод readToyList() вывода списка игрушек из файла toy.txt
    public void readToyList(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            System.out.println("--- Список игрушек : ---".toUpperCase());
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println("ID: " + data[0] + ", Name: " + data[1] + ", Quantity: " + data[2] + ", Rate: " + data[3]);
            }
            System.out.println(); // Пустая строка для форматирования вывода в консоли
        } catch (IOException e) {
            System.err.println("Ошибка чтения в файле toy.txt : " + e.getMessage());
        }
    }

    // Метод changeToyRate() нахождения игрушки по Id для изменения и перезаписи рейтинга игрушки
    public void changeToyRate(String shopFile, String toyFile) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ведите ID игрушки (для изменения рейтинга) : ");
            int id = scanner.nextInt();
            System.out.print("Введите новый рейтинг : ");
            int newRate = scanner.nextInt();
            boolean toyFound = false;
            try (BufferedReader reader = new BufferedReader(new FileReader(toyFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (Integer.parseInt(data[0]) == id) {
                        Toy toy = new Toy(id, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));
                        toy.changeRate(newRate, shopFile, toyFile);
                        toyFound = true;
                        break;
                    }
                }
            }
            if (toyFound) {
                System.out.println(ANSI_CYAN + "Рейтинг добавлен. Файл обновлен успешно" + ANSI_RESET);
            } else {
                System.out.println("Файл не найден");
            }
        } catch (IOException e) {
            System.err.println("Ошибка обновления рейтинга игрушки : " + e.getMessage());
        }
    }

    // Метод holdLottery() запроса данных из класса Lottery
    public void holdLottery(Lottery lottery) {
        lottery.holdLottery();
    }


}