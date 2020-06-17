package part1.lesson10.task01.server;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;
import java.util.*;

/**
 * Задание 1. Разработать приложение - многопользовательский чат, в котором участвует произвольное количество клиентов.
 * Каждый клиент после запуска отправляет свое имя серверу. После чего начинает отправлять ему сообщения.
 * Каждое сообщение сервер подписывает именем клиента и рассылает всем клиентам (broadcast).
 */
public class Server {

    public static final Integer SERVER_PORT = 4999; // Прослушиваемый порт
    public static final String SERVER_HOST = "127.0.0.2";
    private static DatagramSocket datagramSocket = null;

    public static void main(String[] args) throws IOException {

        datagramSocket = new DatagramSocket(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
        datagramSocket.setBroadcast(true);
        Map<SocketAddress, String> users = new HashMap<>();
        List<DatagramChannel> channels = new ArrayList<>();

        new Thread(() -> {
            while (true) {
                DatagramPacket datagramPacket = new DatagramPacket(new byte[10000], 10000);
                try {
                    datagramSocket.receive(datagramPacket);
                    System.out.println(datagramPacket.getSocketAddress());
                    System.out.println(new String(datagramPacket.getData()).trim());
                    users.put(datagramPacket.getSocketAddress(), Arrays.toString(datagramPacket.getData()));
                    channels.add(DatagramChannel.open().connect(datagramPacket.getSocketAddress()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            ByteBuffer byteBuffer = ByteBuffer.allocate(10000000);
            while (channels.size() == 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (true) {
                if (channels.size() == 0)
                    for (DatagramChannel channel : channels) {
                        try {
                            SocketAddress socketAddress = channel.receive(byteBuffer);
                            String user = users.get(socketAddress);
                            String message = user + ": " + Arrays.toString(byteBuffer.array());
                            for (Map.Entry<SocketAddress, String> entry : users.entrySet()) {
                                channel.send(ByteBuffer.wrap(message.getBytes()), entry.getKey());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }
        }).start();


//
//        serverSocket = new ServerSocket(SERVER_PORT,0, InetAddress.getByName("127.0.0.1"));
//        Socket socket = serverSocket.accept(); // Слушать!
//
//        InputStream fromClient = socket.getInputStream();
//        OutputStream toClient = socket.getOutputStream();
//        BufferedReader clientReader = new BufferedReader(new InputStreamReader(fromClient));
//        BufferedWriter clientWriter = new BufferedWriter(new OutputStreamWriter(toClient));
//
//        String message;
//        while ((message = clientReader.readLine()) != null) {
//            System.out.println("Server read: " + message);
//            clientWriter.write("\"" + message + "\" received \n");
//            clientWriter.flush();
//        }
//    }
//
//    List<InetAddress> listAllBroadcastAddresses() throws SocketException {
//        List<InetAddress> broadcastList = new ArrayList<>();
//        Enumeration<NetworkInterface> interfaces
//                = NetworkInterface.getNetworkInterfaces();
//        while (interfaces.hasMoreElements()) {
//            NetworkInterface networkInterface = interfaces.nextElement();
//
//            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
//                continue;
//            }
//
//            networkInterface.getInterfaceAddresses().stream()
//                    .map(a -> a.getBroadcast())
//                    .filter(Objects::nonNull)
//                    .forEach(broadcastList::add);
//        }
//        return broadcastList;
    }
}
