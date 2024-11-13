import java.io.*;
import java.util.*;

public class IceCreamParlour {
    static class Data {
        int cnt;
        int max;
        int min;
        int quantity;

        Data(int q) {
            cnt = q;
            max = q;
            min = q;
            quantity = q;
        }

        void newData(int q) {
            cnt++;
            max = Math.max(max, q);
            min = Math.min(min, q);
            quantity += q;
        }
    }
    private static Map<Integer,String> monthName = new HashMap<>();
    private static Map<String, Integer> unitPrice = new HashMap<>();
    private static Map<Integer, Map<String, Data>> map = new HashMap<>();

    public static void file(String file) {
        // System.out.println(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line = br.readLine();
            int cnt=0;
            while (line != null) {
                String lines[] = line.split(",");
                int month = Integer.parseInt(lines[0].substring(5, 7));
                String name = lines[1];
                int price = Integer.parseInt(lines[2]);
                int quantity = Integer.parseInt(lines[3]);
                unitPrice.put(name, price);
                map.computeIfAbsent(month, k -> new HashMap<>()).computeIfAbsent(name, k -> new Data(quantity))
                        .newData(quantity);
                line = br.readLine();
                cnt++;
            }
            System.out.println(cnt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void totalSales() {
        int sales = 0;
        for (int key : map.keySet()) {
            Map<String, Data> temp = map.get(key);
            for (String s : temp.keySet()) {
                Data data = temp.get(s);
                sales += (data.quantity * unitPrice.get(s));
            }
        }
        System.out.println("Total Sales = " + sales);
    }

    public static void monthlySales() {
        // int sales = 0;
        System.out.println("Monthly Sales ");
        for (int key : map.keySet()) {
            Map<String, Data> temp = map.get(key);
            int sales = 0;
            for (String s : temp.keySet()) {
                Data data = temp.get(s);
                sales += (data.quantity * unitPrice.get(s));
            }
            System.out.println(monthName.get(key) + ": " + sales);
        }
    }

    public static void MPI() {
        System.out.println("Most Popular Item ");
        for (int key : map.keySet()) {
            Map<String, Data> temp = map.get(key);
            int q = 0;
            String ans = "";
            for (String s : temp.keySet()) {
                Data data = temp.get(s);
                if (q < data.quantity) {
                    ans = s;
                    q = data.quantity;
                }
            }
            System.out.println(monthName.get(key) + ": " + ans);
        }
    }

    public static void MRI() {
        System.out.println("Most Revenue ");
        for (int key : map.keySet()) {
            Map<String, Data> temp = map.get(key);
            int rev = 0;
            String ans = "";
            for (String s : temp.keySet()) {
                Data data = temp.get(s);
                if (rev < data.quantity * unitPrice.get(s)) {
                    ans = s;
                    rev = data.quantity * unitPrice.get(s);
                }
            }
            System.out.println(monthName.get(key) + ": " + ans);
        }
    }

    public static void Stats() {
        System.out.println("Most Popular Item's Stats ");
        for (int key : map.keySet()) {
            Map<String, Data> temp = map.get(key);
            int max = 0, min = Integer.MAX_VALUE, avg = 0;
            String name = " ";
            int quantity = 0;

            for (String s : temp.keySet()) {
                Data data = temp.get(s);
                if (data.quantity > quantity) {
                    name = s;
                    max = data.max;
                    min = data.min;
                    avg = data.quantity / data.cnt;
                    quantity = data.quantity;
                }
            }
            System.out.println("Month " + monthName.get(key) + ": " + name + " Max = " + max + " Min = " + min + " Avg = " + avg);
        }
    }

    public static void main(String[] args) {
        String filePath = " ";//Enter the path here
        file(filePath);
        monthName.put(1, "January");
        monthName.put(2, "February");
        monthName.put(3, "March");
        monthName.put(4, "April");
        monthName.put(5, "May");
        monthName.put(6, "June");
        monthName.put(7, "July");
        monthName.put(8, "August");
        monthName.put(9, "September");
        monthName.put(10, "October");
        monthName.put(11, "November");
        monthName.put(12, "December");
        totalSales();
        System.out.println();
        monthlySales();
        System.out.println();
        MPI();
        System.out.println();
        MRI();
        System.out.println();
        Stats();
    }
}
