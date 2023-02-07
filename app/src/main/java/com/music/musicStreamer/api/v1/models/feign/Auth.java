package com.music.musicStreamer.api.v1.models.feign;

import com.music.musicStreamer.entities.user.UserAuthRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "auth", url = "http://localhost:8081/getToken")
public interface Auth {
    @RequestMapping(method = RequestMethod.POST, value = "", consumes = "*/*")
    String getToken(@RequestBody UserAuthRequest userAuthRequest);
}
