package com.rallydev.intellij.wsapi

import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.HttpStatus
import org.apache.commons.httpclient.UsernamePasswordCredentials
import org.apache.commons.httpclient.auth.AuthScope
import org.apache.commons.httpclient.auth.InvalidCredentialsException
import org.apache.commons.httpclient.methods.GetMethod

class RallyClient extends HttpClient {

    String username
    String password

    ApiResponse makeRequest(GetRequest request) {
        state.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password))
        def method = new GetMethod(request.url)

        int code = executeMethod(method)
        println method.responseBodyAsString
        switch (code) {
            case HttpStatus.SC_OK:
                return new ApiResponse(method.responseBodyAsString)
                break;
            case HttpStatus.SC_UNAUTHORIZED:
                throw new InvalidCredentialsException('Invalid credentials')
            default:
                break;
        }
    }

}
