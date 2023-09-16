package com.store.implementations;

import com.store.models.ProductDetails;
import com.store.services.CATEGORY;
import com.store.services.ProductsManagement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


@NoArgsConstructor
@AllArgsConstructor
public class Products implements ProductsManagement {

    private ProductDetails product;

    private static Map<String, ProductDetails> provisions = new HashMap<>();
    private static Map<String, ProductDetails> vegetables = new HashMap<>();
    private static Map<String, ProductDetails> tools = new HashMap<>();
    private static Map<String, ProductDetails> fruits = new HashMap<>();
    private static Map<String, ProductDetails> others = new HashMap<>();


    @Override
    public boolean addProductsToShelve() {
        File file = new File("./src/main/resources/products.csv");
        String line = "";

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            while ((line = reader.readLine()) != null){
                String[] row = line.split(",");
                product = new ProductDetails();
                if(!row[0].equalsIgnoreCase("name")){
                    product.setNameOfProduct(row[0]);

                    product.setCAT(
                            (row[1].equalsIgnoreCase("fruits"))?CATEGORY.FRUITS:
                                    (row[1].equalsIgnoreCase("vegetables"))?CATEGORY.VEGETABLES:
                                            (row[1].equalsIgnoreCase("tools"))?CATEGORY.TOOLS:
                                                    (row[1].equalsIgnoreCase("provisions"))?CATEGORY.PROVISIONS:
                                                            CATEGORY.OTHERS
                    );

                    product.setPrice(Integer.parseInt(row[2]));
                    product.setQuantity(Integer.parseInt(row[3]));

                    if(product.getCAT() == CATEGORY.FRUITS){
                        fruits.put(product.getNameOfProduct(), product);
                    }
                    if(product.getCAT() == CATEGORY.VEGETABLES){
                        vegetables.put(product.getNameOfProduct(), product);
                    }
                    if(product.getCAT() == CATEGORY.PROVISIONS){
                        provisions.put(product.getNameOfProduct(), product);
                    }
                    if(product.getCAT() == CATEGORY.TOOLS){
                        tools.put(product.getNameOfProduct(), product);
                    }
                }
            }

            System.out.println("Done adding Products to Shelve");
            return true;
        } catch (IOException e) {
            System.out.println("File reading exception "+ e);
            return false;
        }
    }

    @Override
    public boolean updateStock() {
        final String DELIMINATOR = ",";
        final String NEW_LINE = "\n";

        StringBuilder header = new StringBuilder();
        StringBuilder fruitsList = new StringBuilder();
        StringBuilder vegetablesList = new StringBuilder();
        StringBuilder provisionsList = new StringBuilder();
        StringBuilder toolsList = new StringBuilder();
        header.append("Name").append(DELIMINATOR).
                append("Category").append(DELIMINATOR).
                append("Price").append(DELIMINATOR).
                append("Quantity").append(NEW_LINE);

        fruits.values().forEach(item -> fruitsList.append(item.getNameOfProduct()).append(DELIMINATOR).
                append(item.getCAT()).append(DELIMINATOR).
                append(item.getPrice()).append(DELIMINATOR).
                append(item.getQuantity()).append(NEW_LINE));

        vegetables.values().forEach(item -> vegetablesList.append(item.getNameOfProduct()).append(DELIMINATOR).
                append(item.getCAT()).append(DELIMINATOR).
                append(item.getPrice()).append(DELIMINATOR).
                append(item.getQuantity()).append(NEW_LINE));

        provisions.values().forEach(item -> provisionsList.append(item.getNameOfProduct()).append(DELIMINATOR).
                append(item.getCAT()).append(DELIMINATOR).
                append(item.getPrice()).append(DELIMINATOR).
                append(item.getQuantity()).append(NEW_LINE));

        tools.values().forEach(item -> toolsList.append(item.getNameOfProduct()).append(DELIMINATOR).
                append(item.getCAT()).append(DELIMINATOR).
                append(item.getPrice()).append(DELIMINATOR).
                append(item.getQuantity()).append(NEW_LINE));

        File file = new File("./src/main/resources/new_products.csv");
        try(PrintWriter writer = new PrintWriter(file)){
            writer.write(String.valueOf(header));
            writer.write(String.valueOf(fruitsList));
            writer.write(String.valueOf(vegetablesList));
            writer.write(String.valueOf(provisionsList));
            writer.write(String.valueOf(toolsList));
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private static boolean viewProducts(Map<String, ProductDetails> productMap){
        System.out.println("**********************************************************");
        System.out.println("Products                  Price(₦\u200E)              Quantity");
        productMap.forEach((key, value) -> {
            System.out.printf("%-25s %-25s %-25s", key, value.getPrice(), value.getQuantity());
            System.out.println();
        });
        System.out.println();
        return true;
    }

     static Consumer<CATEGORY> view = (CAT) -> {
        if (CAT == CATEGORY.FRUITS){
            System.out.printf("%30s", CATEGORY.FRUITS);
            System.out.println();
            viewProducts(fruits);
        }
        else if (CAT == CATEGORY.VEGETABLES){
            System.out.printf("%30s", CATEGORY.VEGETABLES);
            System.out.println();
            viewProducts(vegetables);
        }
        else if (CAT == CATEGORY.PROVISIONS){
            System.out.printf("%30s", CATEGORY.PROVISIONS);
            System.out.println();
            viewProducts(provisions);
        }
        else if (CAT == CATEGORY.TOOLS){
            System.out.printf("%30s", CATEGORY.TOOLS);
            System.out.println();
            viewProducts(tools);
        }
        else System.out.println("We do not have such category in our store");
    };

public Map<String, ProductDetails> getProvisions() {
    return provisions;
}

    public Map<String, ProductDetails> getVegetables() {
        return vegetables;
    }

    public Map<String, ProductDetails> getTools() {
        return tools;
    }

    public Map<String, ProductDetails> getFruits() {
        return fruits;
    }
    public Map<String, ProductDetails> getOthers() {
        return others;
    }
}
