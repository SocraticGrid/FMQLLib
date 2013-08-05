/*
 * To change this template, choose Tools | Templates and open the template in the
 * editor.
 */
package org.socraticgrid.fmqllib;

import junit.framework.TestCase;

import static junit.framework.TestCase.assertNotNull;

import org.apache.commons.io.IOUtils;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.socraticgrid.patientdataservices.MainRetriever;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * DOCUMENT ME!
 *
 * @author  Jerry Goodnough
 */
@ContextConfiguration(locations = { "classpath:Test-FMQLDataSourceTest.xml" })
// @DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from "/applicationContext.xml" and
// "/applicationContext-test.xml" in the root of the classpath
public class PatientGraphRDFDataSourceTest extends TestCase
{
    private static final Logger logger = Logger.getLogger(
            PatientGraphRDFDataSourceTest.class.getName());
    @Autowired
    private ApplicationContext ctx;

    public PatientGraphRDFDataSourceTest()
    {
    }

    /**
     * Test of getData method, of class DataSource. NOTE: weirdly, "focused" test or
     * debug test on this test passes ok. But, an overall "test file" fails on
     * building the endpoint+query URL string.
     */
    @Test
    public void testGetData() throws IOException
    {
        logger.info("\n=========================\nTESTING testGetData");
        assertNotNull("Missing Context Object", ctx);

        String domain = "demographics";
        String id = "1006387";
        PaientGraphRDFDataSource source = (PaientGraphRDFDataSource) ctx.getBean(
                "SamplePatientDataGraphSource");
        assertNotNull("Missing SamplePatientDataGraphSource", source);

        InputStream result = source.getData(domain, id, null);
        assertNotNull("No data returned for domain " + domain, result);

        String test = IOUtils.toString(result, "UTF-8");
        assertFalse(test.contains("\"error\":"));
        logger.info(test);
    }

    /**
     * Test of getDomainQueryMap method, of class DataSource. NOTE: weirdly,
     * "focused" test or debug test on this test passes ok. But, an overall "test
     * file" fails on building the endpoint+query URL string.
     */
    @Test
    public void testGetDomainQueryMap()
    {
        logger.info("\n=========================\nTESTING getDomainQueryMap");
        assertNotNull("Missing Context Object", ctx);

        PaientGraphRDFDataSource instance = (PaientGraphRDFDataSource) ctx.getBean(
                "SamplePatientDataGraphSource");
        Map expResult = null;
        Map result = instance.getDomainQueryMap();
        // assertEquals(expResult, result); TODO review the generated test code and
        // remove the default call to fail. fail("The test case is a prototype.");
    }

    /**
     * Test of getFmqlEndpoint method, of class DataSource.
     */
    @Test
    public void testGetPatientGraphEndpoint()
    {
        logger.info("\n=========================\nTESTING getPatientGraphEndpoint");
        assertNotNull("Missing Context Object", ctx);

        PaientGraphRDFDataSource instance = (PaientGraphRDFDataSource) ctx.getBean(
                "SamplePatientDataGraphSource");
        String result = instance.getPatientGraphEndpoint();
        logger.log(Level.INFO, "EP={0}", result);
    }

    /**
     * Test of isDomainSupported method, of class DataSource.
     */
    @Test
    public void testIsDomainSupported()
    {
        logger.info("\n=========================\nTESTING isDomainSupported");
        assertNotNull("Missing Context Object", ctx);

        String domain = "demographics";
        PaientGraphRDFDataSource instance = (PaientGraphRDFDataSource) ctx.getBean(
                "SamplePatientDataGraphSource");
        boolean expResult = false;
        boolean result = instance.isDomainSupported(domain);

        if (result)
        {
            logger.info("Domain " + domain + " is supported");
        }
        else
        {
            logger.info("Domain " + domain + " is NOT upported");
        }

        // assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * NOTE: weirdly, "focused" test or debug test on this test passes ok. But, an
     * overall "test file" fails on building the endpoint+query URL string.
     *
     * <p>Running Focused Test is ok:
     * 10.255.167.112/pgrafEP?patient=1006387&type=ALLERGIES</p>
     *
     * @throws  IOException
     */
    @Test
    public void testRetrieveDemographicData() throws IOException
    {
        logger.info("\n=========================\nTESTING testRetrieveFMQLData");
        assertNotNull("Missing Context Object", ctx);

        String domain = "demographics";
        String id = "1006387";
        MainRetriever retriever = (MainRetriever) ctx.getBean("FMQLRetriever");

        // Transformer trans = retriever.getTransformer(); //OPTIONAL
        InputStream result = retriever.getDataAsStream("PatientRDFGraph", domain, id,
                null);
        assertNotNull("No data returned for domain " + domain, result);

        String test = IOUtils.toString(result, "UTF-8");
        assertFalse(test.contains("\"error\":"));
        logger.info(test);
    }

    @Test
    public void testRetrieveLabData() throws IOException
    {
        logger.info("\n=========================\nTESTING testRetrieveLabData");
        assertNotNull("Missing Context Object", ctx);

        String domain = "labs";
        String id = "1006387";
        MainRetriever retriever = (MainRetriever) ctx.getBean("FMQLRetriever");

        // Transformer trans = retriever.getTransformer(); //OPTIONAL
        InputStream result = retriever.getDataAsStream("PatientRDFGraph", domain, id,
                null);
        assertNotNull("No data returned for domain " + domain, result);

        String test = IOUtils.toString(result, "UTF-8");
        assertFalse(test.contains("\"error\":"));
        logger.info(test);
    }

    @Test
    public void testRetrieveMedData() throws IOException
    {
        logger.info("\n=========================\nTESTING testRetrieveMedData");
        assertNotNull("Missing Context Object", ctx);

        String domain = "medications";
        String id = "1006387";
        MainRetriever retriever = (MainRetriever) ctx.getBean("FMQLRetriever");

        // Transformer trans = retriever.getTransformer(); //OPTIONAL
        InputStream result = retriever.getDataAsStream("PatientRDFGraph", domain, id,
                null);
        assertNotNull("No data returned for domain " + domain, result);

        String test = IOUtils.toString(result, "UTF-8");
        assertFalse(test.contains("\"error\":"));
        logger.info(test);
    }

    /**
     * Test of setDomainQueryMap method, of class DataSource.
     */
    @DirtiesContext
    @Test
    public void testSetDomainQueryMap()
    {
        logger.info("\n=========================\nTESTING testSetDomainQueryMap");
        assertNotNull("Missing Context Object", ctx);

        Map<String, String> domainQueryMap = null;
        PaientGraphRDFDataSource instance = (PaientGraphRDFDataSource) ctx.getBean(
                "SamplePatientDataGraphSource");
        instance.setDomainQueryMap(domainQueryMap);

        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of setFmqlEndpoint method, of class DataSource.
     */
    @DirtiesContext
    @Test
    public void testSetPatientGraphEndpoint()
    {
        logger.info("\n=========================\nTESTING setPatientGraphEndpoint");
        assertNotNull("Missing Context Object", ctx);

        String endpoint = "http://localhost";
        PaientGraphRDFDataSource instance = (PaientGraphRDFDataSource) ctx.getBean(
                "SamplePatientDataGraphSource");
        instance.setPatientGraphEndpoint(endpoint);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }
}
