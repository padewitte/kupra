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
<tr><td><strong>writeConcern</strong></td><td>NONE, NORMAL, SAFE, JOURNALED, FSYNC_SAFE, REPLICA_SAFE. Specifie the <a href="http://docs.mongodb.org/manual/core/write-concern/">write concern</a> </td><td>optionnal</td></tr>
</table>

###Examples

#####Update
`curl -X PUT -d "[{name : {\$exists : true}}, {\$set: {update : true}}]" http://127.0.0.1:8667/mrc/test` return `todo`   
If you perform a get `curl -X GET http://127.0.0.1:8667/mrc/test` you will see a update field add to each document.

### Going deeper
A [update](http://docs.mongodb.org/manual/reference/method/db.collection.update/) is perform in database