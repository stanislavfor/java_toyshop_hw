package shop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lottery {

    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    private final Random random = new Random();

    public void holdLottery() {
        try {
            // Чтение данных объекта игрушки из shop.txt
            List<Toy> toys = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("src/files/shop.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    Toy toy = new Toy(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));
                    toys.add(toy);
                }
            }
            // Выполнение подсчета лотереи 10 раз в цикле
            List<Toy> lotteryResults = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Toy winningToy = selectWinningToy(toys);
                lotteryResults.add(winningToy);
                winningToy.quantity--; // Уменьшение количества (quantity) одной игрушки по результатам лотереи
                if (winningToy.quantity == 0) {
                    toys.remove(winningToy); // Исключение игрушек из лотереи, если количество их равно 0
                }
            }
            // Обновление файла toy.txt количества игрушек
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/toy.txt"))) {
                for (Toy toy : toys) {
                    writer.write(toy.id + "," + toy.name + "," + toy.quantity + "," + toy.rate + "\n");
                }
            }
            // Запись результатов лотереи в файл lottery.txt
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/files/lottery.txt"))) {
                for (Toy toy : lotteryResults) {
                    writer.write(toy.id + "," + toy.name + "," + toy.rate + "\n");
                }
                System.out.println(ANSI_CYAN + "Лотерея проведена. Результаты в файле lottery.txt" + ANSI_RESET);
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл lottery.txt : " + e.getMessage());
        }
    }

    // Метод selectWinningToy() проведения лотереи
    private Toy selectWinningToy(List<Toy> toys) {
        int randomIndex = random.nextInt(toys.size());
        int randomRate = random.nextInt(100) + 1; // Генерация случайного числа рейтинга от 1 до 100
        Toy selectedToy = toys.get(randomIndex);
        if (randomRate <= 20) { // Вероятность выбора случайной игрушки составляет 20%
            return selectedToy;
        } else if (randomRate <= 40) { // 20% вероятность выбора следующей игрушки
            int nextIndex = (randomIndex + 1) % toys.size();
            return toys.get(nextIndex);
        } else { // Вероятность выбора оставшейся случайной игрушки составляет 60%
            return toys.get(random.nextInt(toys.size()));
        }
    }

//    // Метод writeLotteryResult() записи результата лотереи в файл lottery.txt
//    public void writeLotteryResult(String filename, Toy toy) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
//            writer.write(toy.id + "," + toy.name + "," + toy.rate + "\n");
//        } catch (IOException e) {
//            System.err.println("Ошибка записи в файл lottery.txt : " + e.getMessage());
//        }
//    }


    // Метод readLotteryResult() чтения результата лотереи в файле lottery.txt
    public void readLotteryResult(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            System.out.println("--- Результаты лотереи : ---".toUpperCase());
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println(ANSI_CYAN + "ID: " + data[0] + ", Name: " + data[1] + ", Rate: " + data[2] + ANSI_RESET);
            }
            System.out.println(); // Пустая строка для форматирования вывода в консоли
        } catch (IOException e) {
            System.err.println("Ошибка чтения в файле lottery.txt : " + e.getMessage());
        }
    }


}

