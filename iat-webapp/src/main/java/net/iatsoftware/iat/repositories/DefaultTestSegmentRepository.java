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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.iatsoftware.iat.entities.IAT;

import org.springframework.stereotype.Repository;

import net.iatsoftware.iat.entities.TestSegment;

@Repository
public class DefaultTestSegmentRepository extends GenericJpaRepository<Long, TestSegment>
        implements TestSegmentRepository {

    @Override
    public String getHtmlBytes(Long testSegmentID) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<String> query = cb.createQuery(String.class);
        Root<TestSegment> root = query.from(TestSegment.class);
        Predicate pred = cb.equal(root.get("id"), testSegmentID);
        return this.entityManager.createQuery(query.select(root.get("html")).where(pred)).getSingleResult();
    }

    @Override
    public void rotateItems(IAT test) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class);
        Root<TestSegment> root = query.from(TestSegment.class);
        Predicate pred = cb.equal(root.get("test"), test);
        Predicate having = cb.ge(root.get("alternationPriority"), 0);
        List<Tuple> alternationDataList = this.entityManager.createQuery(query.multiselect(cb.count(root.get("alternationPriority")), root.get("alternationPriority"), root.get("numAlternations")).where(pred).groupBy(root.get("alternationPriority")).having(having)).getResultList();
        if (alternationDataList.isEmpty()) {
            return;
        }
        long groupSize = 0;
        int alternationNum = 0, alternationPriority = 0;
        int ctr = 0;
        boolean readyToRotate = false;
        while ((ctr < alternationDataList.size()) && (!readyToRotate)) {
            Tuple t = alternationDataList.get(ctr);
            groupSize = t.get(0, Long.class);
            alternationPriority = t.get(1, Integer.class);
            alternationNum = t.get(2, Integer.class);
            if ((alternationNum != groupSize) && (alternationPriority != -1)) {
                alternationNum++;
                readyToRotate = true;
            } else {
                alternationNum = 0;
                ctr++;
            }
        }
        CriteriaUpdate<TestSegment> update = cb.createCriteriaUpdate(TestSegment.class);
        root = update.from(TestSegment.class);
        if (ctr == alternationDataList.size()) {
            pred = cb.equal(root.get("test"), test);
            this.entityManager.createQuery(update.set(root.get("numAlternations"), 0).where(pred)).executeUpdate();
        } else {
            pred = cb.and(cb.equal(root.get("test"), test), cb.equal(root.get("alternationPriority"), alternationPriority));
            this.entityManager.createQuery(update.set(root.get("numAlternations"), alternationNum).where(pred)).executeUpdate();
            pred = cb.and(cb.equal(root.get("test"), test), cb.lt(root.get("alternationPriority"), alternationPriority), cb.notEqual(root.get("alternationPriority"), -1));
            this.entityManager.createQuery(update.set(root.get("numAlternations"), 0).where(pred)).executeUpdate();
        }
    }

    @Override
    public List<Long> getTestElems(IAT test) {
        final Map<Double, Long> elemMap = new HashMap<>();
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class);
        Root<TestSegment> root = query.from(TestSegment.class);
        Predicate pred = cb.and(cb.equal(root.get("test"), test), cb.equal(root.get("alternationPriority"), -1));
        List<Tuple> nonAlternated = this.entityManager.createQuery(query.select(cb.tuple(root.get("initialPos"), root.get("id"), root.get("iat"))).where(pred)).getResultList();
        int iatPos = 0;
        for (Tuple elem : nonAlternated) {
            elemMap.put((double) elem.get(0, Integer.class), elem.get(1, Long.class));
            if (elem.get(2, Boolean.class))
                iatPos = elem.get(0, Integer.class);
        }
        CriteriaQuery<Integer> intQuery = cb.createQuery(Integer.class);
        root = intQuery.from(TestSegment.class);
        pred = cb.and(cb.equal(root.get("test"), test), cb.ge(root.get("alternationPriority"), 0));
        Predicate havingPred = cb.gt(cb.count(root.get("alternationPriority")), 1);
        List<Integer> priorities = this.entityManager.createQuery(intQuery.select(root.get("alternationPriority")).where(pred).groupBy(root.get("alternationPriority")).having(havingPred)).getResultList();
        priorities.forEach((p) -> {
            CriteriaQuery<Tuple> multipleAlternationQuery = cb.createQuery(Tuple.class);
            Root<TestSegment> multipleAlternationRoot = multipleAlternationQuery.from(TestSegment.class);
            Predicate multipleAlternationPred = cb.and(cb.equal(multipleAlternationRoot.get("test"), test), cb.equal(multipleAlternationRoot.get("alternationPriority"), p));
            List<Tuple> segments = this.entityManager.createQuery(multipleAlternationQuery.multiselect(multipleAlternationRoot.get("initialPos"), multipleAlternationRoot.get("numAlternations"), multipleAlternationRoot.get("id")).where(multipleAlternationPred)).getResultList();
            for (int ctr = 0; ctr < segments.size(); ctr++) {
                int alternationNum = segments.get(ctr).get(1, Integer.class);
                int ndx = (ctr + alternationNum) % segments.size();
                elemMap.put((double) segments.get(ndx).get(0, Integer.class), segments.get(ctr).get(2, Long.class));
            }
        });
        pred = cb.and(cb.equal(root.get("test"), test), cb.ge(root.get("alternationPriority"), 0));
        havingPred = cb.equal(cb.count(root.get("alternationPriority")), 1);
        priorities = this.entityManager.createQuery(intQuery.select(root.get("alternationPriority")).where(pred).groupBy(root.get("alternationPriority")).having(havingPred).orderBy(cb.asc(root.get("alternationPriority")))).getResultList();
        final int iPos = iatPos;
        priorities.forEach((p) -> {
            CriteriaQuery<Tuple> singleAlternationQuery = cb.createQuery(Tuple.class);
            Root<TestSegment> singleAlternationRoot = singleAlternationQuery.from(TestSegment.class);
            Predicate singleAlternationPred = cb.and(cb.equal(singleAlternationRoot.get("test"), test), cb.equal(singleAlternationRoot.get("alternationPriority"), p));
            Tuple segment = this.entityManager.createQuery(singleAlternationQuery.multiselect(singleAlternationRoot.get("initialPos"), singleAlternationRoot.get("id"), singleAlternationRoot.get("numAlternations")).where(singleAlternationPred)).getSingleResult();
            if (segment.get(2, Integer.class) == 1) {
                if (segment.get(0, Integer.class) < iPos) {
                    elemMap.put(iPos + p / 100.0, segment.get(1, Long.class));
                } else {
                    elemMap.put(iPos - .99 + p / 100, segment.get(1, Long.class));
                }
            } else
                elemMap.put((double)segment.get(0, Integer.class), segment.get(1, Long.class));
        });
        return elemMap.entrySet().stream().sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey())).map(e -> e.getValue()).collect(Collectors.toList());
    }

    @Override
    public int getElementPositionInTest(IAT test, String elemName) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
        Root<TestSegment> root = query.from(TestSegment.class);
        Predicate pred1 = cb.equal(root.get("test"), test);
        Predicate pred2 = cb.equal(root.get("elemName"), elemName);
        return this.entityManager.createQuery(query.select(root.get("initialPos")).where(pred1, pred2)).getSingleResult();
    }

    @Override
    public List<String> getTestElemNames(IAT test) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<String> query = cb.createQuery(String.class);
        Root<TestSegment> root = query.from(TestSegment.class);
        Predicate pred = cb.equal(root.get("test"), test);
        return this.entityManager.createQuery(query.select(root.get("elemName")).where(pred).orderBy(cb.asc(root.get("initialPos")))).getResultList();
    }

    @Override
    public TestSegment getByTestAndElemName(IAT test, String elemName) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<TestSegment> query = cb.createQuery(TestSegment.class);
        Root<TestSegment> root = query.from(TestSegment.class);
        Predicate pred = cb.and(cb.equal(root.get("test"), test), cb.equal(root.get("elemName"), elemName));
        return this.entityManager.createQuery(query.select(root).where(pred)).getSingleResult();
    }
    
    @Override
    public int getInitialIatPositionInTest(IAT test) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
        Root<TestSegment> root = query.from(TestSegment.class);
        Predicate pred = cb.and(cb.equal(root.get("test"), test), cb.isTrue(root.get("iat")));
        return this.entityManager.createQuery(query.select(root.get("initialPos")).where(pred)).getSingleResult();
    }

    public List<TestSegment> getTestSegments(IAT test) {
        var cb = this.entityManager.getCriteriaBuilder();
        var query = cb.createQuery(TestSegment.class);
        var root = query.from(TestSegment.class);
        return this.entityManager.createQuery(query.select(root).where(cb.equal(root.get("test"), test))).getResultList();
    }
}
