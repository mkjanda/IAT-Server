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
import net.iatsoftware.iat.entities.TestResultFragment;
import net.iatsoftware.iat.entities.ResultSet;

import java.util.List;

public interface TestResultFragmentRepository extends GenericRepository<Long, TestResultFragment> {
    List<TestResultFragment> getResultFragments(ResultSet rs);
    void deleteResultFragments(ResultSet rs);
    List<TestResultFragment> get(AdminTimer timer);
    void setComplete(AdminTimer timer);
}
