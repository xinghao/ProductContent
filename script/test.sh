#!/bin/sh
# source the properties:  
#. ../conf/deploy.properties  

#echo "Release Version: $release"
#scp -r ../$release root@192.168.26.73:/opt/downloads/device
#rsync --progress --stats --archive --compress-level=0 --rsh='ssh' ../$release root@192.168.26.73:/opt/downloads/device

# Scan RT server everyhour
#53 * * * * root cd /opt/apps/rtmonitor/current && /opt/apps/rtmonitor/current/seed.sh > /opt/apps/rtmonitor/current/cront.log
#57 * * * * /bin/bash -l -c 'cd /opt/apps/rtmonitor/current && /opt/apps/rtmonitor/current/seed.sh > /opt/apps/rtmonitor/current/cront.log'

java -cp :lib/jsoup-1.6.1.jar:lib/antlr-2.7.7.jar:lib/axis-1.4.jar:lib/axis-jaxrpc-1.2.1.jar:lib/c3p0-0.9.1.jar:lib/commons-codec-1.6.jar:lib/commons-collections-3.2.1.jar:lib/dom4j-1.6.1.jar:lib/postgresql-9.1-901.jdbc4.jar:lib/hibernate-c3p0-4.1.0.Final.jar:lib/hibernate-commons-annotations-4.0.1.Final.jar:lib/hibernate-core-4.1.0.Final.jar:lib/hibernate-entitymanager-4.1.0.Final.jar:lib/hibernate-jpa-2.0-api-1.0.1.Final.jar:lib/javassist-3.15.0-GA.jar:lib/jboss-logging-3.1.0.CR2.jar:lib/jboss-transaction-api_1.1_spec-1.0.0.Final.jar:lib/log4j-1.2.15.jar:jar/product_content.jar com.airarena.products.aws.main.ProductContent 5




