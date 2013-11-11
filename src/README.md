Mongo REST Camel - MRC
======================


A simple REST interface for MongoDB running on a JVM. Download jar, run java -jar mrc.jar and you will be able to access your collection with GET, POST, PUT and DELETE.
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Why ?
-------------------
MongoDB already provide a [simple REST interface](http://docs.mongodb.org/ecosystem/tools/http-interfaces/#simple-rest-interface|docs.mongodb.org).
This interface could be quite sufficient for queries but do not provide any save or update operations. After testing all solutions listed by MongoDB website i did not find a open source solution available to expose all simple MongoDB operations in REST an simple to install. 
After a quick read on Camel MongoDB documentation idea of Mongo Rest Camel was born.


Getting started
-----------------------------

### Installing
Just unzip MRC-0.0.2.zip and launch mrc.sh or mrc.bat. You only need a JRE upper to 1.5 in your classpath.
By default ports 8667 and 9667 are bind. test:test@127.0.0.1:27017/mrc is the MongoDB URI used. More launch option with --help.

### Running first example

Curl come with commons Linux distro's. On windows you can download [curl](http://curl.haxx.se/download.html) or just run JUnit tests.

Type curl -X POST -d "{'_id' : '1', 'name' : 'Sylvain CHAVANEL'}" http://127.0.0.1:8667/mrc/test it will add a record in test collection

Type curl -X POST -d "{'_id' : '2', 'name' : 'Thomas VOECKLER'}" http://127.0.0.1:8667/mrc/test to add a second record.

Type curl -X GET http://127.0.0.1:8667/mrc/test, it will show you the content of test collection

Type curl -X GET http://127.0.0.1:8667/mrc/test/2, it will show you the collection item with id

Type curl -X GET  -H "count:true" http://127.0.0.1:8667/mrc/test, it will count the items in collection

Type curl -X GET -H "query:{'name' : 'Sylvain CHAVANEL'}"  http://127.0.0.1:8667/mrc/test list all items where name equal CHAVANEL

Type curl -X PUT -d "{'_id' : '1', 'name' : 'Thibaut PINOT'}" http://127.0.0.1:8667/mrc/test and then list again the test ollection to see an update of your record.

Type curl -X DELETE -H "query:{name' : 'Thomas VOECKLER'}" http://127.0.0.1:8667/mrc/test to delete


Documentation
=============


GET /mrc/{collection name} List all items in a collection
-----------------------------------------------------------------------------
List all items contains in the collection in url
You can filter by setting the header query with a valid MongoDB query.
The request will return all items in JSON Format 

You can only count items by settings the header count with value true

You can get col stats by settings getColStats header with value true

You can perform a agregate operation on collection by setting aggregate header with a [valid pipeline](http://docs.mongodb.org/manual/core/aggregation-pipeline/)

###Examples :

####To get all items in collection test with name matching Sylvain CHAVANEL.

	curl -X GET -H "query:{'name' : 'Sylvain CHAVANEL'}"  http://127.0.0.1:8667/mrc/test

returns 

	TODO Example
	
####To count all items in collection test


	curl -X GET  -H "count:true" http://127.0.0.1:8667/mrc/test 

returns

	TODO

TODO :
Make a paginate queryPOST /mrc/{collection name} Add a document in a collection
----------------------------------------------------------------------------------------------
Add to collection in URL the object pass in body worload
Perform a [insert]() to the database.

Retrun the status the JSON object returned by getLastError MongoDB command. 

Examples :
Type curl -X POST -d "{'_id' : '1', 'name' : 'Sylvain CHAVANEL'}" http://127.0.0.1:8667/mrc/test it will add a record in test collection
Mongo REST Camel - MRC
======================


A simple REST interface for MongoDB running on a JVM. Download jar, run java -jar mrc.jar and you will be able to access your collection with GET, POST, PUT and DELETE.
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Why ?
-------------------
MongoDB already provide a [simple REST interface](http://docs.mongodb.org/ecosystem/tools/http-interfaces/#simple-rest-interface|docs.mongodb.org).
This interface could be quite sufficient for queries but do not provide any save or update operations. After testing all solutions listed by MongoDB website i did not find a open source solution available to expose all simple MongoDB operations in REST an simple to install. 
After a quick read on Camel MongoDB documentation idea of Mongo Rest Camel was born.


Getting started
-----------------------------

### Installing
Just unzip MRC-0.0.2.zip and launch mrc.sh or mrc.bat. You only need a JRE upper to 1.5 in your classpath.
By default ports 8667 and 9667 are bind. test:test@127.0.0.1:27017/mrc is the MongoDB URI used. More launch option with --help.

### Running first example

Curl come with commons Linux distro's. On windows you can download [curl](http://curl.haxx.se/download.html) or just run JUnit tests.

Type curl -X POST -d "{'_id' : '1', 'name' : 'Sylvain CHAVANEL'}" http://127.0.0.1:8667/mrc/test it will add a record in test collection

Type curl -X POST -d "{'_id' : '2', 'name' : 'Thomas VOECKLER'}" http://127.0.0.1:8667/mrc/test to add a second record.

Type curl -X GET http://127.0.0.1:8667/mrc/test, it will show you the content of test collection

Type curl -X GET http://127.0.0.1:8667/mrc/test/2, it will show you the collection item with id

Type curl -X GET  -H "count:true" http://127.0.0.1:8667/mrc/test, it will count the items in collection

Type curl -X GET -H "query:{'name' : 'Sylvain CHAVANEL'}"  http://127.0.0.1:8667/mrc/test list all items where name equal CHAVANEL

Type curl -X PUT -d "{'_id' : '1', 'name' : 'Thibaut PINOT'}" http://127.0.0.1:8667/mrc/test and then list again the test ollection to see an update of your record.

Type curl -X DELETE -H "query:{name' : 'Thomas VOECKLER'}" http://127.0.0.1:8667/mrc/test to delete


Documentation
=============

GET /mrc/<collection name> List all items in a collection
-----------------------------------------------------------------------------
LINK


POST /mrc/<collection name> Add a document in a collection
-----------------------------------------------------------------------------
LINK

DELETE /mrc/<collection name> Delete items in collection
-----------------------------------------------------------------------------
LINK


GET /mrc/<collection name>/<id> Get an element by his id
-----------------------------------------------------------------------------
LINK


PUT /mrc/<collection name>/<id> Replace a document by his id
-----------------------------------------------------------------------------
LINK


DELETE /mrc/<collection name>/<id> Delete a document by his id
-----------------------------------------------------------------------------
LINK

DELETE /mrc/{collection name} Delete items in collection
-----------------------------------------------------------------------------
Warning :  The delete should always specifie a match criteria with the query header like the GET command
TO drop all items just use exists condition on a common field.

A remove is performed in database.
GET /mrc/{collection name}/{id} Get an element by his id
-----------------------------------------------------------------------------
Return the item with _id value equals to {id} in the collection given in parameter.
Return the JSON object with the matching _id.

A find by id is performed in database.
PUT /mrc/{collection name}/{id} Replace a document by his id
-----------------------------------------------------------------------------
Replace the document with {id} in the collection in URL with the body workload of the http request. This workload should contain a valid JSON object.
A save is perform in database.DELETE /mrc/{collection name}/{id} Delete a document by his id
-----------------------------------------------------------------------------
Delete item with the matching {id} in the collaction given in URL.
A [remove](http://docs.mongodb.org/manual/reference/method/db.collection.remove/#db.collection.remove) with the query {_id : '{id}'} is performed.
