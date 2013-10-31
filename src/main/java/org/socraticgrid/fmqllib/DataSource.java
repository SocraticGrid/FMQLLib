/*-
 *
 * *************************************************************************************************************
 *  Copyright (C) 2013 by Cognitive Medical Systems, Inc
 *  (http://www.cognitivemedciine.com) * * Licensed under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in compliance *
 *  with the License. You may obtain a copy of the License at * *
 *  http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable
 *  law or agreed to in writing, software distributed under the License is *
 *  distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied. * See the License for the specific language
 *  governing permissions and limitations under the License. *
 * *************************************************************************************************************
 *
 * *************************************************************************************************************
 *  Socratic Grid contains components to which third party terms apply. To comply
 *  with these terms, the following * notice is provided: * * TERMS AND
 *  CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION * Copyright (c) 2008,
 *  Nationwide Health Information Network (NHIN) Connect. All rights reserved. *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that * the following conditions are met:
 *  - Redistributions of source code must retain the above copyright notice, this
 *  list of conditions and the *     following disclaimer. * - Redistributions in
 *  binary form must reproduce the above copyright notice, this list of
 *  conditions and the *     following disclaimer in the documentation and/or
 *  other materials provided with the distribution. * - Neither the name of the
 *  NHIN Connect Project nor the names of its contributors may be used to endorse
 *  or *     promote products derived from this software without specific prior
 *  written permission. * * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS
 *  AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED * WARRANTIES, INCLUDING,
 *  BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  A * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *  OR CONTRIBUTORS BE LIABLE FOR * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, *
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 *  OR BUSINESS INTERRUPTION HOWEVER * CAUSED AND ON ANY THEORY OF LIABILITY,
 *  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 *  OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, * EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. * * END OF TERMS AND CONDITIONS *
 * *************************************************************************************************************
 */
package org.socraticgrid.fmqllib;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * DOCUMENT ME!
 *
 * @author  Jerry Goodnough
 */
public class DataSource extends org.socraticgrid.patientdataservices.BaseDataSource
{
    private static final Logger logger = Logger.getLogger(
            PatientGraphRDFDataSource.class.getName());
    private Map<String, String> domainQueryMap; //
    private String fmqlEndpoint; // DATA SOURCE endpoint

    public DataSource()
    {
    }

    @Override
    public InputStream getData(String domain, String id, java.util.Properties props)
    {
        String query = domainQueryMap.get(domain);
        String realQuery = query;
        
        //TODO: use some kind of template framework?
        //add 'id' to the replacement properties.
        Properties templateProps = new Properties();
        if (props != null){
            templateProps.putAll(props);
        }
        templateProps.put("id", id);
        
        for (Map.Entry<Object, Object> entry : templateProps.entrySet()) {
            realQuery = realQuery.replaceAll("@"+entry.getKey().toString()+"@", entry.getValue().toString());
        }
        
        logger.log(Level.FINE, "realQuery={0}", realQuery);

        // Use the new real query string and the endpoint to query fmwl.
        // --------------------------------
        // EXEC the query
        // --------------------------------
        InputStream in = null;

        try
        {
            in = querySource(realQuery, this.fmqlEndpoint);
        }
        catch (Exception ex)
        {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return in;
    }

    /**
     * Get the value of domainQueryMap.
     *
     * @return  the value of domainQueryMap
     */
    public Map<String, String> getDomainQueryMap()
    {
        return domainQueryMap;
    }

    /**
     * Get the value of fmqlEndpoint.
     *
     * @return  the value of fmqlEndpoint
     */
    public String getFmqlEndpoint()
    {
        return fmqlEndpoint;
    }

    @Override
    public boolean isDomainSupported(String domain)
    {
        return this.domainQueryMap.containsKey(domain);
    }

    public InputStream querySource(String query, String ep) throws Exception
    {
        logger.log(Level.FINE, "fmql ep= {0}", ep);

        String sparqlrs = ep.concat("?fmql=").concat(URLEncoder.encode(query,
                    "UTF-8"));
// + "?fmql="
// + URLEncoder.encode(query, "UTF-8");
        logger.log(Level.FINE, "ep+query= {0}", sparqlrs);

        URL sparqlr = new URL(sparqlrs);

        // 1. Make the query
        URLConnection sparqlc = sparqlr.openConnection();

        // 2. Read the Response
        InputStream in = sparqlc.getInputStream();

        return in;
    }

    /**
     * This will send the given FMQL format query to the FILEMAN interface, and get a
     * response as a BufferedReader object.
     *
     * @param   query
     *
     * @return
     *
     * @throws  Exception
     */
    public BufferedReader request(String query, String ep) throws Exception
    {
        return new BufferedReader(new InputStreamReader(querySource(query, ep)));
    }

    /**
     * Set the value of domainQueryMap.
     *
     * @param  domainQueryMap  new value of domainQueryMap
     */
    public void setDomainQueryMap(Map<String, String> domainQueryMap)
    {
        this.domainQueryMap = domainQueryMap;
    }

    /**
     * Set the value of fmqlEndpoint.
     *
     * @param  fmqlEndpoint  new value of fmqlEndpoint
     */
    public void setFmqlEndpoint(String fmqlEndpoint)
    {
        this.fmqlEndpoint = fmqlEndpoint;
    }
}
