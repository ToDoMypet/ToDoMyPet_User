package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.FeignClientResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="todo-service", url="${feign.todo.url}")
public interface TodoServiceClient {
    @DeleteMapping(value = "/todo/delete-all-category-and-todo", consumes = "application/json")
    FeignClientResDTO<Void> deleteAllCategoryAndTodoByUserId(@RequestHeader String userId);
}

