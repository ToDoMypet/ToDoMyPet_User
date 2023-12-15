package com.todomypet.userservice.service;


import com.todomypet.userservice.dto.background.AddBackgroundReqDTO;
import com.todomypet.userservice.dto.background.BackgroundResDTO;

import java.util.List;

public interface BackgroundService {
    void addBackground(AddBackgroundReqDTO addBackgroundReqDTO);
    String getBackgroundUrlById(String backgroundId);

    List<BackgroundResDTO> getBackgroundList();

    void changeBackground(String userId, String backgroundId);
}
