package part1.lesson10.task02.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * Задание 2.  Усовершенствовать задание 1: *
 * a. добавить возможность отправки личных сообщений (unicast).
 * b. добавить возможность выхода из чата с помощью написанной в чате команды «quit»
 */
public class Server {

    public static final Integer SERVER_PORT = 4999;
    public static final String SERVER_HOST = "127.0.0.2";
    static Map<SocketAddress, String> users = new HashMap<>();


    public static void main(String[] args) throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.setOption(StandardSocketOptions.SO_BROADCAST, true);
        SocketAddress address = new InetSocketAddress(SERVER_HOST, SERVER_PORT);
        channel.bind(address);

        while (true) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            try {
                SocketAddress socketAddress = channel.receive(byteBuffer);
                byteBuffer.flip();
                String receivedMessage = new String(byteBuffer.array()).trim();
                if (users.containsKey(socketAddress)) {
                    if ("quit".equals(receivedMessage)) {
                        users.remove(socketAddress);
                        continue;
                    } else if (receivedMessage.startsWith("@")) {
                        String userName = receivedMessage.split(" ")[0].substring(1);
                        String message = "From " + users.get(socketAddress) + ": " + receivedMessage.substring(userName.length() + 1);
                        ByteBuffer bbMessage = ByteBuffer.wrap(message.getBytes());
                        SocketAddress key = users.entrySet()
                                .stream()
                                .filter(entry -> userName.equals(entry.getValue()))
                                .map(Map.Entry::getKey)
                                .findFirst().get();
                        channel.send(bbMessage, key);
                        continue;
                    } else {
                        for (Map.Entry<SocketAddress, String> entry : users.entrySet()) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(users.get(socketAddress));
                            stringBuilder.append(": ");
                            stringBuilder.append(receivedMessage);
                            ByteBuffer bbMessage = ByteBuffer.wrap(stringBuilder.toString().getBytes());
                            try {
                                channel.send(bbMessage, entry.getKey());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        continue;
                    }
                }
                users.put(socketAddress, receivedMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
