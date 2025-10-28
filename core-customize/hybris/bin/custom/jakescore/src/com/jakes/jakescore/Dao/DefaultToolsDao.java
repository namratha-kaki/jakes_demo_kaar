package com.jakes.jakescore.Dao;

import com.jakes.jakescore.model.ToolsItemModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public class DefaultToolsDao implements ToolsDao {
    private FlexibleSearchService flexibleSearchService;
    private ModelService modelService;

    @Override
    public List<ToolsItemModel> findAllTools() {
        final String query = "SELECT {PK} FROM {ToolsItem}";
        return flexibleSearchService.<ToolsItemModel>search(new FlexibleSearchQuery(query)).getResult();
    }

    @Override
    public List<ToolsItemModel> findToolsByYear(int year) {
        final String query = "SELECT {PK} FROM {ToolsItem} WHERE YEAR({releaseDate}) = ?year";
        final FlexibleSearchQuery fsq = new FlexibleSearchQuery(query);
        fsq.addQueryParameter("year", year);
        return flexibleSearchService.<ToolsItemModel>search(fsq).getResult();
    }


    @Override
    public void saveTool(ToolsItemModel tool) {
        modelService.save(tool);
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
}