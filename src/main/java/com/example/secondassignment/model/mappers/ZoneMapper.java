package com.example.secondassignment.model.mappers;

import com.example.secondassignment.model.DTO.ZoneDTO;
import com.example.secondassignment.model.Zone;

/**
 * Singleton class used for mapping a Zone to a ZoneDTO and vice-versa.
 */
public class ZoneMapper implements Mapper<Zone, ZoneDTO> {

    /**
     * Singleton instance of a ZoneMapper class.
     */
    private static ZoneMapper zoneMapper = null;

    /**
     * Constructor.
     */
    private ZoneMapper() {

    }

    /**
     * Retrieves the single instance of the ZoneMapper class.
     * @return the instance of the ZoneMapper class.
     */
    public static ZoneMapper getInstance() {
        if (zoneMapper == null) {
            zoneMapper = new ZoneMapper();
        }
        return zoneMapper;
    }

    @Override
    public Zone convertFromDTO(ZoneDTO zoneDTO) {
        return null;
    }

    @Override
    public ZoneDTO convertToDTO(Zone zone) {
        return ZoneDTO.builder()
                .name(zone.getName())
                .build();
    }
}
