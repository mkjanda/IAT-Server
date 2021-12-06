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
/*
import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.messaging.XmlPacket;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.services.WebSocketService;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import org.springframework.web.multipart.commons.CommonsFileUploadSupport;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.RemoteEndpoint;

public class DeploymentUploadResolver extends CommonsMultipartResolver {
    
    @Inject 
    IATRepositoryManager repositoryManager;
    @Inject
    WebSocketService webSocketService;
    
    @Override
    protected CommonsFileUploadSupport.MultipartParsingResult parseRequest(HttpServletRequest request) {
        final String encoding = determineEncoding(request);
        FileUpload upload = prepareFileUpload(encoding);
        Long deploymentID = Long.parseLong(request.getParameter("DeploymentID"));
        DeploymentSession ds = repositoryManager.getDeploymentSession(deploymentID);
        final RemoteEndpoint.Async endpoint = webSocketService.getAsyncRemote(ds.getWebSocketId());
        upload.setProgressListener(new ProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, int pItems) {
                endpoint.sendObject(new XmlPacket(Long.toString(bytesRead)));
            }
        });
        
        CommonsFileUploadSupport.MultipartParsingResult result = null;
        try {
            result = parseFileItems(upload.parseRequest(new ServletRequestContext(request)), encoding);
        }
        catch (org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException ex) {
            throw new MultipartException("Could not parse multipart request", ex);
        }
        catch (org.apache.commons.fileupload.FileUploadException ex) {
            throw new MultipartException("Error parsing multipart request", ex);
        }
        return result;
    }
}
*/