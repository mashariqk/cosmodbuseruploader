# Azure Cosmos DB Uploader

This small spring boot app reads a .txt file from dropfolder/inbound 
directory and uploads it to the Azure Cosmos DB. After the file
has been processed it is moved to the dropfolder/archive folder.

Makes use of Spring Boot, Spring Batch and Spring Integration

To make this work you will need to put the following values in the
src/main/resources/application.properties file

```properties
# Specify the DNS URI of your Azure Cosmos DB.
azure.cosmosdb.uri=<Your cosmodb uri>

# Specify the access key for your database.
azure.cosmosdb.key=<Your cosmodb key>

# Specify the name of your database.
azure.cosmosdb.database=<Your cosmodb name>
debug=false
# Prevent jobs to be executed at startup
spring.batch.job.enabled=false
```

Make sure you create folders dropfolder/archive and dropfolder/processing. Since git ignores empty folders therefore these will not be created when you clone the repo

