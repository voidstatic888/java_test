package aboutSelector_learn;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Selector selector;

    public void initServer(int port) throws Exception{
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(port));
        this.selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws Exception{
        System.out.println("start server");
        while (selector.select() > 0){
            //int selectedNum =;
            //System.out.println("server select num: " + selectedNum);
            Iterator ite = this.selector.selectedKeys().iterator();
            while (ite.hasNext()){
                SelectionKey key = (SelectionKey) ite.next();
                ite.remove();
                if (key.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();
                    channel.configureBlocking(false);
                    channel.write(ByteBuffer.wrap("hello client".getBytes("utf-8")));
                    channel.register(this.selector, SelectionKey.OP_READ);
                } else if (key.isReadable()){
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(128);
                    channel.read(buffer);
                    byte[] data = buffer.array();
                    String msg = new String(data).trim();
                    System.out.println(DATE_FORMAT.format(Calendar.getInstance().getTime()) + " server receive from client: " + msg);
                    channel.write(ByteBuffer.wrap("hello, client".getBytes("utf-8")));
                }
            }
        }
    }

    public static void main(String[] args) throws Exception{
        NIOServer server = new NIOServer();
        server.initServer(8000);
        server.listen();
    }
}
