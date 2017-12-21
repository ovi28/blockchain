package blockchain;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static blockchain.Message.MESSAGE_TYPE.*;

public class ClientThread extends Thread {
    private Socket socket;
    private final Client client;

    ClientThread(final Client client, final Socket socket) {
        super(client.getName() + System.currentTimeMillis());
        this.client = client;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                final ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            Message message = new Message.MessageBuilder().withSender(client.getPort()).withType(READY).build();
            out.writeObject(message);
            Object fromClient;
            while ((fromClient = in.readObject()) != null) {
                if (fromClient instanceof Message) {
                    final Message msg = (Message) fromClient;
                    System.out.println(String.format("%d received: %s", client.getPort(), fromClient.toString()));
                    if (INFO_NEW_BLOCK == msg.type) {
                        if (msg.blocks.isEmpty() || msg.blocks.size() > 1) {
                            System.err.println("Invalid block received: " + msg.blocks);
                        }
                        synchronized (client) {
                            client.addBlock(msg.blocks.get(0));
                        }
                        break;
                    } else if (REQ_ALL_BLOCKS == msg.type) {
                        out.writeObject(new Message.MessageBuilder()
                                .withSender(client.getPort())
                                .withType(RSP_ALL_BLOCKS)
                                .withBlocks(client.getBlockchain())
                                .build());
                        break;
                    }
                }
            }
            socket.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}

