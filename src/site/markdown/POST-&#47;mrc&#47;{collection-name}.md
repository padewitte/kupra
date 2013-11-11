>**Add to *collection name* in URL the JSON object in body worload**   
>Return the status JSON object returned by getLastError MongoDB command. 

###Request Workload
Contain the document to add in collection

###Path Parameters

<table>
<tr><td><strong>collection name</strong></td><td>The name of the collection to perform the requested action</td><td>mandatory</td></tr>
</table>

### Header Parameters
<table>
<tr><td><strong>writeConcern</strong></td><td>NONE, NORMAL, SAFE, JOURNALED, FSYNC_SAFE, REPLICA_SAFE. Specifie the [write concern](http://docs.mongodb.org/manual/core/write-concern/) </td><td>optional</td></tr>
</table>

###Examples

#####Add a record in test collection
 `curl -X POST -d "{'_id' : '1', 'name' : 'Sylvain CHAVANEL'}" http://127.0.0.1:8667/mrc/test` should produce `TODO`

### Going deeper
Perform a [insert](http://docs.mongodb.org/manual/reference/method/db.collection.insert/) to the database.
