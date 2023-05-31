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
import net.iatsoftware.iat.entities.AdminTimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Calendar;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Predicate;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class DefaultAdminTimerRepository extends GenericJpaRepository<Long, AdminTimer>
        implements AdminTimerRepository {

    private final long timeout = 1_500_000L;

    @Override
    public boolean refreshTimer(Long adminTimerID) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<AdminTimer> root = query.from(AdminTimer.class);
        Predicate pred = cb.equal(root.get("id"), adminTimerID);
        long count = this.entityManager.createQuery(query.select(cb.count(root)).where(pred)).getSingleResult();
        if (count == 0) {
            return false;
        }
        CriteriaUpdate<AdminTimer> update = cb.createCriteriaUpdate(AdminTimer.class);
        root = update.from(AdminTimer.class);
        pred = cb.equal(root.get("id"), adminTimerID);
        Calendar now = Calendar.getInstance();
        this.entityManager.createQuery(update.set(root.get("lastTick"), now).where(pred)).executeUpdate();
        return true;
    }

    @Override
    public List<AdminTimer> getExpiredTimers() {
        Calendar cutoff = Calendar.getInstance();
        cutoff.setTimeInMillis(System.currentTimeMillis() - timeout);
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<AdminTimer> timerQuery = cb.createQuery(AdminTimer.class);
        Root<AdminTimer> root = timerQuery.from(AdminTimer.class);
        Predicate pred = cb.lessThan(root.get("lastTick"), cutoff);
        return this.entityManager.createQuery(timerQuery.select(root).where(pred)).getResultList();
    }
    
    @Override
    public void deleteTimers(Collection<AdminTimer> timers) {
        if (timers.isEmpty())
            return;
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<AdminTimer> deletion = cb.createCriteriaDelete(AdminTimer.class);
        Root<AdminTimer> root = deletion.from(AdminTimer.class);
        Predicate pred = root.in(timers);
        this.entityManager.createQuery(deletion.where(pred)).executeUpdate();
    }

    @Override
    public void setComplete(AdminTimer timer) {
        timer.setComplete(Boolean.TRUE);
        update(timer);
    }

    @Override
    public List<AdminTimer> getCompleted() {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<AdminTimer> query = cb.createQuery(AdminTimer.class);
            Root<AdminTimer> root = query.from(AdminTimer.class);
            Predicate pred = cb.equal(root.get("complete"), true);
            return this.entityManager.createQuery(query.where(pred)).getResultList();
        } catch (Exception ex) {
            return new ArrayList<AdminTimer>();
        }
    }
}
