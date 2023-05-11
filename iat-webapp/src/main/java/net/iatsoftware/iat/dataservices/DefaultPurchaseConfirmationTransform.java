/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.dataservices;

/**
 *
 * @author Michael Janda too
 */
/*
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.oxm.Marshaller;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import javax.inject.Inject;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class DefaultPurchaseConfirmationTransform implements PurchaseConfirmationTransform {

    private static final Logger logger = LogManager.getLogger();
    private final XsltExecutable transform;
    private final Processor processor;

    @Inject
    Marshaller marshaller;

    public DefaultPurchaseConfirmationTransform(XsltExecutable exec) {
        this.transform = exec;
        this.processor = new Processor(false);

    }

    @Override
    public String generateEMailBody(PurchaseConfirmation confirmation) {
        synchronized (this) {
            try {
                ByteArrayOutputStream bOut = new ByteArrayOutputStream();
                StreamResult sr = new StreamResult(bOut);
                marshaller.marshal(confirmation, sr);
                StreamSource ss = new StreamSource(new ByteArrayInputStream(bOut.toByteArray()));
                StringWriter txtWriter = new StringWriter();
                Serializer ser = this.processor.newSerializer(txtWriter);
                XsltTransformer transformer = this.transform.load();
                transformer.setDestination(ser);
                transformer.setSource(ss);
                transformer.transform();
                txtWriter.flush();
                return "<!DOCTYPE html>\n" + txtWriter.toString();
            } catch (Exception ex) {
                logger.error("Error transforming purchase confirmation email");
                return null;
            }
        }
    }
}
*/