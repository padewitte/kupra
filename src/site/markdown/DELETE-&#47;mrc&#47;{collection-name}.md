>**Delete item matching a query in the {collection name}**   
>The result of MongoDB getLastError is return.


###Path Parameters

<table>
<tr><td><strong>collection name</strong></td><td>The name of the collection to perform the requested action</td><td>mandatory</td></tr>
</table>

### Header Parameters
<table>
<tr><td><strong>query</strong></td><td>A valid MongoDB query to spot the documents to delete in collection. To drop all items just use exists condition on a _id field (mandatory in MongoDB).</td><td>**mandatory**</td></tr>
<tr><td><strong>writeConcern</strong></td><td>NONE, NORMAL, SAFE, JOURNALED, FSYNC_SAFE, REPLICA_SAFE. Specifies the [write concern](http://docs.mongodb.org/manual/core/write-concern/) </td><td>optional</td></tr>
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