package com.entagen.jenkins


import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import org.apache.http.client.HttpResponseException
import org.apache.http.conn.HttpHostConnectException


/**
 *
 * @author svondruska
 */
class ZeuthApiReadOnly extends ZeuthApi {
       
    @Override
    protected Integer post(String path, params = [:], ContentType contentType = ContentType.URLENC) {
        println "READ ONLY! skipping POST to $path with params: ${params}, postBody:\n$postBody"
        // we never want to post anything with a ReadOnly API, just return OK for all requests to it
        return HttpStatus.SC_OK
    }
}

