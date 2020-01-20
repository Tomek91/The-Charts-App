package com.app.listaprzebojow.repository;


import com.app.listaprzebojow.model.Playlist;
import com.app.listaprzebojow.model.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {


}
