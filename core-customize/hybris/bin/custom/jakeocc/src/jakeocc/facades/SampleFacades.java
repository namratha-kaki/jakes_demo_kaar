/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package jakeocc.facades;

import de.hybris.platform.core.servicelayer.data.SearchPageData;

import java.util.List;

import jakeocc.data.UserData;
import jakeocc.dto.SampleWsDTO;
import jakeocc.dto.TestMapWsDTO;


public interface SampleFacades
{
	SampleWsDTO getSampleWsDTO(final String value);

	UserData getUser(String id);

	List<UserData> getUsers();

	SearchPageData<UserData> getUsers(SearchPageData<?> params);

	void updateUser(String id, UserData user);

	TestMapWsDTO getMap();
}
