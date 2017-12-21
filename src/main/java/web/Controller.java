package web;


import blockchain.Block;
import blockchain.Client;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "/client")
public class Controller {

    private static Client client = new Client();

    @RequestMapping(method = GET)
    public String getClient() {
        return client.getName();
    }

    @RequestMapping(method = GET, path = "mine")
    public void createBlock() {
        client.createBlock();
    }

    @RequestMapping(method = GET, path = "block")
    public String getBlock() {
        return client.getLatestBlock().getHash();
    }

/*
    @RequestMapping(method = DELETE)


    @RequestMapping(method = POST, params = {"name", "port"})


    @RequestMapping(path = "all", method = GET)


    @RequestMapping(method = POST, path = "mine")*/

}
