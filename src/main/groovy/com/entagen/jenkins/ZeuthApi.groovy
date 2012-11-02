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

        def url = new URL ("http://zeuth.lan.im.com/" + path) 
        def conn = url.openConnection() 
        conn.setRequestMethod("POST") 

        String data = "branch=" + params.get('branch') + "&site=" + params.get('site')

        conn.doOutput = true 

        Writer wr = new OutputStreamWriter(conn.outputStream) 
        wr.write(data) 
        wr.flush() 
        wr.close() 

        conn.connect() 
        
        println conn.content.text 
        
        return 200
    }
}

