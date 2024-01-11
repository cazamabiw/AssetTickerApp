import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class AssetTickerApp extends JFrame {

	private JPanel labelPanel;
    private MqttHandler mqttHandler;

    public AssetTickerApp() {
    	  setTitle("Asset Ticker");
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

          //Create a panel to hold the labels
          labelPanel = new JPanel();
          labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

          //Set up a scroll pane to handle scrolling if there are too many labels
          JScrollPane scrollPane = new JScrollPane(labelPanel);
          scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

          //Add the scroll pane to the frame
          add(scrollPane);

          setSize(400, 300);
          setLocationRelativeTo(null);

          mqttHandler = new MqttHandler();
          mqttHandler.initializeMqtt();

        //Schedule periodic updates using Timer
        int delay = 10000; // delay in milliseconds
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	updateAssetInformation();
            }
        });

        timer.start();
    }

    //Updates asset information and publishes data to MQTT
    private void updateAssetInformation() {
          String[] assetSymbols = {"bitcoin", "ethereum", "omisego"};    
          
          //Display UTC datetime label
          JLabel datetimeUTCLabel = new JLabel(getCurrentUTCDateTime());
          labelPanel.add(datetimeUTCLabel);

          for (String symbol : assetSymbols) {
        	
        	   String assetData = Asset.parseAssetData(getAssetDataFromAPI(symbol)).toString();
               JLabel newLabel = new JLabel(assetData);

               //Add the new label to the panel
               labelPanel.add(newLabel);
               
               //Refresh the panel to reflect the changes
               labelPanel.revalidate();
               labelPanel.repaint();


               //Publish the asset data to the MQTT topic
               mqttHandler.publishAssetData(assetData);
          }
          

       
    }

    //Helper method to fetch data from the API
    private static String getAssetDataFromAPI(String symbol) {
        try {
            String apiUrl = "https://api.coingecko.com/api/v3/simple/price?ids=" + symbol + "&vs_currencies=usd";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == 429) {
                //If rate limited, wait for some time before retrying
                System.out.println("Rate limited. Waiting before retrying...");
                Thread.sleep(10000);  //Add a delay 10seconds before retrying
                return getAssetDataFromAPI(symbol);  //Retry the request
            }
            
            //Read the response from the API
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
               return response.toString();
             
            }
        } catch (Exception e) {
        	 //Print the stack trace in case of an exception
        	 e.printStackTrace();
             return "error";
        }
    }
    //Helper method to get current UTC date and time
    private String getCurrentUTCDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return "UTC Time: "+dateFormat.format(new Date());
    } 
    
}
