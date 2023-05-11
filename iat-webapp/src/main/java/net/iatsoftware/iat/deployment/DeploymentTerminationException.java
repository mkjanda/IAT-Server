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
public class DeploymentTerminationException extends Exception {
    private static final long serialVersionUID = 1L;

    public DeploymentTerminationException(String message, Exception cause) {
        super(message, cause);
    }
}
