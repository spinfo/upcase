package de.uni_koeln.spinfo.upcase.mongodb.repository.future;

import org.springframework.data.repository.CrudRepository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.WordVersion;

public interface WordVersionRepository extends CrudRepository<WordVersion, String> {

}
