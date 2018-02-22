/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.configuration;

/**
 *
 * @author Rider1
 */

import javax.servlet.ServletContext;
import org.springframework.security.web.context.*;
import org.springframework.web.multipart.support.MultipartFilter;

public class DCSecurityInitializer extends AbstractSecurityWebApplicationInitializer{
    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        insertFilters(servletContext, new MultipartFilter());
    }
}
