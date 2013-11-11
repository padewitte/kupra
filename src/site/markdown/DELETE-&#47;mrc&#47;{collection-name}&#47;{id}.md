
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




