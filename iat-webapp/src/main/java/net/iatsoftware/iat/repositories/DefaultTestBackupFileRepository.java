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
import net.iatsoftware.iat.entities.TestBackupFile;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Repository;

import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class DefaultTestBackupFileRepository extends GenericJpaRepository<Long, TestBackupFile> implements TestBackupFileRepository {

    private final static Logger logger = LogManager.getLogger();

    @Override
    public void deleteTestBackup(IAT test) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<TestBackupFile> deletion = cb.createCriteriaDelete(TestBackupFile.class);
        Root<TestBackupFile> root = deletion.from(TestBackupFile.class);
        Predicate pred = cb.equal(root.get("test"), test);
        this.entityManager.createQuery(deletion.where(pred)).executeUpdate();
    }

    @Override
    public void restoreTestBackup(IAT test) throws java.io.IOException, java.net.URISyntaxException {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class);
        Root<TestBackupFile> root = query.from(TestBackupFile.class);
        Predicate pred = cb.equal(root.get("test"), test);
        List<Tuple> files = this.entityManager.createQuery(query.multiselect(root.get("fileName").alias("fileName"), root.get("fileData").alias("fileData")).where(pred)).getResultList();
        try {
            for (Tuple t : files) {
                String fName = t.get("fileName", String.class);
                byte[] fData = t.get("fileData", byte[].class);
                try (OutputStream outStream = Files.newOutputStream(Paths.get(new URI(fName)), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                    outStream.write(fData);
                    outStream.close();
                } catch (java.io.IOException | java.net.URISyntaxException ex) {
                    logger.error("Error restoring test backup file", ex);
                    throw ex;
                }
            }
        } catch (java.io.IOException | java.net.URISyntaxException ex) {
            throw ex;
        } finally {
            deleteTestBackup(test);
        }
    }
}
