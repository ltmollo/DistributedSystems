import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Admin {

    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("Admin");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        String DOCTOR_EXCHANGE_NAME = "doctor_exchange";
        String TECHNICIAN_EXCHANGE_NAME = "technician_exchange";
        String key = "#";


        // admin listens to exchanges
        channel.exchangeDeclare(DOCTOR_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare(TECHNICIAN_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String QUEUE_NAME = channel.queueDeclare().getQueue();

        channel.queueBind(QUEUE_NAME, DOCTOR_EXCHANGE_NAME, key);
        channel.queueBind(QUEUE_NAME, TECHNICIAN_EXCHANGE_NAME, key);


        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                channel.basicAck(envelope.getDeliveryTag(), false);
                channel.basicQos(1);
                System.out.println("Received: " + message);
            }
        };

        channel.basicConsume(QUEUE_NAME, false, consumer);

        while (true) {

            // write to doctor/technician
            System.out.println("Enter doctor or technician: ");
            String type = br.readLine();

            System.out.println("Enter name:");
            String name = br.readLine();

            System.out.println("Enter message:");
            String message = "Admin -> " + br.readLine();

            String EXCHANGE_NAME;
            if (type.equals("doctor")) {
                EXCHANGE_NAME = DOCTOR_EXCHANGE_NAME;
            } else if (type.equals("technician")) {
                EXCHANGE_NAME = TECHNICIAN_EXCHANGE_NAME;
            } else {
                System.out.println("Wrong type");
                continue;
            }

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            // publish
            channel.basicPublish(EXCHANGE_NAME, name, null, message.getBytes("UTF-8"));
            System.out.println("Sent: " + message);
        }
    }
}