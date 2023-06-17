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

import java.util.regex.Pattern;

import java.util.HashMap;
import java.util.regex.Matcher;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GlobalVarNameTable")
@XmlAccessorType(XmlAccessType.NONE)
public class GlobalVarNameTable extends net.iatsoftware.iat.generated.GGlobalVarNameTable {

    public void addGlobalVars(GlobalVarNameTable varTable) {
        for (VarTableEntry vte : varTable.getVarTableEntry()) {
            getVarTableEntry().add(vte);
        }
    }

    private Pattern getReplaceRegEx() {
        String pattStr = "((([^A-Za-z0-9\\.]|^)(";
        pattStr += varTableEntry.stream()
                .reduce("", (str, vte) -> (str.isEmpty() ? vte.getOrigName() : (str + "|" + vte.getOrigName())),
                        (str1, str2) -> str1 + "|" + str2)
                .concat(")([^A-Za-z0-9_]|$))|([^\\\\]?\\x22))");
        return Pattern.compile(pattStr);
    }

    private HashMap<String, String> getVariableMap() {
        HashMap<String, String> varMap = new HashMap<>();
        for (VarTableEntry vte : getVarTableEntry())
            varMap.put(vte.getOrigName(), vte.getNewName());
        return varMap;
    }

    public void clear() {
        getVarTableEntry().clear();
    }

    public String replaceGlobals(String str) {
        Pattern replacePattern = getReplaceRegEx();
        HashMap<String, String> varMap = getVariableMap();
        StringBuffer sb = new StringBuffer();
        int nQuotes = 0;
        Matcher m = replacePattern.matcher(str);
        while (m.find()) {
            StringBuilder replacement = new StringBuilder();
            if (m.group(2) == null) {
                if (!m.group(6).startsWith("\\")) {
                    nQuotes++;
                }
                replacement.append(m.group(6));
                continue;
            }
            if (m.group(3) != null)
                replacement.append(m.group(3));
            if (nQuotes % 2 == 0)
                replacement.append(varMap.get(m.group(4)));
            else
                replacement.append(m.group(4));
            if (m.group(5) != null)
                replacement.append(m.group(5));
            m.appendReplacement(sb, replacement.toString());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public void replaceGlobals(CodeFile cf) {
        cf.setGlobalCode(replaceGlobals(cf.getGlobalCode()));
        for (ProcessedCode pc : cf.getProcessedCode())
            for (CodeLine cl : pc.getCodeLine())
                cl.setValue(replaceGlobals(cl.getValue()));
    }

    public GlobalVarNameTable getCopy() {
        GlobalVarNameTable o = new GlobalVarNameTable();
        for (VarTableEntry vte : getVarTableEntry()) {
            VarTableEntry cpy = new VarTableEntry();
            cpy.setNewName(vte.getNewName());
            cpy.setOrigName(vte.getOrigName());
            o.getVarTableEntry().add(cpy);
        }
        return o;
    }
}
