package com.jakes.jakesfacades.Facades.impl;

import com.jakes.jakescore.model.ToolsItemModel;
import com.jakes.jakescore.service.ToolsService;
import com.jakes.jakesfacades.Facades.ToolsFacade;
import com.jakes.jakesfacades.Data.ToolData;
import de.hybris.platform.webservicescommons.mapping.DataMapper;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultToolsFacade implements ToolsFacade {
    private ToolsService toolsService;
    private DataMapper dataMapper;

    public DefaultToolsFacade() {}

    @Override
    public List<ToolData> getAllTools() {
        List<ToolsItemModel> tools = toolsService.getAllTools();
        return tools.stream()
                .map(tool -> dataMapper.map(tool, ToolData.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ToolData> getToolsByYear(int year) {
        List<ToolsItemModel> tools = toolsService.getToolsByYear(year);
        return tools.stream()
                .map(tool -> dataMapper.map(tool, ToolData.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public void saveTool(ToolData toolData) {
//        ToolsItemModel existingTool = toolsService.getToolByCode(toolData.getCode());
//
//        if (existingTool != null) {
//            existingTool.setName(toolData.getName());
//            existingTool.setDescription(toolData.getDescription());
//            existingTool.setReleaseDate(toolData.getReleaseDate());
//            toolsService.saveTool(existingTool);
//        } else {
//            ToolsItemModel newTool = new ToolsItemModel();
//            newTool.setCode(toolData.getCode());
//            newTool.setName(toolData.getName());
//            newTool.setDescription(toolData.getDescription());
//            newTool.setReleaseDate(toolData.getReleaseDate());
//            toolsService.saveTool(newTool);
//        }
//    }

    @Override
    public void saveTool(ToolData toolData) {
        ToolsItemModel existingTool = toolsService.getToolByCode(toolData.getCode());

        if (existingTool != null) {
            dataMapper.map(toolData, existingTool);
            toolsService.saveTool(existingTool);
        } else {
            ToolsItemModel newTool = dataMapper.map(toolData, ToolsItemModel.class);
            toolsService.saveTool(newTool);
        }
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public void setDataMapper(DataMapper dataMapper) {
        this.dataMapper = dataMapper;
    }
}
