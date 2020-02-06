import java.text.DecimalFormat;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

class Shopping{
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        Scanner sc = new Scanner(file);
        PrintWriter printWriter = new PrintWriter(args[1]);
        double sales_tax = 0.0;
        double total_amount = 0.0;
        DecimalFormat f = new DecimalFormat("#0.00");
        while(sc.hasNextLine()){
            String in = sc.nextLine();
            Item obj = new Item();
            obj.isExempted = Exemption(in);
            obj.isImported = in.contains("import");
            obj.price = calcPrice(in);
            obj.tax = calcTax(obj.price, obj.isImported, obj.isExempted);
            sales_tax+=obj.tax;
            obj.amount = Math.round((obj.price+obj.tax)*100.0)/100.0;
            total_amount+=obj.amount;
            String output = billDisplay(in)+": "+f.format(obj.amount);
            printWriter.println(output);
            System.out.println(output);
        }
        sales_tax = Math.round(sales_tax*20.0)/20.0;
        String output = "Sales Taxes: "+f.format(sales_tax)+"\n"+"Total: "+f.format(total_amount);
        printWriter.println(output);
        System.out.println("Sales Taxes: "+f.format(sales_tax));
        System.out.println("Total: "+f.format(total_amount));
        sc.close();
        printWriter.close();
    }   

    private static boolean Exemption(String order){
        if(order.contains("pill")||order.contains("syrup")||order.contains("book")||order.contains("chocolate")||order.contains("biscuit")){
            return true;
        }
        return false;
    }

    private static double calcTax(double val, boolean isImported, boolean isExempted){
        double tax=0.00;
        if(!isExempted)
            tax = 0.10*val;
        if(isImported)
            tax = tax+(0.05*val);
        return tax;   
    }

    private static double calcPrice(String order){
        int at_pos = order.lastIndexOf(" at ");
        int blank_pos = order.indexOf(' ');
        int quantity = Integer.valueOf(order.substring(0, blank_pos));
        double price = Double.valueOf(order.substring(at_pos+4));
        return quantity*price;
    }

    private static String billDisplay(String order){
        int at_pos = order.lastIndexOf(" at ");
        return order.substring(0,at_pos);
    }
}

class Item{
    double price;
    double tax;
    double amount;
    boolean isImported;
    boolean isExempted;
}