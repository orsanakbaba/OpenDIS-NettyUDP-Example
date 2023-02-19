package tr.com.orsan.academy.learning.netty.opendis.receiver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetAddress;

public class IncomingPacketHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    public IncomingPacketHandler( ){

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) throws Exception {
        final InetAddress srcAddr = packet.sender().getAddress();
        final ByteBuf buf = packet.content();
        final int rcvPktLength = buf.readableBytes();
        final byte[] rcvPktBuf = new byte[rcvPktLength];
        buf.readBytes(rcvPktBuf);
        System.out.println("Inside incomming packet handler");

        //rcvPktProcessing(rcvPktBuf, rcvPktLength, srcAddr);
    }

}

