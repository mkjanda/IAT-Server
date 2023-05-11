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

import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

public interface MyBeanFactory {
   PacketDataSource deploymentPacketDataSource(Long deploymentID, PacketType packetType);
   ItemSlideRecorder itemSlideRecorder(PacketDataSource dataSource, IAT test, DeploymentSession ds);
   IATResultRecorder IATResultRecorder(TestSegment testSegment, Long adminID, int numItems, Map<String, String> responseData, boolean complete);
   SurveyResultRecorder surveyResultRecorder(TestSegment testSegment, long adminID, int numItems, Map<String, String> responseData, boolean complete);
   PacketDataSource itemSlideDataSource(IAT test);
   IATReport IATReport();
   ServerReport serverReport();
   CodeResult codeResult(Long testSegmentID);
   AjaxTextResult ajaxTextResult(Long segmentID);
   PacketQueue packetQueue(PacketType packetType, boolean closeOnComplete);
   ResultSetDescriptor resultSetDescriptor();
   OutboundWebsocketTransmission outboundWebsocketTransmission(WebSocketSession sess);
   JSKeys jsKeys(JSKeys keySet);
   JSKeys jsKeys();
   JSKey jsKey();

}
