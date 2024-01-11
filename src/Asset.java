import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Asset {

    private String symbol;
    private double price;

    public Asset(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Symbol: " + symbol + ", Price: $" + price;
    }

	public static Asset parseAssetData(String jsonResponse) {
	    try {
	   
	        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();	    
	        String symbol = jsonObject.entrySet().iterator().next().getKey();
	        double price = jsonObject.getAsJsonObject(symbol).get("usd").getAsDouble();
	        return new Asset(symbol, price);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}