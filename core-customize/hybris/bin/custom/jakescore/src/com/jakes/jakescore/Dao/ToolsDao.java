package com.jakes.jakescore.Dao;

import java.util.Date;
import java.util.List;
import com.jakes.jakescore.model.ToolsItemModel;

public interface ToolsDao {
    List<ToolsItemModel> findAllTools();
    List<ToolsItemModel> findToolsByYear(int year);
    void saveTool(ToolsItemModel tool);
    ToolsItemModel findToolByCode(String code);
    void removeTool(ToolsItemModel tool);
}
