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

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.TestSegment;

import java.util.List;

public interface TestSegmentRepository extends GenericRepository<Long, TestSegment> {
    String getHtmlBytes(Long testSegmentID);
    void rotateItems(IAT test);
    List<Long> getTestElems(IAT test);
    int getElementPositionInTest(IAT test, String elemName);
    List<String> getTestElemNames(IAT test);
    List<TestSegment> getTestSegments(IAT test);
    TestSegment getByTestAndElemName(IAT test, String elemName);
    int getInitialIatPositionInTest(IAT test);
    
}
