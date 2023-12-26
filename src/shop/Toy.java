package shop;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Toy {
    final int id;
    final String name;
    int quantity;
    int rate;

    public Toy(int id, String name, int quantity, int rate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.rate = rate;
    }

//     // Геттеры и Сеттеры

//    public int getId() { return id; }
//
//    public String getName() { return name; }
//
//    public int getQuantity() { return quantity; }
//
//    public void setQuantity(int newQuantity) { this.quantity = newQuantity; }
//
//    public int getRate() { return rate; }
//
//    public void setRate(int newRate) { this.rate = newRate; }
//
//    public void addNewToy(String filename) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
//            writer.write(id + "," + name + "," + quantity + "," + rate + "\n");
//        } catch (IOException e) {
//            System.err.println("Ошибка записи в файл toy.txt : " + e.getMessage());
//        }
//    }

    public void changeRate(int newRate, String shopFile, String toyFile) {
        try {
            // Создание промежуточных списков для чтения данных в файлах с сохраненным списком игрушек
            List<String> updatedShopLines = new ArrayList<>();
            List<String> updatedToyLines = new ArrayList<>();
            // Чтение из файлов shop.txt и toy.txt
            try (BufferedReader shopReader = new BufferedReader(new FileReader("src/files/shop.txt"));
                 BufferedReader toyReader = new BufferedReader(new FileReader("src/files/toy.txt"))) {
                String shopLine;
                String toyLine;
                while ((shopLine = shopReader.readLine()) != null) {
                    updatedShopLines.add(shopLine);
                }
                while ((toyLine = toyReader.readLine()) != null) {
                    updatedToyLines.add(toyLine);
                }
            }
            // Обновление рейтинга игрушки в обоих файлах shop.txt и toy.txt
            for (int i = 0; i < updatedShopLines.size(); i++) {
                String[] shopData = updatedShopLines.get(i).split(",");
                if (Integer.parseInt(shopData[0]) == this.id) {
                    // Замена рейтинга игрушки в файле shop.txt
                    shopData[3] = Integer.toString(newRate);
                    updatedShopLines.set(i, String.join(",", shopData));
                }
            }
            for (int i = 0; i < updatedToyLines.size(); i++) {
                String[] toyData = updatedToyLines.get(i).split(",");
                if (Integer.parseInt(toyData[0]) == this.id) {
                    // Замена рейтинга игрушки в файле toy.txt
                    toyData[3] = Integer.toString(newRate);
                    updatedToyLines.set(i, String.join(",", toyData));
                }
            }
            // Запись и обновления записи в файлах shop.txt и toy.txt
            try (BufferedWriter shopWriter = new BufferedWriter(new FileWriter(shopFile));
                 BufferedWriter toyWriter = new BufferedWriter(new FileWriter(toyFile))) {
                for (String line : updatedShopLines) {
                    shopWriter.write(line + "\n");
                }
                for (String line : updatedToyLines) {
                    toyWriter.write(line + "\n");
                }
            }
            // Обновление рейтинга игрушки в объекте игрушки
            this.rate = newRate;
        } catch (IOException e) {
            System.err.println("Ошибка при обновлении рейтинга игрушки : " + e.getMessage());
        }
    }


}
