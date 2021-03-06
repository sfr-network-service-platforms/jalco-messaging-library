/**
 * Copyright 2012,2013 - SFR (http://www.sfr.com/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.sfr.tv.jms.cnxmgt.tasks;

import java.util.concurrent.Callable;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.NamingException;
import net.sfr.tv.exceptions.ResourceInitializerException;
import net.sfr.tv.jms.context.JmsContext;
import net.sfr.tv.model.Credentials;
import org.apache.log4j.Logger;

/**
 *
 * @author matthieu.chaplin@sfr.com
 * @param <T>
 */
public class ConnectTask<T extends JmsContext> implements Callable<JmsContext> {
    
    private static final Logger logger = Logger.getLogger(ConnectTask.class);
    
    private final Context jndiContext;
    
    private final String clientId;
    
    private final String connectionFactory;
    
    private final Credentials credentials;
    
    private final ExceptionListener el;
    
    public ConnectTask(Context jndiContext, String clientId, String connectionFactory, Credentials credentials, ExceptionListener el) {
        this.jndiContext = jndiContext;
        this.clientId = clientId;
        this.connectionFactory = connectionFactory;
        this.credentials = credentials;
        this.el = el;
    }
    
    /**
     * Create a Session in CLIENT_ACKNOWLEDGE mode.
     * 
     * Refer to JMS spec for further information.
     * 
     * @return 
     * @throws ResourceInitializerException
     */
    @Override
    public JmsContext call() throws Exception {
        
        Connection cnx;
        Session session;
        
        try {
                    
            cnx = getConnection(jndiContext, clientId, connectionFactory, credentials);
            cnx.setExceptionListener((ExceptionListener) el);
            //session = ContextFactory.createSession(cnx);
            session = cnx.createSession(false, Session.DUPS_OK_ACKNOWLEDGE);
          
            return new JmsContext(jndiContext, cnx, session);
            
        } catch (ResourceInitializerException rie) {
            logger.error("Error while attempting to create a JMS connection : ".concat(rie.getMessage()));
            return null;
        }
    }
    
    /**
     * Creates a JMS Connection.
     * 
     * Refer to JMS spec for further information.
     * 
     * @param ctx
     * @param clientId
     * @param connectionFactory 
     * @param login
     * @param password
     * @return
     * @throws ResourceInitializerException
     */
    private Connection getConnection(Context ctx, String clientId, String connectionFactory, Credentials credentials) throws ResourceInitializerException {

        logger.info("Trying to connect to ".concat(connectionFactory != null ? connectionFactory : "null").concat(", with clientId : ".concat(clientId != null ? clientId : "null")));
        
        Connection cnx = null;

        try {
            // TODO : Variabiliser Consumer factory
            ConnectionFactory cf = (ConnectionFactory) ctx.lookup("/".concat(connectionFactory));
            cnx = cf.createConnection(credentials.getLogin(), credentials.getPassword());
            cnx.setClientID(clientId);
        } catch (NamingException ex) {
            throw new ResourceInitializerException(ex);
        } catch (JMSException ex) {
            if (cnx != null) {
                try { cnx.close(); } catch (JMSException e) { logger.error("Unable to gracefully close connection upon error ! : ".concat(e.getMessage())); };
            }
            throw new ResourceInitializerException(ex);
        }

        return cnx;
    }
}
