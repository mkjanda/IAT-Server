package net.iatsoftware.iat.resultretrieval;

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.services.PacketDataSource;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import java.util.ArrayList;
import java.util.List;

public class ItemSlidePacketDataSource implements PacketDataSource {

    private List<byte[]> slides;
    private byte[] currentSlide;
    private int byteCounter = 0;
    private int packetSize = 8192;
    private boolean halted = false;

    public ItemSlidePacketDataSource(IATRepositoryManager repositoryManager, IAT test) {
        slides = new ArrayList<byte[]>();
        slides.addAll(repositoryManager.getItemSlides(test));
        currentSlide = slides.remove(0);
    }

    public void halt() {
        halted = true;
    }
    
    public boolean isHalted() {
        return this.halted;
    }
    
    public byte[] getMoreData() {
        if (currentSlide == null)
            return null;
        byte[] buffer;    
        if (currentSlide.length - byteCounter <= packetSize) {
            buffer = new byte[currentSlide.length - byteCounter];
            System.arraycopy(currentSlide, byteCounter, buffer, 0, buffer.length);
            byteCounter = 0;
            if (this.slides.size() > 0)
                currentSlide = slides.remove(0);
            else
                currentSlide = null;     
            return buffer;        
        } else {
            buffer = new byte[packetSize];
            System.arraycopy(currentSlide, byteCounter, buffer, 0, packetSize);
            byteCounter += packetSize;
            return buffer;
        }
    }
}
