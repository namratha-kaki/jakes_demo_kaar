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


    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public void setDataMapper(DataMapper dataMapper) {
        this.dataMapper = dataMapper;
    }

    @Override
    public List<ToolData> getAllTools() {
        List<ToolsItemModel> tools = toolsService.getAllTools();
        return tools.stream()
                .map(tool -> dataMapper.map(tool, ToolData.class))
                .collect(Collectors.toList());
    }
}
