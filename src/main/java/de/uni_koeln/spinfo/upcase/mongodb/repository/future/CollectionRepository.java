package de.uni_koeln.spinfo.upcase.mongodb.repository.future;

import org.springframework.data.repository.Repository;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Collection;

public interface CollectionRepository extends Repository<Collection, String> {

	public Collection save(Collection collection);

	public long count();

	public Collection findbyId(String id);

	public Collection findbyTitle(String title);

	public Collection update(Collection c);

}
