/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.resultdata;

/**
 *
 * @author michael
 */

import net.iatsoftware.iat.controllers.RestException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBContext;
import javax.xml.transform.stream.StreamSource;

public abstract class AbstractResultData {

    protected SecretKeyFactory desKeyFactory;
    protected ResultTOC toc = null;
    private Cipher decryptCipher = null;


    public void initDecryptor() throws java.security.NoSuchAlgorithmException {
        desKeyFactory = SecretKeyFactory.getInstance("DES");
    }

    public void setCipher(Cipher c) {
        this.decryptCipher = c;
    }
    
    private byte[] decryptData(byte[] key, byte[] iv, byte[] data) throws RestException {
        ByteArrayOutputStream bOut = null;
        try {
            byte[] keyBytes = this.decryptCipher.doFinal(key);
            byte[] ivBytes = this.decryptCipher.doFinal(iv);
            SecretKey dataKey = desKeyFactory.generateSecret(new DESKeySpec(keyBytes));
            Cipher dataCipher = Cipher.getInstance("DES/CBC/ISO10126PADDING");
            dataCipher.init(Cipher.DECRYPT_MODE, dataKey, new IvParameterSpec(ivBytes));
            bOut = new ByteArrayOutputStream();
            try (CipherOutputStream cStream = new CipherOutputStream(bOut, dataCipher)) {
                cStream.write(data);
            }
        } catch (Exception ex) {
            throw new RestException("Error decrypting result data", ex);
        }
        return bOut.toByteArray();
    }
    
    public SurveyResponseSet decryptSurveyResults(byte[] resultData, int elemPos) throws RestException {
        try {
            ResultTOCEntry tocEntry = this.toc.getResultTOCEntry().get(elemPos);
            byte[] iatKey = new byte[tocEntry.getKeyLength()];
            System.arraycopy(resultData, (int)tocEntry.getKeyOffset(), iatKey, 0, tocEntry.getKeyLength());
            byte[] iatIV = new byte[tocEntry.getIVLength()];
            System.arraycopy(resultData, (int)tocEntry.getIVOffset(), iatIV, 0, tocEntry.getIVLength());
            byte[] results = new byte[tocEntry.getDataLength()];
            System.arraycopy(resultData, (int)tocEntry.getDataOffset(), results, 0, tocEntry.getDataLength());
            byte[] decData = decryptData(iatKey, iatIV, results);
            Unmarshaller unmarshaller = JAXBContext.newInstance(SurveyResponseSet.class).createUnmarshaller();
            return (SurveyResponseSet)unmarshaller.unmarshal(new StreamSource(new ByteArrayInputStream(decData)));
        }
        catch (Exception ex) {
            throw new RestException("Error decrypting iat data", ex);
        }
    }
    
    public ResultSetElementList decryptIatResults(byte[] resultData, int iatPos) throws RestException {
        try {
            ResultTOCEntry tocEntry = this.toc.getResultTOCEntry().get(iatPos);
            byte[] iatKey = new byte[tocEntry.getKeyLength()];
            System.arraycopy(resultData, (int)tocEntry.getKeyOffset(), iatKey, 0, tocEntry.getKeyLength());
            byte[] iatIV = new byte[tocEntry.getIVLength()];
            System.arraycopy(resultData, (int)tocEntry.getIVOffset(), iatIV, 0, tocEntry.getIVLength());
            byte[] results = new byte[tocEntry.getDataLength()];
            System.arraycopy(resultData, (int)tocEntry.getDataOffset(), results, 0, tocEntry.getDataLength());
            byte[] decData = decryptData(iatKey, iatIV, results);
            Unmarshaller unmarshaller = JAXBContext.newInstance(ResultSetElementList.class).createUnmarshaller();
            return (ResultSetElementList)unmarshaller.unmarshal(new StreamSource(new ByteArrayInputStream(decData)));
        }
        catch (Exception ex) {
            throw new RestException("Error decrypting iat data", ex);
        }
    }

    public void buildToc(String toc) throws java.io.IOException, javax.xml.bind.JAXBException {
        StringReader sr = new StringReader(toc);
        StreamSource src = new StreamSource(sr);
        Unmarshaller unmarshaller = JAXBContext.newInstance(ResultTOC.class).createUnmarshaller();
        this.toc = (ResultTOC)unmarshaller.unmarshal(src);
    }
    
    public abstract void compute(byte[] data, int elemPos) throws RestException;
}
