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
import org.springframework.test.annotation.DirtiesContext.ClassMode;


/**
 * DOCUMENT ME!
 *
 * @author  Jerry Goodnough
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:Test-FMQLDataSourceTest.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
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
     * Test of getData method, of class DataSource. 
     * @throws java.io.IOException
     */
    @Test
    public void testGetData() throws IOException
    {
        logger.info("\n=========================\nTESTING testGetData");
        assertNotNull("Missing Context Object", ctx);

        String domain = "demographics";
        String id = "1006387";
        PatientGraphRDFDataSource source = (PatientGraphRDFDataSource) ctx.getBean(
                "SamplePatientDataGraphSource");
        assertNotNull("Missing SamplePatientDataGraphSource", source);

        InputStream result = source.getData(domain, id, null);
        assertNotNull("No data returned for domain " + domain, result);

        String test = IOUtils.toString(result, "UTF-8");
        assertFalse(test.contains("\"error\":"));
        logger.info(test);
    }

    /**
     * Test of getDomainQueryMap method, of class DataSource.
     */
    @Test
    public void testGetDomainQueryMap()
    {
        logger.info("\n=========================\nTESTING getDomainQueryMap");
        assertNotNull("Missing Context Object", ctx);

        PatientGraphRDFDataSource instance = (PatientGraphRDFDataSource) ctx.getBean(
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

        PatientGraphRDFDataSource instance = (PatientGraphRDFDataSource) ctx.getBean(
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
        PatientGraphRDFDataSource instance = (PatientGraphRDFDataSource) ctx.getBean(
                "SamplePatientDataGraphSource");
        boolean expResult = false;
        boolean result = instance.isDomainSupported(domain);

        if (result)
        {
            logger.log(Level.INFO, "Domain {0} is supported", domain);
        }
        else
        {
            logger.log(Level.INFO, "Domain {0} is NOT upported", domain);
        }

        // assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    
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
    @Test
    public void testSetDomainQueryMap()
    {
        logger.info("\n=========================\nTESTING testSetDomainQueryMap");
        assertNotNull("Missing Context Object", ctx);

        Map<String, String> domainQueryMap = null;
        PatientGraphRDFDataSource instance = (PatientGraphRDFDataSource) ctx.getBean(
                "SamplePatientDataGraphSource");
        instance.setDomainQueryMap(domainQueryMap);

        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of setFmqlEndpoint method, of class DataSource.
     */
    @Test
    public void testSetPatientGraphEndpoint()
    {
        logger.info("\n=========================\nTESTING setPatientGraphEndpoint");
        assertNotNull("Missing Context Object", ctx);

        String endpoint = "http://localhost";
        PatientGraphRDFDataSource instance = (PatientGraphRDFDataSource) ctx.getBean(
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
