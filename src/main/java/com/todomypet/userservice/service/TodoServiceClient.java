package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.FeignClientResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="todo-service", url="${feign.todo.url}")
public interface TodoServiceClient {
    @DeleteMapping(value = "/category/delete-all-category", consumes = "application/json")
    FeignClientResDTO<Void> deleteAllCategoryByUserId(@RequestHeader String userId);

    @DeleteMapping(value = "/todo/delete-all-todo", consumes = "application/json")
    FeignClientResDTO<Void> deleteAllTodoByUserId(@RequestHeader String userId);
}
