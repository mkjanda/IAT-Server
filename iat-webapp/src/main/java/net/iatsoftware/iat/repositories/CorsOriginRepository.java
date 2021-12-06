/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.repositories;

/**
 *
 * @author michael
 */

import net.iatsoftware.iat.entities.Client;
import net.iatsoftware.iat.entities.CorsOrigin;

import java.util.List;

public interface CorsOriginRepository extends GenericRepository<Long, CorsOrigin> {
    public List<Client> getClientsWithCors();
    public List<CorsOrigin> getCorsOriginsForClient(Client c);
    public long getNumClientOrigins(Client c);
}
