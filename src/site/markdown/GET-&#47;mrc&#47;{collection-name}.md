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