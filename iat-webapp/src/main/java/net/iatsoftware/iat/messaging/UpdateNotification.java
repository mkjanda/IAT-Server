package net.iatsoftware.iat.messaging;

import java.util.ArrayList;
import java.util.Base64;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UpdateNotification")
@XmlAccessorType(XmlAccessType.NONE)
public class UpdateNotification extends net.iatsoftware.iat.generated.UpdateNotificationPojo {
    private static final Base64.Encoder b64Encoder = Base64.getEncoder();
    private boolean isBase64 = false;
    public UpdateNotification() {
        if (notification == null)
            notification = new ArrayList<>();
    }

    @Override
    public boolean doBeforeMarshal(Marshaller m) {
        if (isBase64)
            return true;
        isBase64 = true;
        this.notification.stream().forEach((n) -> {
            try {
                n.setVersion(n.getVersion());
                n.setFlags(n.getFlags());
                n.setValue(b64Encoder.encodeToString(n.getValue().getBytes("UTF-8")));
            } catch (java.io.UnsupportedEncodingException ex) {
                n.setValue("");
            }
        });
        return true;
    }
}
