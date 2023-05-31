/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.deployment;

/**
 *
 * @author Michael Janda
 */
import net.iatsoftware.iat.events.WebSocketDataSent;
import net.iatsoftware.iat.events.WebSocketFinalDataSent;
import net.iatsoftware.iat.generated.DeploymentStage;
import net.iatsoftware.iat.messaging.ServerExceptionMessage;
import net.iatsoftware.iat.messaging.Envelope;

import org.springframework.context.ApplicationEventPublisher;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "DeploymentProgress")
@XmlAccessorType(XmlAccessType.NONE)
public class DeploymentProgress extends net.iatsoftware.iat.generated.DeploymentProgressPojo {

    private String sessionId = null;

    private ApplicationEventPublisher publisher = null;

    public DeploymentProgress() {
        this.activeElement = " ";
        sessionId = null;
    }

    public DeploymentProgress(String sessionId, ApplicationEventPublisher publisher) {
        this.activeElement = " ";
        this.sessionId = sessionId;
        this.stage = DeploymentStage.UNSET;
        this.publisher = publisher;
    }

    public void setSuccess() {
        super.setStage(DeploymentStage.SUCCESS);
        if (this.sessionId != null)
            this.publisher.publishEvent(new WebSocketFinalDataSent(this.sessionId, new Envelope(this.makeClone())));
    }

    @Override
    public void setStage(DeploymentStage ds) {
        super.setStage(ds);
        this.activeElement = " ";
        this.progressMin = 0;
        this.progressMax = 0;
        this.progressVal = 0;
        this.lastUpdate = false;
        if (sessionId != null) {
            this.publisher.publishEvent(new WebSocketDataSent(this.sessionId, new Envelope(this.makeClone())));
        }
    }

    public void setStage(DeploymentStage ds, int progressMin, int progressMax) {
        super.setStage(ds);
        this.activeElement = " ";
        this.progressMin = progressMin;
        this.progressMax = progressMax;
        this.progressVal = progressMin;
        this.lastUpdate = false;
        if (sessionId != null) {
            this.publisher.publishEvent(new WebSocketDataSent(this.sessionId, new Envelope(this.makeClone())));
        }
    }

    public void setStage(DeploymentStage ds, String activeElement) {
        super.setStage(ds);
        this.activeElement = activeElement;
        this.progressMin = 0;
        this.progressMax = 0;
        this.progressVal = 0;
        this.lastUpdate = false;
        if (sessionId != null) {
            this.publisher.publishEvent(new WebSocketDataSent(this.sessionId, new Envelope(this.makeClone())));
        }
    }

    public void setStage(DeploymentStage ds, String activeElement, int progressMin, int progressMax) {
        super.setStage(ds);
        this.activeElement = activeElement;
        this.progressMin = progressMin;
        this.progressMax = progressMax;
        this.progressVal = progressMin;
        this.lastUpdate = false;
        if (sessionId != null) {
            this.publisher.publishEvent(new WebSocketDataSent(this.sessionId, new Envelope(this.makeClone())));
        }
    }

    @Override
    public String getActiveElement() {
        return this.activeElement;
    }

    @Override
    public void setActiveElement(String activeItem) {
        this.activeElement = activeItem;
        this.progressMin = 0;
        this.progressMax = 0;
        this.progressVal = 0;
        this.lastUpdate = false;
        if (sessionId != null) {
            this.publisher.publishEvent(new WebSocketDataSent(this.sessionId, new Envelope(this.makeClone())));
        }
    }

    public void incProgress() {
        this.progressVal++;
        if (sessionId != null) {
            this.publisher.publishEvent(new WebSocketDataSent(this.sessionId, new Envelope(this.makeClone())));
        }
    }

    public void setProgressRange(int min, int max) {
        this.progressMin = min;
        this.progressMax = max;
        this.progressVal = min;
        if (sessionId != null) {
            this.publisher.publishEvent(new WebSocketDataSent(this.sessionId, new Envelope(this.makeClone())));
        }
    }

    @Override
    public void setServerException(ServerExceptionMessage ex) {
        this.stage = DeploymentStage.FAILED;
        if (sessionId != null) {
            this.publisher.publishEvent(new WebSocketDataSent(this.sessionId, new Envelope(ex)));
        }
    }

    private DeploymentProgress makeClone() {
        DeploymentProgress copy = new DeploymentProgress();
        copy.activeElement = this.activeElement;
        copy.stage = this.stage;
        copy.progressMin = this.progressMin;
        copy.progressMax = this.progressMax;
        copy.progressVal = this.progressVal;
        copy.lastUpdate = this.lastUpdate;
        return copy;
    }
}
