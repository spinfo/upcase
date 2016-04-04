package de.uni_koeln.spinfo.upcase.mongodb.repository;

import org.springframework.data.repository.CrudRepository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.Volume;

public interface VolumeRepository extends CrudRepository<Volume, String> {

	public Volume findByTitle(String title);
	
}
