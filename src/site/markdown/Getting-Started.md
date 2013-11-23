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

**Show the item the collection item with id 2**   
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


