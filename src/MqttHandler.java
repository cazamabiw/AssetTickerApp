import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttHandler {

    private MqttClient mqttClient;

    public void initializeMqtt() {
        try {
        
        	 //Configure MQTT
        	 String broker = "tcp://yourbroker"; 
             String clientId = "AssetTickerClient";
             String username = "yourusername"; 
             String password = "yourpassword"; 

             MqttConnectOptions options = new MqttConnectOptions();
             options.setUserName(username);
             options.setPassword(password.toCharArray());

             mqttClient = new MqttClient(broker, clientId);
             mqttClient.connect(options);

         
        } catch (MqttException e) {
        	//Print the stack trace in case of an exception
            e.printStackTrace();
        }
    }

    public void publishAssetData(String assetData) {
        try {
            //Publish the asset data to the MQTT topic
            mqttClient.publish("asset/ticker", new MqttMessage(assetData.getBytes()));
        } catch (MqttException e) {
        	//Print the stack trace in case of an exception
            e.printStackTrace();
        }
    }
}
