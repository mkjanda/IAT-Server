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

import net.iatsoftware.iat.entities.EncCodeLine;
import net.iatsoftware.iat.entities.TestSegment;
import net.iatsoftware.iat.generated.CodeType;

import java.util.List;

public interface EncCodeLineRepository extends GenericRepository<Long, EncCodeLine> {
    public List<EncCodeLine> getEncryptedLines(TestSegment testSegment, CodeType codeType);
    public List<EncCodeLine> getOrderedCodeLines(TestSegment testSegment);
}
