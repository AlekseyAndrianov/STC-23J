package part1.lesson10.task01.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * Задание 1. Разработать приложение - многопользовательский чат, в котором участвует произвольное количество клиентов.
 * Каждый клиент после запуска отправляет свое имя серверу. После чего начинает отправлять ему сообщения.
 * Каждое сообщение сервер подписывает именем клиента и рассылает всем клиентам (broadcast).
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
                users.put(socketAddress, receivedMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
