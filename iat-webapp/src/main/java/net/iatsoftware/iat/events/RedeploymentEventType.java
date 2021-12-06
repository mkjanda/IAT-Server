/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.events;

/**
 *
 * @author Michael Janda
 */
public enum RedeploymentEventType {
    BEGIN_BACKUP,
    RESTORE_BACKUP,
    CLEAR_BACKUP,
    REASSOCIATE_RESULTS,
    COPY_RSA_KEY
}
