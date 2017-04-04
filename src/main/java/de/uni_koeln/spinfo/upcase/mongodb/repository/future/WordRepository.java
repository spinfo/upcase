package de.uni_koeln.spinfo.upcase.mongodb.repository.future;

import org.springframework.data.repository.CrudRepository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Word;

public interface WordRepository extends CrudRepository<Word, String> {

}
