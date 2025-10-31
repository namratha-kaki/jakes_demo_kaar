package com.jakes.jakescore.service;

import com.jakes.jakescore.model.ToolsItemModel;

import java.util.List;

public interface ToolsService {
    List<ToolsItemModel> getAllTools();
    List<ToolsItemModel> getToolsByYear(int year);
    void saveTool(ToolsItemModel tool);
    ToolsItemModel getToolByCode(String code);

}
