# Cloudera-HBase-CDC
How to implement Change Data Capture (CDC) with HBase

Steps:

1. Create an HBase table with versions
1. Create a Hive table with the HBase SerDe

1. Insert rows into Hive table every 1 second
1. Insert rows into Hive table every 5 seconds

1. Use java to query HBase for all records changed in the last 1 second

This will simulate extracting HBase records that have been modified in the last 24 hours since the last batch process.

Change Data Capture

# Running

```
[christoph@hazel] $ java -jar target/ztrew-cloudera-hbase-cdc-0.0.0-SNAPSHOT-jar-with-dependencies.jar

Valid options are:
                 get-latest-records : Get records modified in the last 1 second from HBase.
          update-every-five-seconds : Update the hive record every 5 seconds.
                update-every-second : Update the hive record every 1 second.

Wed Mar 21 22:49:43 : ~/IdeaProjects/hbasecdc
[christoph@hazel] $
```

```
hbase(main):015:0> create 'hbase_table_3', { NAME=>'c', VERSIONS=>20}
0 row(s) in 2.3690 seconds

=> Hbase::Table - hbase_table_3

hbase(main):017:0> describe 'hbase_table_3'
Table hbase_table_3 is ENABLED
hbase_table_3
COLUMN FAMILIES DESCRIPTION
{NAME => 'c', DATA_BLOCK_ENCODING => 'NONE', BLOOMFILTER => 'ROW', REPLICATION_SCOPE => '0', VERSIONS => '20', COMPRESSION => 'NONE', M
IN_VERSIONS => '0', TTL => 'FOREVER', KEEP_DELETED_CELLS => 'FALSE', BLOCKSIZE => '65536', IN_MEMORY => 'false', BLOCKCACHE => 'true'}
1 row(s) in 0.0290 seconds

hbase(main):018:0>
```
