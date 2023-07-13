/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.dataservices;

/**
 *
 * @author Michael Janda
 */
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;
import net.sf.saxon.s9api.Processor;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.io.StringWriter;
import javax.inject.Inject;
import javax.xml.transform.stream.StreamSource;

@Service
public class XsltService {

    private static final Logger logger = LogManager.getLogger();
    private XsltExecutable iatScriptX = null, iatHeaderX = null, iatPageX = null, surveyScriptX = null, surveyHeaderX = null, surveyPageX = null;
    private XsltExecutable iatDescriptorX = null, surveyDescriptorX = null, aesX = null, jsCodeSegmentX = null, postMungeX = null;
    private XsltExecutable encPostMungeX = null, iatGlobalsX = null, surveyGlobalsX = null;
    private static final String IAT_SCRIPT = "classpath:XSLT/IATScript.xslt";
    private static final String IAT_HEADER = "classpath:XSLT/IATHeaderJS.xslt";
    private static final String IAT_PAGE = "classpath:XSLT/IATPage.xslt";
    private static final String SURVEY_SCRIPT = "classpath:XSLT/SurveyScript.xslt";
    private static final String SURVEY_HEADER = "classpath:XSLT/SurveyHeaderJS.xslt";
    private static final String SURVEY_PAGE = "classpath:XSLT/SurveyPage.xslt";
    private static final String IAT_DESCRIPTOR = "classpath:XSLT/IATDescriptor.xslt";
    private static final String SURVEY_DESCRIPTOR = "classpath:XSLT/SurveyDescriptor.xslt";
    private static final String AES = "classpath:XSLT/AES.xslt";
    private static final String JS_CODE_SEGMENT = "classpath:XSLT/JSCodeSegment.xslt";
    private static final String POST_MUNGE = "classpath:XSLT/PostMunge.xslt";
    private static final String ENCRYPTED_POST_MUNGE = "classpath:XSLT/EncPostMunge.xslt";
    private static final String IAT_GLOBAL_VARIABLES = "classpath:XSLT/IATGlobals.xslt";
    private static final String SURVEY_GLOBAL_VARIABLES = "classpath:XSLT/SurveyGlobals.xslt";
    private static final Processor xsltProcessor = new Processor(false);

    @Inject ApplicationContext ctx;

    public String transform(String source, String xslt) throws java.io.IOException, net.sf.saxon.s9api.SaxonApiException {
        XsltCompiler compiler = xsltProcessor.newXsltCompiler();
        StringReader sReader = new StringReader(xslt);
        StreamSource src = new StreamSource(sReader);
        XsltTransformer trans = compiler.compile(src).load();
        sReader = new StringReader(source);
        src = new StreamSource(sReader);
        trans.setSource(src);
        StringWriter sWriter = new StringWriter();
        trans.setDestination(xsltProcessor.newSerializer(sWriter));
        trans.transform();
        return sWriter.toString();
    }

    synchronized private XsltExecutable compile(String src)
            throws java.security.InvalidKeyException, java.io.IOException, java.security.InvalidAlgorithmParameterException,
            net.sf.saxon.s9api.SaxonApiException {
        Resource res = this.ctx.getResource(src);
        XsltCompiler compiler = xsltProcessor.newXsltCompiler();
        return compiler.compile(new StreamSource(res.getInputStream()));
    }

    public synchronized XsltExecutable getIATScriptX() {
        try {
            if (iatScriptX != null) {
                return iatScriptX;
            } else {
                iatScriptX = compile(IAT_SCRIPT);
            }
            return iatScriptX;
        } catch (Exception ex) {
            logger.error("Error compiling IAT Script XSLT", ex);
        }
        return null;
    }

    public synchronized XsltExecutable getIATHeaderX() {
        try {
            if (iatHeaderX != null) {
                return iatHeaderX;
            } else {
                iatHeaderX = compile(IAT_HEADER);
            }
            return iatHeaderX;
        } catch (Exception ex) {
            logger.error("Error compiling IAT Header JS XSLT", ex);
        }
        return null;

    }

    public synchronized XsltExecutable getIATPageX() {
        try {
            if (iatPageX != null) {
                return iatPageX;
            } else {
                iatPageX = compile(IAT_PAGE);
            }
            return iatPageX;
        } catch (Exception ex) {
            logger.error("Error compiling IAT Page HTML XSLT", ex);
        }
        return null;
    }

