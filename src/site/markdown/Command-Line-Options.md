
Depending on your system mrc.ksh or mrc.bat is the launcher of Mongo REST Camel.

***
 
###Usage
**mrc.ksh [options] mongoDbUri**    

Format of *mongoDbUri* is : [mongodb://][username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]    
Read [MongoDB uri documentation](http://docs.mongodb.org/manual/reference/connection-string/) for full documentation and possibilities.

For a quick test start mongod with no option and then launch :   
**mrc.ksh -doc 127.0.0.0:27017/mrc**    
Open http://127.0.0.1:8669/ with a modern browser and enjoy your first queries.
    

***

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
