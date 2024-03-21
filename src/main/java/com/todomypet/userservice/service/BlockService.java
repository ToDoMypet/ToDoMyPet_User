package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.block.GetBlockListDTO;

public interface BlockService {
    void setBlockRelationship(String userId, String targetId);
    void deleteBlockRelationship(String userId, String targetId);
    GetBlockListDTO getBlockListByUserId(String userId);
}
