/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.config;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.admin.AjaxTextResult;
import net.iatsoftware.iat.admin.CodeResult;
import net.iatsoftware.iat.admin.IATResultRecorder;
import net.iatsoftware.iat.admin.SurveyResultRecorder;
import net.iatsoftware.iat.deployment.ItemSlideRecorder;
import net.iatsoftware.iat.deployment.JSKeys;
import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.JSKey;
import net.iatsoftware.iat.entities.TestSegment;
import net.iatsoftware.iat.generated.PacketType;
import net.iatsoftware.iat.messaging.IATReport;
import net.iatsoftware.iat.messaging.ServerReport;
import net.iatsoftware.iat.resultdata.ResultSetDescriptor;
import net.iatsoftware.iat.resultretrieval.PacketQueue;
import net.iatsoftware.iat.services.PacketDataSource;
import net.iatsoftware.iat.services.OutboundWebsocketTransmission;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import javax.inject.Inject;

@Service
public class DefaultIATServerBeanFactory implements MyBeanFactory {
    
    @Inject ApplicationContext ctx;
    @Override
    public PacketDataSource deploymentPacketDataSource(Long deploymentID, PacketType packetType) {
        return (PacketDataSource)ctx.getBean("DeploymentPacketDataSource", deploymentID, packetType);
    }
    
    @Override
    public ItemSlideRecorder itemSlideRecorder(PacketDataSource dataSource, IAT test, DeploymentSession ds) {
        return (ItemSlideRecorder)ctx.getBean("DefaultItemSlideRecorder", dataSource, test, ds);
    }
    
    @Override
    public IATResultRecorder IATResultRecorder(TestSegment testSegment, Long adminID, int numItems, Map<String, String> responseData, boolean complete) {
        return (IATResultRecorder)ctx.getBean("DefaultIATResultRecorder", testSegment, adminID, numItems, responseData, complete);
    }
    
    @Override
    public SurveyResultRecorder surveyResultRecorder(TestSegment testSegment, long adminID, int numItems, Map<String, String> responseData, boolean complete) {
        return (SurveyResultRecorder)ctx.getBean("DefaultSurveyResultRecorder", testSegment, adminID, numItems, responseData, complete);
    }
    
    @Override
    public PacketDataSource itemSlideDataSource(IAT test) {
        return (PacketDataSource)ctx.getBean("ItemSlideDataSource", test);
    }

    @Override
    public IATReport IATReport() {
        return ctx.getBean(IATReport.class);
    }
    
    @Override
    public ServerReport serverReport() {
        return ctx.getBean(ServerReport.class);
    }
    
    @Override
    public CodeResult codeResult(Long testSegmentID) {
        return ctx.getBean(CodeResult.class, testSegmentID);
    }
    
    @Override
    public AjaxTextResult ajaxTextResult(Long segmentID) {
        return ctx.getBean(AjaxTextResult.class, segmentID);
    }
    
    @Override
    public PacketQueue packetQueue(PacketType packetType, boolean closeOnComplete) {
        return (PacketQueue)ctx.getBean("DefaultPacketQueue", packetType, closeOnComplete);
    }
    
    @Override
    public ResultSetDescriptor resultSetDescriptor() {
        return ctx.getBean(ResultSetDescriptor.class);
    }

    @Override
    public OutboundWebsocketTransmission outboundWebsocketTransmission(WebSocketSession sess) {
        return (OutboundWebsocketTransmission)ctx.getBean("OutboundWebsocketTransmission", sess);
    }

    public JSKeys jsKeys(JSKeys keySet) {
        var ks =  (JSKeys)ctx.getBean(JSKeys.class);
        for (JSKey k : keySet.getKeys())
            ks.addKey(k);
        return ks;
    }

    public JSKeys jsKeys() {
        return (JSKeys)ctx.getBean(JSKeys.class);
    }

    public JSKey jsKey() {
        return (JSKey)ctx.getBean(JSKey.class);
    }
}
