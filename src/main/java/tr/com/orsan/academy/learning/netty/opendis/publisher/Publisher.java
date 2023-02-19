package tr.com.orsan.academy.learning.netty.opendis.publisher;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tr.com.orsan.academy.learning.netty.opendis.receiver.IncomingPacketHandler;

import java.net.InetAddress;

public class Publisher {
    private static final Logger logger = LogManager.getLogger(Publisher.class);
    private final int port;

    public Publisher(int port) {
        this.port = port;

    }

    public static void main(String[] args) throws Exception {
        int port = 51444;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new Publisher(port).run();
    }

    public void run() throws Exception {
        int port = Integer.parseInt(args[1]);
        final NioEventLoopGroup group = new NioEventLoopGroup();
        final Bootstrap b = new Bootstrap();
        try {
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                        @Override
                        public void initChannel(final NioDatagramChannel ch) throws Exception {

                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new IncomingPacketHandler());
                        }
                    });

            // Bind and start to accept incoming connections.
            Integer pPort = port;
            InetAddress address  = InetAddress.getLocalHost();
            System.out.printf("waiting for message %s %s",String.format(pPort.toString()),String.format( address.toString()));

            ChannelFuture f = b.bind(address,port).sync(); // (5)
            // Wait until the connection is closed.
            f.channel().closeFuture().sync().await();


        } finally {
            System.out.print("In Server Finally");
            group.shutdownGracefully();

        }
    }
}
