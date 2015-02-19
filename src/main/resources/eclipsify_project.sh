cd ../../../../WebCrawlerServerV2Common
mvn source:jar install
cd ../WebCrawlerServerV2Master
mvn eclipse:eclipse -DdownloadSources=true
