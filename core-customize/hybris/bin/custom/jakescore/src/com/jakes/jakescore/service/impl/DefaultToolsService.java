package com.jakes.jakescore.service.impl;



import com.jakes.jakescore.Dao.ToolsDao;
import com.jakes.jakescore.model.ToolsItemModel;
import com.jakes.jakescore.service.ToolsService;

import java.util.List;

public class DefaultToolsService implements ToolsService {
    private ToolsDao toolsDao;

    @Override
    public List<ToolsItemModel> getAllTools() {
        return toolsDao.findAllTools();
    }


    public void setToolsDao(final ToolsDao toolsDao) {
        this.toolsDao = toolsDao;
    }
}
