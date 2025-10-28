package com.jakes.jakesfacades.Facades;



import com.jakes.jakesfacades.Data.ToolData;

import java.util.List;

public interface ToolsFacade {
    List<ToolData> getAllTools();
    List<ToolData> getToolsByYear(int year);
    void saveTool(ToolData toolData);
}

