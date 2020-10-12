package io.quarkus.reproducer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * ------------
 * FIRST ISSUE:
 * ------------
 * Run mvn clean package
 * => NO logs are issued to console during tests (while they are when using the runner jar)
 *
 *--------------
 * SECOND ISSUE:
 * -------------
 * Uncomment the two jboss logger lines in the code below
 * Run mvn clean package
 * => Still no logs are issued in the console
 * => But now the runner jar issues a stack trace:
 * Exception in thread "main" java.lang.ExceptionInInitializerError
        at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
        at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
        at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
        at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:490)
        at java.base/java.lang.Class.newInstance(Class.java:584)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:60)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:38)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:106)
        at io.quarkus.runner.GeneratedMain.main(GeneratedMain.zig:29)
Caused by: java.lang.RuntimeException: Failed to start quarkus
        at io.quarkus.runner.ApplicationImpl.<clinit>(ApplicationImpl.zig:189)
        ... 9 more
Caused by: java.lang.RuntimeException: Failed to initialize Arc
        at io.quarkus.arc.Arc.initialize(Arc.java:26)
        at io.quarkus.arc.runtime.ArcRecorder.getContainer(ArcRecorder.java:24)
        at io.quarkus.deployment.steps.ArcProcessor$generateResources-1025303321.deploy_0(ArcProcessor$generateResources-1025303321.zig:76)
        at io.quarkus.deployment.steps.ArcProcessor$generateResources-1025303321.deploy(ArcProcessor$generateResources-1025303321.zig:40)
        at io.quarkus.runner.ApplicationImpl.<clinit>(ApplicationImpl.zig:147)
        ... 9 more
Caused by: java.lang.ExceptionInInitializerError
        at java.base/java.lang.Class.forName0(Native Method)
        at java.base/java.lang.Class.forName(Class.java:398)
        at io.quarkus.reproducer.HelloResource_Bean.<init>(HelloResource_Bean.zig:40)
        at io.quarkus.arc.setup.Default_ComponentsProvider.addBeans1(Default_ComponentsProvider.zig:134)
        at io.quarkus.arc.setup.Default_ComponentsProvider.getComponents(Default_ComponentsProvider.zig:38)
        at io.quarkus.arc.impl.ArcContainerImpl.<init>(ArcContainerImpl.java:113)
        at io.quarkus.arc.Arc.initialize(Arc.java:20)
        ... 13 more
Caused by: java.lang.IllegalStateException: Maximum number of attachments exceeded
        at org.jboss.logmanager.LoggerNode.attachIfAbsent(LoggerNode.java:428)
        at org.jboss.logmanager.Logger.attachIfAbsent(Logger.java:194)
        at org.jboss.logging.JBossLogManagerProvider.doGetLogger(JBossLogManagerProvider.java:93)
        at org.jboss.logging.JBossLogManagerProvider.getLogger(JBossLogManagerProvider.java:53)
        at org.jboss.logging.Logger.getLogger(Logger.java:2465)
        at org.jboss.logging.Logger.getLogger(Logger.java:2490)
        at io.quarkus.reproducer.HelloResource.<clinit>(HelloResource.java:24)
        ... 20 more 
 * 
 */
@Path("/hello")
public class HelloResource {

    private static org.apache.logging.log4j.Logger LOG4J_LOGGER = org.apache.logging.log4j.LogManager.getLogger(HelloResource.class);
    //private static org.jboss.logging.Logger JBOSS_LOGGER = org.jboss.logging.Logger.getLogger(HelloResource.class);

    @Path("/log4j")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String log4j() {
        System.out.println("Invoking HelloResource.log4j()");
        LOG4J_LOGGER.info("Invoking log4j logger");
        return "log4j";
    }

    @Path("/jboss")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String jboss() {
        System.out.println("Invoking HelloResource.jboss()");
        //JBOSS_LOGGER.info("Invoking jboss logger");
        return "jboss";
    }
}