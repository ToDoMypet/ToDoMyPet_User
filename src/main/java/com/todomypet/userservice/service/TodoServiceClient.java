package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.FeignClientResDTO;
import com.todomypet.userservice.dto.openFeign.AddCategoryReqDTO;
import com.todomypet.userservice.dto.openFeign.AddCategoryResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="todo-service", url="${feign.todo.url}")
public interface TodoServiceClient {
    @PostMapping(value = "/category", consumes = "application/json")
    FeignClientResDTO<AddCategoryResDTO> addCategory(@RequestHeader String userId,
                                                     @RequestBody AddCategoryReqDTO addCategoryReqDTO);
}
