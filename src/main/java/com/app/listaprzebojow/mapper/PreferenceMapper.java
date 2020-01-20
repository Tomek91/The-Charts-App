package com.app.listaprzebojow.mapper;

import com.app.listaprzebojow.dto.PreferenceDTO;
import com.app.listaprzebojow.model.Preference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PreferenceMapper {
    @Mappings({
            @Mapping(source = "user", target = "userDTO")
    })
    PreferenceDTO preferenceToPreferenceDTO(Preference preference);

    @Mappings({
            @Mapping(source = "userDTO", target = "user")
    })
    Preference preferenceDTOToPreference(PreferenceDTO preferenceDTO);
}

