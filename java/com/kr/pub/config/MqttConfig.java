package com.kr.pub.config;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.Header;

import com.kr.pub.service.MqttService;

@Configuration
@EnableIntegration
public class MqttConfig {
	/*
	 * 메시지 발행  
	 * mosquitto_pub -h www.chocomungco.store -p 1883 -t /public/ -u chocomungco -P choco11 -m "메시지"
	 * 메시지 구독 
	 * mosquitto_sub -h www.chocomungco.store -p 1883 -t /public/# -u chocomungco -P choco11
	 */
  private final String topic = "/public/";
  private static final String USERNAME = "chocomungco";
  private static final String PASSWORD = "choco11";
  private static final String MQTT_SUB_CLIENT_ID = MqttAsyncClient.generateClientId();	
  private static final String MQTT_PUB_CLIENT_ID = MqttAsyncClient.generateClientId();	
  @Bean
  public MqttConnectOptions getReceiverMqttConnectOptions() {
      MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
      mqttConnectOptions.setCleanSession(true);
      mqttConnectOptions.setConnectionTimeout(30);
      mqttConnectOptions.setKeepAliveInterval(60);
      mqttConnectOptions.setAutomaticReconnect(true);
      mqttConnectOptions.setUserName(USERNAME);
      mqttConnectOptions.setPassword(PASSWORD.toCharArray());

      String hostUrl = "tcp://www.chocomungco.store:1883";
      mqttConnectOptions.setServerURIs(new String[] { hostUrl });
     System.out.println("connect=>" + mqttConnectOptions);
      return mqttConnectOptions;
  }

  @Bean
  public MqttPahoClientFactory mqttClientFactory() {
      DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
      factory.setConnectionOptions(getReceiverMqttConnectOptions());
      System.out.println("connect=>" + factory);
      return factory;
  }

  @Bean
  public MessageProducer inbound() {
      //String clientId = "uuid-" + UUID.randomUUID().toString();
      MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(MQTT_SUB_CLIENT_ID,
              mqttClientFactory(), topic + "#");
      adapter.setCompletionTimeout(20000);
      adapter.setConverter(new DefaultPahoMessageConverter());
      adapter.setQos(1);
      adapter.setOutputChannel(mqttInputChannel());
      System.out.println("connect=>" + adapter);
      return adapter;
  }

  @Autowired
  MqttService mqttService;

  @Bean
  public MessageChannel mqttInputChannel() {
      return new DirectChannel();
  }

  @Bean
  @ServiceActivator(inputChannel = "mqttInputChannel")
  public MessageHandler handler() {
      return new MessageHandler() {

          @Override
          public void handleMessage(Message<?> message) throws MessagingException {
              MessageHeaders messageHeaders = message.getHeaders();
              String mqtt_receivedTopic = (String) messageHeaders.get("mqtt_receivedTopic");

              if (mqttService != null && StringUtils.startsWith(mqtt_receivedTopic, topic)) {
              try {
				mqttService.execute(mqtt_receivedTopic, (String) message.getPayload());
			} catch (Exception e) {
				e.printStackTrace();
			}
              }
          }
      };
  }

  @Bean
  @ServiceActivator(inputChannel = "mqttOutboundChannel")
  public MessageHandler mqttOutbound() {
      MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(MQTT_PUB_CLIENT_ID, mqttClientFactory());
      messageHandler.setAsync(true);
      messageHandler.setDefaultTopic("/public/");
      return messageHandler;
  }

  @Bean
  public MessageChannel mqttOutboundChannel() {
      return new DirectChannel();
  }

  @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
  public interface OutboundGateway {
      void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
  }    

}
