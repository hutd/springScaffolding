package com.quick.client;

import com.quick.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value="quick-user")
public interface UserClient extends UserApi {
}
