package utils;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import pages.common.Globals;

import java.util.List;

public class PacketCapturer {
    private PcapDumper dumper;
    private PcapHandle handle;

    public PacketCapturer(String filePath) throws PcapNativeException, NotOpenException {
        //1.Create a handle which is used to get the packet
        int snaplen = 64*1024;//Capture all packets, no trucation
        int timeout = 10*1000;//10 seconds in mills
        handle = findNetworkDevice().openLive(snaplen, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS,timeout);
        dumper = handle.dumpOpen(filePath);
    }
    private PcapNetworkInterface findNetworkDevice() throws PcapNativeException{
        List<PcapNetworkInterface> devices = Pcaps.findAllDevs();
        for(PcapNetworkInterface device: devices) {
            System.out.println(device.getAddresses().get(1).getAddress().toString());
            if(device.getAddresses().get(1).getAddress().toString().contains(Globals.params.get("ipv6"))) {
                return device;
            }
        }
        return null;
    }

    public void startCapture() throws PcapNativeException, NotOpenException, InterruptedException{
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    captureLoop();
                } catch (PcapNativeException | InterruptedException | NotOpenException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void captureLoop() throws PcapNativeException, InterruptedException, NotOpenException {
        handle.loop(-1, new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {
                try {
                    dumper.dump(packet);
                }catch(NotOpenException e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }

    public void stopCapture() throws NotOpenException{
        handle.breakLoop();
    }
}
