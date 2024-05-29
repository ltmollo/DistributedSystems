import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Doctor {

    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("Doctor");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter doctor name: ");
        String DOCTOR_KEY = br.readLine();

        String DOCTOR_EXCHANGE_NAME = "doctor_exchange";

        //doctor exchange
        channel.exchangeDeclare(DOCTOR_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String QUEUE_NAME = channel.queueDeclare().getQueue();
        channel.queueBind(QUEUE_NAME, DOCTOR_EXCHANGE_NAME, DOCTOR_KEY);

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

            System.out.println("Enter operation and patient last name: ");
            String input = br.readLine();

            String[] parts = input.split(" ", 2);

            if (parts.length < 2) {
                System.out.println("Invalid input");
                continue;
            }


            // operation and name
            String key = parts[0];
            String patientName = parts[1];

            String message = DOCTOR_KEY + " -> " + key + " " + patientName;

            Operations operations;
            String EXCHANGE_NAME;

            switch (key) {
                case "hip" -> operations = Operations.HIP;
                case "knee" -> operations = Operations.KNEE;
                case "elbow" -> operations = Operations.ELBOW;
                default -> operations = null;
            }

            if (operations == null) {
                System.out.println("Invalid operation");
                continue;
            }

            // to send messages
            EXCHANGE_NAME = "technician_exchange";
            String ADMIN_EXCHANGE_NAME = "admin_exchange";
            channel.exchangeDeclare(ADMIN_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            // publish
            channel.basicPublish(EXCHANGE_NAME, key, null, message.getBytes("UTF-8"));
            channel.basicPublish(ADMIN_EXCHANGE_NAME, "admin", null, message.getBytes("UTF-8"));
        }
    }
}