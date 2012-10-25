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
    
    void create(String branch, String job) {
        println "creating zeuth $branch $job"
        post('api/iis/create', [branch: branch, site: job] )
    }
    void delete(String branch, String job) {
        println "deleting zeuth $branch $job"
        post('api/iis/remove', [branch: branch, site: job] )
    }
        
    protected Integer post(String path, params = [:], ContentType contentType = ContentType.URLENC) {

        HTTPBuilder http = new HTTPBuilder("http://zeuth.lan.im.com/")
        
        Integer status = HttpStatus.SC_EXPECTATION_FAILED

        http.handler.failure = { resp ->
            def msg = "Unexpected failure on $path: ${resp.statusLine} ${resp.status}"
            status = resp.statusLine.statusCode
            throw new Exception(msg)
        }
        
        http.request(POST) {
            uri.path = path
            body = params
            requestContentType = URLENC
            response.success = { resp, InputStreamReader reader ->
                assert resp.statusLine.statusCode == 200
            }
        }
        return 200
    }
}

