package com.jakes.jakescore.service.impl;



import com.jakes.jakescore.Dao.ToolsDao;
import com.jakes.jakescore.model.ToolsItemModel;
import com.jakes.jakescore.service.ToolsService;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public class DefaultToolsService implements ToolsService {
    private ToolsDao toolsDao;

    @Override
    public List<ToolsItemModel> getAllTools() {
        return toolsDao.findAllTools();
    }
    @Override
    public List<ToolsItemModel> getToolsByYear(int year) {
        return toolsDao.findToolsByYear(year);
    }

    @Transactional
    @Override
    public void saveTool(ToolsItemModel tool) {
        toolsDao.saveTool(tool);
    }



    public void setToolsDao(final ToolsDao toolsDao) {
        this.toolsDao = toolsDao;
    }
}
