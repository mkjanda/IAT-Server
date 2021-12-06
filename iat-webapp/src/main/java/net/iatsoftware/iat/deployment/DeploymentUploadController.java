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

import net.iatsoftware.iat.config.IatConfigurationProperties;
import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.entities.TestResource;
import net.iatsoftware.iat.generated.FileManifestType;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;

@Controller
@RequestMapping("/DeploymentUpload")
public class DeploymentUploadController {

    private static final Logger critical = LogManager.getLogger("critical");
    private static final ConcurrentHashMap<Long, DeploymentSession> deployments = new ConcurrentHashMap<>();
    @Inject
    IATRepositoryManager iatRepositoryManager;
    @Inject
    IatConfigurationProperties serverConfiguration;
    @Inject
    ApplicationEventPublisher eventPublisher;

    @PostMapping(value = "", params = { "DeploymentID", "Key", "Size" })
    @ResponseBody
    public Callable<ResponseEntity<String>> receiveUpload(@RequestParam("DeploymentID") long deploymentID,
            @RequestParam("Key") String key, @RequestParam("Size") long size, @RequestBody byte[] data) {
        return () -> {
            DeploymentSession ds = iatRepositoryManager.getDeploymentSession(deploymentID);
            if (!ds.getDeploymentUploadKey().equals(key) && !ds.getItemSlideUploadKey().equals(key)) {
                critical.error("Deployment session mismatch");
                return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
            }
            FileManifestType mf;
            if (ds.getDeploymentUploadKey().equals(key))
                mf = FileManifestType.DEPLOYMENT_FILES;
            else 
                mf = FileManifestType.ITEM_SLIDES;
            deployments.put(ds.getId(), ds);
            var test = ds.getTest();
            int dataOffset = 0;
            try (var is = new ByteArrayInputStream(data)) {
                var files = iatRepositoryManager.getDeploymentFileManifest(deploymentID, mf);
                for (var f : files) {
                    byte[] resourceData = new byte[(int)f.getFileSize()];
                    System.arraycopy(data, dataOffset, resourceData, 0, (int)f.getFileSize());
                    dataOffset += f.getFileSize();
                    var resource = new TestResource(test, f.getFileName(), f.getMimeType(), resourceData);
                    iatRepositoryManager.storeTestResource(resource);
                }
            }
            catch (org.hibernate.exception.ConstraintViolationException ex) {
                return new ResponseEntity<>("failure", HttpStatus.OK);
            }
            return new ResponseEntity<>("success", HttpStatus.OK);
        };
    }
}
