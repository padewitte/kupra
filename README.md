Mongo REST Camel - MRC
======================


A simple *REST* interface for **MongoDB** running on a JVM.   With MRC you will be able to access your collection with GET, POST, PUT and DELETE.

Getting started
-----------------------------

### Installing
Download, launch MongoDB,  unzip MRC-0.0.3.zip and launch mrc.sh 127.0.0.1/test or mrc.bat 127.0.0.1/test depending on your OS. You only need a JRE upper to 1.5 in your path.
By default port 8667 is bind. More launch option with --help.

### Running first example

Once you run MRC, you can start testing it. Curl come with commons Linux distro's. On windows you can download [curl](http://curl.haxx.se/download.html) or just run JUnit tests.

**Add a record in test collection**   
`curl -X POST -d "{'_id' : '1', 'name' : 'Sylvain CHAVANEL'}" http://127.0.0.1:8667/mrc/test`

**Add a second record**   
`curl -X POST -d "{'_id' : '2', 'name' : 'Thomas VOECKLER'}" http://127.0.0.1:8667/mrc/test` 

**Show the content of test collection**   
`curl -X GET http://127.0.0.1:8667/mrc/test`   
produce  `[{ "_id" : "1" , "name" : "Sylvain CHAVANEL"}, { "_id" : "2" , "name" : "Thomas VOECKLER"}]`   

**Show you the collection item with id 2**   
`curl -X GET http://127.0.0.1:8667/mrc/test/2`   
produce `{ "_id" : "2" , "name" : "Thomas VOECKLER"}`

**Count items in collection**   
`curl -X GET  -H "count:true" http://127.0.0.1:8667/mrc/test`   
produce   `2`

**List all items where name equal CHAVANEL**   
`curl -X GET -H "query:{'name' : 'Sylvain CHAVANEL'}"  http://127.0.0.1:8667/mrc/test`   
produce `[{ "_id" : "1" , "name" : "Sylvain CHAVANEL"}]`

**Perform an update replacing object with id 1**   
`curl -X PUT -d "{'_id' : '1', 'name' : 'Thibaut PINOT'}" http://127.0.0.1:8667/mrc/test/1` and then list again the test collection to see an update of your record.

**Delete items**   
`curl -X DELETE -H "query:{name' : 'Thomas VOECKLER'}" http://127.0.0.1:8667/mrc/test`


Documentation
=============

