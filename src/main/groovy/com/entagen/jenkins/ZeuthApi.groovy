package com.entagen.jenkins

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.*
import org.apache.http.conn.HttpHostConnectException
import org.apache.http.client.HttpResponseException
import org.apache.http.HttpStatus
import org.apache.http.HttpRequestInterceptor
import org.apache.http.protocol.HttpContext
import org.apache.http.HttpRequest


/**
 *
 * @author svondruska
 */
public class ZeuthApi {
    String zeuthUrl;
    
    public void setZeuthUrl(String zeuthUrl) {
        this.zeuthUrl = zeuthUrl
    }
    
    void create(String branch, String job) {
        println "creating zeuth $branch $job"
        post('api/iis/create', [branch: branch, site: job] )
    }
    void delete(String branch, String job) {
        println "deleting zeuth $branch $job"
        post('api/iis/remove', [branch: branch, site: job] )
    }
        
    protected Integer post(String path, params = [:], ContentType contentType = ContentType.URLENC) {
        Integer status = HttpStatus.SC_EXPECTATION_FAILED
        
        HTTPBuilder http = new HTTPBuilder(zeuthUrl)
        http.post(path: path, body: params,
                requestContentType: contentType) { resp ->
            assert resp.statusLine.statusCode < 400
            status = resp.statusLine.statusCode
        }
        
        return status

    }
}

