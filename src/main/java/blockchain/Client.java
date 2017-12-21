package blockchain;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static blockchain.Message.MESSAGE_TYPE.*;

public class Client {

    private String NAME = "Client 1";
    private String ADDRESS = "localhost";
    private int PORT = 3001;
    private List<Integer> ports = new ArrayList<>();
    private List<Block> blockchain = new ArrayList<>();
    private static final Block root = new Block(0, "ROOT_HASH", "ROOT");

    private ServerSocket serverSocket;
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);

    private boolean listening = true;

    public Client() {
        blockchain.add(root);
        //ports.add(3001);
        ports.add(3002);
        ports.add(3003);
        ports.add(3004);
        startHost();
    }


    public String getName() {
        return NAME;
    }

    public String getAddress() {
        return ADDRESS;
    }

    public int getPort() {
        return PORT;
    }

    public List<Block> getBlockchain() {
        return blockchain;
    }

    public Block createBlock() {
        if (blockchain.isEmpty()) {
            return null;
        }

        Block previousBlock = getLatestBlock();
        if (previousBlock == null) {
            return null;
        }

        final int index = previousBlock.getIndex() + 1;
        final Block block = new Block(index, previousBlock.getHash(), NAME);
        System.out.println(String.format("%s created new block %s", NAME, block.toString()));
        broadcast(INFO_NEW_BLOCK, block);
        blockchain.add(block);
        return block;
    }

    void addBlock(Block block) {
        System.out.println("Is block valid");
        if (isBlockValid(block)) {
            blockchain.add(block);
        }
    }

    void startHost() {
        executor.execute(() -> {
            try {
                serverSocket = new ServerSocket(PORT);
                System.out.println(String.format("Server %s started", serverSocket.getLocalPort()));
                listening = true;
                while (listening) {
                    final ClientThread thread = new ClientThread(Client.this, serverSocket.accept());
                    thread.start();
                }
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Could not listen to port " + PORT);
            }
        });
        broadcast(REQ_ALL_BLOCKS, null);
    }

    void stopHost() {
        listening = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Block getLatestBlock() {
        if (blockchain.isEmpty()) {
            return null;
        }
        return blockchain.get(blockchain.size() - 1);
    }

    private boolean isBlockValid(final Block block) {
        final Block latestBlock = getLatestBlock();
        if (latestBlock == null) {
            return false;
        }
        final int expected = latestBlock.getIndex() + 1;
        if (block.getIndex() != expected) {
            System.out.println(String.format("Invalid index. Expected: %s Actual: %s", expected, block.getIndex()));
            return false;
        }
        if (!Objects.equals(block.getPreviousHash(), latestBlock.getHash())) {
            System.out.println("Unmatched hash code");
            return false;
        }
        return true;
    }

    private void broadcast(Message.MESSAGE_TYPE type, final Block block) {
        for (Integer port : ports) {
            sendMessage(type, ADDRESS, port, block);
        }

    }

    private void sendMessage(Message.MESSAGE_TYPE type, String host, int port, Block... blocks) {
        try (
                final Socket peer = new Socket(host, port);
                final ObjectOutputStream out = new ObjectOutputStream(peer.getOutputStream());
                final ObjectInputStream in = new ObjectInputStream(peer.getInputStream())) {
            Object fromPeer;
            while ((fromPeer = in.readObject()) != null) {
                if (fromPeer instanceof Message) {
                    final Message msg = (Message) fromPeer;
                    System.out.println(String.format("%d received: %s", this.PORT, msg.toString()));
                    if (READY == msg.type) {
                        out.writeObject(new Message.MessageBuilder()
                                .withType(type)
                                .withReceiver(port)
                                .withSender(this.PORT)
                                .withBlocks(Arrays.asList(blocks)).build());
                    } else if (RSP_ALL_BLOCKS == msg.type) {
                        if (!msg.blocks.isEmpty() && this.blockchain.size() == 1) {
                            blockchain = new ArrayList<>(msg.blocks);
                        }
                        break;
                    }
                }
            }
        } catch (UnknownHostException e) {
            System.err.println(String.format("Unknown host %s %d", host, port));
        } catch (IOException e) {
            System.err.println(String.format("%s couldn't get I/O for the connection to %s. Retrying...%n", getPort(), port));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
