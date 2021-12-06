/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.deployment;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 *
 * @author Michael Janda
 */

@Service
public class JSAES {
    private final Logger logger = LogManager.getLogger("critical");
    private final Invocable encryptor;

    public JSAES() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        String aes = "";
        try {
            File aesFile = new ClassPathResource("/scripts/core_aes.js").getFile();
            try (InputStreamReader inStream = new InputStreamReader(new FileInputStream(aesFile))) {
                try (BufferedReader aesReader = new BufferedReader(inStream)) {
                    StringBuilder aesBuilder = new StringBuilder();
                    String line = "";
                    while ((line = aesReader.readLine()) != null) {
                        aesBuilder.append(line + "\n");
                    }
                    aes = aesBuilder.toString();
                }
            }
        } catch (java.io.IOException ex) {
            logger.error("Error loading 'aes.js'", ex);
        }
        try {
            engine.eval(aes);
        }
        catch (javax.script.ScriptException ex) {
            logger.error("Failed compiling aes_core.js", ex);
        }
        this.encryptor = (Invocable)engine;
    }

    public String[][] encryptWords(String[][] inWordAry, String[] key) {
        try {
            return (String[][])this.encryptor.invokeFunction("encryptWords", key, inWordAry);
        }
        catch (javax.script.ScriptException | NoSuchMethodException ex) {
            logger.error("Error encrypting word set", ex);
            return new String[4][];
        }
    }

    public String[] encryptWordSet(String[] inWords, String[] key) {
        try {
            return (String[])this.encryptor.invokeFunction("encryptWordSet", key, inWords);
        }
        catch (javax.script.ScriptException | NoSuchMethodException ex) {
            logger.error("Error encrypting word set", ex);
            return new String[4];
        }
    }

    public String[] encryptTextToB64Segments(String text, String[] key) {
      return new String[4];
    }
}
