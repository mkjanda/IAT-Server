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

import net.iatsoftware.iat.admin.AdminVersion;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.net.URI;

@ConfigurationProperties(prefix = "iat.webapp")
public class IatConfigurationProperties {
    private String aesCoreFileUri, iatFilesUri, iatDeploymentUri, itemSlidesUri, clientSoftwareUri,
            clientSoftwareUpdatesUri, resultDataUri;
    private AdminVersion adminVersion;
    private int dataFormatVersion;
    private boolean oauthHttpsEnabled;
    private String path;

    @ConstructorBinding
    public IatConfigurationProperties(String aesCoreFilename, String iatFiles, String iatDeployment,
            String itemSlideDirectory, String clientSoftware, String clientSoftwareUpdates, boolean oauthHttpsEnabled,
            String adminVersion, int dataFormatVersion, String resultData, String path) {
        this.iatFilesUri = iatFiles;
        this.iatDeploymentUri = iatDeployment;
        this.itemSlidesUri = itemSlideDirectory;
        this.clientSoftwareUri = clientSoftware;
        this.clientSoftwareUpdatesUri = clientSoftwareUpdates;
        this.resultDataUri = resultData;
        this.adminVersion = new AdminVersion(adminVersion);
        this.dataFormatVersion = dataFormatVersion;
        this.oauthHttpsEnabled = oauthHttpsEnabled;
        this.path = path;
    }

    public URI getAesCoreUri() throws java.net.URISyntaxException {
        return new URI(aesCoreFileUri);
    }

    public URI getIatFilesUri() throws java.net.URISyntaxException {
        return new URI(this.iatFilesUri);
    }

    public URI getIatDeploymentUri() throws java.net.URISyntaxException {
        return new URI(this.iatDeploymentUri);
    }

    public URI getClientSoftwareUri() throws java.net.URISyntaxException {
        return new URI(clientSoftwareUri);
    }

    public URI getClientSoftwareUpdatesUri() throws java.net.URISyntaxException {
        return new URI(clientSoftwareUpdatesUri);
    }

    public int getDataFormat() {
        return dataFormatVersion;
    }

    public AdminVersion getAdminVersion() {
        return adminVersion;
    }

    public boolean isOauthHttpsEnabled() {
        return oauthHttpsEnabled;
    }
/*
    public URI getIatFileUri(long clientId, String testName, String filename) throws java.net.URISyntaxException {
        return new URI(iatFilesUri + "/" + Long.toString(clientId) + "/" + testName + "/" + filename);
    }

    public URI getIatDirectoryUri(long clientId, String testName) throws java.net.URISyntaxException {
        return new URI(iatFilesUri + "/" + Long.toString(clientId) + "/" + testName);
    }

    public URI getDeploymentFileUri(String uploadKey, long deploymentSessionId) throws java.net.URISyntaxException {
        return new URI(iatDeploymentUri + "/" + uploadKey + "." + Long.toString(deploymentSessionId));
    }

    public URI getClientDirectoryUri(long clientId) throws java.net.URISyntaxException {
        return new URI(iatFilesUri + "/" + Long.toString(clientId));
    }
*/
    public URI getItemSlideFileUri(String prefix, Long clientId, String testName) throws java.net.URISyntaxException {
        return new URI(itemSlidesUri + "/" + prefix + "." + clientId.toString() + "." + testName + ".slides");
    }

    public URI getResultFileUri(String testName, long clientId) throws java.net.URISyntaxException {
        return new URI(resultDataUri + "/" + testName + "-" + Long.toString(clientId));
    }
/*
    public String getTestResourcesPath() {
        return this.testResourcesPath;
    }
*/
    public String getPath() {
        return this.path;
    }
    //
    // public URI getResultDataUri() {
    // return this.resultDataUri;
    // }
}