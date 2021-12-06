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
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.iatsoftware.iat.repositories.IATRepositoryManager;

import org.springframework.context.annotation.Scope;
import org.springframework.oxm.Marshaller;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.xml.transform.stream.StreamResult;
import net.iatsoftware.iat.entities.EncCodeLine;
import net.iatsoftware.iat.entities.TestSegment;
import net.iatsoftware.iat.generated.CodeType;

@Component
@Scope(value = "prototype")
public class CodeFileEncryptor {

    @Inject
    JSAES aes;
    @Inject
    IATRepositoryManager iatRepositoryManager;
    @Inject
    Marshaller marshaller;

    private JSKeys keys;
    private Random random = new Random();

    public List<EncCodeLine> encrypt(TestSegment testSegment, CodeFile codeFile) {
        keys = new JSKeys();
        List<EncCodeLine> codeLines = new ArrayList<>();
        String[] cipher = keys.addKey();
        String[] origCipher = cipher;
        String[] encCode = aes.encryptTextToB64Segments(codeFile.getGlobalVarDecl(), cipher);
        Integer ordinal = 0;
        codeLines.add(new EncCodeLine(testSegment, encCode, "GLOBALVARS", codeFile.getGlobalVarDecl().length(), 0, CodeType.GLOBAL_DECLARATION, 0));
        if (codeFile.getGlobalCode().length() > 0) {
            encCode = aes.encryptTextToB64Segments(codeFile.getGlobalCode(), cipher);
            codeLines.add(new EncCodeLine(testSegment, encCode, "GLOBALCODE", codeFile.getGlobalCode().length(), 0, CodeType.GLOBAL_CODE, 0));
        }
        int keySetNum = 1;
        List<ProcessedCode> procCode = codeFile.getProcessedCode();
        for (ProcessedCode pc : procCode) {
            cipher = keys.addKey();
            List<EncCodeLine> procCodeLines = new ArrayList<>();
            String[] procEncCode = aes.encryptTextToB64Segments(pc.getConstructor(), cipher);
            procCodeLines.add(new EncCodeLine(testSegment, procEncCode, "CON(" + pc.getEntityName() + ")", pc.getConstructor().length(), keySetNum, CodeType.CONSTRUCTOR, ++ordinal));
            procEncCode = aes.encryptTextToB64Segments(pc.getDeclaration(), cipher);
            procCodeLines.add(new EncCodeLine(testSegment, procEncCode, "DECL(" + pc.getEntityName() + ")", pc.getDeclaration().length(), keySetNum, CodeType.DECLARATION, ++ordinal));
            for (CodeLine cl : pc.getCodeLine()) {
                procEncCode = aes.encryptTextToB64Segments(cl.getValue(), cipher);
                procCodeLines.add(new EncCodeLine(testSegment, procEncCode, cl.getName(), cl.getValue().length(), keySetNum, CodeType.CODE, -1));
            }
            codeLines.addAll(procCodeLines);
            keySetNum++;
        }
        List<EncCodeLine> encryptedFuncts = new ArrayList<>();
        for (EncCodeLine cl : codeLines) {
            if (cl.getType() == CodeType.CODE) {
                encryptedFuncts.add(cl);
            }
        }

        HashMap<Integer, EncCodeLine> functMap = new HashMap<>();
        Integer ctr = 0;
        while (!encryptedFuncts.isEmpty()) {
            int ndx = random.nextInt(encryptedFuncts.size()) + 1;
            functMap.put(ctr, encryptedFuncts.get(ndx - 1));
            encryptedFuncts.get(ndx - 1).setOrdinal(ordinal + ctr++);
            encryptedFuncts.remove(ndx - 1);
        }
        String tocJSON = "var TOC = {";
        Iterator<Integer> functItr = functMap.keySet().iterator();
        while (functItr.hasNext()) {
            int ndx = functItr.next();
            int otherNdx = random.nextInt(65536);
            tocJSON += "\"" + functMap.get(ndx).getEntityName() + "\" : {\"ndx1\" : " + Integer.toString(ndx ^ otherNdx) + ", \"ndx2\" : " + Integer.toString(otherNdx) + "}";
            if (functItr.hasNext()) {
                tocJSON += ", ";
            }
        }
        tocJSON += "};";
        encCode = aes.encryptTextToB64Segments(tocJSON, origCipher);
        codeLines.add(new EncCodeLine(testSegment, encCode, "TOC", tocJSON.length(), 0, CodeType.TOC, -1));
        return codeLines;
    }

    
    public JSKeys getJSKeys() {
        return keys;
    }
    
    public String getKeyJSON() {
        return keys.getKeySetJSON();
    }

    public String getKeyXML()
            throws java.io.IOException {
        StringWriter sWriter = new StringWriter();
        StreamResult sResult = new StreamResult(sWriter);
        marshaller.marshal(keys, sResult);
        return sWriter.toString();
    }

}
