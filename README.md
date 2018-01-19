### Implementation
There are 4 nodes in the blockchain.
In order to run them, you need a Linux machine with Docker installed on it.
Run the script from /images/script.sh
The script will build the 4 images, create a network, run the containers, add them to the network and then run a couple of tests on them.
The script should look like: 
![](https://raw.githubusercontent.com/ovi28/blockchain/master/ss1.png)
![](https://raw.githubusercontent.com/ovi28/blockchain/master/ss2.png)
![alt text](https://raw.githubusercontent.com/ovi28/blockchain/master/ss3.png)

The nodes will be on port 8080, 8081, 8082, 8083.
The client on node 8081 has a slightly slower mining algorithm.

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
https://www.youtube.com/watch?v=Ojs9I-gnkc8&feature=youtu.be
https://stackoverflow.com/questions/27767264/how-to-dockerize-maven-project-and-how-many-ways-to-accomplish-it