Why ?
-------------------
MongoDB already provide a [simple REST interface](http://docs.mongodb.org/ecosystem/tools/http-interfaces/#simple-rest-interface|docs.mongodb.org).
This interface could be quite sufficient for queries but do not provide any save or update operations. After testing all solutions listed by MongoDB website i did not find a open source solution available to expose all simple MongoDB operations in REST an simple to install. 
After a quick read on Camel MongoDB documentation idea of Mongo Rest Camel was born.

Disclaimer
--------------------
**This is alpha work. MRC is not production ready. Feel free to contribute by filing bug or improvement request in GitHub or even better by pushing pull request or forking.**



Launch options
----------------------------------------------------------------------------

Depending on yout system mrc.ksh or mrc.bat is the launcher of Mongo REST Camel.
 
###Usage
`mrc.ksh [options] mongoDbUri`    

Format of *mongoDbUri* is : [mongodb://][username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]    
Read [MongoDB uri documentation](http://docs.mongodb.org/manual/reference/connection-string/) for full documentation and possibilities.

For a quick test start mongod with no option and then launch :   
	`mrc.ksh -doc 127.0.0.0:27017/mrc`    
Open http://127.0.0.1:8669/ with a modern browser and enjoy your first queries.

###Basic options
You can change the default values by specifying those options :
<table  width="95%">
<thead>
    <tr>
        <th>parameter</th>
        <th>description</th>
        <th>default value</th>
    </tr>
</thead>
<tr><td><strong>-a, --bindingAdress</strong></td><td>Adress binded by REST server.</td><td>0.0.0.0</td></tr>
<tr><td><strong>-c, --bindingContext</strong></td><td>Context of REST server.</td><td>mrc</td></tr>
<tr><td><strong>-p, --bindingPort</strong></td><td>Listen port of REST server.</td><td>mrc</td></tr>
<tr><td><strong> -h, --help</strong></td><td>Show usage on standard output</td><td></td></tr>
<tr><td><strong>-cont, --content</strong></td><td>If set, Start an additional HTTP content server to serve static files if set to true</td><td>false</td></tr>
<tr><td><strong>-contFolder, --contentFolder</strong></td><td>System folder place to read the static content</td><td>content</td></tr>
<tr><td><strong>-contPort, --contentPort</strong></td><td>Listen port for static content HTTP server. Default value : listenPort +2</td><td>listenPort +2 </td></tr>
<tr><td><strong>-doc, --documentation</strong></td><td>If set to true start the documentation server on listenPort +1</td><td>false</td></tr>
<tr><td><strong>-docPort, --documentationPort</strong></td><td>Listen port for documentation server</td><td>listenPort +1</td></tr>
<tr><td><strong>-r, --rewrite</strong></td><td>If set to true start a URL rewrite mode on contentPort + 1. More documentation needed./<i>defaultContext</i> requests are routed to MongoDB urls, /documentation requests are routed to documentation server and  others path are routed to content server.</td><td>false</td></tr>
<tr><td><strong>--multiBindingContext</strong></td><td>Specify the context for each additional database binded at launch. For example, if you launch mrc with mrc.ksh 127.0.0.1/test 127.0.0.1/mrc and you want to bind /firstDatabase and /secondDatabase just add --multiBindingContext firstDatabase secondDatabase. This argument should be palce after mongoDBUri launch argument.</td><td></td></tr>
</table>

###Multi-database binding configuration
In order to bind more than one database just specify a list of mongoDbUri ( `,` is the mongoDbUri separators) . In this case the contexts slash plus the database name is added to the binding context. 
See examples for more details.


###Examples

##### Launch targeting 8669 port and connect to local userDatabase with no MongoDB authentication.    
`mrc.ksh --bindingPort 8669 127.0.0.1:27017/userDatabase `

##### Launch server binding to uris on        
`mrc.ksh -doc 127.0.0.1:27017/mrc 127.0.01:27017/test --multiBindingContext mrcContext testContext`

##### Bind two base on default uris, launch documentation and content server with rewrite available
`mrc.ksh -doc -cont -r 127.0.0.1:27017/mrc 127.0.01:27017/test`    
You can now query collection users in database test just like this
`curl -X GET http://127.0.0.1:8670/mrc/test/users`


###Launch from files
If you prefer to specify launch option with a file you should use :    
`mrc.ksh @/path/to/configFile`    
In this case option --mongoDbUri is mandatory and should contained the MongoDB uri.

Here is a sample file with all options used. To test it just launch `mrc.ksh @basicExample.cfg`     

     #
     # Sample config file to launch MRC binding 3 databases
     #
     # Specifie the database to bind. , should be used to split list items in case of a multidatabase binding configuration
     #
     # Comments are only allowed at the file begiinning
     #
     --mongoDbUri
     127.0.0.1:27017/test,127.0.0.1:27017/mrc,127.0.0.1:27017/other
     --multiBindingContext
     firstDatbase,secondBase,thirdBase
     --bindingAdress
     0.0.0.0
     --bindingPort
     8667
     --content
     --contentFolder
     content
     --contentPort
     8669
     --documentation
     --documentationPort
     8668
     --rewrite

GET /mrc/{collection name} 
--------------------------------------------
>**List all items contains in the collection in url.**   

>The request will return all items in JSON Format.   
>You can filter by setting the header query with a valid MongoDB query.  
>You can only count items by settings the header count with value true.  
>You can get col stats by settings getColStats header with value true.  
>You can perform a aggregate operation on collection by setting aggregate header with a [valid pipeline](http://docs.mongodb.org/manual/core/aggregation-pipeline/).  

###Path Params

<table width="95%">
<tr><td><strong>collection name</strong></td><td>The name of the collection to perform the requested action</td><td>mandatory</td></tr>
</table>

### Header Params
<table  width="95%">
<tr><td><strong>query</strong></td><td>Contains a matching query to filter the result of a find. This header should contains [a valid criteria](http://docs.mongodb.org/manual/reference/method/db.collection.find/)</td><td>Optionnal</td></tr>
<tr><td><strong>sortBy</strong></td><td>Sort items with the JSON filter object in header. See [MongoDB sort operation](http://docs.mongodb.org/manual/reference/operator/meta/orderby/) and examples. Should be use with indexed fields.</td><td>Optionnal</td></tr>
<tr><td><strong>limit</strong></td><td>Integer. Limits the number of elements returned.</td><td>Optionnal</td></tr>
<tr><td><strong>numToSkip</strong></td><td>Integer. Discards a given number of elements at the beginning of the cursor.</td><td>Optionnal</td></tr>
<tr><td><strong>batchSize</strong></td><td>Integer. Limits the number of elements returned in one batch. A cursor typically fetches a batch of result objects and store them locally. If batchSize is positive, it represents the size of each batch of objects retrieved. It can be adjusted to optimize performance and limit data transfer. If batchSize is negative, it will limit of number objects returned, that fit within the max batch size limit (usually 4MB), and cursor will be closed. For example if batchSize is -10, then the server will return a maximum of 10 documents and as many as can fit in 4MB, then close the cursor. Note that this feature is different from limit() in that documents must fit within a maximum size, and it removes the need to send a request to close the cursor server-side. The batch size can be changed even after a cursor is iterated, in which case the setting will apply on the next batch retrieval.</td><td>Optionnal</td></tr>
<tr><td><strong>fieldsFilter</strong></td><td>Perform a [projection as describe in MongoDB documentation](http://docs.mongodb.org/manual/reference/method/db.collection.find/). Usefull when to discard or only show some fileds of a document.</td><td>Optionnal</td></tr>
<tr><td><strong>count</strong></td><td>Perform a count of items in collection. No additional header could be add with this one.</td><td>Optionnal</td></tr>
<tr><td><strong>getColStats</strong></td><td>Perform a getColStats instead of a find on collection specifie in url. The  headers querie, count and aggregate could not be used at the same time.</td><td>Optionnal</td></tr>
<tr><td><strong>aggregate</strong></td><td>Perform a aggregate on collection. This header should contain [a valid aggregate pipeline](http://docs.mongodb.org/manual/reference/method/db.collection.aggregate/).  The  Headers querie, count and getColStats could not be used at the same time. </td><td>Optionnal</td></tr>
</table>

### Returned Headers
When performing a GET some extra HTTP headers are returned by MRC.
<table  width="95%">
<tr><td><strong>ResultTotalSize</strong></td><td>Number of items matching the querie in collection. usefull to count ellements combined with limit header set to 1 in request.</td><td>Optionnal</td></tr>
<tr><td><strong>ResultPageSize</strong></td><td>Number of items in current page</td><td>Optionnal</td></tr>
</table>

###Examples

####To get all items in collection test with name matching Sylvain CHAVANEL.
`curl -X GET -H "query:{'name' : 'Sylvain CHAVANEL'}"  http://127.0.0.1:8667/mrc/test` should produce `[{ "_id" : "1" , "name" : "Sylvain CHAVANEL"}]`
	
####To count all items in collection test
`curl -X GET  -H "count:true" http://127.0.0.1:8667/mrc/test` should produce `2`

####To obtain coll stats of collection test
`curl -X GET -H "getColStats:true" http://127.0.0.1:8667/mrc/test` should produce `{ "serverUsed" : "/127.0.0.1:27017" , "ns" : "mrc.test" , "count" : 2 , "size" : 88 , "avgObjSize" : 44.0 , "storageSize" : 4096 , "numExtents" : 1 , "nindexes" : 1 , "lastExtentSize" : 4096 , "paddingFactor" : 1.0 , "systemFlags" : 1 , "userFlags" : 0 , "totalIndexSize" : 8176 , "indexSizes" : { "_id_" : 8176} , "ok" : 1.0}`

####To perform an aggregation operation
To run this example just load data as explain [here](http://docs.mongodb.org/manual/tutorial/aggregation-zip-code-data-set/)
`curl -X GET -H "aggregate:{ $group : { _id : '$state', totalPop : { $sum : '$pop' } } }, { $match : {city :  'EVANSTON' , totalPop : { $gte : 10*1000*1000 } } }" http://127.0.0.1:8667/mrc/zipcode` should produce `2`
TODO working example

####To get all items with all fields except _id
`curl -X GET -H "fieldsFilter:{_id:0}"  http://127.0.0.1:8667/mrc/test`

####To sort items by their id desc
`curl -X GET -H "sortBy:{_id:0}"  http://127.0.0.1:8667/mrc/test`

####To count items matching a query
`curl -i -X GET -H "query:{'city' : 'KEYSTONE'}" -H "limit:-1" http://127.0.0.1:8667/mrc/zipcode` returns   
````
HTTP/1.1 200 OK  
...   
ResultTotalSize: 6   
ResultPageSize: 6  
...   
[{ "city" : "KEYSTONE" , "loc" : [ -86.812861 , 33.236868] , "pop" : 14218 , "state" : "AL" , "_id" : "35007"}]   
````
	
### Going deeper
Item listing is done with a search operation, count with a count, getColStats with a getColStats and aggregate with aggregate.



POST /mrc/{collection name} 
----------------------------------------------------------------------------
>**Add to *collection name* in URL the JSON object in body worload**   
>Return the status JSON object returned by getLastError MongoDB command. 

###Workload
Contain the document to add in collection

###Path Params

<table>
<tr><td><strong>collection name</strong></td><td>The name of the collection to perform the requested action</td><td>mandatory</td></tr>
</table>

### Header Params
<table>
<tr><td><strong>writeConcern</strong></td><td>NONE, NORMAL, SAFE, JOURNALED, FSYNC_SAFE, REPLICA_SAFE. Specifie the [write concern](http://docs.mongodb.org/manual/core/write-concern/) </td><td>optionnal</td></tr>
</table>

###Examples

#####Add a record in test collection
 `curl -X POST -d "{'_id' : '1', 'name' : 'Sylvain CHAVANEL'}" http://127.0.0.1:8667/mrc/test` should produce `TODO`

### Going deeper
Perform a [insert](http://docs.mongodb.org/manual/reference/method/db.collection.insert/) to the database.

### TODO
Allow user to specifie the write concern
 

PUT /mrc/{collection name}
----------------------------------------------------------------------------
>**Perform an update on a collection.** 
>The result of MongoDB getLastError is return.
>As MongoDB [does not support transaction](http://docs.mongodb.org/manual/faq/fundamentals/#does-mongodb-support-transactions) you realy should avoid massives updates on your database.
>

###Workload
Contain a JSON array. First item should containthe selection criteria for the update. Use the same [query selectors](http://docs.mongodb.org/manual/reference/operator/query/#query-selectors) as used in the find() method. Second item should contain the [update parameter](http://docs.mongodb.org/manual/reference/method/db.collection.update/#update-parameter) after this table.

###Path Params
<table>
<tr><td><strong>collection name</strong></td><td>The name of the collection to perform the requested action</td><td>mandatory</td></tr>
</table>

### Header Params
<table>
<tr><td><strong>multiUpdate</strong></td><td>By default, MongoDB will only update 1 object even if multiple objects match the filter query. To instruct MongoDB to update all matching records, set the MultiUpdate header to true.</td><td>optionnal</td></tr>
<tr><td><strong>writeConcern</strong></td><td>NONE, NORMAL, SAFE, JOURNALED, FSYNC_SAFE, REPLICA_SAFE. Specifie the [write concern](http://docs.mongodb.org/manual/core/write-concern/) </td><td>optionnal</td></tr>
</table>

###Examples

#####Update
`curl -X PUT -d "[{name : {\$exists : true}}, {\$set: {update : true}}]" http://127.0.0.1:8667/mrc/test` return `todo`   
If you perform a get `curl -X GET http://127.0.0.1:8667/mrc/test` you will see a update field add to each document.

### Going deeper
A [update](http://docs.mongodb.org/manual/reference/method/db.collection.update/) is perform in database

###TODO
Manage the multi-update flag

DELETE /mrc/{collection name} 
-----------------------------------------------------------------------------
>**Delete item matching a querie in the {collection name}**   
>The result of MongoDB getLastError is return.


###Path Params

<table>
<tr><td><strong>collection name</strong></td><td>The name of the collection to perform the requested action</td><td>mandatory</td></tr>
</table>

### Header Params
<table>
<tr><td><strong>query</strong></td><td>A valid MongoDB query to spot the documents to delete in collection. To drop all items just use exists condition on a _id field (mandatory in MongoDB).</td><td>**mandatory**</td></tr>
<tr><td><strong>writeConcern</strong></td><td>NONE, NORMAL, SAFE, JOURNALED, FSYNC_SAFE, REPLICA_SAFE. Specifie the [write concern](http://docs.mongodb.org/manual/core/write-concern/) </td><td>optionnal</td></tr>
</table>

###Examples

#####Delete a document matching on name filed
 `curl -X DELETE -H "query:{'name' : 'Thomas VOECKLER'}" http://127.0.0.1:8667/mrc/test` should produce `{ "serverUsed" : "/127.0.0.1:27017" , "n" : 1 , "connectionId" : 2 , "err" :  null  , "ok" : 1.0}` in case of a succesful delete

#####Delete all document of a collection
 `curl -X DELETE -H "query:{'_id' : {\$exists : true}}" http://127.0.0.1:8667/mrc/test` should produce `{ "serverUsed" : "/127.0.0.1:27017" , "n" : 1 , "connectionId" : 2 , "err" :  null  , "ok" : 1.0}` in case of a succesful delete

### Going deeper
A [remove](http://docs.mongodb.org/manual/reference/method/db.collection.remove/#db.collection.remove) is perform.

### TODO
Return the number of items deleted


GET /mrc/*{collection name}*/*{id}*
----------------------------------------------------------------------------
>**Get an element by his id**   
>Query the *{collection name}* in mongo, to find the document with the *_id* filed equas to the *{id}* in query parameter.    
>The matching document is return as a JSON string in body workload.


###Path Params

<table>
<tr><td><strong>collection name</strong></td><td>The name of the collection to perform the requested action</td><td>mandatory</td></tr>
<tr><td><strong>id</strong></td><td>Id of object to get</td><td>mandatory</td></tr>
</table>

### Header Params
<table  width="95%">
<tr><td><strong>FieldsFilter</strong></td><td>Perform a [projection as describe in MongoDB documentation](http://docs.mongodb.org/manual/reference/method/db.collection.find/). Usefull when to discard or only show some fileds of a document.</td><td>Optionnal</td></tr>
</table>

###Examples

#####To get the document with _id 1 in collection test
 `curl -X GET http://127.0.0.1:8667/mrc/test/1` should produce `{ "_id" : "1" , "name" : "Sylvain CHAVANEL"}`

### Going deeper
A find by id is performed in database.



 

PUT /mrc/{collection name}/{id}
----------------------------------------------------------------------------
>**Replace a document by his {id}**    in the collection in URL with the body workload of the http request. This workload should contain a valid JSON object.
>Query the *{collection name}* in mongo, to find the document with the *_id* filed equas to the *{id}* in query parameter.   
>Beware that in case of an update, the object is replaced entirely and the usage of MongoDB's $modifiers is not permitted. 
>The matching document is return as a JSON string in body workload.

###Workload
Document content that will replace current document with id given in parameter. Take care _id should match id in url.

###Path Params
<table>
<tr><td><strong>collection name</strong></td><td>The name of the collection to perform the requested action</td><td>mandatory</td></tr>
<tr><td><strong>id</strong></td><td>Id of object to update</td><td>mandatory</td></tr>
</table>

### Header Params
<table>
<tr><td><strong>writeConcern</strong></td><td>NONE, NORMAL, SAFE, JOURNALED, FSYNC_SAFE, REPLICA_SAFE. Specifie the [write concern](http://docs.mongodb.org/manual/core/write-concern/) </td><td>optionnal</td></tr>
</table>


###Examples

#####Update
`curl -X PUT -d "{'_id' : '1', 'name' : 'Thibaut PINOT'}" http://127.0.0.1:8667/mrc/test/1` and then list again the test collection to see an update of your record.

### Going deeper
A [save](http://docs.mongodb.org/manual/reference/method/db.collection.save/) is perform in database


DELETE /mrc/{collection name}/{id} 
---------------------------------------------------------
>**Delete item with the matching {id}**   
>The result of MongoDB getLastError is return.


###Path Params

<table>
<tr><td><strong>collection name</strong></td><td>The name of the collection to perform the requested action</td><td>mandatory</td></tr>
<tr><td><strong>id</strong></td><td>Id of object to delete</td><td>mandatory</td></tr>
</table>

### Header Params
<table>
<tr><td><strong>writeConcern</strong></td><td>NONE, NORMAL, SAFE, JOURNALED, FSYNC_SAFE, REPLICA_SAFE. Specifie the [write concern](http://docs.mongodb.org/manual/core/write-concern/) </td><td>optionnal</td></tr>
</table>

###Examples

#####Succesfull delete case
 `curl -X DELETE http://127.0.0.1:8667/mrc/test/1` should produce `{ "serverUsed" : "/127.0.0.1:27017" , "n" : 1 , "connectionId" : 2 , "err" :  null  , "ok" : 1.0}` in case of a succesful delete

### Going deeper
A [remove](http://docs.mongodb.org/manual/reference/method/db.collection.remove/#db.collection.remove) with the query {_id : '{id}'} is performed.




