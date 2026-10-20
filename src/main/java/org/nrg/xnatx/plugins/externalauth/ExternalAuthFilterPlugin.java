package org.nrg.xnatx.plugins.externalauth;

import lombok.extern.slf4j.Slf4j;
import org.nrg.framework.annotations.XnatPlugin;
import org.nrg.xdat.services.AliasTokenService;
import org.nrg.xnat.security.BaseXnatSecurityExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@XnatPlugin(value = "externalAuthFilterPlugin",
            name = "XNAT External Authentication Filter Plugin",
            logConfigurationFile = "org/nrg/xnatx/plugins/externalauth/external-auth-filter-logback.xml")
@ComponentScan("org.nrg.xnatx.plugins.externalauth")
@Slf4j
public class ExternalAuthFilterPlugin extends BaseXnatSecurityExtension {
    private final AliasTokenService aliasTokenService;

    @Autowired
    public ExternalAuthFilterPlugin(final AliasTokenService aliasTokenService) {
        this.aliasTokenService = aliasTokenService;
    }

    @Override
    public String getAuthMethod() {
        return "Any";
    }

    @Override
    public void configure(final HttpSecurity http) {
        if (checkForCoreXnatFilter()) {
            http.addFilterAfter(new ExternalClientAuthFilter(aliasTokenService), AnonymousAuthenticationFilter.class);
        }
    }

    private boolean checkForCoreXnatFilter() {
        try {
            Class.forName("org.nrg.xnat.security.ExternalClientAuthFilter");
            log.warn("I found the class org.nrg.xnat.security.ExternalClientAuthFilter on the classpath. That means you're running a newer version of XNAT that already has this filter built in. You should remove the ExternalAuthFilterPlugin from your plugins directory to avoid conflicts.");
            return false;
        } catch (ClassNotFoundException e) {
            log.debug("Class org.nrg.xnat.security.ExternalClientAuthFilter not found, so we can safely add our own filter.");
            return true;
        }
    }
}
