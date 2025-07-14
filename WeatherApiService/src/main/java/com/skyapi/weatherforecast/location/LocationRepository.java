package com.skyapi.weatherforecast.location;

import com.skyapi.weatherforecast.common.Location;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, String> {

    @Query("select l from Location l where l.trashed = false")
    List<Location> findUntrashed();

    @Query("select l from Location l where l.trashed = false and l.code = ?1")
    Optional<Location> findLocationByCode(String code);

    @Modifying
    @Query("update Location set trashed = true where code = ?1")
    void trashByCode(String code);
}