    public synchronized XsltExecutable getSurveyScriptX() {
        try {
            if (surveyScriptX != null) {
                return surveyScriptX;
            } else {
                surveyScriptX = compile(SURVEY_SCRIPT);
            }
            return surveyScriptX;
        } catch (Exception ex) {
            logger.error("Error compiling Survey Script XSLT", ex);
        }
        return null;
    }

    public synchronized XsltExecutable getSurveyHeaderX() {
        try {
            if (surveyHeaderX != null) {
                return surveyHeaderX;
            } else {
                surveyHeaderX = compile(SURVEY_HEADER);
            }
            return surveyHeaderX;
        } catch (Exception ex) {
            logger.error("Error compiling Survey Header JS XSLT", ex);
        }
        return null;
    }

    public synchronized XsltExecutable getSurveyPageX() {
        try {
            if (surveyPageX != null) {
                return surveyPageX;
            } else {
                surveyPageX = compile(SURVEY_PAGE);
            }
            return surveyPageX;
        } catch (Exception ex) {
            logger.error("Error compiling Survey Page HTML XSLT", ex);
        }
        return null;
    }

    public synchronized XsltExecutable getIATDescriptorX() {
        try {
            if (iatDescriptorX != null) {
                return iatDescriptorX;
            } else {
                iatDescriptorX = compile(IAT_DESCRIPTOR);
            }
            return iatDescriptorX;
        } catch (Exception ex) {
            logger.error("Error compiling IAT Descriptor XSLT", ex);
        }
        return null;
    }

    public synchronized XsltExecutable getSurveyDescriptorX() {
        try {
            if (surveyDescriptorX != null) {
                return surveyDescriptorX;
            } else {
                surveyDescriptorX = compile(SURVEY_DESCRIPTOR);
            }
            return surveyDescriptorX;
        } catch (Exception ex) {
            logger.error("Error compiling Survey Descriptor XSLT", ex);
        }
        return null;
    }

    public synchronized XsltExecutable getAESX() {
        try {
            if (aesX != null) {
                return aesX;
            } else {
                aesX = compile(AES);
            }
            return aesX;
        } catch (Exception ex) {
            logger.error("Error compiling AES XSLT", ex);
        }
        return null;
    }

    public synchronized XsltExecutable getJSCodeSegmentX() {
        try {
            if (jsCodeSegmentX != null) {
                return jsCodeSegmentX;
            } else {
                jsCodeSegmentX = compile(JS_CODE_SEGMENT);
            }
            return jsCodeSegmentX;
        } catch (Exception ex) {
            logger.error("Error compiling JS Code Segment XSLT", ex);
        }
        return null;
    }

    public synchronized XsltExecutable getPostMungeX() {
        try {
            if (postMungeX != null) {
                return postMungeX;
            } else {
                postMungeX = compile(POST_MUNGE);
            }
            return postMungeX;
        } catch (Exception ex) {
            logger.error("Error compiling Post Munge XSLT", ex);
        }
        return null;
    }

    public synchronized XsltExecutable getEncPostMungeX() {
        try {
            if (encPostMungeX != null) {
                return encPostMungeX;
            } else {
                encPostMungeX = compile(ENCRYPTED_POST_MUNGE);
            }
            return encPostMungeX;
        } catch (Exception ex) {
            logger.error("Error compiling Encrypted Post Munge XSLT", ex);
        }
        return null;
    }

    public synchronized XsltExecutable getIATGlobalsX() {
        try {
            if (iatGlobalsX != null) {
                return iatGlobalsX;
            } else {
                iatGlobalsX = compile(IAT_GLOBAL_VARIABLES);
            }
            return iatGlobalsX;
        } catch (Exception ex) {
            logger.error("Error compiling Global Variable XSLT", ex);
        }
        return null;
    }

    public synchronized XsltExecutable getSurveyGlobalsX() {
        try {
            if (surveyGlobalsX != null) {
                return surveyGlobalsX;
            } else {
                surveyGlobalsX = compile(SURVEY_GLOBAL_VARIABLES);
            }
            return surveyGlobalsX;
        } catch (Exception ex) {
            logger.error("Error compiling Global Variable XSLT", ex);
        }
        return null;
    }
}
