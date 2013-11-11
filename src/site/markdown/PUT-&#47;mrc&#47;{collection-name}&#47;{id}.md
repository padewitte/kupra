>**Replace a document by his {id}**    in the collection in URL with the body workload of the http request. This workload should contain a valid JSON object.
>Query the *{collection name}* in mongo, to find the document with the *_id* filed equas to the *{id}* in query parameter.   
>Beware that in case of an update, the object is replaced entirely and the usage of MongoDB's $modifiers is not permitted. 
>The matching document is return as a JSON string in body workload.

###Workload
Document content that will replace current document with id given in parameter. Take care _id should match id in url.

###Path Parameters
<table>
<tr><td><strong>collection name</strong></td><td>The name of the collection to perform the requested action</td><td>mandatory</td></tr>
<tr><td><strong>id</strong></td><td>Id of object to update</td><td>mandatory</td></tr>
</table>

### Header Parameters
<table>
<tr><td><strong>writeConcern</strong></td><td>NONE, NORMAL, SAFE, JOURNALED, FSYNC_SAFE, REPLICA_SAFE. Specifie the [write concern](http://docs.mongodb.org/manual/core/write-concern/) </td><td>optionnal</td></tr>
</table>


###Examples

#####Update
`curl -X PUT -d "{'_id' : '1', 'name' : 'Thibaut PINOT'}" http://127.0.0.1:8667/mrc/test/1` and then list again the test collection to see an update of your record.

### Going deeper
A [save](http://docs.mongodb.org/manual/reference/method/db.collection.save/) is perform in database