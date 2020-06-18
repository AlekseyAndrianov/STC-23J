package part1.lesson10.task02.client;

import part1.lesson10.task02.server.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Client {

    public void startChat() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.bind(null);
        channel.connect(new InetSocketAddress(Server.SERVER_HOST, Server.SERVER_PORT));

        new Thread(() -> {
            while (true) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                try {
                    channel.receive(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byteBuffer.flip();
                System.out.println(new String(byteBuffer.array()));
            }
        }).start();

        new Thread(() -> {
            String message;
            Scanner scanner = new Scanner(System.in);
            while (!(message = scanner.nextLine()).isEmpty()) {
                try {
                    channel.write(ByteBuffer.wrap(message.getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

}
