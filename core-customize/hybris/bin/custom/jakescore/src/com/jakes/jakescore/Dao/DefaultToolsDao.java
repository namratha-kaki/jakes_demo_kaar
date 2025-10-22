package com.jakes.jakescore.Dao;

import com.jakes.jakescore.model.ToolsItemModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;


import java.util.List;

public class DefaultToolsDao implements ToolsDao {
    private FlexibleSearchService flexibleSearchService;

    @Override
    public List<ToolsItemModel> findAllTools() {
        final String query = "SELECT {PK} FROM {ToolsItem}";
        return flexibleSearchService.<ToolsItemModel>search(new FlexibleSearchQuery(query)).getResult();
    }

    public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
}