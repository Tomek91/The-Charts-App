package com.app.listaprzebojow.repository;


import com.app.listaprzebojow.model.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {

    Optional<Preference> findByUserId_Is(Long userId);
}
