import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Technician {

    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("Technician");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("Enter technician name: ");
        String TECHNICIAN_KEY = br.readLine();

        System.out.println("Enter specializations: ");

        String input = br.readLine();
        String[] parts = input.split(" ", 2);


        // check valid operations
        try {
            Operations.fromString(parts[0]);
            Operations.fromString(parts[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal operation type");
            return;
        }

        String EXCHANGE_NAME = "technician_exchange";

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        // bind to specialization queue
        for (String specialization : parts) {
            String QUEUE_NAME = specialization + "_queue";
            String KEY = specialization;

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, KEY);
        }

        // Communicate with Admin
        String QUEUE_NAME = channel.queueDeclare().getQueue();
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, TECHNICIAN_KEY);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                String[] parts = message.split(" ");
                String DOCTOR_KEY = parts[0];
                int timeToSleep = 3;

                if (DOCTOR_KEY.equals("Admin")) {
                    System.out.println("Received: " + message);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    return;
                }

                if (parts[2].equals("elbow")){
                    timeToSleep += 10;
                }

                try {
                    Thread.sleep(timeToSleep * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
                channel.basicQos(1);
                System.out.println("Received: " + message);


                // send doctor and admin a response
                String DOCTOR_EXCHANGE_NAME = "doctor_exchange";
                channel.exchangeDeclare(DOCTOR_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

                String response = TECHNICIAN_KEY + " -> " + parts[3] + " " + parts[2] + " done";
                channel.basicPublish(DOCTOR_EXCHANGE_NAME, DOCTOR_KEY, null, response.getBytes("UTF-8"));
            }
        };

        // start listening
        System.out.println("Waiting for messages...");

        for (String specialization : parts) {
            String QUEUE = specialization + "_queue";
            channel.basicConsume(QUEUE, false, consumer);
        }
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}