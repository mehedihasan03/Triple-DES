package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/3des")
public class EncryptionController {


    private final TripleDesUtils tripleDesUtils;

    public EncryptionController(TripleDesUtils tripleDesUtils) {
        this.tripleDesUtils = tripleDesUtils;
    }

    @GetMapping("/encrypt")
    public String encryptData(@RequestParam("data") String data,
                                            @RequestParam("key1") String key1,
                                            @RequestParam("key2") String key2) throws Exception {
        return tripleDesUtils.encrypt(data, key1, key2);
    }

    @GetMapping("/decrypt")
    public String decryptData(@RequestParam("data") String data,
                              @RequestParam("key1") String key1,
                              @RequestParam("key2") String key2) throws Exception {
        return tripleDesUtils.decrypt(data, key1, key2);
    }
}
