/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.resultretrieval;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.config.MyBeanFactory;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.ResultSet;
import net.iatsoftware.iat.events.DataRequestEvent;
import net.iatsoftware.iat.events.WebSocketDataSent;
import net.iatsoftware.iat.events.WebSocketFinalDataSent;
import net.iatsoftware.iat.generated.TokenType;
import net.iatsoftware.iat.generated.TransactionType;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.messaging.ServerExceptionMessage;
import net.iatsoftware.iat.messaging.ServerReport;
import net.iatsoftware.iat.messaging.TransactionRequest;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.resultdata.ResultSetDescriptor;
import net.iatsoftware.iat.resultdata.ResultSetEntry;
import net.iatsoftware.iat.resultdata.ResultTOC;
import net.iatsoftware.iat.resultdata.TestResults;
import net.iatsoftware.iat.services.PacketDataSource;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.StringReader;
import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.text.DateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

@Service
public class DataRetrievalSession {

    private static final Logger log = LogManager.getLogger("critical");
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Random rand = new Random();

    @Inject
    MyBeanFactory iatServerBeanFactory;
    @Inject
    ThreadPoolTaskScheduler scheduler;
    @Inject
    IATRepositoryManager iatRepositoryManager;
    @Inject
    Marshaller marshaller;
    @Inject
    MyBeanFactory beanFactory;
    @Inject
    Unmarshaller unmarshaller;
    @Inject
    ApplicationEventPublisher publisher;
    @Inject
    @Named("ServerConfiguration")
    Properties serverConfiguration;

    @EventListener
    public void onDataRequest(DataRequestEvent event) {
        try {
            switch (event.getRequest()) {
                case retrieveItemSlides:
                    retrieveItemSlides(event);
                    break;

                case retrieveResults:
                    retrieveResults(event);
                    break;

                case retrieveServerReport:
                    retrieveServerReport(event);
                    break;
            }
        } catch (jakarta.persistence.NoResultException | jakarta.persistence.NonUniqueResultException ex) {
            var outTrans = new TransactionRequest(TransactionType.NOTIFY_DOWNLOAD_LEGACY_SOFTWARE);
            outTrans.addStringValue("version", "1.1.1.46");
            this.publisher.publishEvent(new WebSocketFinalDataSent(event.getSessionId(), new Envelope(outTrans)));
        } catch (Exception ex) {
            log.error("error retrieving data", ex);
        }
    }

    private void retrieveItemSlides(DataRequestEvent e) throws java.net.URISyntaxException {
        try {
            IAT test = iatRepositoryManager.getIATByNameAndClientID(e.getTestName(), e.getClientID());
            byte[] randData = new byte[40];
            rand.nextBytes(randData);
            String fPrefix = encoder.encodeToString(randData).replace("=", "").replace("/", "").replace("+", "");
            final File slideFile = new File(new URI(String.format("%s/%s.%d.%s.slides",
                    serverConfiguration.getProperty("item-slide-directory"), fPrefix,
                    test.getClient().getClientId(), test.getTestName())));
            ItemSlidePacketQueue pQueue = new ItemSlidePacketQueue(slideFile);
            PacketDataSource dataSource = new ItemSlidePacketDataSource(iatRepositoryManager, test);
            pQueue.queueData(dataSource);
            test.setItemSlideDownloadKey(fPrefix);
            iatRepositoryManager.updateIAT(test);
            TransactionRequest trans = new TransactionRequest(TransactionType.ITEM_SLIDE_DOWNLOAD_READY);
            trans.addStringValue("DownloadKey", fPrefix);
            trans.setClientID(e.getClientID());
            this.publisher.publishEvent(new WebSocketFinalDataSent(e.getSessionId(), new Envelope(trans)));
            this.scheduler.schedule(() -> {
                slideFile.delete();
            }, Instant.now().plus(10, ChronoUnit.MINUTES));
        } catch (java.io.IOException | java.net.URISyntaxException ex) {
            log.error("Error generating item slide file for download", ex);
            this.publisher.publishEvent(new WebSocketFinalDataSent(e.getSessionId(),
                    new Envelope(new ServerExceptionMessage("Error generating item slide file for download", ex))));
        }
    }

    private void retrieveResults(DataRequestEvent e) throws java.net.URISyntaxException, java.io.IOException {
        ResultSetDescriptor rsd = beanFactory.resultSetDescriptor();
        TestResults testResults = new TestResults();
        IAT test = iatRepositoryManager.getIATByNameAndClientID(e.getTestName(), e.getClientID());
        rsd.load(e.getClientID(), e.getTestName());
        testResults.setDescriptor(rsd);
        List<ResultSet> resultSets = iatRepositoryManager.getResults(e.getClientID(), e.getTestName());
        testResults.setNumResultSets(resultSets.size());
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        for (ResultSet rs : resultSets) {
            StringReader sReader = new StringReader(rs.getToc());
            StreamSource sSource = new StreamSource(sReader);
            ResultTOC toc = (ResultTOC) unmarshaller.unmarshal(sSource);
            ResultSetEntry rse = new ResultSetEntry();
            rse.setTOC(toc);
            rse.setAdminTime(df.format(rs.getAdminTime().getTime()));
            rse.setResultData(encoder.encodeToString(rs.getResults()));
            if (test.getTokenType() != TokenType.NONE)
                rse.setToken(encoder.encodeToString(rs.getTesteeToken()));
            rse.setResultId(rs.getId());
            testResults.getResultSet().add(rse);
        }
        byte[] authTokenData = new byte[64];
        rand.nextBytes(authTokenData);
        File resultFile = new File(new URI(String.format("%s/%s-%d", serverConfiguration.getProperty("result-data"),
                test.getTestName(), test.getClient().getClientId())));
        StreamResult sResult = new StreamResult(resultFile);
        marshaller.marshal(testResults, sResult);
        test.setResultRetrievalToken(authTokenData);
        test.setResultRetrievalTokenAge(Calendar.getInstance());
        iatRepositoryManager.updateIAT(test);
        TransactionRequest trans = new TransactionRequest(TransactionType.RESULTS_READY);
        trans.addStringValue("AuthToken", encoder.encodeToString(authTokenData));
        trans.addLongValue("ClientId", e.getClientID());
        this.publisher.publishEvent(new WebSocketFinalDataSent(e.getSessionId(), new Envelope(trans)));
    }

    private void retrieveServerReport(DataRequestEvent e) {
        ServerReport report = beanFactory.serverReport();
        report.load(e.getClientID());
        this.publisher.publishEvent(new WebSocketFinalDataSent(e.getSessionId(), new Envelope(report)));
    }
}
