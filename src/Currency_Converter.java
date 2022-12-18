import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

public class Currency_Converter {
    public static void main(String[] args) throws IOException {

        boolean running = true;
        do {
            HashMap<Integer, String> currencyCodes = new HashMap<>();

            currencyCodes.put(1, "USD");
            currencyCodes.put(2, "CAD");
            currencyCodes.put(3, "EUR");
            currencyCodes.put(4, "HKD");
            currencyCodes.put(5, "INR");

            int from, to;
            String fromCode, toCode;
            double amount;

            Scanner sc = new Scanner(System.in);

            System.out.println("Welcome To The Currency Converter!");

            System.out.println("Currency Converting From?");
            System.out.println("1:USD (US Dollar)\t 2:CAD (Canadian Dollar)\t 3:EUR (EURO)\t4:HKD (Hong Kong Dollar)\t 5:INR (Indian Rupee)");
//        fromCode = currencyCodes.get(sc.nextInt());
            from = sc.nextInt();
            while (from < 1 || from > 5) {
                System.out.println("Please select a valid currency (1-5)");
                System.out.println("1:USD (US Dollar)\t 2:CAD (Canadian Dollar)\t 3:EUR (EURO)\t4:HKD (Hong Kong Dollar)\t 5:INR (Indian Rupee)");
                from = sc.nextInt();
            }
            fromCode = currencyCodes.get(from);


            System.out.println("Currency Converting To?");
            System.out.println("1:USD (US Dollar)\t 2:CAD (Canadian Dollar)\t 3:EUR (EURO)\t4:HKD (Hong Kong Dollar)\t 5:INR (Indian Rupee)");
//        toCode = currencyCodes.get(sc.nextInt());
            to = sc.nextInt();
            while (to < 1 || to > 5) {
                System.out.println("Please select a valid currency (1-5)");
                System.out.println("1:USD (US Dollar)\t 2:CAD (Canadian Dollar)\t 3:EUR (EURO)\t4:HKD (Hong Kong Dollar)\t 5:INR (Indian Rupee)");
                to = sc.nextInt();
            }
            toCode = currencyCodes.get(to);


            System.out.println("Amount You Wish To Convert?");
            amount = sc.nextFloat();

            sendRequest(fromCode, toCode, amount);

            System.out.println("Would you like to make another conversion?");
            System.out.println("1: Yes \t Any other Integer: No");
            if(sc.nextInt() != 1){
                running = false;
            }

        }while(running);

        System.out.println("Thanks For Using The Currency Converter!!");
    }


    private static void sendRequest(String fromCode, String toCode, double amount) throws IOException {

        DecimalFormat f = new DecimalFormat("00.00");

        //API
        String GET_URL = "https://api.apilayer.com/exchangerates_data/latest?base=" +toCode+ "&symbols=" +fromCode+ "&apikey=TyjOWeeii1sKoDr5eOdDUXpcNTKrjMrl";
        //For This API,  API key can expired any time so please visit API Layer web site and use new API key
        URL url = new URL(GET_URL);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK){   //Success      code = 200
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine=in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();

            //This data stored in JSON Format

            //Here I have include JSON jar file

            JSONObject obj = new JSONObject(response.toString());
            Double exchangeRate = obj.getJSONObject("rates").getDouble(fromCode);
            System.out.println(obj.getJSONObject("rates"));
            System.out.println(exchangeRate);    //For debugging
            System.out.println();

            System.out.println(f.format(amount) + fromCode + " = " + f.format(amount/exchangeRate) + toCode);
            System.out.println();

        }
        else{
            System.out.println();
            System.out.println("GET request failed!!");
        }

    }
}

