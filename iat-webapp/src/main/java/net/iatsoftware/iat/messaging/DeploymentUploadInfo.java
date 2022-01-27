package net.iatsoftware.iat.messaging;

import net.iatsoftware.iat.generated.DeploymentUploadInfoPojo;

public class DeploymentUploadInfo extends DeploymentUploadInfoPojo implements java.io.Serializable {
    private static final long serialVersionUID = 1;

    public DeploymentUploadInfo() {}

    public DeploymentUploadInfo(long ds) {
        this.deploymentId = ds;
    }
}