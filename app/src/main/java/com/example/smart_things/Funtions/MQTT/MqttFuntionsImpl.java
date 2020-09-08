package com.example.smart_things.Funtions.MQTT;

import android.app.Notification;
import android.content.Context;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MqttFuntionsImpl implements MQTTinterface  {

    public MqttAndroidClient client;
    public IMqttToken token;
    String clientId = "";

    @Override
    public void connection(@NotNull Context context, @NotNull String ser, @NotNull String use, @NotNull String pass, @NotNull final MQTTcallback listener) {
        clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(context,ser,clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(use);
        options.setPassword(pass.toCharArray());
        try {
            token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    listener.AutheonSuccessful();
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    listener.AutheonFailure(exception.getMessage());
                }
            });
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean StateConnection() {
        boolean valided = false;
        if(client != null){
            if (client.isConnected()) {
                valided = true;
            }
        }
        return valided;
    }

    @Override
    public void publish(@NotNull String topic, @Nullable String Type, @Nullable String mensaje, @Nullable Boolean OnOff, @NotNull MQTTcallback listener){
        if (Type != null){
            if (Type.compareTo("Luz") == 0 ) {
                try {
                    if (OnOff) {
                        client.publish(topic, "ON".getBytes(), 0, false);
                    } else {
                        client.publish(topic, "OFF".getBytes(), 0, false);
                    }
                    listener.AutheonSuccessful();
                } catch (Exception e) {
                    listener.AutheonFailure(e.getMessage());
                }
            }
        }else if (Type == null){
            try{
                if(OnOff){
                    client.publish(topic, "ON".getBytes(), 0, false);
                }else{
                    client.publish(topic, "OFF".getBytes(), 0, false);
                }
                listener.AutheonSuccessful();
            }catch (Exception e) {
                listener.AutheonFailure(e.getMessage());
            }
        }else if (Type == "join"){
            try {
                client.publish(topic, "all".getBytes(), 0, false);
            }catch (Exception e) {
                listener.AutheonFailure(e.getMessage());
            }
        }
    }

    @Override
    public void Disconnec(@NotNull final MQTTcallback listener) {
        try {
            IMqttToken disconToken = client.disconnect();
            disconToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    listener.AutheonSuccessful();
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    listener.AutheonFailure(exception.getMessage());
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            listener.AutheonFailure(e.getMessage());
        }
    }

    @Override
    public void subscribe(@NotNull String topic, @NotNull final MQTTcallback listener) {
        try {
            IMqttToken subToken = client.subscribe(topic, 1);
            subToken.setActionCallback(new IMqttActionListener(){
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    listener.AutheonSuccessful();
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    listener.AutheonFailure(exception.getMessage());
                }
            });
        }catch (MqttException e){
            e.printStackTrace();
            listener.AutheonFailure(e.getMessage());
        }
    }

    @Override
    public void callback(@NotNull MqttCallback listener) {
        client.setCallback(listener);
    }
}
