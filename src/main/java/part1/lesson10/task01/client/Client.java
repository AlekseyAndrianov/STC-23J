package part1.lesson10.task01.client;

import part1.lesson10.task01.server.Server;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    public static final Integer CLIENT_PORT = 4990; // Прослушиваемый порт
    public static final String CLIENT_HOST = "127.0.0.3";
    static DatagramSocket datagramSocket = null;

    public static void main(String[] args) throws IOException {
        DatagramChannel channel = DatagramChannel.open().bind(new InetSocketAddress(CLIENT_HOST, CLIENT_PORT));
        datagramSocket = channel.socket();

        datagramSocket.setBroadcast(true);
        datagramSocket.connect(InetAddress.getByName(Server.SERVER_HOST), Server.SERVER_PORT);

        Scanner scanner = new Scanner(System.in);
        String userName = scanner.nextLine();
        broadcast(userName);

        new Thread(() -> {
            while (datagramSocket.isConnected()) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1000000);
                try {
                    channel.read(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(Arrays.toString(byteBuffer.array()));

            }
            datagramSocket.close();

        }).start();

        new Thread(() -> {
            String message;
            while (datagramSocket.isConnected() && !(message = scanner.nextLine()).isEmpty()) {
                try {
                    channel.write(ByteBuffer.wrap(message.getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            datagramSocket.close();

        }).start();
    }


    public static void broadcast(String broadcastMessage) throws IOException {
        byte[] buffer = broadcastMessage.getBytes();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        datagramSocket.send(packet);
    }
}
