package com.quick.client;

import com.quick.api.InnerApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "quick-clinical")
public interface InnerClient extends InnerApi {

}
