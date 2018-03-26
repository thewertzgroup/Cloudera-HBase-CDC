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

```
[christoph@edge ~]$ hbase shell
18/03/26 17:00:16 INFO Configuration.deprecation: hadoop.native.lib is deprecated. Instead, use io.native.lib.available
HBase Shell; enter 'help<RETURN>' for list of supported commands.
Type "exit<RETURN>" to leave the HBase Shell
Version 1.2.0-cdh5.12.0, rUnknown, Thu Jun 29 04:38:21 PDT 2017

hbase(main):001:0> list
TABLE
hbase_table_1
hbase_table_2
2 row(s) in 0.2420 seconds

=> ["hbase_table_1", "hbase_table_2"]
hbase(main):002:0> scan 'hbase_table_1'
ROW                                COLUMN+CELL
 100                               column=a:b, timestamp=1521245555289, value=Matt
 100                               column=a:c, timestamp=1521245555289, value=2
 100                               column=d:e, timestamp=1521245555289, value=2
1 row(s) in 0.1920 seconds

hbase(main):003:0> scan "hbase_table_2"
ROW                                COLUMN+CELL
0 row(s) in 0.0220 seconds

hbase(main):015:0> create 'hbase_table_3', { NAME=>'c', VERSIONS=>20}
0 row(s) in 2.3690 seconds

]hbase(main):017:0> describe 'hbase_table_3'
Table hbase_table_3 is ENABLED
hbase_table_3
COLUMN FAMILIES DESCRIPTION
{NAME => 'c', DATA_BLOCK_ENCODING => 'NONE', BLOOMFILTER => 'ROW', REPLICATION_SCOPE => '0', VERSIONS => '20', COMPRESSION => 'NONE', M
IN_VERSIONS => '0', TTL => 'FOREVER', KEEP_DELETED_CELLS => 'FALSE', BLOCKSIZE => '65536', IN_MEMORY => 'false', BLOCKCACHE => 'true'}
1 row(s) in 0.0290 seconds

hbase(main):033:0> put 'hbase_table_3', '100', 'c:first', 'chrisoph'
0 row(s) in 0.1250 seconds

hbase(main):039:0> put 'hbase_table_3', '100', 'c:middle', 'eric'
0 row(s) in 0.0090 seconds

hbase(main):040:0> put 'hbase_table_3', '100', 'c:last', 'wertz'
0 row(s) in 0.0070 seconds

hbase(main):041:0> put 'hbase_table_3', '100', 'c:age', 18
0 row(s) in 0.0120 seconds

hbase(main):042:0> scan hbase_table_3
NameError: undefined local variable or method `hbase_table_3' for #<Object:0x7a4c472b>

hbase(main):043:0> scan 'hbase_table_3'
ROW                                COLUMN+CELL
 100                               column=c:age, timestamp=1522101454227, value=18
 100                               column=c:first, timestamp=1522101397713, value=chrisoph
 100                               column=c:last, timestamp=1522101440964, value=wertz
 100                               column=c:middle, timestamp=1522101423514, value=eric
1 row(s) in 0.0240 seconds

hbase(main):044:0> put 'hbase_table_3', '100', 'c:age', 19
0 row(s) in 0.0070 seconds

hbase(main):045:0> scan 'hbase_table_3'
ROW                                COLUMN+CELL
 100                               column=c:age, timestamp=1522101477190, value=19
 100                               column=c:first, timestamp=1522101397713, value=chrisoph
 100                               column=c:last, timestamp=1522101440964, value=wertz
 100                               column=c:middle, timestamp=1522101423514, value=eric
1 row(s) in 0.0280 seconds

hbase(main):046:0> describe 'hbase_table_3'
Table hbase_table_3 is ENABLED
hbase_table_3
COLUMN FAMILIES DESCRIPTION
{NAME => 'c', DATA_BLOCK_ENCODING => 'NONE', BLOOMFILTER => 'ROW', REPLICATION_SCOPE => '0', VERSIONS => '20', COMPRESSION => 'NONE', M
IN_VERSIONS => '0', TTL => 'FOREVER', KEEP_DELETED_CELLS => 'FALSE', BLOCKSIZE => '65536', IN_MEMORY => 'false', BLOCKCACHE => 'true'}
1 row(s) in 0.0300 seconds

hbase(main):047:0> list 'hbase_table_3'
TABLE
hbase_table_3
1 row(s) in 0.0030 seconds

=> ["hbase_table_3"]
hbase(main):048:0> get 'hbase_table_3', 'c:age'
COLUMN                             CELL
0 row(s) in 0.0200 seconds

hbase(main):049:0> scan 'hbase_table_3'
ROW                                COLUMN+CELL
 100                               column=c:age, timestamp=1522101477190, value=19
 100                               column=c:first, timestamp=1522101397713, value=chrisoph
 100                               column=c:last, timestamp=1522101440964, value=wertz
 100                               column=c:middle, timestamp=1522101423514, value=eric
1 row(s) in 0.0150 seconds

hbase(main):050:0> get 'hbase_table_3', '100', 'c:age'
COLUMN                             CELL
 c:age                             timestamp=1522101477190, value=19
1 row(s) in 0.0170 seconds

hbase(main):060:0> describe 'hbase_table_3'
Table hbase_table_3 is ENABLED
hbase_table_3
COLUMN FAMILIES DESCRIPTION
{NAME => 'c', DATA_BLOCK_ENCODING => 'NONE', BLOOMFILTER => 'ROW', REPLICATION_SCOPE => '0', VERSIONS => '20', COMPRESSION => 'NONE', M
IN_VERSIONS => '0', TTL => 'FOREVER', KEEP_DELETED_CELLS => 'FALSE', BLOCKSIZE => '65536', IN_MEMORY => 'false', BLOCKCACHE => 'true'}
1 row(s) in 0.0620 seconds

hbase(main):061:0> scan 'hbase_table_3', {COLUMNS => ['c:age'], VERSIONS => 10}
ROW                                COLUMN+CELL
 100                               column=c:age, timestamp=1522102510292, value=20
 100                               column=c:age, timestamp=1522101477190, value=19
 100                               column=c:age, timestamp=1522101454227, value=18
1 row(s) in 0.0120 seconds

hbase(main):062:0> scan 'hbase_table_3', {VERSIONS => 10}
ROW                                COLUMN+CELL
 100                               column=c:age, timestamp=1522102510292, value=20
 100                               column=c:age, timestamp=1522101477190, value=19
 100                               column=c:age, timestamp=1522101454227, value=18
 100                               column=c:first, timestamp=1522101397713, value=chrisoph
 100                               column=c:last, timestamp=1522101440964, value=wertz
 100                               column=c:middle, timestamp=1522101423514, value=eric
1 row(s) in 0.0140 seconds

hbase(main):063:0>
```
