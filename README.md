### Implementation
Simple blockchain implementation where the nodes are hard coded.
There are 4 nodes. In order to launch them you have to
* change the PORT variable in client to 3002,3003 and 3004 for the second, third and forth node
* make sure that in the constructor the other ports are called. For example, if the current node is 3001, make sure that 3002,3003 and 3004 are in the ports List.
* each node has to run on a different server port, so change the port before each run in Application.java. Uncomment and comment each line one by one before each run

### How to use
After running all the four nodes you have a few options
* localhost:clientPort/client will return the name of the client
* localhost:clientPort/client/mine will start the mining process for that client
* localhost:clientPort/block will show the latest block that that client has

clientPort should be replaced with the port of the node that needs to be accessed(8080,8081,8082,8083)

### In the future
Docker containers will be added and a way to run the application in one go will be added.



### sources
https://github.com/Will1229/Blockchain
