package aboutSelector_learn;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class NIOClient {

    private Selector selector;

    public void initClient(String ip, int port) throws Exception{
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        this.selector = Selector.open();
        channel.connect(new InetSocketAddress(ip, port));
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void listen() throws Exception{

        while (selector.select() > 0){
            //int selectedNum = ;
            //System.out.println("client select num: " + selectedNum);
            //System.out.println(11);
            Iterator iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                if (key.isConnectable()){
                    SocketChannel channel = (SocketChannel) key.channel();
                    if (channel.isConnectionPending()){
                        channel.finishConnect();
                    }
                    channel.configureBlocking(false);
                    channel.write(ByteBuffer.wrap("hello server".getBytes("utf-8")));
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()){
                    read(key);
                }
            }
        }
    }

    private void read(SelectionKey key) throws Exception{
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(128);
        channel.read(buffer);
        String msg = new String(buffer.array(), "utf-8").trim();
        System.out.println(NIOServer.DATE_FORMAT.format(Calendar.getInstance().getTime()) + " client receive from server:" + msg);
        Thread.sleep(5000);
        channel.write(ByteBuffer.wrap("hello, server".getBytes("utf-8")));
    }

    public static void main(String[] args) throws Exception{
        NIOClient client = new NIOClient();
        client.initClient("127.0.0.1", 8000);
        client.listen();
    }
}
