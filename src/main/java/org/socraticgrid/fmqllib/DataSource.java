/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.fmqllib;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jerry Goodnough
 */
public class DataSource extends org.socraticgrid.patientdataservices.BaseDataSource
{

    public DataSource()
    {
    }
    
    private String fmqlEndpoint; // DATA SOURCE endpoint

    /**
     * Get the value of fmqlEndpoint
     *
     * @return the value of fmqlEndpoint
     */
    public String getFmqlEndpoint()
    {
        return fmqlEndpoint;
    }

    /**
     * Set the value of fmqlEndpoint
     *
     * @param fmqlEndpoint new value of fmqlEndpoint
     */
    public void setFmqlEndpoint(String fmqlEndpoint)
    {
        this.fmqlEndpoint = fmqlEndpoint;
    }

    @Override
    public boolean isDomainSupported(String domain)
    {
        return this.domainQueryMap.containsKey(domain); 
    }
    
    
    private Map<String, String> domainQueryMap;    //

    /**
     * Get the value of domainQueryMap
     *
     * @return the value of domainQueryMap
     */
    public Map<String, String> getDomainQueryMap()
    {
        return domainQueryMap;
    }

    /**
     * Set the value of domainQueryMap
     *
     * @param domainQueryMap new value of domainQueryMap
     */
    public void setDomainQueryMap(Map<String, String> domainQueryMap)
    {
        this.domainQueryMap = domainQueryMap;
    }


    @Override
    public InputStream getData(String domain, String id, java.util.Properties props)
    {
        String query = domainQueryMap.get(domain);
        String realQuery=String.format(query,id);
        
        System.out.println("realQuery="+realQuery);
        
        //Use the new real query string and the endpoint to query fmwl.
        //--------------------------------
        // EXEC the query
        //--------------------------------
        InputStream in = null;
        try {
            in = querySource(realQuery, this.fmqlEndpoint);
        } catch (Exception ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return in;
    }
    
    /**
     * This will send the given FMQL format query to the FILEMAN interface, 
     * and get a response as a BufferedReader object.
     * 
     * @param query
     * @return
     * @throws Exception
     */
    public BufferedReader request(String query, String ep) throws Exception {
        return new BufferedReader( 
                new InputStreamReader(querySource(query, ep) ) );
    }
    public InputStream querySource(String query, String ep) throws Exception {
        
        System.out.println("ep= "+ ep);
        
        String sparqlrs =  ep.concat("?fmql=").concat(URLEncoder.encode(query, "UTF-8"));
//                + "?fmql=" 
//                + URLEncoder.encode(query, "UTF-8");
        
        System.out.println("ep+query= "+ sparqlrs);

        URL sparqlr = new URL(sparqlrs);
        // 1. Make the query
        URLConnection sparqlc = sparqlr.openConnection();
        // 2. Read the Response
        InputStream in = sparqlc.getInputStream();
        
        return in;
    }


}
