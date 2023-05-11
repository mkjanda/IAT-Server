/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.repositories;

/**
 *
 * @author Michael Janda
 */

import java.util.Collection;
import java.util.List;

import net.iatsoftware.iat.entities.AdminTimer;

public interface AdminTimerRepository extends GenericRepository<Long, AdminTimer> {
    boolean refreshTimer(Long adminTimerID);
    List<AdminTimer> getExpiredTimers();
    void deleteTimers(Collection<AdminTimer> timers);
    void setComplete(AdminTimer timer);
    List<AdminTimer> getCompleted();
}
