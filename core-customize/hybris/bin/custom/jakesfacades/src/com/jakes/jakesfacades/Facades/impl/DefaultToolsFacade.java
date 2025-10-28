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

    @Override
    public void saveTool(ToolData toolData) {

//        ToolsItemModel model = dataMapper.map(toolData, ToolsItemModel.class);
//        toolsService.saveTool(model);
        ToolsItemModel model = new ToolsItemModel();
        model.setCode(toolData.getCode());
        model.setName(toolData.getName());
        model.setDescription(toolData.getDescription());
        model.setReleaseDate(toolData.getReleaseDate());
        toolsService.saveTool(model);
    }

//    @Override
//    public List<ToolData> getToolsByYear(int year) {
//        return toolsService.getToolsByYear(year).stream()
//                .map(toolConverter::convert)
//                .collect(Collectors.toList());
//    }
//
//    public void setToolReverseConverter(ToolReverseConverter toolReverseConverter) {
//        this.toolReverseConverter = toolReverseConverter;
//    }

//    @Override
//    public void saveTool(ToolData toolData) {
////        ToolsItemModel model = new ToolsItemModel();
////        model.setCode(toolData.getCode());
////        model.setReleaseDate(toolData.getReleaseDate());
////        model.setName(toolData.getName());
////        model.setDescription(toolData.getDescription());
//        ToolsItemModel model = toolReverseConverter.convert(toolData);
//        toolsService.saveTool(model);
//    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public void setDataMapper(DataMapper dataMapper) {
        this.dataMapper = dataMapper;
    }


}
